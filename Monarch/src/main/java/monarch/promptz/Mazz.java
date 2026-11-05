/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.promptz;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import monarch.shell.Konsole;

/**
 *
 * @author rash4
 */
public final class Mazz extends Branch implements Installable<Mazz>{
    private final Konsole con;
    public Mazz(Konsole con){
        super(null, con);
        this.con = con;
    }
    @Override public Mazz initialize(){
        this.addCmd("?", Mazz.this::zum);
        
        return this;
    }
    private void zum(String arg){
        final String[] sus = arg.trim().split("\\s*=\\s*", 2);
        if(this.condit(sus.length < 2, ">> No shit!"))return;
        
        final String xName = sus[0];
        final var parts = Arrays.stream(sus[1]
                .split("(?<=[+\\-/*])|(?=[+\\-/*])"))
                .map(String::trim).toArray(String[]::new);
        if(this.condit(parts.length < 3, ">> " + xName + " " + parts[0]))return;
        
        final double results = zum(parts);
        
        this.con.appendl(String.format(">> %s = %.5f", xName, results));
    }
    private double zum(String ... args){
        if (args.length == 0 || args.length % 2 == 0)
            throw new IllegalArgumentException("Invalid expression");
        double result = Double.parseDouble(args[0]);
        for (int i = 1; i < args.length; i += 2) {
            char op = args[i].charAt(0);
            double next = Double.parseDouble(args[i + 1]);
            result = oper.operate(op, result, next);
        }
        return result;
    }
    private boolean condit(boolean cond, String msg){
        if(cond)this.con.appendl(msg);
        return cond;
    }
    private enum oper{
        SUM((one, two)-> one + two),
        SUB((one, two)-> one - two),
        MUL((one, two)-> one * two),
        DIV((one, two)-> two == 0 ? one : one / two),
        ;
        private final DoubleBinaryOperator OP;
        private oper(DoubleBinaryOperator op){
            this.OP = op;
        }
        
        public double operate(double one, double two){
            return this.OP.applyAsDouble(one, two);
        }
        public static double operate(char c, double one, double two){
            return get(c).operate(one, two);
        }
        public static oper get(char c){
            return switch(c){
                case '+' -> SUM;
                case '-' -> SUB;
                case '*' -> MUL;
                case '/' -> DIV;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
