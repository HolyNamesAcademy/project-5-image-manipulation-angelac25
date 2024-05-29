import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Static utility class that is responsible for transforming the images.
 * Each function (or at least most functions) take in an Image and return
 * a transformed image.
 */
public class ImageManipulator {
    /**
     * Loads the image at the given path
     * @param path path to image to load
     * @return an Img object that has the given image loaded
     * @throws IOException
     */
    public static Img LoadImage(String path) throws IOException {
        return new Img(path);
    }

    /**
     * Saves the image to the given file location
     * @param image image to save
     * @param path location in file system to save the image
     * @throws IOException
     */
    public static void SaveImage(Img image, String path) throws IOException {
        image.Save(path.substring(path.length()-3), path);
        // Implement this method and remove the line below

    }

    /**
     * Converts the given image to grayscale (black, white, and gray). This is done
     * by finding the average of the RGB channel values of each pixel and setting
     * each channel to the average value.
     * @param image image to transform
     * @return the image transformed to grayscale
     */
    public static Img ConvertToGrayScale(Img image) {
        for(int l = 0; l < image.GetHeight(); l++){
            for(int c = 0; c < image.GetWidth(); c++){
                int r = image.GetRGB(c, l).GetRed();
                int b = image.GetRGB(c, l).GetBlue();
                int g = image.GetRGB(c, l).GetGreen();
                int avg = (r+g+b)/3;
                image.SetRGB(c, l, new RGB(avg, avg, avg));

            }
        }
        // Implement this method and remove the line below
        return image;
    }

    /**
     * Inverts the image. To invert the image, for each channel of each pixel, we get
     * its new value by subtracting its current value from 255. (r = 255 - r)
     * @param image image to transform
     * @return image transformed to inverted image
     */
    public static Img InvertImage(Img image) {
        for(int l = 0; l < image.GetHeight(); l++) {
            for (int c = 0; c < image.GetWidth(); c++) {
                int r = image.GetRGB(c, l).GetRed();
                r = 255-r;
                int b = image.GetRGB(c, l).GetBlue();
                b = 255-b;
                int g = image.GetRGB(c, l).GetGreen();
                g = 255-g;
                image.SetRGB(c, l, new RGB(r, g, b));
            }
        }
        // Implement this method and remove the line below
        return image;
    }

    /**
     * Converts the image to sepia. To do so, for each pixel, we use the following equations
     * to get the new channel values:
     * r = .393r + .769g + .189b
     * g = .349r + .686g + .168b
     * b = 272r + .534g + .131b
     * @param image image to transform
     * @return image transformed to sepia
     */
    public static Img ConvertToSepia(Img image) {
        for(int l = 0; l < image.GetHeight(); l++) {
            for (int c = 0; c < image.GetWidth(); c++) {
                int r = image.GetRGB(c, l).GetRed();
                int b = image.GetRGB(c, l).GetBlue();
                int g = image.GetRGB(c, l).GetGreen();
                int nr = (int)(.393*r + .769*g + .189*b);
                int ng = (int)(.349*r + .686*g + .168*b);
                int nb = (int)(.272*r + .534*g + .131*b);
                image.SetRGB(c, l, new RGB(nr, ng, nb));


            }
        }
        // Implement this method and remove the line below
        return image;
    }

    /**
     * Creates a stylized Black/White image (no gray) from the given image. To do so:
     * 1) calculate the luminance for each pixel. Luminance = (.299 r^2 + .587 g^2 + .114 b^2)^(1/2)
     * 2) find the median luminance
     * 3) each pixel that has luminance >= median_luminance will be white changed to white and each pixel
     *      that has luminance < median_luminance will be changed to black
     * @param image image to transform
     * @return black/white stylized form of image
     */
    public static Img ConvertToBW(Img image) {
        ArrayList <Double> l = new ArrayList<Double>();
        for(int h = 0; h < image.GetHeight(); h++){
            for(int w = 0; w < image.GetWidth(); w++){
                int r = image.GetRGB(w, h).GetRed();
                int g = image.GetRGB(w, h).GetGreen();
                int b = image.GetRGB(w, h).GetBlue();
                double luminance = Math.sqrt((.299*r*r + .587*g*g + .114*b*b));
                l.add(luminance);
            }
        }
        Collections.sort(l);
        int median = 0;
        if(l.size()%2 == 1){
            median = (int)(l.size()/2);
        }
        else{
            median = (int)((l.size()/2));
        }

        double mid = l.get(median);
        for(int h = 0; h < image.GetHeight(); h ++){
            for(int w = 0; w < image.GetWidth(); w++){
                int r = image.GetRGB(w, h).GetRed();
                int g = image.GetRGB(w, h).GetGreen();
                int b = image.GetRGB(w, h).GetBlue();
                double luminance = Math.sqrt((.299*r*r + .587*g*g + .114*b*b));
                if(luminance < mid){
                    image.SetRGB(w, h, new RGB(0, 0, 0));

                }
                else{
                    image.SetRGB(w, h, new RGB(255, 255, 255));

                }
            }
        }
        return image;
        // Implement this method and remove the line below

    }

    /**
     * Rotates the image 90 degrees clockwise.
     * @param image image to transform
     * @return image rotated 90 degrees clockwise
     */
    public  static Img RotateImage(Img image) {
        Img rotate = new Img(image.GetHeight(), image.GetWidth());
        for(int h = 0; h < image.GetHeight(); h++){
            for(int w = 0; w < image.GetWidth(); w++){
                rotate.SetRGB(image.GetHeight()-h-1, w, new RGB(image.GetRGB(w, h).GetRed(), image.GetRGB(w, h).GetGreen(), image.GetRGB(w, h).GetBlue()));
            }
        }
        return rotate;
        // Implement this method and remove the line below

    }

