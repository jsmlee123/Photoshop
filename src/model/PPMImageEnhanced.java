package model;

/**
 * Enhanced version of PPMImage class. Includes all methods of PPMImage
 * as well as IImageConverter methods.
 */
public class PPMImageEnhanced extends AbstractImageEnhanced {
  private final ImageEditor delegator;

  /**
   * Constructs abstract Image. Uses delegation when possible.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   maxValue of color allowed for format
   * @param pixelArray array of pixels representing the image
   * @throws IllegalArgumentException if pixelArray is invalid
   */
  public PPMImageEnhanced(int width, int height, int maxColor, IPixel[][] pixelArray)
      throws IllegalArgumentException {
    super(width, height, maxColor, pixelArray);
    this.delegator = new PPMImage(width, height, maxColor, pixelArray);
  }


  @Override
  public String toString() {
    return delegator.toString();
  }

  @Override
  public byte[] getBytes() {
    return toString().getBytes();
  }

  @Override
  protected IImageConverter fromImage(int width, int height, int maxColor, IPixel[][] pixelArray) {
    return new PPMImageEnhanced(width, height, maxColor, pixelArray);
  }
}
