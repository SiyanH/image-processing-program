package imageprocessing.operation;

import java.util.Objects;

/**
 * This class represents a 24-bit rainbow stripes image generation operation, and offers all the
 * methods mandated by the {@link ImageProcessing} interface. It contains the direction of the
 * stripes (horizontal or vertical) to be generated.
 */
public class GenerationRainbow extends AbstractImageGeneration {
  private PatternDirection direction;

  /**
   * Construct a rainbow stripes generation object with the given height and width of the rainbow
   * image, and the direction of the stripes to be generated.
   *
   * @param height    the height of the rainbow image to be generated
   * @param width     the width of the rainbow image to be generated
   * @param direction the direction of the rainbow stripes to be generated
   * @throws IllegalArgumentException if the given height or width of the image is not positive
   */
  public GenerationRainbow(int height, int width, PatternDirection direction)
          throws IllegalArgumentException {
    super(height, width);
    this.direction = Objects.requireNonNull(direction, "The direction cannot be null");
  }

  @Override
  protected int[] color(int i, int j) {
    int stripe;
    if (direction == PatternDirection.HORIZONTAL) {
      stripe = (int) Math.round(height / 7.0);
      return rainbowColor(i / stripe + 1);
    } else {
      stripe = (int) Math.round(width / 7.0);
      return rainbowColor(j / stripe + 1);
    }
  }

  /**
   * Return as an array the RGB values of the pixel at (i, j) of the image to be generated. The
   * order of the values in the array is red, green and blue.
   *
   * @param i the position of the pixel
   * @return the RGB values of the pixel at the given position as an array
   */
  private int[] rainbowColor(int i) {
    int[] color;
    switch (i) {
      case 1:
        color = new int[]{255, 0, 0};
        break;
      case 2:
        color = new int[]{255, 127, 0};
        break;
      case 3:
        color = new int[]{255, 255, 0};
        break;
      case 4:
        color = new int[]{0, 255, 0};
        break;
      case 5:
        color = new int[]{0, 0, 255};
        break;
      case 6:
        color = new int[]{75, 0, 130};
        break;
      default:
        color = new int[]{148, 0, 211};
    }
    return color;
  }
}
