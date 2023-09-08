import java.awt.Color;

public class pictureEffect {

    public static int GlassEffect (int a, int b){
    return StdRandom.uniform(b-a+1)+ a;
    }

    public static void main(String[] args) {
        Picture a = new Picture("Cuphead.jpg");
        a.show();

        //2d arrays colum
        int width= a.width();
        //2d arrays row
        int height= a.height();

        //creating a blank picture with same width, and height as cuphead
        Picture blank = new Picture(width, height);


        // (x,y) = (col, row) = (width, height)
        //traverse the input of pixel array
        for (int x = 0; x < width; x++) {
            for( int y = 0; y < height; y++) {

                // grabbing a random value of x around 5 pixels
                int x2 = (width + x + GlassEffect(-5,5)) % width ; 
                // grabbing a random value of y around 5 pixels
                int y2 = (height + y + GlassEffect(-5,5)) % height ; 
            
                // retrieve pixel at (x2,y2) and get color. This is near (x,y) in the perimeter of 5 pixels from row and col
                Color color = a.get(x2,y2); 
                //set x and y to a neighbor pixel color, setting (x,y) to (x2,y2)
                blank.set(x,y, color); 

            }

        }
        blank.show();
    }
    
}
