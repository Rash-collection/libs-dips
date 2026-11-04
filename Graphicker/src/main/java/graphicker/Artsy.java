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
    private volatile int i = -1;
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
    public synchronized int getIndex(){
        return this.i;
    }
    public int getWidth(){
        return this.w;
    }
    public int getHeight(){
        return this.h;
    }
    public synchronized BufferedImage getImage(){
        return this.ARTS.get(this.i);
    }
    public synchronized BufferedImage getImage(int index){
        return this.ARTS.get(index);
    }
    public synchronized BufferedImage[] getImages(){
        return this.ARTS.toArray(BufferedImage[]::new);
    }
    public synchronized Artsy addArt(Painter painter){
        final int preSize = this.size();
        final var img = Graphz.toImage(this.w, this.h, painter);
        if(img != null && this.ARTS.add(img))
            this.i = preSize;
        return this;
    }
    public synchronized Artsy addArt(Pixelator pixelator){
        final int preSize = this.size();
        final var img = Graphz.toImage(this.w, this.h, pixelator);
        if(img != null && this.ARTS.add(img))
            this.i = preSize;
        return this;
    }
    public Artsy addArt(BufferedImage image){
        if(image == null)return this;
        final int preSize = this.size();
        final var resized = this.resize(this.w, this.h, preSize);
        synchronized(this){
            if(this.ARTS.add(resized))
                this.i = preSize;
        }return this;
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
        final var neoImage = new BufferedImage(neoW, neoH, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        graph.drawImage(this.ARTS.get(index), 0, 0, this.w, this.h, null);
        graph.dispose();
        return neoImage;
    }
    public synchronized BufferedImage scale(double scale, int index){
        if(scale <= 0 || index >= this.size() || index < 0)
            throw new IllegalArgumentException();
        final var target = this.ARTS.get(index);
        final int width = (int)Math.round(scale * (double)target.getWidth());
        final int height = (int)Math.round(scale * (double)target.getHeight());
        final var neoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final var graph = neoImage.createGraphics();
        graph.drawImage(target, 0, 0, width, height, null);
        graph.dispose();
        return neoImage;
    }
    public static Artsy factory(int width, int height){
        if(width < 1 || height < 1)throw new IllegalArgumentException();
        return new Artsy(width, height);
    }
}
