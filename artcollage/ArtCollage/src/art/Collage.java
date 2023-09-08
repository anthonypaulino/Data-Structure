package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {

        collageDimension = 4;
        tileDimension = 150;
        originalPicture = new Picture(filename);
        collagePicture = new Picture (tileDimension * collageDimension,  tileDimension* collageDimension);
        scale(originalPicture, collagePicture);
        

    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

        collageDimension = cd;
        tileDimension = td;

        originalPicture = new Picture(filename);
        collagePicture = new Picture (tileDimension * collageDimension,  tileDimension* collageDimension);
        scale(originalPicture, collagePicture);
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        for (int targetcol = 0; targetcol < target.width(); targetcol++){
            for (int targetrow = 0; targetrow < target.height(); targetrow++){
                int sourcecol = targetcol * (source.width())  / target.width();
                int sourcerow = targetrow * (source.height()) / target.height();
                Color color = source.get(sourcecol, sourcerow);
                target.set(targetcol, targetrow, color);
            }
        }
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {

        Picture tileD = new Picture(tileDimension,tileDimension);
        scale(originalPicture,tileD);
        
        for (int tilecol = 0; tilecol < tileDimension; tilecol++){
            for (int tilerow = 0; tilerow < tileDimension; tilerow++){
                Color colorT = tileD.get(tilecol,tilerow);

                for (int collagecol = 0; collagecol < collageDimension; collagecol++){   
                    for (int collagerow = 0; collagerow < collageDimension; collagerow++){
                        collagePicture.set(tileDimension*collagecol + tilecol,tileDimension*collagerow + tilerow, colorT);
                        
                    }
        
                }
            }
        
                
        }


    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        for (int tilecol = 0; tilecol < tileDimension; tilecol++){
            for (int tilerow = 0; tilerow < tileDimension; tilerow++){

                if(component.equalsIgnoreCase("blue")){
                    int col = tileDimension*collageCol + tilecol;
                    int row = tileDimension*collageRow + tilerow;
                    Color tilePixel = collagePicture.get(col,row);
                    int b = tilePixel.getBlue();
                    Color finalColor = new Color(0,0,b);
                    collagePicture.set(col,row,finalColor);
                    }

                if(component.equalsIgnoreCase("red")){
                    int col = tileDimension*collageCol + tilecol;
                    int row = tileDimension*collageRow + tilerow;
                    Color tilePixel = collagePicture.get(col,row);
                    int r = tilePixel.getRed();
                    Color finalColor = new Color(r,0,0);
                    collagePicture.set(col,row,finalColor);
                    }

                if(component.equalsIgnoreCase("green")){
                    int col = tileDimension*collageCol + tilecol;
                    int row = tileDimension*collageRow + tilerow;
                    Color tilePixel = collagePicture.get(col,row);
                    int g = tilePixel.getGreen();
                    Color finalColor = new Color(0,g,0);
                    collagePicture.set(col,row,finalColor);
                    }
            }
        }
        
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        Picture tileD = new Picture(tileDimension,tileDimension);
        Picture replacement = new Picture(filename);
        scale(replacement,tileD);

        for (int tilecol = 0; tilecol < tileDimension; tilecol++){
            for (int tilerow = 0; tilerow < tileDimension; tilerow++){
                Color colorT = tileD.get(tilecol,tilerow);
                int col = tileDimension*collageCol + tilecol;
                int row = tileDimension*collageRow + tilerow;
                collagePicture.set(col,row, colorT);
            }
        }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {

        
        
        for (int tilecol = 0; tilecol < tileDimension; tilecol++){
            for (int tilerow = 0; tilerow < tileDimension; tilerow++){
                int col = tileDimension*collageCol + tilecol;
                int row = tileDimension*collageRow + tilerow;
                Color tilePixel = collagePicture.get(col,row);
                Color gray = toGray(tilePixel);
                collagePicture.set(col,row,gray);
            }
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
