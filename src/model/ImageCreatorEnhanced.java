package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Static Utils class for Reading and Writing file data. Enhanced version of the previous
 * ImageCreator Class.
 */
public class ImageCreatorEnhanced extends ImageCreator {

  /**
   * Writes the information of this image to the specified file, or creates a new file with the
   * given name if it's not existing, using toString to translate this PPMImage into a readable file
   * format.
   *
   * @param model     the image we are writing to a file
   * @param file      name of file to write to
   * @param directory the directory to write the model image to
   * @throws IllegalArgumentException if the given arguments are null
   * @throws IOException              if something goes bad with FOS
   */
  public static void writeImage(IImageConverter model, String file, String directory)
      throws IllegalArgumentException,
      IOException {
    if (file == null || model == null || directory == null) {
      throw new IllegalArgumentException("Null Input");
    }
    if (!directory.equals("")) {
      directory += "/";
    }
    try {
      FileOutputStream fos = new FileOutputStream(directory + file);
      fos.write(model.getBytes());
      fos.close();
    } catch (IOException e) {
      throw new IOException("Could not write image to the specified file");
    }
  }

  /**
   * Writes the given string message to a specific directory with the given file name.
   *
   * @param file      name of file. Not including format
   * @param directory directory of the file
   * @param message   String we want to put in the text file
   * @throws IOException if something goes wrong with FOS
   */
  public static void writeText(String file, String directory, String message)
      throws IOException {
    if (file == null || directory == null || message == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    try {
      FileOutputStream fos = new FileOutputStream(directory + "/" + file + ".txt");
      fos.write(message.getBytes());
      fos.close();
    } catch (IOException e) {
      throw new IOException("Could not write image to the specified file");
    }
  }

  /**
   * Using the given array of pixels(image), creates an JPEGImage object.
   *
   * @param pixelArray array of pixel
   * @return JPEGImage object of given pixelArray specifications.
   */
  public static IImageConverter createJPEG(IPixel[][] pixelArray) {
    checkArray(pixelArray);
    return new JPEGImage(pixelArray[0].length, pixelArray.length, 255, pixelArray);
  }

  /**
   * Using the given array of pixels(image), creates an PPMEnhanced object.
   *
   * @param pixelArray array of pixel
   * @return PPMEnhanced object of given pixelArray specifications.
   */
  public static IImageConverter createPPMEnhanced(IPixel[][] pixelArray) {
    checkArray(pixelArray);
    return new PPMImageEnhanced(pixelArray[0].length, pixelArray.length, 255, pixelArray);
  }

  /**
   * Using the given array of pixels(image), creates an PNGImage object.
   *
   * @param pixelArray array of pixel
   * @return PNGImage object of given pixelArray specifications.
   */
  public static IImageConverter createPNG(IPixel[][] pixelArray) {
    checkArray(pixelArray);
    return new PNGImage(pixelArray[0].length, pixelArray.length, 255, pixelArray);
  }

  /**
   * Read the given png file and store information as a PNGObject.
   *
   * @param filename name of png file
   * @return PNGImage object of information in given file
   * @throws IllegalArgumentException if something goes wrong with FOS
   */
  public static IImageConverter readPNG(String filename)
      throws IllegalArgumentException, IOException {
    if (filename == null) {
      throw new IllegalArgumentException("Null Input");
    }

    File file = new File(filename);
    BufferedImage image = ImageIO.read(file);
    int width = image.getWidth();
    int height = image.getHeight();
    IPixel[][] pixelArray = makePixelArray(width, height, image);

    return new PNGImage(width, height, 255, pixelArray);
  }

  /**
   * Read the given jpeg file and store its information in a JPEGImage file.
   *
   * @param filename name of jpeg file to be read
   * @return JPEGImage object of information in the given file
   * @throws IOException              if something goes wrong with FOS
   * @throws IllegalArgumentException if input is null
   */
  public static IImageConverter readJPEG(String filename)
      throws IOException, IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Null Input");
    }

    File file = new File(filename);
    BufferedImage image = ImageIO.read(file);
    int width = image.getWidth();
    int height = image.getHeight();
    IPixel[][] pixelArray = makePixelArray(width, height, image);

    return new JPEGImage(width, height, 255, pixelArray);
  }

  /**
   * Helper method for readPNG and readJPEG. Reads the BufferedImage and returns a 2d array of
   * pixels representing the image.
   *
   * @param width  width of image
   * @param height height of image
   * @param image  Buffered of image to read from
   * @return 2d IPixel Array representing the image
   */
  private static IPixel[][] makePixelArray(int width, int height, BufferedImage image) {
    IPixel[][] pixelArray = new IPixel[height][width];

    for (int y = 0; y < height; y += 1) {
      for (int x = 0; x < width; x += 1) {
        Color color = new Color(image.getRGB(x, y));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        pixelArray[y][x] = new Pixel(red, green, blue);
      }
    }
    return pixelArray;
  }

  /**
   * Reads information from a text file and returns the contents of the text file as a String.
   * Removes comments from the String starting with #.
   *
   * @param filename name of text File to read
   * @return the String contents of the file.
   * @throws IllegalArgumentException if file is null/ not found
   */
  public static String readTextFile(String filename) throws IllegalArgumentException {
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
    return builder.toString();
  }

  /**
   * Reads the given ppm file and returns a PPMEnhanced object of given ppm file.
   *
   * @param filename name of ppm file to be read
   * @return PPMEnhanced object of given file information
   * @throws IllegalArgumentException if file is null/ not found
   */
  public static IImageConverter readPPMEnhanced(String filename) throws IllegalArgumentException {
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
    return new PPMImageEnhanced(width, height, maxValue, pixelArray);
  }

  /**
   * helper method to check that the given pixelArray is completely valid.
   *
   * @param pixelArray 2d array of pixels to verify
   * @throws IllegalArgumentException if the pixelArray is invalid
   */
  private static void checkArray(IPixel[][] pixelArray) throws IllegalArgumentException {
    if (pixelArray == null) {
      throw new IllegalArgumentException("Null input");
    }
    if (pixelArray.length == 0 || pixelArray[0].length == 0) {
      throw new IllegalArgumentException("Invalid pixelArray");
    }
  }

  static BufferedImage deepCopy(BufferedImage bi) {
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }
}
