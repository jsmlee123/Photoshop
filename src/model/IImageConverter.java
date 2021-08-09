package model;

import java.awt.image.BufferedImage;

/**
 * Enhances the ImageEditor interfaces by adding methods to transfer from image format to image
 * format. Get byte data of images, method to compare dimension as well as a method to provide a
 * deep copy.
 */
public interface IImageConverter extends ImageEditorEnhanced {

  /**
   * Changes the instance of this image to PPMEnhanced.
   *
   * @return ppm version of this image
   */
  IImageConverter toPPM();


  /**
   * Changes the instance of this image to JPEG.
   *
   * @return jpeg version of this image
   */
  IImageConverter toJPEG();

  /**
   * Changes the instance of this image to PNG.
   *
   * @return png version of this image
   */
  IImageConverter toPNG();


  /**
   * Return the byte data of this image. Extremely different for each different file format.
   *
   * @return the byte data of this image
   */
  byte[] getBytes();

  /**
   * Checks if this image and that image have the same dimensions.
   *
   * @param that the image to compare to
   * @return if the two images have same dimension.
   */
  boolean compareDimensions(IImageConverter that);

  /**
   * Creates a deep copy of this image. Useful for Aliasing purposes.
   *
   * @return A deep copy of this Image
   */
  IImageConverter copy();

  /**
   * Returns a BufferedImage version of this image. Used for extracting byte data for specific
   * formats using ImageIO.
   *
   * @return A BufferedImage version of this image
   */
  BufferedImage getBufferedImage();
}
