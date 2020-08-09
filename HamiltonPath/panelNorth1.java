package HamiltonPath;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class panelNorth1 extends JPanel{
    JLabel statics = new JLabel();
    
    public panelNorth1(){
        styling();
    }
    
    public void styling(){
        statics.setFont(new Font("Arial", Font.PLAIN, 20));
        statics.setText("  press start to find the solution  ");
        statics.setForeground(Color.red);
        statics.setOpaque(false);
        statics.setBackground(Color.GRAY);
        add(statics);
    }
    
    public void lableCleaner(){
        statics.setText("press start to find the solution");
        statics.setForeground(Color.red);
        statics.setOpaque(false);
    }
}

//Hamidreza Zamanian