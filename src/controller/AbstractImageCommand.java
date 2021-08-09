package controller;

import java.io.IOException;
import model.ImageModel;
import view.ImageView;

/**
 * Represents the abstract class for function objects to modify an {@code ImageModel}.
 */
public abstract class AbstractImageCommand implements ImageCommand {
  protected final ImageView view;
  protected final ImageModel model;

  // creates a function object command of the given arguments
  protected AbstractImageCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public abstract void run();

  // transmits messages to the view
  protected void write(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not render message");
    }
  }
}
