package controller;

import controller.commands.Blur;
import controller.commands.Checkerboard;
import controller.commands.CreateLayer;
import controller.commands.DuplicateLayer;
import controller.commands.Greyscale;
import controller.commands.Load;
import controller.commands.LoadImages;
import controller.commands.Mosaic;
import controller.commands.RemoveLayer;
import controller.commands.Save;
import controller.commands.SaveAll;
import controller.commands.Sepia;
import controller.commands.SetCurrent;
import controller.commands.Sharpen;
import controller.commands.SwapLayer;
import controller.commands.ToggleVisible;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.ImageCreatorEnhanced;
import model.ImageModel;
import view.ImageView;

/**
 * Represents a controller for {@code ImageModel}, an image editing program. Allows modifying layers
 * of images, loading images of valid type, creating programmatic images, saving one or more images,
 * and modifying the images of the model.
 */
public class ImageEditorController implements ImageController {

  private final Reader rd;
  private final ImageView view;
  private final Map<String, Function<Scanner, ImageCommand>> commandMap;

  /**
   * Creates an {@code ImageEditorController} to control the model, reading input and output
   * messages to the view.
   *
   * @param model the image model
   * @param rd    the reader to input
   * @param view  view to output messages
   * @throws IllegalArgumentException if the given arguments are invalid
   */
  public ImageEditorController(ImageModel model, Reader rd, ImageView view)
      throws IllegalArgumentException {
    if (model == null || rd == null || view == null) {
      throw new IllegalArgumentException("Invalid controller");
    }
    this.rd = rd;
    this.view = view;
    this.commandMap = new HashMap<>();
    commandMap.putIfAbsent("blur", s -> new Blur(model, view));
    commandMap.putIfAbsent("sharpen", s -> new Sharpen(model, view));
    commandMap.putIfAbsent("greyscale", s -> new Greyscale(model, view));
    commandMap.putIfAbsent("sepia", s -> new Sepia(model, view));
    commandMap.putIfAbsent("mosaic", s -> new Mosaic(model, view,s.next()));
    commandMap.putIfAbsent("load", s -> new Load(model, view, s.next()));
    commandMap.putIfAbsent("save", s -> new Save(model, view, s.next()));
    commandMap.putIfAbsent("saveAll", s -> new SaveAll(model, view, s.next(), s.next()));
    commandMap.putIfAbsent("createLayer", s -> new CreateLayer(model, view));
    commandMap.putIfAbsent("setCurrent", s -> new SetCurrent(model, view, s.next()));
    commandMap.putIfAbsent("removeLayer", s -> new RemoveLayer(model, view, s.next()));
    commandMap.putIfAbsent("loadImages", s -> new LoadImages(model, view, s.next()));
    commandMap.putIfAbsent("duplicateLayer", s -> new DuplicateLayer(model, view, s.next()));
    commandMap.putIfAbsent("toggleVisible", s -> new ToggleVisible(model, view, s.next()));
    commandMap.putIfAbsent("swapLayer", s -> new SwapLayer(model, view, s.next(), s.next()));
    commandMap.putIfAbsent("checkerboard",
        s -> new Checkerboard(model, view, s.next(), s.next(), s.next(),
            s.next(), s.next(), s.next(), s.next(), s.next(), s.next()));
  }

  @Override
  public void editImages() throws IllegalStateException {
    Scanner scan = new Scanner(rd);
    write("Use \"script\" or \"type\" image commands: ");
    while (scan.hasNext()) {
      String input = scan.next();
      if (input.equals("script")) {
        write("Type file name: ");

        String fileName = scan.next();
        String text;
        try {
          text = ImageCreatorEnhanced.readTextFile(fileName);
          scan = new Scanner(text);
          break;
        } catch (IllegalArgumentException e) {
          write("Could not read file: " + e.getMessage() +
              " Use \"script\" or \"type\" image commands: ");
        }
      } else if (input.equals("type")) {
        break;
      } else if (input.equalsIgnoreCase("q")) {
        write("\nProgram has ended");
        return;
      } else {
        write("Invalid input. Use \"script\" or \"type\" image commands: ");
      }
    }

    while (scan.hasNext()) {
      ImageCommand c;
      String input = scan.next();
      if (input.equalsIgnoreCase("q")) {
        write("\nProgram has ended");
        return;
      }
      Function<Scanner, ImageCommand> command = commandMap.getOrDefault(input, null);
      if (command != null) {
        c = command.apply(scan);
        c.run();
      } else {
        write("Invalid input ");
      }
    }
    throw new IllegalStateException("Expected input");
  }

  // transmits messages to view
  protected void write(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not render message");
    }
  }
}
