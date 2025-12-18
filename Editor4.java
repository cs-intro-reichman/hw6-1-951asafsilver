import java.awt.Color;

/**
 * Demonstrates the morphing operation from an image to its greyscaled version.
 * The program takes two command-line arguments: the name of the PPM file
 * representing the image, and the number of morphing steps (int).
 */
public class Editor4 {

	public static void main (String[] args) {
		String fileName = args[0];
		int n = Integer.parseInt(args[1]);
		Color[][] sourceImage = Runigram.read(fileName);
		Color[][] targetImage = Runigram.grayScaled(sourceImage);
		Runigram.setCanvas(sourceImage);
		Runigram.morph(sourceImage, targetImage, n);
	}
}