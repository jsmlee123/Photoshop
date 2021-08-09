package controller.commands;

import controller.AbstractImageCommand;
import model.IImageConverter;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import model.Pixel;
import view.ImageView;

/**
 * The command to create a checkerboard image for an {@code ImageModel}.
 */
public class Checkerboard extends AbstractImageCommand {

  private final String size;
  private final String width;
  private final String height;
  private final String red1;
  private final String green1;
  private final String blue1;
  private final String red2;
  private final String green2;
  private final String blue2;

  /**
   * Creates the function object to load a checkerboard image of the given arguments to model.
   * @param model the model where to add the checkerboard
   * @param view where to output messages
   * @param size the size of each tile in the image
   * @param width how many tiles width wise we want
   * @param height how many tiles height wise we want
   * @param red1 the red component for the first color of the checkerboard
   * @param green1 the green component for the first color of the checkerboard
   * @param blue1 the blur component for the first color of the checkerboard
   * @param red2 the red component for the second color of the checkerboard
   * @param green2 the green component for the second color of the checkerboard
   * @param blue2 the blur component for the second color of the checkerboard
   */
  public Checkerboard(ImageModel model, ImageView view, String size, String width, String height,
      String red1, String green1, String blue1, String red2, String green2, String blue2) {
    super(model, view);
    this.size = size;
    this.width = width;
    this.height = height;
    this.red1 = red1;
    this.green1 = green1;
    this.blue1 = blue1;
    this.red2 = red2;
    this.green2 = green2;
    this.blue2 = blue2;
  }

  @Override
  public void run() {
    int s;
    int w;
    int h;
    int r1;
    int g1;
    int b1;
    int r2;
    int g2;
    int b2;

    try {
      s = Integer.parseInt(size);
      w = Integer.parseInt(width);
      h = Integer.parseInt(height);
      r1 = Integer.parseInt(red1);
      g1 = Integer.parseInt(green1);
      b1 = Integer.parseInt(blue1);
      r2 = Integer.parseInt(red2);
      g2 = Integer.parseInt(green2);
      b2 = Integer.parseInt(blue2);
    } catch (NumberFormatException e) {
      write("Not a number: " + e.getMessage());
      return;
    }
    IImageConverter image;
    try {
      image = ImageCreatorEnhanced.createPPMEnhanced(ImageCreatorEnhanced.createCheckerBoard(s, w,
          h, new Pixel(r1, g1, b1), new Pixel(r2, g2, b2)));
    } catch (IllegalArgumentException e) {
      write("Could not create checkerboard with given arguments: " + e.getMessage());
      return;
    }
    try {
      model.loadImage(image);
    } catch (IllegalStateException e) {
      write("Could not create checkerboard: " + e.getMessage());
    }
  }
}
