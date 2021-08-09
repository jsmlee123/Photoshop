package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstract representation of images. Assumes all images can be converted into array of pixels and
 * has associated height/width consequently and max color that can be provided or assumed.
 * Difference between image formats, is how they are read from a file and exported into a file. Uses
 * {@code Pixel}.
 */
public abstract class AbstractImage implements ImageEditorEnhanced {

  protected final IPixel[][] pixelArray;
  protected final int maxColor;
  protected final int height;
  protected final int width;

  /**
   * Constructs abstract Image.
   *
   * @param width      width of image
   * @param height     height of image
   * @param maxColor   maxValue of color allowed for format
   * @param pixelArray array of pixels representing the image
   * @throws IllegalArgumentException if pixelArray is invalid
   */
  public AbstractImage(int width, int height, int maxColor, IPixel[][] pixelArray)
      throws IllegalArgumentException {
    if (pixelArray == null) {
      throw new IllegalArgumentException("Null Parameter");
    }
    if (pixelArray.length == 0 || pixelArray[0].length == 0) {
      throw new IllegalArgumentException("Bad pixelArray");
    }
    for (IPixel[] row : pixelArray) {
      if (Arrays.asList(row).contains(null)) {
        throw new IllegalArgumentException("Null Parameter");
      }
    }
    this.pixelArray = pixelArray;
    this.maxColor = maxColor;
    this.width = width;
    this.height = height;
  }

