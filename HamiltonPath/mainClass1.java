package HamiltonPath;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class mainClass1 {

    public mainClass1() {
        JFrame f=new JFrame("Chess");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 700);
        f.setLocationRelativeTo(null);
        
        String input = JOptionPane.
                    showInputDialog("enter coordinate (\"1 1\" to \"8 8\"): ");
        int a = input.charAt(0)-48, b = input.charAt(2)-48;
        
        Board2 bd = new Board2(a-1, b-1);
        panelNorth1 pN = new panelNorth1();
        panelSouth1 pS = new panelSouth1(bd, pN);
        
        f.add(bd, BorderLayout.CENTER);
        f.add(pN, BorderLayout.NORTH);
        f.add(pS, BorderLayout.SOUTH);
        
        f.setResizable(false);
        f.setVisible(true);
    }
    
    public static void main(String[] args) {
        new mainClass1();
    }
}

//Hamidreza Zamanian