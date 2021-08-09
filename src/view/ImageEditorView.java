package view;

import java.io.IOException;

/**
 * Class to represent a view for a textual ImageModel.
 * Contains only an appendable for which the textual view is output to.
 * Does not ask for anything related to the model.
 */
public class ImageEditorView implements ImageView {
  private final Appendable destination;

  /**
   * Constructs an ImageEditorView object given an appendable.
   *
   * @param destination destination of output
   * @throws IllegalArgumentException if destination is null
   */
  public ImageEditorView(Appendable destination) throws IllegalArgumentException {
    if (destination == null) {
      throw new IllegalArgumentException("Null Input");
    }
    this.destination = destination;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      return;
    }
    try {
      destination.append(message);
    } catch (IOException e) {
      throw new IOException("Could not render message");
    }
  }
}
