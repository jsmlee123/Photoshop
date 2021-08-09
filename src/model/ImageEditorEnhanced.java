package model;

/**
 * Represents an enhanced version of the ImageEditor interface since it
 * adds an increased amount of image editing operations.
 */
public interface ImageEditorEnhanced extends ImageEditor {

  /**
   * Apply a mosaic filter to this image.
   *
   * @param seeds No of seeds in this
   */
  void mosaic(int seeds);
}
