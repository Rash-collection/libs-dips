/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

/**
 *
 * @author rash4
 */
public interface Installable<T extends Branch> {
    T initialize();
}
