package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to change the current layer working on for an {@code ImageModel}.
 */
public class SetCurrent extends AbstractImageCommand {
  private String layer;

  /**
   * Creates the function object to set the current layer working on at the given index.
   * @param model the model being worked on
   * @param view where to output messages
   * @param layer the index of the layer to set current layer to
   */
  public SetCurrent(ImageModel model, ImageView view, String layer) {
    super(model, view);
    this.layer = layer;
  }

  @Override
  public void run() {
    int index;
    try {
      index = Integer.parseInt(layer);
    } catch (NumberFormatException e) {
      write("Not a number");
      return;
    }
    try {
      model.setCurrent(index - 1);
    } catch (IllegalArgumentException e) {
      write("Invalid index");
    }
  }
}
