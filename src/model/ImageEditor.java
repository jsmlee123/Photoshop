package model;

/**
 * Represents an Image interface with associated Image editing methods.
 */
public interface ImageEditor {

  /**
   * Blurs this Image using a 3x3 kernel.
   */
  void blurImage();

  /**
   * Sharpens this image using a 5x5 kernel.
   */
  void sharpenImage();

  /**
   * Creates a greyscale image of this image.
   */
  void greyscaleImage();

  /**
   * Creates a sepia-toned image of this image.
   */
  void sepiaImage();

}
