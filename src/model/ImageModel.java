package model;



/**
 * Represents a Photoshop-esque model that has layers of images.
 * Contains methods to manipulate/modify layers.
 */
public interface ImageModel extends IImageConverter {

  /**
   * Return number of layers in this Image model.
   *
   * @return the number of layers currently in this model
   */
  int getNumLayers();

  /**
   * Adds an empty layer to this Image model.
   */
  void addLayer();

  /**
   * Removes the layer at the specified index completely.
   * If the current image was at the removed index, moves the new current to the
   * image in the current index. If removed index is last index, move current index down one.
   *
   * @param index index of layer to be removed
   * @throws IllegalArgumentException if the index is out of bounds of the model
   */
  void removeLayer(int index) throws IllegalArgumentException;

  /**
   * Loads the given image into the current layer.
   *
   * @param image image to be loaded
   * @throws IllegalStateException if there is no layer to put an image on or
   *                               the image is of invalid dimensions
   */
  void loadImage(IImageConverter image) throws IllegalStateException;

  /**
   * Toggles the visibility of the layer at the given index.
   *
   *
   * @param index index of layer to be toggled
   * @throws IllegalArgumentException if the index is out of bounds for the model
   */
  void toggleLayer(int index) throws IllegalArgumentException;

  /**
   * Swaps the the two layers of the two given indexes.
   * Swaps their visibility too.
   *
   * @param indexOne first layer to be swapped
   * @param indexTwo second layer to be swapped
   * @throws IllegalArgumentException if either of the two indexes are out of bounds of the model
   */
  void swapLayers(int indexOne, int indexTwo) throws IllegalArgumentException;

  /**
   * Return the visibility of the layer at given index.
   *
   * @param index index of layer to be queried
   * @return the visibility of the given layer
   * @throws IllegalArgumentException if the index is out of bounds of the model
   */
  boolean getVisibility(int index) throws IllegalArgumentException;

  /**
   * Sets the current layer to the layer at given index.
   *
   * @param index index of layer to set current to
   * @throws IllegalArgumentException if the index is out of bounds
   */
  void setCurrent(int index) throws IllegalArgumentException;

  /**
   * Copies the image at the given index onto the current layer.
   *
   * @param index index of layer to be duplicated
   * @throws IllegalArgumentException if the index is out of bounds
   */
  void duplicateImage(int index) throws IllegalArgumentException;

  /**
   * Gets the index of the current layer we are on.
   *
   * @return the index of the current layer
   */
  int getCurrentIndex();


  /**
   * Checks that the image at the given index is actually an image
   * and not for instance a null. Returns false for out of bounds indeces too.
   *
   * @param index index of layer to be queried
   * @return whether or not the layer has a valid image
   */
  boolean isImage(int index);


}