    /**
     * Applies an Instagram-like filter to the image. To do so, we apply the following transformations:
     * 1) We apply a "warm" filter. We can produce warm colors by reducing the amount of blue in the image
     *      and increasing the amount of red. For each pixel, apply the following transformation:
     *          r = r * 1.2
     *          g = g
     *          b = b / 1.5
     * 2) We add a vignette (a black gradient around the border) by combining our image with an
     *      an image of a halo (you can see the image at resources/halo.png). We take 65% of our
     *      image and 35% of the halo image. For example:
     *          r = .65 * r_image + .35 * r_halo
     * 3) We add decorative grain by combining our image with a decorative grain image
     *      (resources/decorative_grain.png). We will do this at a .95 / .5 ratio.
     * @param image image to transform
     * @return image with a filter
     * @throws IOException
     */
    public static Img InstagramFilter(Img image) throws IOException {
        Img hal = LoadImage("C:/Users/azcxz/IdeaProjects/project-5-image-manipulation-angelac25/resources/halo.png");
        Img grain = LoadImage("C:/Users/azcxz/IdeaProjects/project-5-image-manipulation-angelac25/resources/decorative_grain.png");
        double ratioWidthHalo = (hal.GetWidth() / (double) image.GetWidth());
        double ratioHeightHalo = (hal.GetHeight() / (double) image.GetHeight());
        double ratioWidthGrain = (grain.GetWidth() / (double) image.GetWidth());
        double ratioHeightGrain = (grain.GetHeight() / (double) image.GetHeight());
        for(int h = 0; h < image.GetHeight(); h++){
            for(int w = 0; w < image.GetWidth(); w++){
                int r = image.GetRGB(w, h).GetRed();
                int g = image.GetRGB(w, h).GetGreen();
                int b = image.GetRGB(w, h).GetBlue();
                int nr = (int)(r*1.2);


                int nb = (int)(b/1.5);


                image.SetRGB(w, h, new RGB(nr, g, nb));

            }

        }
        //vignette
        for(int h = 0; h < image.GetHeight(); h++) {
            for (int w = 0; w < image.GetWidth(); w++) {
                RGB hval = image.GetRGB(w, h);
                RGB valHalo = hal.GetRGB(((int) (w * ratioWidthHalo)), ((int) (h * ratioHeightHalo)));
                int nr = (int) (0.65 * hval.GetRed() + 0.35 * valHalo.GetRed());
                int ng = (int) (0.65 * hval.GetGreen() + 0.35 * valHalo.GetGreen());
                int nb = (int) (0.65 * hval.GetBlue() + 0.35 * valHalo.GetBlue());
                image.SetRGB(w, h, new RGB(nr, ng, nb));


            }
        }
        //grain
        for(int h = 0; h < image.GetHeight(); h++) {
            for (int w = 0; w < image.GetWidth(); w++) {
                RGB gval = image.GetRGB(w, h);
                RGB valGrain = grain.GetRGB(((int) (w * ratioWidthGrain)), ((int) (h * ratioHeightGrain)));
                int nr = (int) (0.95 * gval.GetRed() + 0.05 * valGrain.GetRed());
                int ng = (int) (0.95 * gval.GetGreen() + 0.05 * valGrain.GetGreen());
                int nb = (int) (0.95 * gval.GetBlue() + 0.05 * valGrain.GetBlue());
                image.SetRGB(w, h, new RGB(nr, ng, nb));

            }
        }
        // Implement this method and remove the line below
        return image;
    }

    /**
     * Sets the given hue to each pixel image. Hue can range from 0 to 360. We do this
     * by converting each RGB pixel to an HSL pixel, Setting the new hue, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param hue amount of hue to add
     * @return image with added hue
     */
    public static Img SetHue(Img image, int hue) {
        for(int l = 0; l < image.GetHeight(); l++){
            for(int w = 0; w < image.GetWidth(); w++){

                RGB n = image.GetRGB(w, l);
                HSL change = n.ConvertToHSL();
                change.SetHue(hue);
                image.SetRGB(w, l, change.GetRGB());

            }
        }
        return image;
        // Implement this method and remove the line below

    }

    /**
     * Sets the given saturation to the image. Saturation can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new saturation, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param saturation amount of saturation to add
     * @return image with added hue
     */
    public static Img SetSaturation(Img image, double saturation) {
        for(int l = 0; l < image.GetHeight(); l++){
            for(int w = 0; w < image.GetWidth(); w++){
                RGB n = image.GetRGB(w, l);
                HSL change = n.ConvertToHSL();
                change.SetSaturation(saturation);
                image.SetRGB(w, l, change.GetRGB());

            }
        }
        return image;
        // Implement this method and remove the line below

    }

    /**
     * Sets the lightness to the image. Lightness can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new lightness, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param lightness amount of hue to add
     * @return image with added hue
     */
    public static Img SetLightness(Img image, double lightness) {
        for(int l = 0; l < image.GetHeight(); l++){
            for(int w = 0; w < image.GetWidth(); w++){
                RGB n = image.GetRGB(w, l);
                HSL change = n.ConvertToHSL();
                change.SetLightness(lightness);
                image.SetRGB(w, l, change.GetRGB());


            }
        }
        return image;
        // Implement this method and remove the line below

    }
}
