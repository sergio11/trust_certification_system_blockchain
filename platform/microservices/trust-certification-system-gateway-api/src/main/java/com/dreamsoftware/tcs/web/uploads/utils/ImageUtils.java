package com.dreamsoftware.tcs.web.uploads.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author ssanchez
 */
public class ImageUtils {

    /**
     * Resize Image from file
     *
     * @param imageFile
     * @param width
     * @param height
     * @param withHints
     * @throws IOException
     */
    public static void resizeImageFile(final File imageFile, final int width, final int height, boolean withHints) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        final BufferedImage resizeImage = withHints ? resizeImageWithHint(originalImage, width, height, type)
                : resizeImage(originalImage, width, height, type);
        final String format = FilenameUtils.getExtension(imageFile.getName());
        ImageIO.write(resizeImage, format, imageFile);
    }

    /**
     *
     * @param originalImage
     * @param type
     * @return
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, final int width, final int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     *
     * @param originalImage
     * @param type
     * @return
     */
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, final int width, final int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

}
