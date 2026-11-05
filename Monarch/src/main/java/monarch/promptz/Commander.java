/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

import monarch.shell.Konsole;

/**
 *
 * @author rash4
 */
public class Commander implements Methoz{
    private final Branch ROT;
    private Branch lastCom;
    private final Konsole CON;
    private final StringBuilder MSG = new StringBuilder();
    private String PRV = "";
    public Commander(Konsole con){
        this.ROT = new Branch(null, con);
        this.CON = con;
    }
    public Commander(Branch root, Konsole con){
        this.ROT = root;
        this.CON = con;
    }
    public Commander(Commander old){
        this(old.ROT, old.CON);
    }
    public Commander(Konsole neo, Konsole old){
        this.ROT = old.getEXE().ROT;
        this.CON = neo;
    }
//    public Branch root(){return this.ROOT;}
    
    public Commander install(String name, Branch app){
        this.ROT.install(name, app, true);
        return this;
    }
    public Commander initialize(){
        this.ROT
                .addCmd("install", arg->{
                    if(arg == null || arg.isBlank())return;
                    String name = arg.trim().split("\\s+")[0].toLowerCase();
                    final Branch app = Branch.APPS.get(name);
                    if(app == null)CON.appendl("App - %s - not found.".formatted(name));
                    else install(name, app);
                })
                .addCmd("exit", arg->{System.exit(0);})
                .addCmd("close", arg->{CON.close();})
//                .addCmd("help",  arg->{CON.append(ROT.toString());})
                .addCmd("clear", arg->{CON.clear();})
                // still NOT working 0> repeater -_-
                .addCmd("repeat", arg->{Commander.this.execute(Commander.this.PRV);})
                ;
        this.ROT.branching("say", arg->{CON.appendl(arg);})
                .addCmd("somet", arg->{CON.appendl(arg);})
                .addCmd("hello", arg->{CON.appendl("Hello DUD!");})
                .addCmd("fkn", arg->{
                    final var pr = arg.split("\\s+", 2);
                    final int ln = pr.length;
                    try{
                        final int ind = Integer.parseInt(pr[0]);
                        CON.insert("\nHello DUD!\n" + (ln > 1 ? pr[1]: ""), ind);
                    }catch(NumberFormatException e){
                        CON.appendl("insert a numerical value(int).");
                    }
                })
                .branching("bob", null)
                .addCmd("-", arg->{CON.appendl("BOBO - " + arg + " - shit");})
                .addCmd("la", arg->{CON.appendl("lalallallaa");})
                ;
//        this.install("math", new Mazz(this.CON).initialize());
        return this;
    }
    @Override public void execute(String param) {
        this.CON.appendl("> " + (this.MSG.isEmpty()? "ROOT ": this.MSG.toString().trim())  + ": " + param);
        final String trm = param.trim();
        // Navigation
        if (trm.equalsIgnoreCase("cd") || trm.regionMatches(true, 0, "cd ", 0, 3)) {
            String smt = param.substring(2).trim();
            if (smt.isEmpty()) return;
            // Absolute navigation
            if (smt.startsWith("~")) {
                smt = smt.substring(1).trim();
                if (smt.isEmpty()) {
                    this.lastCom = null;
                    this.MSG.setLength(0);
                    return;
                }
                this.MSG.setLength(0);
                this.lastCom = this.ROT.getBrr(smt, this.MSG);
            } else {
                // Relative navigation
                final var blr = new StringBuilder();
                boolean navigated = true;
                if (this.lastCom == null) {
                    this.lastCom = this.ROT.getBrr(smt, blr);
                } else {
                    final Branch pre = this.lastCom.getBrr(smt, blr);
                    if ((navigated = pre != this.lastCom))this.lastCom = pre;
                }
                // Execute the unconsumed input in the resolved branch.
                final String nrm = smt
                    .replaceAll("\\s+", " ").substring(blr.length()).trim();
                if(this.lastCom == null) this.MSG.setLength(0);
                else if(navigated){
                    if(this.MSG.isEmpty())this.MSG.append(blr);
                    else this.MSG.append(' ').append(blr);
                }
                this.lastCom.execute(nrm, this.MSG);
                return;
            }
            return;
        }
        // Explicit root execution
        if (trm.startsWith("~")) {
            this.ROT.execute(param.trim().substring(1).trim(), MSG);
            return;
        }
        // CWD execution
        if (this.lastCom != null) {
            this.lastCom.execute(param, MSG);
            return;
        }
        // Root execution
        this.ROT.execute(param, MSG);
    }
}
