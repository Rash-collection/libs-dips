/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package graphicker;

/**
 *
 * @author rash4
 */
public final class Kolor{
    private Kolor(){}
    
    private final static int MAX = 256;
    public  final static int LAS = 255;
    
    // core colors
    public final static int BLACK = getInt(0, 0, 0);
    public final static int GRAY_LIGHT = getInt(192, 192, 192);
    public final static int GRAY = getInt(128, 128, 128);
    public final static int GRAY_DARK = getInt(64, 64, 64);
    public final static int WHITE = getInt(255, 255, 255);
    
    // primery colors
    public final static int RED_BRIGHT = getInt(255, 0, 0);
    public final static int RED_LIGHT = getInt(192, 0, 0);
    public final static int RED_MEDIUM = getInt(128, 0, 0);
    public final static int RED_DARK = getInt(64, 0, 0);
    
    public final static int GREEN_BRIGHT = getInt(0, 255, 0);
    public final static int GREEN_LIGHT = getInt(0, 192, 0);
    public final static int GREEN_MEDIUM = getInt(0, 128, 0);
    public final static int GREEN_DARK = getInt(0, 64, 0);
    
    public final static int BLUE_BRIGHT = getInt(0, 0, 255);
    public final static int BLUE_LIGHT = getInt(0, 0, 192);
    public final static int BLUE_MEDIUM = getInt(0, 0, 128);
    public final static int BLUE_DARK = getInt(0, 0, 64);
    
    //secondery colors
    public final static int CYAN_BRIGHT = getInt(0, 255, 255);
    public final static int CYAN_LIGHT = getInt(0, 192, 192);
    public final static int CYAN_MEDIUM = getInt(0, 128, 128);
    public final static int CYAN_DARK = getInt(0, 64, 64);
    
    public final static int MAGENTA_BRIGHT = getInt(255, 0, 255);
    public final static int MAGENTA_LIGHT = getInt(192, 0, 192);
    public final static int MAGENTA_MEDIUM = getInt(128, 0, 128);
    public final static int MAGENTA_DARK = getInt(64, 0, 64);
    
    public final static int YELLOW_BRIGHT = getInt(255, 255, 0);
    public final static int YELLOW_LIGHT = getInt(192, 192, 0);
    public final static int YELLOW_MEDIUM = getInt(128, 128, 0);
    public final static int YELLOW_DARK = getInt(64, 64, 0);
    
    // special colors
    public final static int ORANGE = getInt(255, 128, 0);
    public final static int GRASS = getInt(128, 255, 0);
    public final static int JEANS = getInt(128, 0, 255);
    
    public final static int TEAL = getInt(0, 128, 128);
    public final static int OLIVE = getInt(128, 128, 0);
    public final static int PURPLE = getInt(128, 0, 128);
    
    // very special colors
    public final static int BROWN = getInt(165, 42, 42);
    public final static int PURPLE_NEON = getInt(170, 0, 255);// my purple XD
    
    public static int getInt(int r, int g, int b) {
        return  (255 & 0xFF) << 24 |  // alpha = 255
                (r   & 0xFF) << 16 |
                (g   & 0xFF) << 8  |
                (b   & 0xFF);
    }
    public static int getInt(int r, int g, int b, int a) {
        return  (a & 0xFF) << 24 |
                (r & 0xFF) << 16 |
                (g & 0xFF) << 8  |
                (b & 0xFF);
    }
    public static int[] getRGBA(int hex){
        final int a = (hex >>> 24) & 0xFF;
        final int r = (hex >>> 16) & 0xFF;
        final int g = (hex >>> 8 ) & 0xFF;
        final int b = (hex       ) & 0xFF;
        return new int[]{ r, g, b, a };
    }
    public static int toNegative(int hex){
        // Invert RGB, keep alpha = true
        return toNegative(hex, true);
    }
    public static int toNegative(int hex, boolean keepAlpha){
        final int a = (hex >>> 24) & 0xFF;
        final int r = (hex >>> 16) & 0xFF;
        final int g = (hex >>> 8 ) & 0xFF;
        final int b = (hex       ) & 0xFF;
        final int nr = 255 - r;
        final int ng = 255 - g;
        final int nb = 255 - b;
        final int na = keepAlpha? a : 255 - a;
        return getInt(nr, ng, nb, na);
    }
    // static solo setters
    public static int setA(int hex, int a) {return (hex & 0x00FFFFFF) | ((a & 0xFF) << 24);}
    public static int setR(int hex, int r) {return (hex & 0xFF00FFFF) | ((r & 0xFF) << 16);}
    public static int setG(int hex, int g) {return (hex & 0xFFFF00FF) | ((g & 0xFF) << 8 );}
    public static int setB(int hex, int b) {return (hex & 0xFFFFFF00) |  (b & 0xFF)       ;}
    // static solo getters
    public static int getA(int hex) {return (hex >>> 24) & 0xFF;}
    public static int getR(int hex) {return (hex >>> 16) & 0xFF;}
    public static int getG(int hex) {return (hex >>> 8 ) & 0xFF;}
    public static int getB(int hex) {return (hex       ) & 0xFF;}
    
    public static int kolor(){return randoms().nextInt(MAX);}
    public static int kolon(int min){return randoms().nextInt(min, MAX);}
    
    public static int kolor(int max){return randoms().nextInt(max);}
    public static int kolor(int min, int max){return randoms().nextInt(min, max);}
    
    public static int kolos(int val, int tol){
        final int haf = Math.floorDivExact(tol, 2);
        final int odd = tol%2;
        final int ran = randoms().nextInt(val - haf, val + haf + odd);
        return Math.clamp(ran, 0, LAS);
    }
    public static int koloz(int val, int tol){return kolos(LAS - val, tol);}
    
    public static int random(){return getInt(kolor(), kolor(), kolor());}
    
    private static java.util.concurrent.ThreadLocalRandom randoms(){
        return java.util.concurrent.ThreadLocalRandom.current();
    }
}
