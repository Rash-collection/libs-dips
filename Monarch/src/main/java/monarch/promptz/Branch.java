/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

import java.util.HashMap;
import monarch.shell.Konsole;

/**
 *
 * @author rash4
 */
public non-sealed class Branch extends AbsCom{
    private final static String PRFX = "-";
    static final HashMap<String, Branch> APPS = new HashMap<>();
    private final HashMap<String, AbsCom> cmds = new HashMap<>();
    private boolean direct;
    public Branch(Methoz cmd){
        this.direct = cmd != null;
        // the direct cmd is NULL named.
        this.cmds.put(null, new Leaf(cmd));
        if(this instanceof Installable inst){
            // do the instalation XD
            String name = this.getClass().getSimpleName().toLowerCase();
            APPS.putIfAbsent(name, inst.initialize());
            System.out.println("");
        }
    }
    public Branch(Methoz dir, Konsole con){
        this(dir);
        this.addBrn("help", arg->{con.appendl(Branch.this.toString());});
    }
    public final boolean hasDirect(){return this.direct;}
    public final int contain(String name){
        if (name == null)return this.hasDirect() ? 0 : -2; // -2 only if explicitly null and no child.
        final AbsCom com = this.cmds.get(name);
        if (com == null) return -1; // no such entry
        if (!(com instanceof Branch sb)) return 0; // leaf
        return sb.cmds.size() > 1 ? 2 : 1; // all branches has one elem, only valid for nav has two or more
    }
    public final Leaf direct(){
        return (Leaf)this.cmds.get(null);
    }
    public <T extends AbsCom> T getCast(String name, Class<T> type){
        AbsCom com = cmds.get(name);
        return type.isInstance(com) ? type.cast(com) : null;
    }
    public Branch getBrr(String tarr, StringBuilder blr){
        final var dir = tarr.trim().split("\\s+", 2);
        final String name = dir[0].toLowerCase();
        final AbsCom com = this.cmds.get(name);
        if (!(com instanceof Branch sb) || sb.cmds.size() < 2)
            return this;
        blr.append(name);
        if (dir.length > 1) {
            blr.append(" "); // add this only if there's more!
            return sb.getBrr(dir[1], blr);
        }return sb;
    }
    public final Branch replaceDirect(Methoz mth){
        this.cmds.replace(null, new Leaf(mth));
        this.direct = mth != null; // update
        return this;
    }
    public Branch addCmd(String name, Methoz mth){
        if(name == null || name.isBlank() || mth == null)return this;
        this.cmds.putIfAbsent(name, new Leaf(mth));
        return this;
    }
    public final Branch addBrn(String name, Methoz mth){
        // here method (aka the direct command) could be null for the branch.
        if(name == null || name.isBlank())return this;
        this.cmds.putIfAbsent(name, new Branch(mth));
        return this;
    }
    public Branch branching(String name, Methoz mth){
        if(name == null || name.isBlank()/* || mth == null*/)return null;
        return (Branch)this.cmds.computeIfAbsent(name, k -> new Branch(mth));
    }
    public Branch addBrn(Konsole con, String name){
        return this.addBrn(name, arg->{con.appendl(Branch.this.toString());});
    }
    public Branch branching(Konsole con, String name){
        return this.branching(name, arg->{con.appendl(Branch.this.toString());});
    }
    void install(String name, Branch app, boolean rep){
        if(name == null || name.isBlank() ||
                app == null || app.cmds.size() < 2)
            return;
        if(rep) this.cmds.put(name, app);
        else this.cmds.putIfAbsent(name, app);
    }
    @Override public void execute(String arg, StringBuilder trace){
        if(arg == null || arg.isBlank()){
            if(this.hasDirect()){
                //MSG.append("null");
                direct().execute("", trace);
            }//else MSG.append("-");
            return;
        }
        final var seg = Methoz.args(arg);
        // ignore cases when calling the command.
        final String name = seg[0].toLowerCase();
        final var com = this.cmds.get(name);
        final String segs = seg.length < 2? "" : seg[1];
        if(com != null){
            com.execute(segs, trace);
            return;
        }
        System.out.printf("command - %s [%s] - not found\n", trace, name);
    }
    @Override public String toString(){
        final var str = new StringBuilder();
        str.append("root.\n");
        final var kys = this.cmds.keySet();
        AbsCom com;
        for(var k: kys){
            com = this.cmds.get(k);
            if(com instanceof Branch sx){
                str.append(sx.toString(k, 1));
            }
            else{
                if(com instanceof Leaf sy){
                final String nm = sy.get() == null? "NULL" : "CMD";
                str.append(String.format("%s(%s)%s.\n",PRFX, nm, k));
            }}
        }
        return str.toString();
    }
    public String toString(String name, int dent){
        final var str = new StringBuilder();
        str.append(PRFX.repeat(dent)).append("(Branch)").append(name).append(":\n");
        dent += 1;
        final String prf = PRFX.repeat(dent);
        final var kys = this.cmds.keySet();
        AbsCom com;
        for(var k: kys){
            com = this.cmds.get(k);
            if(com instanceof Branch sx)
                str.append(sx.toString(k, dent));
            else if(com instanceof Leaf sy){
                final String nm = sy.get() == null? "NULL" : "CMD";
                str.append(String.format("%s(%s)%s.\n", prf, nm, k));
            } // no nulls
        }
        return str.toString();
    }
}
