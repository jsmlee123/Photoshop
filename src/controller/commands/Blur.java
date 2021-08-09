package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to blur an image for an {@code ImageModel}.
 */
public class Blur extends AbstractImageCommand {

  /**
   * Creates the function object to blur an image in the model.
   * @param model the model to blur an image
   * @param view where to transmit messages to
   */
  public Blur(ImageModel model, ImageView view) {
    super(model, view);
  }

  @Override
  public void run() {
    if (model.isImage(model.getCurrentIndex())) {
      model.blurImage();
      write("blur applied");
    } else {
      write("Could not blur");
    }
  }
}
