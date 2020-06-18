/**
 * This abstract class contains the common field and constructor for all types of color
 * transformation operations on 24-bit images, and offers all the methods mandated by the {@link
 * ImageProcessing} interface. It contains the linear color transformation matrix of this operation.
 * A linear color transformation is simply a color transformation in which the final red, green and
 * blue values of a pixel are linear combinations of its initial red, green and blue values. For
 * example, if the initial color of the pixel at (5, 4) of the image is (r, g, b), then the final
 * red value is 0.3r + 0.4g + 0.6b for transMatrix[0] = {0.3, 0.4, 0.6}. Examples of such operation
 * are greyscale and sepia tone, etc.
 */
public abstract class ColorTransformation extends AbstractImageProcessing {
  protected double[][] transMatrix;

  /**
   * Construct a color transformation object with the given image.
   *
   * @param img the image to which this color transformation operation is to be applied
   * @throws IllegalArgumentException if the given image is null
   */
  public ColorTransformation(Image img) throws IllegalArgumentException {
    super(img);
  }

  @Override
  public Image apply() {
    int[][][] rgb = img.getRGB();
    int height = img.getHeight();
    int width = img.getWidth();
    int[][][] rgbOutput = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgbOutput[i][j][0] = (int) Math.round(transMatrix[0][0] * rgb[i][j][0]
                + transMatrix[0][1] * rgb[i][j][1] + transMatrix[0][2] * rgb[i][j][2]);
        rgbOutput[i][j][1] = (int) Math.round(transMatrix[1][0] * rgb[i][j][0]
                + transMatrix[1][1] * rgb[i][j][1] + transMatrix[1][2] * rgb[i][j][2]);
        rgbOutput[i][j][2] = (int) Math.round(transMatrix[2][0] * rgb[i][j][0]
                + transMatrix[2][1] * rgb[i][j][1] + transMatrix[2][2] * rgb[i][j][2]);
      }
    }

    clamp(rgbOutput);

    return new Image(rgbOutput);
  }
}