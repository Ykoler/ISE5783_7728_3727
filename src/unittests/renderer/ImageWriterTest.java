package unittests.renderer;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.ImageWriter;

/**
 * Testing Camera Class
 * 
 * @author Yahel and Ashi
 *
 */

class ImageWriterTest {
	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void makeImage() {
		ImageWriter imageWriter = new ImageWriter("TestPic", 801, 501);
		Color background = new Color(41, 227, 128);
		Color grid = new Color(130, 10, 245);

		// Separately colors each pixel based on the ray tracer's findings
		for (int i = 0; i <= 500; ++i)
			for (int j = 0; j <= 800; ++j)
				imageWriter.writePixel(j, i, i % 50 == 0 || j % 50 == 0 ? grid : background);
		imageWriter.writeToImage();

	}
}