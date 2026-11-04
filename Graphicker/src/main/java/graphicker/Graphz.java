/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package graphicker;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author rash4
 */
public final class Graphz {
    private Graphz(){}
    public static BufferedImage toImage(int w, int h, Painter painter){
        final BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graph = image.createGraphics();
        try{
            painter.paint(graph);
        }finally {
            graph.dispose();
        }
        return image;
    }
    public static BufferedImage toImage(int w, int h, Pixelator pixelator){
        final int[][] grid = new int[h][w];
        pixelator.pixelate(grid);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int y = 0; y < h; y++) { // avoids nested for-loops
            System.arraycopy(grid[y], 0, pixels, y * w, w);
        }
        return image;
    }
    public static BufferedImage asIntArgb(BufferedImage src) {
        if (src.getType() == BufferedImage.TYPE_INT_ARGB)
            return src;
        BufferedImage converted = new BufferedImage(
                src.getWidth(),
                src.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graph = converted.createGraphics();
        try {
            graph.drawImage(src, 0, 0, null);
        } finally {
            graph.dispose();
        }
        return converted;
    }
    public static int[][] asIntGrid(BufferedImage src){
        if(src == null)throw new IllegalArgumentException(
                "\nNPE -> BufferedImage img is null.\n");
        if(!(src.getRaster().getDataBuffer() instanceof DataBufferInt))
            throw new IllegalArgumentException(
                    "Image must have an INT-based raster (e.g., TYPE_INT_ARGB).");
        final int w = src.getWidth(), h = src.getHeight(),
                grid[][] = new int[h][w], 
                pixels[] = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
        for(int row = 0; row < h; row++){
            System.arraycopy(pixels, row * w, grid[row], 0, w);
        }
        return grid;
    }
    public static BufferedImage resize(int neoWidth, int neoHeight, BufferedImage image){
        if(neoWidth < 1 || neoHeight < 1 || image == null)
            throw new IllegalArgumentException();
        if(neoWidth == image.getWidth() && neoHeight == image.getHeight())
            return image;
        final var neoImage = new BufferedImage(neoWidth, neoHeight, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        try{
            graph.drawImage(image, 0, 0, neoWidth, neoHeight, null);
        }finally{
            graph.dispose();
        }
        return neoImage;
    }
    public static BufferedImage scale(double scaler, BufferedImage image){
        if(scaler <= 0 || scaler == Double.NaN || image == null)
            throw new IllegalArgumentException();
        final int width = (int)Math.round(scaler * (double)image.getWidth());
        final int height = (int)Math.round(scaler * (double)image.getHeight());
        if(width < 1 || height < 1)
            throw new UnsupportedOperationException();
        if(width == image.getWidth() && height == image.getHeight())
            return image;
        final var neoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        try{
            graph.drawImage(image, 0, 0, width, height, null);
        }finally{
            graph.dispose();
        }
        return neoImage;
    }
}
