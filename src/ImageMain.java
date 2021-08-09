import controller.ImageController;
import controller.ImageEditorController;
import controller.ImageGraphicsController;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.ImageModel;
import model.LayeredImages;
import view.ImageEditorView;
import view.ImageGraphicsView;
import view.ImageView;
import view.JFrameImageView;

/**
 * Main Class used to run the program.
 */
public class ImageMain {

  /**
   * Main method used to run the program. Changes between interactive scripting, scripts, and GUI
   * depending on cmd line args.
   *
   * @param args command line input
   */
  public static void main(String[] args) throws IllegalArgumentException {
    ImageModel model = new LayeredImages();

    if (args.length == 0) {
      throw new IllegalArgumentException("No Args Given");
    }

    if (args[0].equals("-interactive")) {
      ImageGraphicsView view1 = new JFrameImageView();
      ImageGraphicsController controller = new ImageGraphicsController(model, view1);
    } else if (args[0].equals("-text")) {
      ImageView view = new ImageEditorView(System.out);
      ImageController controller = new ImageEditorController(model,
          new InputStreamReader(System.in), view);
      controller.editImages();
    } else if (args[0].equals("-script")) {
      if (args.length >= 2) {
        String scriptInput = "script " + args[1] + " q";
        StringReader reader = new StringReader(scriptInput);
        ImageView view = new ImageEditorView(System.out);
        ImageController controller = new ImageEditorController(model, reader, view);
        controller.editImages();
      } else {
        throw new IllegalArgumentException("Invalid type");
      }


    }


  }


}
