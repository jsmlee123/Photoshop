package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class to represent a Image in PNG format and related methods to modify PNG image.
 */
public class PNGImage extends AbstractImageEnhanced {

  /**
   * Constructs abstract Image.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   maxValue of color allowed for format
   * @param pixelArray array of pixels representing the image
   * @throws IllegalArgumentException if pixelArray is invalid
   */
  public PNGImage(int width, int height, int maxColor, IPixel[][] pixelArray)
      throws IllegalArgumentException {
    super(width, height, maxColor, pixelArray);
  }

  @Override
  public byte[] getBytes() {
    BufferedImage image = getBufferedImage();
    ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", bytesOutput);
    } catch (IOException e) {
      throw new IllegalStateException("Could not create PNG");
    }
    byte[] bytes = bytesOutput.toByteArray();
    return bytes;
  }

  @Override
  protected IImageConverter fromImage(int width, int height, int maxColor, IPixel[][] pixelArray) {
    return new PNGImage(width, height, maxColor, pixelArray);
  }
}
