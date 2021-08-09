package model;

/**
 * Represents a PPM Image with width, height, maxColor, and array of pixels.
 */
public class PPMImage extends AbstractImage {

  /**
   * Constructs a PPM Image.
   *
   * @param width      width of ppm image
   * @param height     height of ppm image
   * @param maxColor   max int color value of this ppm file
   * @param pixelArray array of colors of ppm image stored as custom pixel
   */
  public PPMImage(int width, int height, int maxColor, IPixel[][] pixelArray) {
    super(width, height, maxColor, pixelArray);
  }

  /**
   * Returns this ppm Image in a way that can be written to a ppm file.
   *
   * @return String Representation in the file
   */
  @Override
  public String toString() {
    StringBuilder ppmFormat = new StringBuilder();
    ppmFormat.append("P3\n" + width + "\n" + height + "\n" + maxColor + "\n");

    for (IPixel[] row : this.pixelArray) {
      StringBuilder rowString = new StringBuilder();
      for (IPixel color : row) {
        rowString.append(color.toString() + " ");
      }
      rowString.append("\n");
      ppmFormat.append(rowString);
    }

    return ppmFormat.toString();
  }

}
