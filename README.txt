== Changes ==
 - We implemented only mosaic for the EC. We implemented it by extending our original user interface with blur/sepia/grey/sharpen in an interface called
   Image Editor Enhanced where we added the mosaic method. We then changed our AbstractImage class to implement this class instead of ImageEditor and added
   the extra code to the class without modifying previous code. We decided to implement the class there since we thought it would be beneficial for clarity
   as mosaic is an operation similar to blur,sepia,greyscale, and sharpen and we didn't have to modify previous methods/ fields.

 - Changes From A6 was that an aliasing issue associated with duplicate was fixed. A new controller was implemented called ImageGraphicsController since
   the method of user input differed greatly from the previous controller. 

 - Mosaic and ScriptRead commands we're added to accomodate new demands.

 - New ImageGraphicsView implemented since textual view and gui view differed greatly and had different methods. New JFrameImageView class implemented
   this and was the class that we used for the JFrame GUI.

==Model==
 - ImageEditor interface represents the model for one image. Contains void methods to modify ImageEditor objects like sharpen,blur, grayscale, sepia
   to mutate the ImageEditor object.

 - IImageConverter interface represents an extension of the ImageEditor interface including methods to convert from format object to format object.
   Contains method to get byte information for each specific image format. Method to compare two images' dimensions, and method to provide a deep copy.

 - ImageModel interface represents the model for a multilayered Image and extends IImageConverter. Contains methods to manipulate layer images in regards to visibility, 
   loading images, modifying images on layres, swapping layers, adding layers, setting the current layer, duplicating layers, and checking if a layer has an image. 

 - IPixel is an interface that represents a single pixel in an image. Contains methods to return modified versions of this IPixel like add, multiply, clampBounds,
   and linearCombine(multiplies pixel by 3x3 color changing matrix).

 - Pixel class implements the interface and represents a pixel for the PPMImage/possibly more.  Contains private final int values red, green, and blue(rgb values).

 - AbstractImage class implements ImageEditor and assumes that a majority of image formats will have an array of pixels minimum,
   such that information like width, height, max integer value for color can be supplied by the wanted image format or at least implied 
   from the array of pixels. As a result, class abstracts image editing methods. 
   If a class is truly different from this format, the new Image class can simply just ignore this abstract class and implement ImageEditor.
   TLDR: Class stores width,height,max int color val, and array of pixels, performs editing operations using this data.

 - AbstractImageEnhanced extends AbstractImage and implements IImageConverter. This is an extension of AbstractImage and represents methods in IImageConverter
   Utilizes the same exact constructor used in AbstractImage.
   
 - PPMImage extends AbstractImage and does not override ImageEditor methods. PPMImage represents an object
   where PPM file information can be stored. PPMImage will only differ in comparison to other ImageEditor objects in how it is exported to a
   file. Overriding each methods toString will allow us to do this.

 - PPMImageEnhanced extends AbstractImageEnhanced and represents an improved PPMImage class. Constructor uses the super constructor but also creates
   a delegate for ease of implementing previous code. Contains methods for switching format, getting bytes, and other methods mentioned in IImage Covnerter.

 - JPEGImage class extends AbstractImageEnhanced and represents a class for JPEG Images. Constructor uses super constructor. Contains methods in
   IImageConverter interface.

 - PNGImageclass extends AbstractImageEnhanced and represents a class for PNGImages. Constructor uses super constructor. Contains methods in
   IImageConverter interface.

 - LayeredImages extends the ImageModel interface and represents the model used in our photoshop editor. Contains several layers in which images
   can be loaded into and manipulated. Constructor constructs a model with no layers and takes in no input. Implements all functionality previously
   stated in the ImageModel interface

 - ImageCreator util class contains methods to programmatically create images(Checkerboard) ,methods to read file data and store the information into ImageEditor objects,
   and export ImageEditor. All methods in this class are static which is why no interface is implemented because that is unnecessary. 

 - ImageCreatorEnhanced util class extend ImageCreator util class and represents an enhanced ImageCreator class. Supports reading and writing 
   IImageConverter objects(ppm, jpeg, png) as well as creating them programmatically. 



==View==
 - ImageView interface represents the view used for our Photoshop editor(Textual). Contains one method renderMessage that takes in a message and outputs it
   to the view.
 - ImageEditorView implements ImageView interface and contains a constructor that takes in an appendable for which the view will be transmitted to.
   Implements one method renderMessage that is used for guiding the client(input query and error messages)
 
 - **NEW** ImageGraphicsView interface represents the view used for the GUI. It extends the ImageView interface as it the view also mandates we give
   the user confirmation the operation requested succeeded/failed and ImageView has renderMessage which is useful for this. Interface contains methods
   to show the user the image representation of the model, methods to prompt user input, methods to show errors/operation success.

 - **NEW** JFrameImageView class is the GUI view. It extends JFrame and implements ImageGraphics view. The entire GUI ranging from menus, image, buttons
   is constructed in the constructor. Contains method to setListener to an action listener, which is our controller in this case.


==Controller==
 - ImageController interface represents the controller used for our photshop editor and has one method, editImages, which runs the photoshop program.
   
 - ImageEditorController class implements ImageController and is the actual controller class used in the program. Constructor takes in an input/output
   sources and an ImageModel. Contains private fields too view, and command map. View is constructed from the output stream and command map is initialized
   to contain all the known commands used in our controller.

 - ImageCommand interface represents an interface for all the commands used in an ImageModel. Contains one method run which is used to execute the command.

 - AbstractImageCommand implements the ImageCommand interface and takes in a constructor for model and view. Contains a method write which is a protected method
   useful for writing to view without having to try catch every single time we want to communicate with the view.

 - Commands folder contains every command used. They are Blur, CreateLayer, DuplicateLayer, Greyscale, Load, LoadImages, RemoveLayer, Save, SaveAll,
   Sepia, SetCurrent, Sharpen, SwapLayer, ToggleVisible. Each are their own separate class.

 - **NEW** Mosaic and ScriptRead commands added.

 - **NEW** ImageGraphicsController extends the action listener and contains only the method actionPerformed from action listener. Contains a command map similar
   to the textual view controller and uses the same commands. However, uses supplier instead of function since method of constructing each command is different
   as previously input was sent through the console, now it is sent through the view.


== Citation ==
 - **NEW** Uyuni Image - https://www.journeygazer.com/destination-item/salar-de-uyuni-bolivia/
  