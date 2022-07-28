# Interactive Scripting

Commands:

--------------------------------------------------------------------------
- supports file types: PPM, JPEG, PNG
- layers begin at index 1
- additional input needs to be after the specific command
--------------------------------------------------------------------------

q/Q - quit the program, cannot be used within a command where a command
        requires additional input (removeLayer q 1 - will not quit)

-----------------------
Modify Layers Commands:
-----------------------

createLayer - adds an invisible layer to the top of the layered images
              keeping the current at the same index layer
EX: createLayer (one invisible blank layer in model now)

removeLayer (int) - removes the layer at a given index if there is a layer
              additional argument must be an integer after the command
EX: removeLayer 2
    removeLayer one -> "Not a numeric index"

duplicateLayer (int) - copies the layer at the given index onto the current
               layer, can only duplicate layers that have an image (not blank)
                additional argument must be an integer after the command
EX: duplicateLayer 1
    (if layer at index has no image) duplicateLayer 2 -> 
                                    "Could not duplicate layer to current"
    duplicateLayer zero -> "Not a number"

swapLayer (int) (int) - swaps the positions of two layers at the given indices
                       the layer at the first index will switch positions with the
                       layer at the second index
                       additional input must be two separate integers after command
EX: swapLayer 1 0 -> "Invalid index"
    createLayer createLayer swapLayer 1 2

setCurrent (int) - sets the layer currently working on to the layer at the 
            given index if there is a layer, else current stays the same
             additional input must be an integer after command
EX: setCurrent 1
    setCurrent 0 -> "Invalid index"

toggleVisible (int) - toggles the visibility of the layer at the given index
                   if there is a layer, additional input must be an integer after command
EX: toggleVisible 1

--------------------------------------------------------------------------

------------------------------
Import/Export Images Commands: 
------------------------------
- imports single images/creates images at the current layer
- there must be a layer for importing, creating, or saving a single image

checkerboard (int) (int) (int) (int) (int) (int) (int) (int) (int) -
        creates a checkerboard of the given arguments onto the current layer
       must be positive integers in this order after the command: 
size (of tile) width (num tiles in board width-wise) height (num tiles in board height-wise) 
red1 green1 blue1 (components of first color) red2 green2 blue2 (components of second color)
EX: checkerboard 5 10 10 0 0 0 255 255 255
    checkerboard 1 2 -1 10 10 10 20 20 20 -> 
     "Could not create checkerboard with given arguments: Invalid dimensions"

load (String) - loads the image of the given file name onto the current layer
               all loaded images must be the same dimensions
              additional input must be a valid file (ppm, jpeg, png) name
EX: load res/Koala.ppm
    load Tree.png
    load Koala.txt -> "Invalid file type"

loadImages (String) - loads all the valid images types recorded in a text file to this
                     regardless of number of layers, does not modify current layer, 
                      images that can be loaded must have a valid file type, be in the
                      location described in the text file and have the same dimension as
                       the first loaded image of the program
                     additional input is the directory where the text file is located
EX: loadImages res/images
    loadImages res/errorImages -> "Could not load jpeg"

save (String) - saves the topmost visible layer as an image if there
               is an image at the layer
               requires additional input for the file name to save as
