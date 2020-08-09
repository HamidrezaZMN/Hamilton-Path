package HamiltonPath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board2 extends JPanel implements MouseListener{
    
// basics ---------------------------- start //
    
    int A = 5, width, height, W, H, gapW, gapH,
            N = 8, N2 = (int) Math.pow((float)N, 2.0), a, b, num=0, counter = 1;
    final int fa = N/2, fb = N/2;
    Point mPoint = new Point(fa, fb), last_point;
    ArrayList<Point> VP = new ArrayList<>()
                    , reOrderedVP = new ArrayList<>()
                    , nextHouses = new ArrayList<>()
                    , VP_clicked = new ArrayList<>();
    ArrayList<int[]> chess = new ArrayList<>(), theBoard = new ArrayList<>();
    int[] pa = {-1,-1,+1,+1,-2,+2,-2,+2};
    int[] pb = {-2,+2,-2,+2,-1,-1,+1,+1};
    boolean conditionA, conditionB;

    public Board2(int a, int b) {
        addMouseListener(this);
        this.a = a;
        this.b = b;
    }
    
    @Override
    public void paintComponent(Graphics g){
        width = getWidth(); height = getHeight();
        W = width-2*A; H = height-2*A;
        gapW = W/N; gapH = H/N;
        
        styling(g, gapW, gapH);
    }
    
// basics ---------------------------- end //
    
// styling ---------------------------- end //
    
    public void styling(Graphics g, int gapW, int gapH){
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++){
                if((i+j)%2==0)
                    g.setColor(new Color(200, 200, 200));
                else
                    g.setColor(Color.WHITE);
                g.fillRect(A+j*gapW, A+i*gapH, gapW, gapH);
                
                g.setColor(Color.BLACK);
                g.drawRect(A+j*gapW, A+i*gapH, gapW, gapH);
                
                int[] coor = {A+j*gapW, A+i*gapH, gapW, gapH};
                theBoard.add(coor);
            }
    }

// styling ---------------------------- end //
    
// execution ---------------------------- start //
    
    //checks if the point coordinate fits in N*N board
    public boolean in_1_N(int x, int y){
        return x<N && x>=0 && y<N && y>=0;
    }
    
    //gives the totall points that the knight can go
    public ArrayList<Point> cbvPoints(Point point){
        int i = point.x, j = point.y;
        ArrayList<Point> arr_cbvPoints = new ArrayList<>();
        for(int x=0; x<N; x++)
            if (in_1_N(i-pa[x], j-pb[x]))
                arr_cbvPoints.add(new Point(i-pa[x], j-pb[x]));
        
        return arr_cbvPoints;
    }
    
    //number of coordinates of a point
    public int numOfCoordinates(Point p){        
        return cbvPoints(p).size();
    }
    
    //chooses a random item of an array
    public Point RandomChoice(ArrayList<Point> inp3){
        int n = (int) (Math.random() * inp3.size());
        return inp3.get(n);
    }
    
    //chooses a random item of an array of points with minimum numOfCoordinate
    public Point randOfMin(ArrayList<Point> inp){
        ArrayList<int[]> inp2 = new ArrayList<>();
        for(int i=0; i<inp.size(); i++){
            int[] num = {inp.get(i).x, inp.get(i).y,
                         numOfCoordinates(inp.get(i))};
            inp2.add(num);
        }
        
        int minimum = numOfCoordinates(new Point(fa, fb));
        ArrayList<Point> inp2_filtered = new ArrayList<>();
        for(int[] i: inp2)
            if(i[2]<minimum)
                minimum = i[2];
        for(int[] i: inp2)
            if(i[2]==minimum)
                inp2_filtered.add(new Point(i[0], i[1]));
        
        return RandomChoice(inp2_filtered);
    }
    
    //executes a random chess of vp
    public void execute(){
        while(true){
            VP.add(mPoint);
            ArrayList<Point> cbvPoints_mPoint = new ArrayList<>();
            for(Point p: cbvPoints(mPoint)){
                if(VP.contains(p)==false){
                    cbvPoints_mPoint.add(p);
                }
            }
            if(cbvPoints_mPoint.isEmpty())
                break;
            mPoint = randOfMin(cbvPoints_mPoint);
            last_point = mPoint;
        }
    }
    
// execution ---------------------------- end //
    
// printers ---------------------------- start //
    
    //chess printer
    public void CP(){
        int aa, bb, cc, dd, x;
        
        Graphics g = getGraphics();
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                if(!(chess.get(i)[j]==0 && i!=a && j!=b)){
                    
                    x = i*8 + j;
                    aa = theBoard.get(x)[0];
                    bb = theBoard.get(x)[1];
                    cc = theBoard.get(x)[2];
                    dd = theBoard.get(x)[3];
                    double c1 = (double) cc/2, c2 = (double) dd/2;
                    c1 += aa-3; c2 += bb+5;
                    if(chess.get(i)[j]==63 || chess.get(i)[j]==0){
                        g.setColor(Color.yellow);
                        g.fillRect(aa, bb, cc, dd);
                    }
                    
                    g.setColor(Color.BLACK);
                    g.drawRect(aa, bb, cc, dd);
                    g.drawString(String.format("%d",
                                    chess.get(i)[j]+1), (int)c1, (int)c2);
                }
    }
    
