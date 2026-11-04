/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package graphicker;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author rash4
 */
public final class Artsy {
    private final ArrayList<BufferedImage> ARTS;
    private final int w, h;
    private Artsy(int width, int height){
        ARTS = new ArrayList<>();
        this.w = width;
        this.h = height;
    }
    public synchronized boolean isEmpty(){
        return this.ARTS.isEmpty();
    }
    public synchronized int size(){
        return this.ARTS.size();
    }
    public int getWidth(){
        return this.w;
    }
    public int getHeight(){
        return this.h;
    }
    public synchronized BufferedImage getImage(int index){
        return this.ARTS.get(index);
    }
    public synchronized BufferedImage[] getImages(){
        return this.ARTS.toArray(BufferedImage[]::new);
    }
    public synchronized Artsy addArt(Painter painter){
        final var img = Graphz.toImage(this.w, this.h, painter);
        this.ARTS.add(img);
        return this;
    }
    public synchronized Artsy addArt(Pixelator pixelator){
        final var img = Graphz.toImage(this.w, this.h, pixelator);
        this.ARTS.add(img);
        return this;
    }
    public synchronized Artsy addArt(BufferedImage image){
        if(image == null)return this;
        final var resized = Graphz.resize(this.w, this.h, image);
        this.ARTS.add(resized);
        return this;
    }
    public synchronized BufferedImage remove(int index){
        return this.ARTS.remove(index);
    }
    public synchronized boolean remove(BufferedImage image){
        return this.ARTS.remove(image);
    }
    public synchronized BufferedImage resize(int neoW, int neoH, int index){
        if(neoW < 1 || neoH < 1 || index >= this.size() || index < 0)
            throw new IllegalArgumentException();
        final var target = this.ARTS.get(index);
        if(neoW == target.getWidth() && neoH == target.getHeight())
            return target;
        final var neoImage = new BufferedImage(neoW, neoH, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        try{
            graph.drawImage(target, 0, 0, neoW, neoH, null);
        }finally{
            graph.dispose();
        }
        return neoImage;
    }
    public synchronized BufferedImage scale(double scale, int index){
        if(scale <= 0 || !Double.isFinite(scale) || index >= this.size() || index < 0)
            throw new IllegalArgumentException();
        final var target = this.ARTS.get(index);
        if(scale == 1)return target;
        final int width = (int)Math.round(scale * (double)target.getWidth());
        final int height = (int)Math.round(scale * (double)target.getHeight());
        if(width < 1 || height < 1)
            throw new UnsupportedOperationException();
        if(width == target.getWidth() && height == target.getHeight())
            return target;
        final var neoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        try{
            graph.drawImage(target, 0, 0, width, height, null);
        }finally{
            graph.dispose();
        }
        return neoImage;
    }
    public static Artsy factory(int width, int height){
        if(width < 1 || height < 1)throw new IllegalArgumentException();
        return new Artsy(width, height);
    }
}
