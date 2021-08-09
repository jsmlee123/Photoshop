package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to apply greyscale an image for an {@code ImageModel}.
 */
public class Greyscale extends AbstractImageCommand {

  /**
   * Creates the function object to apply greyscale on an image in the model.
   * @param model the model to blur an image
   * @param view where to transmit messages to
   */
  public Greyscale(ImageModel model, ImageView view) {
    super(model, view);
  }

  @Override
  public void run() {
    if (model.isImage(model.getCurrentIndex())) {
      model.greyscaleImage();
      write("greyscale applied");
    } else {
      write("Could not apply greyscale");
    }
  }
}