// printers ---------------------------- end //
    
// side methods ---------------------------- start //
    
    //chess maker
    public void CM(){
        reOrder();
        for(int i=0; i<N; i++)
            chess.add(new int[N]);
        for(int i=0; i<reOrderedVP.size(); i++){
            chess.get(reOrderedVP.get(i).x)[reOrderedVP.get(i).y] = i;
        }
    }
    
    //cleans the board to make it ready for another execution
    public void clean(){
        mPoint = new Point(fa, fb);
        VP = new ArrayList<>();
        reOrderedVP = new ArrayList<>();
        num = 0;
        chess = new ArrayList<>();
//        for(int a=0; a<8; a++){
//            for(int b=0; b<8; b++){
//               btns[a][b].setText("");
//            }
//        }
//        setColor();
        Graphics g = getGraphics();
        styling(g, gapW, gapH);
        VP_clicked = new ArrayList<>();
        counter = 1;
    }
    
    //reorders vp to make it start from the input point
    public void reOrder(){
        for(int p=0; p<VP.size(); p++){
            if(VP.get(p).equals(new Point(a,b))){
                num = p;
                break;
            }
        }
        
        for(int i=num; i<VP.size(); i++)
            reOrderedVP.add(VP.get(i));
        for(int i=0; i<num; i++)
            reOrderedVP.add(VP.get(i));
    }
    
    
    public void boardPrinter(Point p, boolean bool){
        int x = p.x, y = p.y;
        int i = x*8 + y;
        int aa = theBoard.get(i)[0],
            bb = theBoard.get(i)[1],
            cc = theBoard.get(i)[2],
            dd = theBoard.get(i)[3];
        double c1 = (double) cc/2, c2 = (double) dd/2;
        c1 += aa-3; c2 += bb+5;
        
        Graphics g = getGraphics();
        
        if(bool==true){
            g.setColor(Color.yellow);
            g.fillRect(aa, bb, cc, dd);
            g.setColor(Color.BLACK);
            g.drawRect(aa, bb, cc, dd);
            g.drawString(String.format("%d", numOfCoordinates(p)),
                                                        (int)c1, (int)c2);
        }
        else{
            if((p.x+p.y)%2==0)
                    g.setColor(new Color(200, 200, 200));
            else
                g.setColor(Color.WHITE);
            g.fillRect(aa, bb, cc, dd);
            g.setColor(Color.BLACK);
            g.drawRect(aa, bb, cc, dd);
        }
        
    }
    
// side methods ---------------------------- end //
    
// Mouse Listener ---------------------------- start //
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int aa=0, bb=0, cc=0, dd=0, coor1=0, coor2=0;
        double c1=0, c2=0;
        Graphics g = getGraphics();

        for(int i=0; i<theBoard.size(); i++){
            aa = theBoard.get(i)[0]; bb = theBoard.get(i)[1];
            cc = theBoard.get(i)[2]; dd = theBoard.get(i)[3];
            boolean b1 = x>=aa && x<aa+cc;
            boolean b2 = y>=bb && y<bb+dd;
            c1 = (double) cc/2; c2 = (double) dd/2;
            c1 += aa-3; c2 += bb+5;
            coor1 = i/8; coor2 = i%8;
            if(b1 && b2){
                break;
            }
        }
        Point clickedPoint = new Point(coor1, coor2);
        VP_clicked.add(clickedPoint);
        
        //nextHouses_clean
        for(Point p: nextHouses)
            boardPrinter(p, false);
        nextHouses = new ArrayList<>();
        
        //1
        g.setColor(new Color(200, 200, 255));
        g.fillRect(aa, bb, cc, dd);
        g.setColor(Color.BLACK);
        g.drawRect(aa, bb, cc, dd);
        g.drawString(String.format("%d", counter), (int)c1, (int)c2);
        counter++;
        
        //nextHouses_find&styling
        int mini=8;
        for(Point p: cbvPoints(clickedPoint)){
            if(VP_clicked.contains(p)==false)
                if(numOfCoordinates(p)<mini)
                    mini = numOfCoordinates(p);
        }
        for(Point p: cbvPoints(clickedPoint)){
            if(VP_clicked.contains(p)==false)
                if(numOfCoordinates(p)==mini)
                   nextHouses.add(p);
        }
        for(Point p: nextHouses)
            boardPrinter(p, true);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
// Mouse Listener ---------------------------- end //

}

//Hamidreza Zamanian