import java.awt.Color;

public class Runigram {

    public static void main(String[] args) {
        Color[][] tinypic = read("tinypic.ppm");
        print(tinypic);
    }

    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        in.readString();
        int numCols = in.readInt();
        int numRows = in.readInt();
        in.readInt();
        Color[][] image = new Color[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
            }
        }
        return image;
    }

    public static void print(Color c) {
        System.out.print("(");
        System.out.printf("%3d,", c.getRed());
        System.out.printf("%3d,", c.getGreen());
        System.out.printf("%3d",  c.getBlue());
        System.out.print(")  ");
    }

    public static void print(Color[][] image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                print(image[i][j]);
            }
            System.out.println();
        }
    }
    
    public static Color[][] flippedHorizontally(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[i][j] = image[i][cols - 1 - j];
            }
        }
        return flipped;
    }
    
    public static Color[][] flippedVertically(Color[][] image){
        int rows = image.length;
        int cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[i][j] = image[rows - 1 - i][j];
            }
        }
        return flipped;
    }
    
    public static Color luminance(Color pixel) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        return new Color(lum, lum, lum);
    }
    
    public static Color[][] grayScaled(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] gray = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gray[i][j] = luminance(image[i][j]);
            }
        }
        return gray;
    }   
    
    public static Color[][] scaled(Color[][] image, int width, int height) {
        int h0 = image.length;
        int w0 = image[0].length;
        Color[][] scaledImage = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                scaledImage[i][j] = image[i * h0 / height][j * w0 / width];
            }
        }
        return scaledImage;
    }
    
    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }
    
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int rows = image1.length;
        int cols = image1[0].length;
        Color[][] blended = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return blended;
    }

    public static void morph(Color[][] source, Color[][] target, int n) {
        Color[][] scaledTarget = scaled(target, source[0].length, source.length);
        for (int i = 0; i <= n; i++) {
            double alpha = (double) (n - i) / n;
            Color[][] blended = blend(source, scaledTarget, alpha);
            display(blended);
            StdDraw.pause(500);
        }
    }

    public static void setCanvas(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        StdDraw.setTitle("Runigram");
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    public static void display(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StdDraw.setPenColor(image[i][j]);
                StdDraw.filledRectangle(j + 0.5, height - i - 0.5, 0.5, 0.5);
            }
        }
        StdDraw.show();
    }
}