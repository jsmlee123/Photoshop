package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class to represent an image in JPEG format and related methods to modify JPEG image.
 */
public class JPEGImage extends AbstractImageEnhanced {

  /**
   * Constructs abstract Image.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   maxValue of color allowed for format
   * @param pixelArray array of pixels representing the image
   * @throws IllegalArgumentException if pixelArray is invalid
   */
  public JPEGImage(int width, int height, int maxColor, IPixel[][] pixelArray)
      throws IllegalArgumentException {
    super(width, height, maxColor, pixelArray);
  }

  @Override
  public byte[] getBytes() {
    BufferedImage image = getBufferedImage();
    ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpeg", bytesOutput);
    } catch (IOException e) {
      throw new IllegalStateException("Could not create JPEG");
    }
    byte[] bytes = bytesOutput.toByteArray();
    return bytes;
  }

  @Override
  protected IImageConverter fromImage(int width, int height, int maxColor, IPixel[][] pixelArray) {
    return new JPEGImage(width, height, maxColor, pixelArray);
  }
}
