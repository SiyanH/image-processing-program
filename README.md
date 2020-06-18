# Image Processing Program

Version 2.0

Image Processing Program is a program to apply various image processing operations on 24-bit images. 
It can also generate images with rainbow stripes and a checkerboard pattern, and create a 
national flag of a user-specified size, but of the prescribed proportions.

This is the second version of the program. The program is still under development now, and thus is 
subject to change script the future.  

## Features

The program delivers several features.  

1. Image processing operations: Blurring, sharpening, greyscale, sepia tone, dither and mosaic.

2. Image generation operations: Horizontal/vertical rainbow stripes, checkerboard pattern, 
flags of France, Greece and Switzerland.

3. Command script support.

More features will come in the next version.

## Usage

The program takes a command script file as its argument. It can suitably loads and saves images, 
apply various image processing operations on them, and also generates various images with different 
patterns, according to the script provided by the user. 

You can find some script example files and example images under `res/` folder of the project.

Two ways to run the program:

1. From IntelliJ: Run the main method in the `ImageProcessingProgram` class with a script file.
2. From `jar` file: `java -jar "Assignment 9.jar" your-script-file.txt`

### Script Format

The script file is a plain `txt` file containing all the commands to be executed. 

A maximum of one command is allowed for each line. The command keywords (e.g. `load`) and its 
argument (e.g. `img.png`) should be separated by one or more white spaces. Any leading or trailing 
spaces will be automatically ignored. There should be no empty line between the commands.

### Supported Commands

#### Command Syntax

Note: `OR` means choosing one of the available optional arguments.

1. `load OR save [file]`:

   - load or save an image from a file
   
   - `file`: the relative path to the image file

2. `greyscale OR blur OR sepiatone OR sharpen OR dither [file]`

   - `file`: the relative path to the image file

3. `mosaic [numberOfSeeds]`

   - create an image mosaic
   
   - `numberOfSeeds`: the number of seeds used to create mosaic (a positive integer)

4. `generate checkerboard [sizeOfSquares]`
  
   - generate a checker board image
   
   - `sizeOfSquares`: the size of squares on the checker board (a positive integer)

5. `generate flag [height] [width] [countryAlphaCode]`

   - generate a national flag image
   
   - `height`: the height of the image
   
   - `width`: the width of the image
   
   - `countryAlphaCode`: the [country alpha code](https://countrycode.org) for the country of which 
   the flag is to be generated

6. `generate rainbow [height] [width] v OR h`

   - generate a rainbow image
   
   - `height`: the height of the image
     
   - `width`: the width of the image
   
   - `v OR h`: `v` for vertical stripes, `h` for horizontal stripes

#### About image file:

Supported formats: JPEG, WBMP, PNG, BMP, GIF.

The file uses relative path.

If the program is run from IntelliJ, the file path is relative to the 
project folder; if the program is run from `jar` file, the file path is relative to the current 
folder (for example, if you run the `jar` file provided in `res/` folder of this project, a `load 
img.png` command will search for `img.png` within this folder to load). 

*Do not precede the path with `/`.*


## Structure

`imageprocessing.controller`: the code for the controller

`imageprocessing.model`: the code for the model

`imageprocessing.operation`: the code for all image processing/generation operations

`imageprocessing.util`: the util code for the program (File I/O)


## Image Citations

Image 0 was downloaded from the 
[course website](https://course.ccs.neu.edu/cs5004/manhattan-small.png).

Image 1 and 2 were downloaded from [Pexels](https://www.pexels.com/) and can be used for free 
according to their terms of use.

Image 1: 
[By Rumples Grumples](https://www.pexels.com/photo/photo-of-tractor-on-fields-1033716/)

Image 2:
[By Peter de Vink](https://www.pexels.com/photo/photography-of-airplane-near-mountain-849534/)