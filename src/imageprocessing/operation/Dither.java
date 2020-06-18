package imageprocessing.operation;

/**
 * This class represents the dithering operation on a 24-bit image, and offers all the methods
 * mandated by the {@link ImageProcessing} interface. It contains the image to be processed and uses
 * the Floyd-Steinberg algorithm.
 */
public class Dither implements ImageProcessing {
  private Image img;

  /**
   * Construct an image dithering object with the given image.
   *
   * @param image the image to be dithered
   */
  public Dither(Image image) {
    this.img = image;
  }

  @Override
  public Image apply() {
    Greyscale grey = new Greyscale(img);
    Image greyedImg = grey.apply();
    int[][][] rgb = greyedImg.getRGB();
    int height = greyedImg.getHeight();
    int width = greyedImg.getWidth();

    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        for (int k = 0; k < 3; ++k) {
          // (i, j) in image
          int old_color = rgb[i][j][k];
          int new_color = old_color > 128 ? 255 : 0;
          int error = old_color - new_color;
          rgb[i][j][k] = new_color;

          if (j + 1 < width) {
            rgb[i][j + 1][k] += (int) Math.round(7 * error / 16.0);
          }

          if (i + 1 < height) {
            rgb[i + 1][j][k] += (int) Math.round(5 * error / 16.0);

            if (j - 1 >= 0) {
              rgb[i + 1][j - 1][k] += (int) Math.round(3 * error / 16.0);
            }

            if (j + 1 < width) {
              rgb[i + 1][j + 1][k] += (int) Math.round(error / 16.0);
            }
          }
        }
      }
    }

    return new Image(rgb);
  }
}
