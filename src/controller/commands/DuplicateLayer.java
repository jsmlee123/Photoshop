package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to duplicate a given layer to the current layer in an {@code ImageModel}.
 */
public class DuplicateLayer extends AbstractImageCommand {
  private final String indexToDuplicate;

  /**
   * Creates the function object to duplicate the layer at the given index to the current layer
   * in the model if it is possible.
   * @param model the model where to duplicate the layer
   * @param view where to output messages
   * @param indexToDuplicate the index of the layer to copy onto the current
   */
  public DuplicateLayer(ImageModel model, ImageView view, String indexToDuplicate) {
    super(model, view);
    this.indexToDuplicate = indexToDuplicate;
  }

  @Override
  public void run() {
    int index;
    try {
      index = Integer.parseInt(indexToDuplicate);
    } catch (NumberFormatException e) {
      write("Not a number");
      return;
    }
    if (!model.isImage(index - 1)) {
      write("Could not duplicate layer to current");
      return;
    }
    model.duplicateImage(index - 1);
  }
}
