package model;

import java.util.List;

/**
 * Represents a pixel of an image with a combination of three base colors.
 */
public interface IPixel {

  /**
   * Return int value of the red component of the pixel.
   *
   *
   * @return the int red component of the pixel
   */
  int getRed();

  /**
   * Return int value of the green component of the pixel.
   *
   * @return the int green component
   */
  int getGreen();

  /**
   * Return int value of the blue component of the pixel.
   *
   * @return the int blue component
   */
  int getBlue();

  /**
   * Multiplies each color of this pixel with the given number and returns a new pixel.
   *
   * @param multiplier multiplicative factor
   * @return a new IPixel with the modified color numbers
   */
  IPixel multiply(double multiplier);

  IPixel divide(double multiplier);

  /**
   * Returns a new Pixel with added numbers of the given pixel's base colors to the respective
   * colors of this pixel's colors.
   *
   * @param other other Pixel to add to this one
   * @return a new IPixel with the modified color numbers.
   * @throws IllegalArgumentException if other is null
   */
  IPixel add(IPixel other) throws IllegalArgumentException;

  /**
   * Clamps the bounds of this Pixel to range [0,255]. Return modified Pixel.
   *
   * @param maxValue max int value of a field
   * @return a new Pixel
   */
  IPixel clampBounds(int maxValue);

  /**
   * Creates a new pixel with linear combinations of this pixel's colors of the given matrix.
   *
   * @param matrix the matrix to transform the colors of this pixel
   * @return the new pixel with the color transformation applied
   * @throws IllegalArgumentException if the given matrix is invalid
   */
  IPixel linearCombine(List<List<Double>> matrix) throws IllegalArgumentException;

}