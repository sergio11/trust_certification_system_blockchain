package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.properties.GoogleApiProperties;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.oauth2.Oauth2;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.File;
import java.io.FileInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
public class GoogleAPIConfig {

    private final GoogleApiProperties googleApiProperties;

    /**
     * Provide Json Factory
     *
     * @return
     */
    @Bean
    public JsonFactory provideJsonFactory() {
        return new JacksonFactory();
    }

    /**
     * Provide HTTP Transport
     *
     * @return
     */
    @Bean
    public HttpTransport provideHttpTransport() {
        return new NetHttpTransport();
    }

    /**
     * Provide Input Stream Reader
     *
     * @return
     * @throws IOException
     */
    @Bean
    public InputStreamReader provideInputStreamReader() throws IOException {
        return new InputStreamReader(new FileInputStream(new File(googleApiProperties.getClientSecretPath())), "UTF-8");
    }

    /**
     * Provide Google Client Secrets
     *
     * @return
     * @throws IOException
     */
    @Bean
    public GoogleClientSecrets provideGoogleClientSecrets() throws IOException {
        return GoogleClientSecrets.load(provideJsonFactory(), provideInputStreamReader());
    }

    /**
     * Provide Google Credentials
     *
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(final String accessToken, final String refreshToken) throws IOException {

        GoogleCredential credential = new GoogleCredential.Builder()
                .setJsonFactory(provideJsonFactory())
                .setTransport(provideHttpTransport())
                .setClientSecrets(provideGoogleClientSecrets())
                .build();

        credential.setAccessToken(accessToken);

        if (refreshToken != null) {
            credential.setRefreshToken(refreshToken);
        }

        return credential;
    }

    /**
     * Provide Google Credentials
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(final String accessToken) throws IOException {
        return new GoogleCredential.Builder()
                .setJsonFactory(provideJsonFactory())
                .setTransport(provideHttpTransport())
                .setClientSecrets(provideGoogleClientSecrets())
                .build()
                .setAccessToken(accessToken);
    }

    /**
     * Provide OAuth 2
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Oauth2 provideOauth2(final String accessToken) throws IOException {
        return new Oauth2.Builder(provideHttpTransport(), provideJsonFactory(), provideGoogleCredential(accessToken))
                .setApplicationName(googleApiProperties.getApplicationName()).build();
    }
}
