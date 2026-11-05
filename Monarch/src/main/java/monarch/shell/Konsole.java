/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package monarch.shell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import monarch.promptz.Commander;

/**
 *
 * @author rash4
 */
public final class Konsole {
    public static final Color COOL = new Color(0xFFC1F1B0); // light greenish
    public static final Dimension DEF_SIZ = new Dimension(500, 420);
    public static final Font BTNZ_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    public static final Font MONOS = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private static final int PMX = 10;
    private static final int GAP = 5;
    
    private JFrame frame;
    private JPanel panel;
    
    private JScrollPane scrl;
    private JTextArea out;
    private JTextField inn;
    
    private JButton neo, exit, clear, save;
    
    private Commander EXE;
    
    private Konsole(){}
    
    public Konsole initialize(){
        frame = new JFrame("Yoy");
        panel = new JPanel(new BorderLayout(GAP, GAP*2));
        EXE = new Commander(this).initialize();
        // Padding around all four edges.
        panel.setBorder(new EmptyBorder(PMX, PMX, PMX, PMX));
        panel.setBackground(COOL);

        // Output area: occupies all remaining space.
        out = new JTextArea("# Hello World\n");
        out.setLineWrap(true);
        out.setWrapStyleWord(true);
        out.setBackground(Color.DARK_GRAY);
        out.setForeground(Color.GREEN);
        out.setFont(MONOS);
        out.setCaretColor(Color.LIGHT_GRAY);
        out.setMargin(new Insets(0, GAP, 0, GAP));

        scrl = new JScrollPane(out,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        panel.add(scrl, BorderLayout.CENTER);

        // Input field: full available width.
        inn = new JTextField("");
        inn.setMargin(new Insets(0, GAP, 0, GAP));
        inn.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER)appendl();
            }
        });
        panel.add(inn, BorderLayout.SOUTH);

        // Buttons: natural preferred height.
        exit = new JButton("Exit");
        exit.addActionListener(e->{Konsole.this.exit();});
        save = new JButton("Save");
        save.addActionListener(e->{Konsole.this.save();});
        clear = new JButton("Clear");
        clear.addActionListener(e->{Konsole.this.clear();});
        neo = new JButton("New");
        neo.addActionListener(e->{Konsole.this.neo();});

        final var insPane = new JPanel();
        insPane.setBackground(COOL);
        insPane.setLayout(new BoxLayout(insPane, BoxLayout.X_AXIS));

        for (JButton btn : new JButton[]{neo, clear, save, exit}) {
            insPane.add(Box.createHorizontalStrut(GAP));
            btn.setFont(BTNZ_FONT);
            insPane.add(btn);
        }

        // Wrap input and buttons into a vertical bottom section.
        JPanel bottom = new JPanel(new BorderLayout(0, GAP));
        bottom.setBackground(COOL);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bottom.add(inn, BorderLayout.NORTH);
        bottom.add(insPane, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setMinimumSize(DEF_SIZ);
//        frame.setMaximumSize(new Dimension(
//                Math.clamp(760, DEF_SIZ.width, Genz.SCREEN.width),
//                Math.clamp(580, DEF_SIZ.height, Genz.SCREEN.height)
//        )); // doesn't seem to work FUCK it.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return this;
    }
    
    public Commander getEXE(){return this.EXE;}
    
    protected void append(String text, int index){
        this.out.append(text);
        this.out.setCaretPosition(Math.clamp(index, 0, out.getDocument().getLength()));
    }
    public void append(String text){
        this.append(text, this.out.getDocument().getLength());
    }
    public void appendl(String text){
        this.append(text + "\n");
    }
    private void appendl(){
        String arg = this.inn.getText();
        this.EXE.execute(arg);
        this.inn.setText("");
    }
    public void insert(String text, int inject){
        if(text == null)return;
        final int pos = Math.clamp(inject, 0, out.getDocument().getLength());
        this.out.insert(text, pos);
        this.out.setCaretPosition(pos + text.length());
    }
    public void insert(String text){
        final int pos = this.out.getCaretPosition();
        System.out.println("caret at " + pos);
        this.insert(text, pos);
    }
    public void neo(){
        final var nao = new Konsole().initialize();
        nao.frame.setLocationRelativeTo(this.frame);
    }
    public void exit(){
        System.exit(0); // for now it's this.
    }
    public void close(){
        this.frame.dispose();
    }
    public void clear(){
        this.out.setText("");
    }
    public void save(){
        // nothing yet..
    }
    
    public static Konsole neYoy(){
        final Konsole yoy = new Konsole();
        return yoy.initialize();
    }
}
