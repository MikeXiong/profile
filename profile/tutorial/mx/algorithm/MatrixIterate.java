package mx.algorithm;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created by hxiong on 6/24/14.
 */
public class MatrixIterate {
    static class Node implements IAction{
        Object data = null;
        String name = null;

        int x, y = -1;

        Node(String name, int x, int y){
            this.name = name;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    '}';
        }


        @Override
        public void doAction() {
            System.out.println(this.toString());
        }

        @Override
        public void doActon(int i, int j, int currentStep) {
            this.doAction();
        }
    }

    static void iterateWithClockwise(int maxRow, int maxCol, IAction [][] matrix){
        int [][] tmp = new int[matrix.length][maxCol];

        int i = 0;
        int j = 0;

        String R = "R";
        String L = "L";
        String D = "D";
        String U = "u";
        String direct = R;//"D", "L", "U"

        int step = 0;
        while(true){
            if(exist(i, j, tmp)){
                tmp[i][j] = 1;
//                System.out.println(matrix[i][j]);
                final IAction action = matrix[i][j];
                try {
//                    action.doAction();
                    action.doActon(i, j, ++step);
                    Thread.currentThread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //we can use below way for swing
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        action.doAction();
//                    }
//                    //never sleep here
//                });
//
//                try {
//                    Thread.currentThread().sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if(last(i, j, tmp)){
                    break;
                }

                if(R == direct){
                    j++;
                } else if(D == direct){
                    i++;
                } else if(L == direct){
                    j--;
                } else if(U == direct){
                    i--;
                }
            }else{
                if(R == direct){
                    j--;
                    i++;
                    direct = D;
                } else if(D == direct){
                    j--;
                    i--;
                    direct = L;
                } else if(L == direct){
                    i--;
                    j++;
                    direct = U;
                } else if(U == direct){
                    i++;
                    j++;
                    direct = R;
                }
            }
        }

    }

    static boolean exist(int i, int j, int [][] tmp){
        if(-1 < i && i < tmp.length && -1 < j &&j < tmp[i].length){
            if(1 == tmp[i][j]){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    static boolean last(int i, int j, int [][] tmp){
        if(-1 < i && i < tmp.length && -1 < j && j < tmp[i].length){
            if(!exist(i - 1, j, tmp) &&
                    !exist(i + 1, j, tmp) &&
                    !exist(i, j -1, tmp) &&
                    !exist(i, j + 1, tmp)){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        int maxRow = 10;
        int maxCol = 11;
        Node [][] matrix = new Node[maxRow][maxCol];
        for(int row =  0; row < matrix.length; row++){
            Node [] cols = matrix[row];
            for(int col = 0; col < cols.length; col++){
                cols[col] = new Node("row-col:" + row + "-" + col, row, col);
            }
        }
//        iterateWithClockwise(10, 11, matrix);

        MatrixIteratorSimulator simulator = new MatrixIteratorSimulator(12, 12);

    }
}

interface  IAction{
    void doAction();
    void doActon(int i, int j, int currentStep);
}

class MatrixIteratorSimulator extends JFrame{

    private JPanel mainPanel = new JPanel();

    private JPanel ctrlpanel = new JPanel();

    CusJpanel [][] gs = null;

    public MatrixIteratorSimulator(int row, int col){
        this.setTitle(this.getClass().getName());
        this.getContentPane().setLayout(new BorderLayout());
        this.setBounds(100, 100, 800, 600);
        this.setPreferredSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridLayout gl = new GridLayout(row, col, 10, 10);

        this.mainPanel.setLayout(gl);

        this.gs = new CusJpanel[row][col];
        for(int i = 0; i < gs.length; i++){
            CusJpanel[] gr = this.gs[i];
            for(int j = 0; j < gr.length; j++){
                CusJpanel p = new CusJpanel();
                p.setBackground(Color.darkGray);

                this.gs[i][j] = p;
                this.mainPanel.add(p);
            }
        }

        JButton btn = new JButton("Start");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                System.out.println("cmd:" + cmd);
                //Need a separate thread here
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MatrixIterate.iterateWithClockwise(gs.length, gs[0].length, gs);
                    }
                }).start();
                //Never use below way as it will add the thread to Swing thread queue, it will cause all action
                //only been execute after this thread has finished
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        MatrixIterate.iterateWithClockwise(gs.length, gs[0].length, gs);
//                    }
//                });
                System.out.println("END click");

            }
        });
        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        });
        this.ctrlpanel.add(btn);



        this.add(mainPanel, BorderLayout.CENTER);
        this.add(ctrlpanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }



    static class CusJpanel extends JPanel implements IAction{


        @Override
        public void doAction() {
            this.setBackground(Color.red);
            this.validate();
        }

        @Override
        public void doActon(int i, int j,  int currentStep) {
            int v = currentStep * 255;
            int [] mask = new int[3];
            int index = 0;
            do {
                mask[index++] = v % 255;
                v = v / 255;
            } while(0 < v);
            System.out.println("Step:" + currentStep + ", v=" + currentStep * 100
                    + ", mask[0]:" + mask[0] + ", mask[1]:" + mask[1] + ", mask[2]:" + mask[2]  );
            Color c = new Color(mask[0] + 80, mask[1], mask[2] + 220);
            this.setBackground(c);
            this.validate();
        }
    }
}
