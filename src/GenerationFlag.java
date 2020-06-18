/**
 * This abstract class contains the common field and constructor for all types of 24-bit national
 * flag image generation operations, and offers all the methods mandated by the {@link
 * ImageProcessing} interface. It has the proportion/ratio (width to height ratio) of the flag to be
 * generated.
 */
public abstract class GenerationFlag extends AbstractImageGeneration {
  protected double ratio;

  /**
   * Construct a flag generation object with the given height, width and ratio (width to height
   * ratio) of the flag to be generated. If the width is not in proportion to the height, the width
   * is re-calculated according to the ratio.
   *
   * @param height the height of the flag to be generated
   * @param width  the width of the flag to be generated
   * @param ratio  the ratio of the flag to be generated
   * @throws IllegalArgumentException if the given height, width or ratio of the flag is not
   *                                  positive
   */
  public GenerationFlag(int height, int width, double ratio) throws IllegalArgumentException {
    super(height, width);

    if (ratio <= 0) {
      throw new IllegalArgumentException("The ratio of the flag should be non-positive");
    }

    this.ratio = ratio;

    if ((double) width / height != ratio) {
      this.width = (int) Math.round(height * ratio);
    } else {
      this.width = width;
    }
    this.rgb = new int[height][this.width][3];
  }
}
