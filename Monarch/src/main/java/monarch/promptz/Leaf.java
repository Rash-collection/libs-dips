/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

/**
 *
 * @author rash4
 */
public non-sealed class Leaf extends AbsCom{
    private final Methoz cmd;
    public Leaf(Methoz cmd){
        this.cmd = cmd;
    }
    @Override public void execute(String arg, StringBuilder trace){
        if(cmd != null) cmd.execute(arg);
    }
    public final Methoz get(){return this.cmd;}
}
