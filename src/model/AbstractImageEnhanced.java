package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Enhanced version of AbstractImage class. Includes support for swapping between different image
 * formats, method to compare dimensions of images, method to create deep copy, and method to export
 * as PNG, JPG formats.
 */
public abstract class AbstractImageEnhanced extends AbstractImage implements IImageConverter {

  /**
   * Delegate constructing Image to super class.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   max int color value of image
   * @param pixelArray array of pixels for the image
   */

  public AbstractImageEnhanced(int width, int height, int maxColor, IPixel[][] pixelArray) {
    super(width, height, maxColor, pixelArray);
  }


  @Override
  public IImageConverter toPPM() {
    return new PPMImageEnhanced(width, height, maxColor, pixelArray);
  }

  @Override
  public IImageConverter toJPEG() {
    return new JPEGImage(width, height, maxColor, pixelArray);
  }

  @Override
  public IImageConverter toPNG() {
    return new PNGImage(width, height, maxColor, pixelArray);
  }

  @Override
  public abstract byte[] getBytes();

  @Override
  public boolean compareDimensions(IImageConverter that) {
    if (!(that instanceof AbstractImageEnhanced)) {
      return false;
    }
    AbstractImageEnhanced thatImage = (AbstractImageEnhanced) that;
    return this.height == thatImage.height && this.width == thatImage.width;
  }

  @Override
  public IImageConverter copy() {

    IPixel[][] copyArray = new IPixel[height][width];
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        copyArray[h][w] = this.pixelArray[h][w];
      }
    }

    return fromImage(width, height, maxColor, copyArray);
  }

  /**
   * Factory method for creating Images.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   maxColor int value of image
   * @param pixelArray array of pixels representing the image
   * @return instance of this Image with specified fields
   */
  protected abstract IImageConverter fromImage(int width, int height, int maxColor,
      IPixel[][] pixelArray);

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < pixelArray.length; y += 1) {
      for (int x = 0; x < pixelArray[0].length; x += 1) {
        IPixel pixel = pixelArray[y][x];
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        image.setRGB(x, y, color.getRGB());
      }
    }
    return image;
  }
}
