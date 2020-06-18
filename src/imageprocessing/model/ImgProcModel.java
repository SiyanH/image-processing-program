package imageprocessing.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import imageprocessing.operation.Dither;
import imageprocessing.operation.GenerationCheckerBoard;
import imageprocessing.operation.GenerationFlagCH;
import imageprocessing.operation.GenerationFlagFR;
import imageprocessing.operation.GenerationFlagGR;
import imageprocessing.operation.GenerationRainbow;
import imageprocessing.operation.Greyscale;
import imageprocessing.operation.Image;
import imageprocessing.operation.ImageBlurring;
import imageprocessing.operation.ImageProcessing;
import imageprocessing.operation.ImageSharpening;
import imageprocessing.operation.Mosaic;
import imageprocessing.operation.PatternDirection;
import imageprocessing.operation.SepiaTone;

/**
 * This class represents a model for the image processing program. It offers all the methods
 * mandated by the {@link ImageProcessingModel} interface, and contains the image to be processed or
 * generated, and the maps of image processing operation and country alpha code.
 */
public class ImgProcModel implements ImageProcessingModel {
  private Image img;
  private final Map<ProcessingOperation, Function<Image, ImageProcessing>> procOperation;
  private final Map<CountryAlphaCode, BiFunction<Integer, Integer, ImageProcessing>> generation;

  /**
   * Construct a image processing model, and initialize its maps.
   */
  public ImgProcModel() {
    this.procOperation = setOperationMap();
    this.generation = setGenerationMap();
  }

  @Override
  public void setImage(Image img) {
    this.img = img;
  }

  @Override
  public Image getImage() throws IllegalStateException {
    if (img == null) {
      throw new IllegalStateException("No image");
    }

    return img;
  }

  @Override
  public void process(ProcessingOperation operation) throws IllegalStateException {
    if (img == null) {
      throw new IllegalStateException("No image to process");
    }

    Objects.requireNonNull(operation, "Operation cannot be null");

    img = procOperation.get(operation).apply(img).apply();
  }

  @Override
  public void mosaic(int numSeeds) throws IllegalArgumentException, IllegalStateException {
    if (img == null) {
      throw new IllegalStateException("No image to convert to mosaic");
    }

    img = new Mosaic(img, numSeeds).apply();
  }

  @Override
  public void generateCheckerBoard(int size) throws IllegalArgumentException {
    img = new GenerationCheckerBoard(size).apply();
  }

  @Override
  public void generateFlag(int height, int width, CountryAlphaCode country)
          throws IllegalArgumentException {
    Objects.requireNonNull(country, "Country cannot be null");

    img = generation.get(country).apply(height, width).apply();
  }

  @Override
  public void generateRainbow(int height, int width, PatternDirection direction)
          throws IllegalArgumentException {
    img = new GenerationRainbow(height, width, direction).apply();
  }

  /**
   * Set the map of image processing operation for use in the process method. Note that the map must
   * include all the supported operations specified in the {@link ProcessingOperation} enum class,
   * and must not contain null value.
   *
   * @return the map of image processing operation
   */
  private static Map<ProcessingOperation, Function<Image, ImageProcessing>> setOperationMap() {
    Map<ProcessingOperation, Function<Image, ImageProcessing>> operationMap
            = new EnumMap<>(ProcessingOperation.class);

    operationMap.put(ProcessingOperation.BLUR, ImageBlurring::new);
    operationMap.put(ProcessingOperation.SHARPEN, ImageSharpening::new);
    operationMap.put(ProcessingOperation.GREYSCALE, Greyscale::new);
    operationMap.put(ProcessingOperation.SEPIATONE, SepiaTone::new);
    operationMap.put(ProcessingOperation.DITHER, Dither::new);

    return operationMap;
  }

  /**
   * Set the map of country alpha code for use in the generateFlag method. Note that the map must
   * include all the supported codes specified in the {@link CountryAlphaCode} enum class, and must
   * not contain null value.
   *
   * @return the map of country alpha code
   */
  private static Map<CountryAlphaCode, BiFunction<Integer, Integer, ImageProcessing>>
      setGenerationMap() {
    Map<CountryAlphaCode, BiFunction<Integer, Integer, ImageProcessing>> generationMap
            = new EnumMap<>(CountryAlphaCode.class);

    generationMap.put(CountryAlphaCode.CH, GenerationFlagCH::new);
    generationMap.put(CountryAlphaCode.FR, GenerationFlagFR::new);
    generationMap.put(CountryAlphaCode.GR, GenerationFlagGR::new);

    return generationMap;
  }
}
