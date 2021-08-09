package view;

import java.io.IOException;

/**
 * Interface to represent a textual view for an image model.
 * Contains method to render a message.
 */
public interface ImageView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
