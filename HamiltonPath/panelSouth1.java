package HamiltonPath;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class panelSouth1 extends JPanel implements ActionListener{
    JButton btnStart = new JButton("Start");
    JButton btnExit = new JButton("Exit");
    JButton btnClean = new JButton("Clean");
    Board2 bd;
    panelNorth1 pN;

    public panelSouth1(Board2 bd, panelNorth1 pN) {
        styling();
        this.bd = bd;
        this.pN = pN;
    }
    
    public void styling(){
        JButton[] btns = {btnClean, btnExit, btnStart};
        for(JButton btn: btns){
            btn.setFont(new Font("Arial", Font.PLAIN, 40));
            btn.addActionListener(this);
            add(btn);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         JButton btn = (JButton) e.getSource();
         
        if(btn == btnExit) {
            System.exit(0);
        }
        else if(btn == btnClean){
            cleanHandler();
        }
        else if(btn == btnStart){
            StartHandler();
        }
    }

	public void cleanHandler(){
		bd.clean();
    	pN.lableCleaner();
    	bd.counter = 1;
	}

    public void StartHandler(){
        bd.execute();
        bd.CM();
        do {
            bd.clean();
            pN.lableCleaner();
            bd.execute();
            bd.CM();
            bd.conditionA = bd.reOrderedVP.size() == Math.pow(bd.N,2);
            bd.conditionB = bd.cbvPoints(new Point(bd.fa,bd.fb)).contains(bd.last_point);
        } while (!(bd.conditionA && bd.conditionB));
        bd.CP();
        pN.statics.setText(
                    "solution found! (do it again for another solution)");
        pN.statics.setForeground(Color.green);
        pN.statics.setOpaque(true);
    }
}
//Hamidreza Zamanian