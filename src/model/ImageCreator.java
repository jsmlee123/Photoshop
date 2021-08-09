package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Factory class used for creating ImageEditor objects whether by reading data from a file or
 * creating it manually.
 */
public class ImageCreator {

  /**
   * Writes the information of this image to the specified file, or creates a new file with the
   * given name if it's not existing, using toString to translate this PPMImage into a readable file
   * format.
   *
   * @param file name of file to write to
   * @throws IllegalArgumentException if the given arguments are null
   * @throws IOException              if something goes bad with FOS
   */
  public static void writeImage(ImageEditor model, String file) throws IllegalArgumentException,
      IOException {
    if (file == null || model == null) {
      throw new IllegalArgumentException("Null Input");
    }
    try {
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(model.toString().getBytes());
      fos.close();
    } catch (IOException e) {
      throw new IOException("Could not write image to the specified file");
    }
  }

  /**
   * Programmatically create a checkerboard pixel array of specified format, size, tiles, and
   * colors.
   *
   * @param size   the size of each tile in the image
   * @param width  how many tiles width wise we want
   * @param height how many tiles height wise we want
   * @param c1     color of half the tiles
   * @param c2     color of other half of the tiles
   * @return Array of pixels that create a checkerboard
   * @throws IllegalArgumentException if given arguments are invalid
   */
  public static IPixel[][] createCheckerBoard(int size, int width, int height, IPixel c1, IPixel c2)
      throws IllegalArgumentException {
    if (size <= 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    if (c1 == null || c2 == null) {
      throw new IllegalArgumentException("Null Colors");
    }

    IPixel[][] pixelArray = new Pixel[size * height][size * width];
    IPixel currentColor;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if ((i + j) % 2 == 0) {
          currentColor = c1;
        } else {
          currentColor = c2;
        }

        for (int y = size * i; y < size * i + size; y++) {
          for (int x = size * j; x < size * j + size; x++) {
            pixelArray[y][x] = currentColor;
          }
        }
      }
    }

    return pixelArray;
  }

  /**
   * Create a PPMImage object with the given array of pixels.
   *
   * @param pixelArray array for image
   * @return PPMImage object of given pixel image.
   * @throws IllegalArgumentException if the given array is invalid
   */
  public static ImageEditor createPPM(IPixel[][] pixelArray) throws IllegalArgumentException {
    if (pixelArray == null) {
      throw new IllegalArgumentException("Null input");
    }

    if (pixelArray.length == 0 || pixelArray[0].length == 0) {
      throw new IllegalArgumentException("Invalid pixelArray");
    }

    return new PPMImage(pixelArray[0].length, pixelArray.length, 255, pixelArray);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @throws IllegalArgumentException if the given file name is invalid
   */
  public static ImageEditor readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    if (filename == null) {
      throw new IllegalArgumentException("Null File Name");
    }

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Error: " + e);
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Improper PPM File");
    }
    int width = sc.nextInt();

    int height = sc.nextInt();

    int maxValue = sc.nextInt();

    Pixel[][] pixelArray = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelArray[i][j] = new Pixel(r, g, b);
      }
    }
    return new PPMImage(width, height, maxValue, pixelArray);
  }

}
