package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * Command to toggle the visibility of a layer in an {@code ImageModel}.
 */
public class ToggleVisible extends AbstractImageCommand {
  private final String layer;

  /**
   * Creates a function object to toggle the visibility of a layer at the index in the model.
   * @param model the image model
   * @param view where to output messages
   * @param layer the index of the layer to toggle the visibility of
   */
  public ToggleVisible(ImageModel model, ImageView view, String layer) {
    super(model, view);
    this.layer = layer;
  }

  @Override
  public void run() {
    int index;
    try {
      index = Integer.parseInt(layer);
    } catch (NumberFormatException e) {
      write("Not a numeric index");
      return;
    }
    try {
      model.toggleLayer(index - 1);
    } catch (IllegalArgumentException e) {
      write("Invalid index");
    }
  }
}
