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
import controller.commands.ScriptRead;
import controller.commands.Sepia;
import controller.commands.SetCurrent;
import controller.commands.Sharpen;
import controller.commands.SwapLayer;
import controller.commands.ToggleVisible;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import model.ImageModel;
import view.ImageGraphicsView;

/**
 * Represents a controller for {@code ImageModel}, an image editing program. Allows modifying layers
 * of images, loading images of valid type, creating programmatic images, saving one or more images,
 * and modifying the images of the model.
 */
public class ImageGraphicsController implements ActionListener {

  //same model
  private final ImageModel model;
  //view is too different from textual view
  private final ImageGraphicsView view;
  //Supplier used so methods like view.loadImages() don't make the program instant pop dialog
  //and instead are only first called when the command is called
  private final Map<String, Supplier<ImageCommand>> commandMap;

  /**
   * Creates an {@code ImageGraphicsController} to control the model and output the model to view.
   *
   * @param m the image model
   * @param v view to display the model
   * @throws IllegalArgumentException if the given arguments are invalid
   */
  public ImageGraphicsController(ImageModel m, ImageGraphicsView v)
      throws IllegalArgumentException {

    if (m == null || v == null) {
      throw new IllegalArgumentException("Null Input");
    }

    this.model = m;
    this.view = v;
    view.setListener(this);
    view.display();
    view.setImage(model.getBufferedImage());

    this.commandMap = new HashMap<>();
    commandMap.putIfAbsent("Load file", () -> new Load(model, view, view.loadImages()));
    commandMap.putIfAbsent("Save file", () -> new Save(model, view, view.saveImage()));
    commandMap.putIfAbsent("Create Layer", () -> new CreateLayer(model, view));
    commandMap.putIfAbsent("Blur", () -> new Blur(model, view));
    commandMap.putIfAbsent("Sharpen", () -> new Sharpen(model, view));
    commandMap.putIfAbsent("Sepia", () -> new Sepia(model, view));
    commandMap.putIfAbsent("Grey", () -> new Greyscale(model, view));
    commandMap
        .putIfAbsent("Save All PPM", () -> new SaveAll(model, view, view.getDirectory(), "ppm"));
    commandMap
        .putIfAbsent("Save All PNG", () -> new SaveAll(model, view, view.getDirectory(), "png"));
    commandMap
        .putIfAbsent("Save All JPEG", () -> new SaveAll(model, view, view.getDirectory(), "jpeg"));
    commandMap.putIfAbsent("Load All", () -> new LoadImages(model, view, view.loadDirectory()));

    commandMap.putIfAbsent("Script", () -> new ScriptRead(model, view, view.loadTXT()));

    commandMap.putIfAbsent("Current", () -> new SetCurrent(model, view, view.chooseLayer(
        model.getNumLayers())));
    commandMap.putIfAbsent("Remove",
        () -> new RemoveLayer(model, view, view.chooseLayer(model.getNumLayers())));
    commandMap.putIfAbsent("Toggle",
        () -> new ToggleVisible(model, view, view.chooseLayer(model.getNumLayers())));
    commandMap.putIfAbsent("Swap",
        () -> new SwapLayer(model, view, view.chooseLayer(model.getNumLayers()), view.chooseLayer(
            model.getNumLayers())));
    commandMap.putIfAbsent("Duplicate",
        () -> new DuplicateLayer(model, view, view.chooseLayer(model.getNumLayers())));
    commandMap.putIfAbsent("Mosaic",
        () -> new Mosaic(model, view, view.getNum("Number of Seeds")));
    commandMap.putIfAbsent("Checkerboard",
        () -> new Checkerboard(model, view, view.getNum("Size"), view.getNum("Width"),
            view.getNum("Height"),
            view.getNum("Red1"), view.getNum("Green1"), view.getNum("Blue1")
            , view.getNum("Red2"), view.getNum("Green2"), view.getNum("Blue2")));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ImageCommand c;
    String input = e.getActionCommand();
    Supplier<ImageCommand> command = commandMap.getOrDefault(input, null);
    if (command != null) {
      c = command.get();
      c.run();
      view.setImage(model.getBufferedImage());
    } else {
      try {
        view.renderMessage("Bad Command");
      } catch (IOException err) {
        throw new IllegalStateException();
      }
    }

  }
}
