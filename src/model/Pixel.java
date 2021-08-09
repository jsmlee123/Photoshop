package model;

import java.util.List;

/**
 * Custom class to represent a pixel. Supports operations on colors. Represents a pixel with a color
 * made up of a combination of three colors: red, green, and blue.
 */
public final class Pixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a {@code Pixel} representing a color. Allows for negative bounds since no lower or
   * upper bounds are specifically specified for an abstract representation of color.
   *
   * @param red   numeric red value of color
   * @param green numeric green value of color
   * @param blue  numeric blue value of color
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public IPixel multiply(double multiplier) {
    return new Pixel((int) Math.round(red * multiplier),
        (int) Math.round(green * multiplier), (int) Math.round(blue * multiplier));
  }

  @Override
  public IPixel divide(double multiplier) {
    return new Pixel((int) Math.round(red / multiplier),
        (int) Math.round(green / multiplier), (int) Math.round(blue / multiplier));
  }

  @Override
  public IPixel add(IPixel other) {
    if (other == null) {
      throw new IllegalArgumentException("Null input");
    }

    if (!(other instanceof Pixel)) {
      throw new IllegalArgumentException("Incompatible pixel");
    }

    Pixel pixelToAdd = (Pixel) other;
    int redSum = this.red + pixelToAdd.red;
    int greenSum = this.green + pixelToAdd.green;
    int blueSum = this.blue + pixelToAdd.blue;
    return new Pixel(redSum, greenSum, blueSum);
  }

  @Override
  public IPixel clampBounds(int maxValue) {
    int redComponent = this.red;
    int greenComponent = this.green;
    int blueComponent = this.blue;

    if (redComponent > maxValue) {
      redComponent = maxValue;
    } else if (redComponent < 0) {
      redComponent = 0;
    }
    if (greenComponent > maxValue) {
      greenComponent = maxValue;
    } else if (greenComponent < 0) {
      greenComponent = 0;
    }
    if (blueComponent > maxValue) {
      blueComponent = maxValue;
    } else if (blueComponent < 0) {
      blueComponent = 0;
    }

    return new Pixel(redComponent, greenComponent, blueComponent);
  }

  @Override
  public IPixel linearCombine(List<List<Double>> matrix) throws IllegalArgumentException {
    if (matrix == null || matrix.size() != 3 || matrix.get(0).size() != 3) {
      throw new IllegalArgumentException("Invalid matrix");
    }
    int[] colors = {this.red, this.green, this.blue};
    int[] newColors = new int[3];
    for (int row = 0; row < matrix.size(); row += 1) {
      int newColor = 0;
      for (int column = 0; column < matrix.size(); column += 1) {
        newColor += matrix.get(row).get(column) * colors[column];
      }
      newColors[row] = newColor;
    }
    return new Pixel(newColors[0], newColors[1], newColors[2]);
  }

  /**
   * Overriden toString method. Return this ColorImpl as a sequence of numbers in order of
   * red,blue,green, all separated by space.
   *
   * @return String Representation of ColorImpl
   */
  @Override
  public String toString() {
    return "" + this.red + " " + this.green + " " + this.blue;
  }


  /**
   * Overriden equals method. Check that int fields are the same.
   *
   * @param other object to compare this to
   * @return whether or not the objects are the same
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Pixel)) {
      return false;
    }

    Pixel otherColor = (Pixel) other;

    return (this.red == otherColor.red)
        && (this.green == otherColor.green)
        && (this.blue == otherColor.blue);
  }

  @Override
  public int hashCode() {
    return this.red * 193 + this.green * 1543 + this.blue * 3079;
  }
}