EX: save Koala.txt -> "Invalid file name"
    (if image doesn't exist) save Koala.jpeg -> "No image to save"
    (no visible layers) save Koala.jpeg -> "No visible layers to save image"

saveAll (String) (String) - saves all the visible layers into each separate image to the 
                       given valid file type along with a text file
                      containing the locations of each image into a directory
                     additional input is in the order: directoryName fileType
EX: saveAll res/Trees png
    saveAll C://images ppm

--------------------------------------------------------------------------

------------------------
Editing Images Commands:
------------------------
- modifies at the current layer
- must have an image to modify

blur - blurs the image at the current layer if there is an image
EX: blur -> "Could not blur"
    createLayer blur -> "Could not blur"
    load exImage.ppm blur

sharpen - sharpens the image at the current layer if there is an image
EX: sharpen -> "Could not sharpen"
    createLayer load exImage.ppm sharpen

greyscale - applies the greyscale transformation on the image at the
           current layer if there is an image
EX: greyscale -> "Could not apply greyscale"
    createLayer load exImage.ppm greyscale

sepia - applies the sepia transformation on the image at the 
          current layer if there is an image
EX: sepia -> "Could not apply sepia"
    createLayer load exImage.ppm sepia



=== JFrame GUI ===

--------------------------------------------------------------------------
- supports file types: PPM, JPEG, PNG
- layers begin at index 1
--------------------------------------------------------------------------

Exit - quit the program

-----------------------
Top Layer Image Panel:
-----------------------
-Shows the top most image of the model
-If it there is none, show a generic black/white checkerboard indicating no image
 is visible in the model.

-----------------------
Modify Layers Menu:
-----------------------

Create Layer Menu Item - adds an invisible layer to the top of the layered images
             		 keeping the current at the same index layer

Remove Layer Menu Item- removes the layer at a given index if there is a layer
               		GUI prompts a dialog for user to choose one of the valid layers.

Duplicate Layer Menu Item - copies the layer at the given index onto the current
             		    layer, can only duplicate layers that have an image (not blank).
               		    GUI prompts a dialog for user to choose one of the valid layers to duplicate.

Swap Layers Menu Item - swaps the positions of two layers at the given indices
                        the layer at the first index will switch positions with the
                        layer at the second index. GUI prompts a dialog for user to choose one of the valid layers
                        twice, one for each of the layers to swap.

Current Layer Menu Item- sets the layer currently working on to the layer at the 
          		 given index if there is a layer, else current stays the same.
          		 GUI prompts a dialog for user to choose one of the valid layers.



Toggle Layer Menu Item - toggles the visibility of the layer at the given index
              		 if there is a layer.
			 GUI prompts a dialog for user to choose one of the valid layers.

--------------------------------------------------------------------------

------------------------------
File Menu: 
------------------------------
- imports single images/creates images at the current layer
- there must be a layer for importing, creating, or saving a single image

Create Checker Menu Item -
        creates a checkerboard of the given arguments onto the current layer
        must be positive integers in this order:
	size (of tile) width (num tiles in board width-wise) height (num tiles in board height-wise) 
	red1 green1 blue1 (components of first color) red2 green2 blue2 (components of second color)
	GUI prompts the user to input for every single one of these fields.

Open Menu Item - loads the image of the given file name onto the current layer
        	 all loaded images must be the same dimensions
                 Opens an open dialog for which the absolute path of the file is input into the command.
		 Only allows PPM, JPEG, PNG to be selected.


Load All Menu Item - loads all the valid images types recorded in a text file to this
                     regardless of number of layers, does not modify current layer, 
                     images that can be loaded must have a valid file type, be in the
                     location described in the text file and have the same dimension as
                     the first loaded image of the program.
		     Opens an open dialog for which the absolute path of the file is input into the command.
	             Only allows Directories to be selected.

Save Menu Item - saves the topmost visible layer as an image if there
                 is an image at the layer
                 Opens a save Dialog for which the user inputs the name/format of the file.

------------------------
Save All SubMenu:
------------------------
ppm - saves all the visible layers into each separate image to the 
      given valid file type along with a text file
      containing the locations of each image into a directory in ppm format.
      Opens a save dialog. User should input a directory name.

jpeg - saves all the visible layers into each separate image to the 
       given valid file type along with a text file
       containing the locations of each image into a directory in jpegformat.
       Opens a save dialog. User should input a directory name.

png - saves all the visible layers into each separate image to the 
      given valid file type along with a text file
      containing the locations of each image into a directory in png format.
      Opens a save dialog. User should input a directory name.


--------------------------------------------------------------------------

------------------------
Transformation Menu:
------------------------
- modifies at the current layer
- must have an image to modify

Blur Current Menu Item - blurs the image at the current layer if there is an image


Sharpen Current Menu Item - sharpens the image at the current layer if there is an image


Greyscale Current Menu Item- applies the greyscale transformation on the image at the
         		     current layer if there is an image


Sepia Current Menu Item- applies the sepia transformation on the image at the 
 	                 current layer if there is an image

Mosaic Current Menu Item - applies the mosaic transformation on the image at the current
			   layer if there is an image. Opens a Dialog for the user to input how many
	                   seeds they want.

------------------------
Script Menu:
------------------------
- Loads a script and runs every single command in the script.

Load Script - Loads and reads through every command and a script and applies each command to the model.
	      Opens a Open dialog for the user to choose a text file script.


------------------------
Transform/filter panel:
------------------------
- Exists for ease of use

Blur Button - same as Blur Current Menu Item.

Sharpen Button - same as Sharpen Current Menu Item.

Sepia Button - same  as Sepia Current Menu Item

Grey Button - same as Greyscale Current Menu Item

Mosaic Button - same as Mosaic Current Menu Item

------------------------
Create Layer panel:
------------------------
- Exists for ease of use

- Create Layer Button - same as Create Layer Menu Item


------------------------
Error/Success Display:
------------------------
-Displays whether or not the last operation succeeded or not.





