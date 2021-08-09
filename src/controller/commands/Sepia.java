package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to apply sepia on an image for an {@code ImageModel}.
 */
public class Sepia extends AbstractImageCommand {

  /**
   * Creates the function object to apply sepia on an image in the model.
   * @param model the model to blur an image
   * @param view where to transmit messages to
   */
  public Sepia(ImageModel model, ImageView view) {
    super(model, view);
  }

  @Override
  public void run() {
    if (model.isImage(model.getCurrentIndex())) {
      model.sepiaImage();
      write("sepia applied");
    } else {
      write("Could not apply sepia");
    }
  }
}
