package controller;

/**
 * Represents a controller for {@code ImageModel} to edit images.
 */
public interface ImageController {

  /**
   * Runs the model to allow loading, saving, and editing images.
   *
   * @throws IllegalStateException if writing to the Appendable object used by it fails or reading
   *                               from the provided Readable fails
   */
  void editImages() throws IllegalStateException;
}
