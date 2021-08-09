package controller.commands;

import controller.AbstractImageCommand;
import java.util.Scanner;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import view.ImageView;

/**
 * The command to load all the images in a text file of the locations to an {@code ImageModel}.
 */
public class LoadImages extends AbstractImageCommand {
  protected String directory;

  /**
   * Creates the function object to load all the images in the directory to the model, regardless
   * of number of layers, and sets the current layer to the last load image. If error in image
   * loading, will still add a layer for each line in the image location text file.
   * @param model the model where to load the images
   * @param view where to output messages
   * @param directory the directory where the text file of the location of the images is located
   */
  public LoadImages(ImageModel model, ImageView view, String directory) {
    super(model, view);
    this.directory = directory;
  }

  @Override
  public void run() {
    String images;
    try {
      images = ImageCreatorEnhanced.readTextFile(directory + "/" + "Layer_locations.txt");
    } catch (IllegalArgumentException e) {
      write("Invalid directory: " + e.getMessage());
      return;
    }
    Scanner scan = new Scanner(images);
    while (scan.hasNext()) {
      model.addLayer();
      model.setCurrent(model.getNumLayers() - 1);
      new Load(model, view, scan.next()).run();
    }
  }
}