  @Override
  public void blurImage() {
    List<List<Double>> blurArray = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(0.0625, 0.125, 0.0625)),
        new ArrayList<>(Arrays.asList(0.125, 0.25, 0.125)),
        new ArrayList<>(Arrays.asList(0.0625, 0.125, 0.0625))));

    this.generateImage(filterImage(blurArray));
  }

  @Override
  public void sharpenImage() {
    List<List<Double>> sharpArray = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 1.0, 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125))));

    this.generateImage(filterImage(sharpArray));
  }

  /**
   * Apply given kernel to this pixelArray.
   *
   * @param filterArray kernel
   * @return modified array of pixels
   * @throws IllegalArgumentException if filterArray is malformed
   */
  protected IPixel[][] filterImage(List<List<Double>> filterArray)
      throws IllegalArgumentException {
    //check that input kernel doesn't have odd dimensions and is square
    //realistically this exception won't occur because its not public facing
    if (filterArray.size() % 2 == 0 || filterArray.size() != filterArray.get(0).size()) {
      throw new IllegalArgumentException("Invalid Kernel");
    }

    IPixel[][] modifiedArray = new IPixel[height][width];

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        modifiedArray[h][w] = addFilter(h, w, filterArray);
      }
    }

    return modifiedArray;
  }

  /**
   * Applies the filter to pixel at specified height and width and returns the modification.
   *
   * @param h           height of pixel we are modifying
   * @param w           width of pixel we are modifying
   * @param filterArray kernel used to filter image
   * @return the pixel at h,w with applied filter
   */
  protected IPixel addFilter(int h, int w, List<List<Double>> filterArray) {
    IPixel result = new Pixel(0, 0, 0);

    int filterHeight = (filterArray.size() - 1) / 2;
    for (int i = 0; i < filterArray.size(); i++) {
      for (int j = 0; j < filterArray.size(); j++) {
        int pixelPosH = h - filterHeight + i;
        int pixelPosW = w - filterHeight + j;
        if (pixelPosH >= 0 && pixelPosW >= 0 && pixelPosH < this.height && pixelPosW < this.width) {
          result = result.add(this.pixelArray[pixelPosH][pixelPosW]
              .multiply(filterArray.get(i).get(j)));
        }
      }
    }

    //final color is made, clamp bounds and return it
    result = result.clampBounds(this.maxColor);
    return result;
  }

  @Override
  public void greyscaleImage() {
    List<List<Double>> greyScaleArray = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722)),
        new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722)),
        new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722))));
    this.generateImage(applyColorTransformation(greyScaleArray));
  }

  @Override
  public void sepiaImage() {
    List<List<Double>> sepiaArray = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(0.3930, .7690, 0.189)),
        new ArrayList<>(Arrays.asList(.349, .686, .168)),
        new ArrayList<>(Arrays.asList(.272, .534, .131))));
    this.generateImage(applyColorTransformation(sepiaArray));
  }

  /**
   * Applies the given matrix onto this image where the new color values are linear combinations of
   * this images old color values to apply a color transformation.
   *
   * @param colorTransformationArray the matrix to apply the color transformation
   * @return the new linear combinations of pixels
   */
  protected IPixel[][] applyColorTransformation(List<List<Double>> colorTransformationArray) {
    IPixel[][] modifiedPixels = new IPixel[height][width];
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        modifiedPixels[h][w] =
            pixelArray[h][w].linearCombine(colorTransformationArray).clampBounds(maxColor);
      }
    }
    return modifiedPixels;
  }

  /**
   * Change this pixelArray to the new one.
   *
   * @param newArray color array to replace this pixelArray
   */
  protected void generateImage(IPixel[][] newArray) {
    for (int i = 0; i < newArray.length; i++) {
      for (int j = 0; j < newArray[0].length; j++) {
        this.pixelArray[i][j] = newArray[i][j];
      }
    }
  }

  @Override
  public void mosaic(int seeds) {
    Random rand = new Random();
    Map<Point2D, List<Point2D>> mapPoints = new HashMap<>();
    Map<Point2D, IPixel> mapColors;

    List<Point2D> seed = new ArrayList<>();

    for (int i = 0; i < seeds; i++) {
      Point point = new Point(rand.nextInt(this.width), rand.nextInt(this.height));
      seed.add(point);
      mapPoints.putIfAbsent(point, new ArrayList<>());
    }

    mapPoints = clusterMap(seed, mapPoints);
    mapColors = averageColors(mapPoints);

    mosaicHelper(mapPoints, mapColors);

  }

  /**
   * Finds the average colors of a cluster and assigns the average color to its seed.
   *
   * @param mapPoints map of seeds and associated closest pixel locations
   * @return a map of each seed to the average color of the cluster
   */
  private Map<Point2D, IPixel> averageColors(Map<Point2D, List<Point2D>> mapPoints) {
    Map<Point2D, IPixel> newMap = new HashMap<>();
    for (Point2D seed : mapPoints.keySet()) {
      IPixel averageColor = new Pixel(0, 0, 0);
      int numColors = 0;
      for (Point2D pixel : mapPoints.get(seed)) {
        averageColor = averageColor.add(this.pixelArray[(int) pixel.getY()][(int) pixel.getX()]);
        numColors += 1;
      }
      averageColor = averageColor.divide(numColors);
      averageColor.clampBounds(this.maxColor);
      newMap.put(seed, averageColor);
    }
    return newMap;
  }

  /**
   * Create a mapping of seeds to its cluster.
   *
   * @param seeds List of seeds
   * @param map   map of seed to all pixels associated in its cluster
   * @return a complete mapping of seeds to its cluster.
   */
  private Map<Point2D, List<Point2D>> clusterMap(List<Point2D> seeds,
      Map<Point2D, List<Point2D>> map) {
    Map<Point2D, List<Point2D>> newMap = map;
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        Point2D closestSeed = findClosestSeed(h, w, seeds);
        List<Point2D> listAddition = newMap.get(closestSeed);
        listAddition.add(new Point(w, h));
        newMap.put(closestSeed, newMap.get(closestSeed));
      }
    }
    return newMap;

  }

  /**
   * For every cluster, sets each pixel in the cluster to the average color of the cluster.
   *
   * @param mapPoints map of seeds to their clusters
   * @param mapColor  map of seeds to the average color of the cluster
   */
  protected void mosaicHelper(Map<Point2D, List<Point2D>> mapPoints,
      Map<Point2D, IPixel> mapColor) {
    for (Map.Entry<Point2D, List<Point2D>> entry : mapPoints.entrySet()) {
      for (Point2D pixel : entry.getValue()) {
        this.pixelArray[(int) pixel.getY()][(int) pixel.getX()] = mapColor.get(entry.getKey());
      }
    }
  }

  /**
   * Return the closest seed in the given list of seeds to this pixel.
   *
   * @param h    y location of pixel
   * @param w    x location of pixel
   * @param seed list of all generated seeds
   * @return the closest seed to the given h,w
   */
  protected Point2D findClosestSeed(int h, int w, List<Point2D> seed) {
    double smallestDist = Integer.MAX_VALUE;
    Point2D closestSeed = seed.get(0);

    for (Point2D point : seed) {
      if (point.distance(w, h) < smallestDist) {
        closestSeed = point;
        smallestDist = point.distance(w, h);
      }
    }

    return closestSeed;
  }
}
