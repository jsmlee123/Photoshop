package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to sharpen an image in an {@code ImageModel}.
 */
public class Sharpen extends AbstractImageCommand {

  /**
   * Creates the function object to sharpen an image in the model.
   * @param model the model to blur an image
   * @param view where to transmit messages to
   */
  public Sharpen(ImageModel model, ImageView view) {
    super(model, view);
  }

  @Override
  public void run() {
    if (model.isImage(model.getCurrentIndex())) {
      model.sharpenImage();
      write("sharpen applied");
    } else {
      write("Could not sharpen");
    }
  }
}
