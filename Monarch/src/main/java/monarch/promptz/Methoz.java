/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

/**
 *
 * @author rash4
 */
@FunctionalInterface public interface Methoz {
    String WS = "\\s+";
    String US = "(?U)\\s+";
    void execute(String param);
    static String[] splitor(String target){
        return target.trim().split("\\s+");
    }
    static String[] args(String target){
        return target.trim().split("\\s+", 2);
    }
}
