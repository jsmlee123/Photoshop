package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * Command to add a layer to an {@code ImageModel}.
 */
public class CreateLayer extends AbstractImageCommand {

  /**
   * Creates a function object to create a layer in the model.
   * @param model where to add the layer
   * @param view where to output messages
   */
  public CreateLayer(ImageModel model, ImageView view) {
    super(model, view);
  }

  @Override
  public void run() {
    model.addLayer();
    write("Layer Added");
  }
}
