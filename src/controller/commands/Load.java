package controller.commands;

import controller.AbstractImageCommand;
import java.io.IOException;
import model.IImageConverter;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import view.ImageView;

/**
 * The command to load an image of a valid format type to an {@code ImageModel}.
 */
public class Load extends AbstractImageCommand {

  protected String file;

  /**
   * Creates the function object to load the given file image at the current layer into the model.
   * Accepts PPM, JPEG, PNG.
   *
   * @param model the model where to load the image
   * @param view  where to output messages
   * @param file  the image file name
   */
  public Load(ImageModel model, ImageView view, String file) {
    super(model, view);
    this.file = file;
  }

  @Override
  public void run() {
    if (file == null) {
      write("Cannot be null");
      return;
    }
    IImageConverter image = readImage();

    if (image == null) {
      return;
    }
    try {
      model.loadImage(image);
    } catch (IllegalStateException e) {
      write("Could not load image: " + e.getMessage());
    }
  }

  // returns an image converted to the format type of the file name
  private IImageConverter readImage() {
    String fileType = file.substring(file.lastIndexOf(".") + 1);
    IImageConverter image = null;
    switch (fileType) {
      case "ppm":
        try {
          image = ImageCreatorEnhanced.readPPMEnhanced(file);
        } catch (IllegalArgumentException e) {
          write("Could not load ppm");
        }
        break;
      case "jpeg":
        try {
          image = ImageCreatorEnhanced.readJPEG(file);
        } catch (IOException e) {
          write("Could not load jpeg");
        }
        break;
      case "png":
        try {
          image = ImageCreatorEnhanced.readPNG(file);
        } catch (IOException e) {
          write("Could not load png");
        }
        break;
      default:
        write("Invalid file type");
    }
    return image;
  }
}
