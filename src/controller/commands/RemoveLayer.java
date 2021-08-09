package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * Command to remove a layer from an {@code ImageModel}.
 */
public class RemoveLayer extends AbstractImageCommand {
  private final String layer;

  /**
   * Creates a function object to remove a layer at the given index in the model.
   * @param model the model to remove the layer
   * @param view where to output messages
   * @param layer the index of the layer to remove
   */
  public RemoveLayer(ImageModel model, ImageView view, String layer) {
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
      model.removeLayer(index - 1);
    } catch (IllegalArgumentException e) {
      write("Invalid index");
    }
  }
}
