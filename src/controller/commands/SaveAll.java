package controller.commands;

import controller.AbstractImageCommand;
import java.io.File;
import java.io.IOException;
import model.IImageConverter;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import view.ImageView;

/**
 * The command to save all the visible images in a directory with a text file of the locations in a
 * specific type for an {@code ImageModel}. Will create directory regardless if there are any images
 * to save.
 */
public class SaveAll extends AbstractImageCommand {

  protected String directoryName;
  private final String fileType;
  private final Appendable files;

  /**
   * Creates the function object to save all visible images at the given directory of the given
   * type, and a text file of the locations of each image saved. Saves the images as its layer
   * position.
   *
   * @param model         the model to save images from
   * @param view          where to output messages
   * @param directoryName the directory to save the images and text file to
   * @param fileType      the format to save the images as
   */
  public SaveAll(ImageModel model, ImageView view, String directoryName, String fileType) {
    super(model, view);
    this.directoryName = directoryName;
    this.fileType = fileType;
    this.files = new StringBuilder();
  }

  @Override
  public void run() {
    if (directoryName == null || fileType == null) {
      write("Cannot be null");
      return;
    }
    File theDir = new File(directoryName);
    if (!theDir.exists()) {
      if (!theDir.mkdirs()) {
        write("Could not create directory");
        return;
      }
    }
    for (int i = 0; i < model.getNumLayers(); i += 1) {
      if (model.getVisibility(i) && model.isImage(i)) {
        model.setCurrent(i);
        IImageConverter image = getImage();
        if (image == null) {
          write("Invalid file type");
          return;
        }
        saveImage(i, image);
      }
    }
    try {
      ImageCreatorEnhanced.writeText("Layer_locations", directoryName, files.toString());
    } catch (IOException e) {
      write("Could not write text file for images");
    }
  }

  // saves the given image to the directory, naming it by the given position
  private void saveImage(int i, IImageConverter image) {
    try {
      ImageCreatorEnhanced.writeImage(image, "layer" + i + "." + fileType, directoryName);
      files.append(directoryName + "/layer" + i + "." + fileType + "\n");
    } catch (IOException | IllegalArgumentException e) {
      write("Could not save image: " + e.getMessage());
    }
  }

  // returns the image of the file type format, else null if it's not a valid file type
  private IImageConverter getImage() {
    switch (fileType) {
      case "ppm":
        return model.toPPM();
      case "jpeg":
        return model.toJPEG();
      case "png":
        return model.toPNG();
      default:
        return null;
    }
  }
}
