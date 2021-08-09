package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Interface to represent a graphical GUI view of an image model. Extends ImageView as
 * ImageGraphicsView GUI uses a display to show errors/others. Contains methods which prompt the
 * user for input which is sent to the controller. Does not ever require the model.
 */
public interface ImageGraphicsView extends ImageView {

  /**
   * Set each button, menu item, etc. to the action listener controller class.
   *
   * @param imageGraphicsController the action listener controller class
   */
  void setListener(ActionListener imageGraphicsController);

  /**
   * Display the JFrame GUI.
   */
  void display();

  /**
   * Prompts the user to choose a file to load.
   *
   * @return absolute path of the file in String format.
   */
  String loadImages();

  /**
   * Displays the given image to the GUI.
   *
   * @param bufferedImage Image to load
   */
  void setImage(BufferedImage bufferedImage);

  /**
   * Prompt the user to create a file in a directory.
   *
   * @return absolute path of the file to create in String format.
   */
  String saveImage();

  /**
   * Prompt the user to create a directory in a directory.
   *
   * @return absolute path of the directory to create in String format.
   */
  String getDirectory();

  /**
   * Prompts the user to choose a directory to load.
   *
   * @return absolute path of the directory to load in String format.
   */
  String loadDirectory();

  /**
   * Prompt the user to choose from a selection of layers from 1 to the number of layers.
   *
   * @param layers number of layers
   * @return String representation of a layer
   */
  String chooseLayer(int layers);

  /**
   * Prompt the user to choose a txt file to load.
   *
   * @return absolute path of txt file to load.
   */
  String loadTXT();

  /**
   * Prompt the user to input a number.
   *
   * @param message message attached to prompt
   * @return String representation of the input.
   */
  String getNum(String message);
}
