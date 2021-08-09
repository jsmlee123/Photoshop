package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * The command to apply mosaic to an image for an {@code ImageModel}.
 */
public class Mosaic extends AbstractImageCommand {

  private final String seed;

  /**
   * Creates the function object to apply mosaic on an image in the model.
   *
   * @param model the model to apply mosaic to an image
   * @param view  where to transmit messages to
   * @param seed  number of seeds used in the mosaic filter
   */
  public Mosaic(ImageModel model, ImageView view, String seed) {
    super(model, view);
    this.seed = seed;
  }

  @Override
  public void run() {
    int noSeed = 0;
    try {
      noSeed = Integer.parseInt(seed);
    } catch (NumberFormatException e) {
      write("Could not parse integer");
      return;
    }

    if (model.isImage(model.getCurrentIndex()) && noSeed > 0) {
      model.mosaic(noSeed);
      write("mosaic applied");
    } else {
      write("Could not apply mosaic filter");
    }

  }
}
