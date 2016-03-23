// Instructions:
// To blur an image, we'll use a simple idea. Consider the first
// 4 x 4 block of pixels in the image at the top left. Suppose
// we compute the average intensity of the 16 pixels in that block.
// Call this average avg. Then, in the blurred image, all 16 of
// those pixels will have the same intensity, equal to avg. Thus,
// we replace the intensity of each pixel with the average intensity
// of the k x k block it belongs to. Clearly, large values of k will
// cause significant blurring.
//
// In the code below, your job is to take an image and blur it using
// the above approach.

public class ImageBlur {

    public static void main (String[] argv)
    {
	ImageTool imTool = new ImageTool ();
	int[][] pixels = imTool.imageFileToGreyPixels ("ace.jpg");
	imTool.showImage (pixels, "original");
	// Each block of k x k pixels has the same color.
	int k = 4;
	int[][] blurredPixels = blur (pixels, k);
	imTool.showImage (blurredPixels, "blurred");
    }

    static int[][] blur (int[][] pixels, int blurSize)
    {
		for(int i = 0; i<pixels.length; i+=blurSize){
			for(int j = 0; j<pixels[0].length; j+=blurSize){
				int average = 0;
				for(int k = 0; k<blurSize; k++){
					for(int l = 0; l<blurSize; l++){
						average+=pixels[i+k][j+l];
					}
				}
				average /= blurSize*blurSize;
				for(int k = 0; k<blurSize; k++){
					for(int l = 0; l<blurSize; l++){
						pixels[i+k][j+l] = average;
					}
				}
			}
		}
    }

}
