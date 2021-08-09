package controller.commands;

import controller.AbstractImageCommand;
import model.ImageModel;
import view.ImageView;

/**
 * Command to swap two layers' positions in an {@code ImageModel}.
 */
public class SwapLayer extends AbstractImageCommand {

  private final String indexOne;
  private final String indexTwo;

  /**
   * Creates a function object to swap the positions of the layers of the given indices in the
   * model.
   * @param model the model where to swap layers
   * @param view the view where to output messages
   * @param indexOne the index of the first layer
   * @param indexTwo the index of the second layer
   */
  public SwapLayer(ImageModel model, ImageView view, String indexOne, String indexTwo) {
    super(model, view);
    this.indexOne = indexOne;
    this.indexTwo = indexTwo;
  }

  @Override
  public void run() {
    int one;
    int two;

    try {
      one = Integer.parseInt(indexOne);
      two = Integer.parseInt(indexTwo);
    } catch (NumberFormatException e) {
      write("Not a number");
      return;
    }
    try {
      model.swapLayers(one - 1, two - 1);
    } catch (IllegalArgumentException e) {
      write("Could not swap: " + e.getMessage());
    }
  }
}
