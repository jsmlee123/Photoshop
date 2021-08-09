package controller.commands;

import controller.AbstractImageCommand;
import java.io.IOException;
import model.IImageConverter;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import view.ImageView;

/**
 * The command to save the topmost visible image in a layer as a file in an {@code ImageModel}.
 */
public class Save extends AbstractImageCommand {

  protected String file;

  /**
   * Creates the function object to save topmost visible image in a layer to the given file name
   * from the model if there is one.
   * Accepts PPM, JPEG, PNG.
   * @param model the model where to save an image from
   * @param view where to output messages
   * @param file the name of the file to save the image as
   */
  public Save(ImageModel model, ImageView view, String file) {
    super(model, view);
    this.file = file;
  }

  @Override
  public void run() {
    int numLayers = model.getNumLayers();
    if (numLayers == 0) {
      write("No layers");
      return;
    }

    for (int i = numLayers - 1; i >= 0; i -= 1) {
      if (model.getVisibility(i)) {
        if (!model.isImage(i)) {
          write("No image to save");
          return;
        }

        int curr = model.getCurrentIndex();
        model.setCurrent(i);
        IImageConverter image = getImage();
        if (image == null) {
          write("Invalid file name");
          return;
        }

        try {
          ImageCreatorEnhanced.writeImage(image, file, "");
          model.setCurrent(curr);
          write("Save Successful");
          return;
        } catch (IOException e) {
          write("Could not save image: " + e.getMessage());
        }
      }
    }
    write("No visible layers to save image");
  }

  // returns the image at the current to the desired file format, else return null
  private IImageConverter getImage() {
    if (file == null) {
      return null;
    }

    switch (file.substring(file.lastIndexOf(".") + 1)) {
      case "ppm":
        return model.toPPM();
      case "jpeg":
        return model.toJPEG();
      case "png":
        return model.toPNG();
      default:
        write(file.substring(file.lastIndexOf(".") + 1));
        return null;
    }


  }
}
