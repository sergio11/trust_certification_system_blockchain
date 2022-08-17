package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.service.ICertificateSigningService;
import com.dreamsoftware.tcs.utils.TimeStampManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Calendar;
import java.util.Optional;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.TSPException;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.io.IOUtils;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.apache.logging.log4j.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("certificateSigningService")
@RequiredArgsConstructor
public class CertificateSigningServiceImpl implements ICertificateSigningService {

    private static final String KEY_STORE_TYPE = "jks";
    private String keyStorePath;
    private String keyStorePassword;
    private String certificateAlias;
    private String tsaUrl;

    /**
     *
     * @param certificateToSign
     * @return
     */
    @Override
    public byte[] sign(byte[] certificateToSign) {
        try {
            KeyStore keyStore = getKeyStore();
            Signature signature = new Signature(keyStore, keyStorePassword.toCharArray(), certificateAlias, tsaUrl);
            File certificateFile = File.createTempFile("pdf", "");
            FileUtils.writeByteArrayToFile(certificateFile, certificateToSign);
            File signedFile = File.createTempFile("signedCertificate", "");
            signDetached(signature, certificateFile, signedFile);
            byte[] signedPdfBytes = Files.readAllBytes(signedFile.toPath());
            //remove temporary files
            certificateFile.deleteOnExit();
            signedFile.deleteOnExit();
            return signedPdfBytes;
        } catch (NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException e) {
            log.error("Cannot obtain proper KeyStore or Certificate", e);
        } catch (IOException e) {
            log.error("Cannot obtain proper file", e);
        }
        return certificateToSign;
    }

    /**
     *
     * @param certificateFileToSign
     * @return
     * @throws java.io.IOException
     */
    @Override
    public byte[] sign(final File certificateFileToSign) throws IOException {
        Assert.notNull(certificateFileToSign, "Certificate File to sign can not be null");
        return sign(Files.readAllBytes(certificateFileToSign.toPath()));
    }

    /**
     *
     * @param signature
     * @param inFile
     * @param outFile
     * @throws IOException
     */
    private void signDetached(final SignatureInterface signature, final File inFile, final File outFile) throws IOException {
        if (inFile == null || !inFile.exists()) {
            throw new FileNotFoundException("Document for signing does not exist");
        }

        try (FileOutputStream fos = new FileOutputStream(outFile); PDDocument doc = PDDocument.load(inFile)) {
            signDetached(signature, doc, fos);
        }
    }

    /**
     *
     * @param signature
     * @param document
     * @param output
     * @throws IOException
     */
    private void signDetached(final SignatureInterface signature, final PDDocument document, final OutputStream output) throws IOException {
        PDSignature pdSignature = new PDSignature();
        pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        pdSignature.setName("");
        pdSignature.setReason("");
        pdSignature.setContactInfo("");
        pdSignature.setSignDate(Calendar.getInstance());
        document.addSignature(pdSignature, signature);
        document.saveIncremental(output);
    }

    /**
     *
     * @return @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    private KeyStore getKeyStore() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        File key = ResourceUtils.getFile(keyStorePath);
        keyStore.load(new FileInputStream(key), keyStorePassword.toCharArray());
        return keyStore;
    }

    private class Signature implements SignatureInterface {

        private PrivateKey privateKey;
        private Certificate[] certificateChain;
        private String tsaUrl;

        /**
         *
         * @param keyStore
         * @param keyStorePassword
         * @param appCertificateAlias
         * @param tsaUrl
         * @throws KeyStoreException
         * @throws UnrecoverableKeyException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws CertificateNotYetValidException
         * @throws CertificateExpiredException
         */
        public Signature(final KeyStore keyStore, final char[] keyStorePassword, final String appCertificateAlias, final String tsaUrl) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateNotYetValidException, CertificateExpiredException {
            this.certificateChain = Optional.ofNullable(keyStore.getCertificateChain(appCertificateAlias))
                    .orElseThrow(() -> (new IOException("Could not find a proper certificate chain")));
            this.privateKey = (PrivateKey) keyStore.getKey(appCertificateAlias, keyStorePassword);
            Certificate certificate = this.certificateChain[0];
            if (certificate instanceof X509Certificate) {
                ((X509Certificate) certificate).checkValidity();
            }
            this.tsaUrl = tsaUrl;
        }

        @Override
        public byte[] sign(InputStream content) throws IOException {
            try {
                CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
                X509Certificate cert = (X509Certificate) this.certificateChain[0];
                ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").build(this.privateKey);
                gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(sha1Signer, cert));
                gen.addCertificates(new JcaCertStore(Arrays.asList(this.certificateChain)));
                CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
                CMSSignedData signedData = gen.generate(msg, false);

                //add timestamp if TSA is available
                if (Strings.isNotBlank(this.tsaUrl)) {
                    TimeStampManager timeStampManager = new TimeStampManager(this.tsaUrl);
                    signedData = timeStampManager.addSignedTimeStamp(signedData);
                }

                return signedData.getEncoded();
            } catch (GeneralSecurityException | CMSException | OperatorCreationException | TSPException e) {
                //throw new IOException cause a SignatureInterface, but keep the stacktrace
                throw new IOException(e);
            }
        }

    }

    /**
     * CMS Processable Input Stream
     */
    private class CMSProcessableInputStream implements CMSTypedData {

        private final InputStream inputStream;
        private final ASN1ObjectIdentifier contentType;

        public CMSProcessableInputStream(InputStream is) {
            this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), is);
        }

        private CMSProcessableInputStream(ASN1ObjectIdentifier type, InputStream is) {
            contentType = type;
            inputStream = is;
        }

        @Override
        public Object getContent() {
            return inputStream;
        }

        @Override
        public void write(OutputStream out) throws IOException {
            // read the content only one time
            IOUtils.copy(inputStream, out);
            inputStream.close();
        }

        @Override
        public ASN1ObjectIdentifier getContentType() {
            return contentType;
        }
    }
}
