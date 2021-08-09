package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a photoshop-esque model. Contains layers of images that can be separately
 * modified and turned off.
 */
public class LayeredImages implements ImageModel {

  private final List<IImageConverter> layers;
  private final List<Boolean> visibility;

  private IImageConverter current;
  private int currentIndex;

  private IImageConverter imageDimension;

  /**
   * Constructor for a LayeredImage model. Starts with no layers, index starting at -1.
   */
  public LayeredImages() {
    this.layers = new ArrayList<>();
    this.visibility = new ArrayList<>();
    this.currentIndex = -1;
  }

  @Override
  public int getNumLayers() {
    return layers.size();
  }

  @Override
  public void addLayer() {
    layers.add(null);
    visibility.add(false);
    if (currentIndex == -1) {
      currentIndex = 0;
    }
  }

  @Override
  public void removeLayer(int index) throws IllegalArgumentException {
    if (index < 0 || index >= layers.size()) {
      throw new IllegalArgumentException("Invalid index");
    }

    layers.remove(index);
    visibility.remove(index);

    // only one layer
    if (layers.size() == 0) {
      currentIndex = -1;
      current = null;
    } else if (currentIndex == layers.size()) {
      // current is end of list
      currentIndex -= 1;
      current = layers.get(currentIndex);
    } else {
      current = layers.get(currentIndex);
    }


  }

  @Override
  public void loadImage(IImageConverter image) throws IllegalStateException {
    if (layers.size() == 0 || currentIndex == -1) {
      throw new IllegalStateException("Need to create layers");
    }
    if (imageDimension == null) {
      imageDimension = image;
    } else if (!imageDimension.compareDimensions(image) && layers.size() > 1) {
      throw new IllegalStateException("Image dimensions don't match");
    }
    layers.set(currentIndex, image);
    visibility.set(currentIndex, true);
    current = layers.get(currentIndex);
  }

  @Override
  public void toggleLayer(int index) {
    if (index < 0 || index >= visibility.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    visibility.set(index, !visibility.get(index));
  }

  @Override
  public void swapLayers(int indexOne, int indexTwo) {
    if (indexOne < 0 || indexOne >= layers.size() || indexTwo < 0 || indexTwo >= layers.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    IImageConverter imageOne = layers.get(indexOne);
    boolean visibilityOne = visibility.get(indexOne);
    IImageConverter imageTwo = layers.get(indexTwo);
    boolean visibilityTwo = visibility.get(indexTwo);
    layers.set(indexOne, imageTwo);
    layers.set(indexTwo, imageOne);
    visibility.set(indexOne, visibilityTwo);
    visibility.set(indexTwo, visibilityOne);
    current = layers.get(currentIndex);
  }

  @Override
  public boolean getVisibility(int index) {
    if (index < 0 || index > visibility.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    return visibility.get(index);
  }

  @Override
  public void setCurrent(int index) throws IllegalArgumentException {
    if (index < 0 || index >= layers.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    current = layers.get(index);
    currentIndex = index;
  }

  // sets current to duplicate the given index's image
  @Override
  public void duplicateImage(int index) {
    if (index < 0 || index > layers.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    IImageConverter image = layers.get(index).copy();
    layers.set(currentIndex, image);

    visibility.set(currentIndex, visibility.get(index));
    current = layers.get(currentIndex);
  }

  @Override
  public int getCurrentIndex() {
    return currentIndex;
  }

  @Override
  public boolean isImage(int index) {
    if (index < 0 || index >= layers.size()) {
      return false;
    }
    return layers.get(index) != null;
  }

  @Override
  public IImageConverter toPPM() {
    return current.toPPM();
  }

  @Override
  public IImageConverter toJPEG() {
    return current.toJPEG();
  }

  @Override
  public IImageConverter toPNG() {
    return current.toPNG();
  }

  @Override
  public byte[] getBytes() {
    return current.getBytes();
  }

  @Override
  public boolean compareDimensions(IImageConverter that) {
    return current.compareDimensions(that);
  }

  @Override
  public IImageConverter copy() {
    return current.copy();
  }

  @Override
  public void blurImage() {
    current.blurImage();
  }

  @Override
  public void sharpenImage() {
    current.sharpenImage();
  }

  @Override
  public void greyscaleImage() {
    current.greyscaleImage();
  }

  @Override
  public void sepiaImage() {
    current.sepiaImage();
  }

  @Override
  public BufferedImage getBufferedImage() {

    for (int i = this.getNumLayers() - 1; i >= 0; i--) {
      if (this.isImage(i) && this.getVisibility(i)) {
        return layers.get(i).getBufferedImage();
      }
    }

    //default return
    return ImageCreatorEnhanced.createJPEG(ImageCreatorEnhanced.
        createCheckerBoard(5, 500, 750, new Pixel(0, 0, 0),
            new Pixel(255, 255, 255))).getBufferedImage();

  }

  @Override
  public void mosaic(int seeds) {
    current.mosaic(seeds);
  }
}
