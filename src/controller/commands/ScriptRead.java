package controller.commands;

import controller.AbstractImageCommand;
import controller.ImageEditorController;
import java.io.Reader;
import java.io.StringReader;
import model.ImageModel;
import view.ImageView;

/**
 * The command to read a script from a text file to modify {@code ImageModel}.
 */
public class ScriptRead extends AbstractImageCommand {

  private final String dir;

  /**
   * Creates the command to read a script.
   *
   * @param model model to mutate
   * @param view  view to mutate
   * @param dir   location of the script
   */
  public ScriptRead(ImageModel model, ImageView view, String dir) {
    super(model, view);
    this.dir = dir;
  }

  @Override
  public void run() {
    if (dir != null && !dir.equals("")) {
      String inputs = "script " + dir + " q";
      Reader reader = new StringReader(inputs);

      //use the ImageEditorController to deal with scripts, this should be fine as long as
      //we make sure to close it. Model and View will be mutated still.
      new ImageEditorController(model, reader, view).editImages();
    } else {
      write("Invalid Script Location");
    }

  }
}
