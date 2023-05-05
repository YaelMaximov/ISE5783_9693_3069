package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

class ImageWriterTest {
    /**
     * Tests the writePixel method of the ImageWriter class with a grid pattern of colors.
     * Creates a new ImageWriter object with dimensions 800x500 and writes pixels to it
     * with alternating colors based on a grid pattern.
     */
    @Test
    public void WritePixelTest1()
    {
        ImageWriter imagewriter= new ImageWriter("Grid1",800,500);
        for(int i=0; i<imagewriter.getNx();i++) {
            for (int j = 0; j < imagewriter.getNy(); j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imagewriter.writePixel(i, j, new Color(255, 0, 0));
                else {
                    imagewriter.writePixel(i, j, new Color(255, 255, 0));
                }
            }
        }
        imagewriter.writeToImage();
    }

}