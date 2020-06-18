# Image Processing Program

Version 1.0

Image Processing Program is a program to apply various image processing operations on 24-bit images. 
It can also generate images with rainbow stripes and a checkerboard pattern, and create a 
national flag of a user-specified size, but of the prescribed proportions.

This is the first version of the program. The program is still under development now, and thus is 
subject to change in the future.  

## Features

The program delivers several features.  

The features completed in version 1.0:

1. Image processing operations: Blurring, sharpening, greyscale and sepia tone.

2. Image generation operations: Horizontal/vertical rainbow stripes, checkerboard pattern, 
flags of France, Greece and Switzerland.

More features will come in next version.


## Usage

Run the main method in `ImageProcessingProgram` class. It already contains a template code to 
perform all the supported image processing operations. 

The program can suitably loads and saves images, apply various image processing operations on them, 
and also generates various images with different patterns. 

All the image files to read and write are in `res/` folder. The images to be generated wll also be
put in this folder.

The folder contains three images (`imgXX.jpg`) to perform image processing operations on, the 
results will be four new images (`imgXX_XX.jpg`) for each image. After running the program, two 
checkerboard images, three flag images and two rainbow images will be generated. 


## Structure

`ImageProcessing`: The top interface for all the operations supported by this program. All image 
operation classes implement this interface.

`AbstractImageProcessing`: The abstract class for all operations of processing type, such as 
blurring and sharpening. It implements `ImageProcessing`.

`AbstractImageGeneration`: The abstract class for all operations of generation type, such as flag 
generation and checkerboard generation. It implements `ImageProcessing`.

`ImageFiltering`: The abstract class for all filtering operations. It extends 
`AbstractImageProcessing`.

`ColorTransformation`: The abstract class for all color transformation operations. It extends 
`AbstractImageProcessing`.

`GenerationBoard`: The class for generating a board pattern with alternating black and white 
color squares.

`GenerationCheckerBoard`: The class for generating a checker board pattern.
                                                                                   
`GenerationFlag`: The abstract class for all flag generation operations.

`GnerationRainbow`: The class for generating an image with rainbow stripes pattern.

`GreyScale`, `SepiaTone`, `ImageBlurring`, `ImageSharpening`: Classes for image processing 
operations.
 
`GenerationFlagXX`: The class for generating national flag of XX.

`Image`: The class for representing 24-bit image data.

`ImageuUtil`: The class for some image reading and writing utilities.

`PatternDirection`: The enum class for representing the direction of a generated pattern.

## Image Citations

Image 0 was downloaded from the 
[course website](https://course.ccs.neu.edu/cs5004/manhattan-small.png).

Image 1 and 2 were downloaded from [Pexels](https://www.pexels.com/) and can be used for free 
according to their terms of use.

Image 1: 
[By Rumples Grumples](https://www.pexels.com/photo/photo-of-tractor-on-fields-1033716/)

Image 2:
[By Peter de Vink](https://www.pexels.com/photo/photography-of-airplane-near-mountain-849534/)


