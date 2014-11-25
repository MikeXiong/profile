package mx.algorithm.tree;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Created by hxiong on 9/30/14.
 */
public class SwingTreeGraphicer extends JFrame implements ITreeGraphicer {

    private JFormattedTextField keyField = null;
    private JFormattedTextField dataField = null;

    private JLabel keyLabel = null;
    private JLabel dataLabel = null;

    private JButton add = null;
    private JButton reset = null;

    private JPanel controlPanel = null;
    private CanvalPanel canvalPanel = null;

    private IGraphicTree tree = null;


    public SwingTreeGraphicer(IGraphicTree tree){
        this.tree = tree;
        this.init();
    }

    void init(){
        this.setLayout(new BorderLayout(10, 10));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(50, 100, 1200, 600);

        initControlPanel();

        initCanvalPanel();

        this.setVisible(true);
    }

    void initControlPanel(){
        this.controlPanel = new JPanel(new FlowLayout());
        MaskFormatter mask = null;
        try{
            mask = new MaskFormatter("##");
        }catch (ParseException e){
            e.printStackTrace();
        }

        this.keyField = new JFormattedTextField(mask);
        this.keyField.setText("07");
        this.keyField.setColumns(10);
        this.dataField = new JFormattedTextField(mask);
        this.dataField.setText("07");
        this.dataField.setColumns(10);
        this.keyLabel = new JLabel("Key:");
        this.dataLabel = new JLabel("Value:");
        this.add = new JButton("Add");
        this.reset = new JButton("Reset");

        this.getContentPane().add(this.controlPanel, BorderLayout.SOUTH);
        this.controlPanel.add(this.keyLabel);
        this.controlPanel.add(this.keyField);
        this.controlPanel.add(this.dataLabel);
        this.controlPanel.add(this.dataField);
        this.controlPanel.add(this.add);
        this.controlPanel.add(this.reset);

        this.controlPanel.setBorder(BorderFactory.createEtchedBorder());

        this.registControlPanelListerner();
    }

    JScrollPane scrollPane = null;
    void initCanvalPanel(){
        this.canvalPanel = new CanvalPanel(this);
        this.scrollPane = new JScrollPane(this.canvalPanel);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    List<Integer> historyKeys = new ArrayList<>();
    void registControlPanelListerner(){
        final SwingTreeGraphicer _this = this;
        this.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numStr = _this.keyField.getText();
                System.out.println("numStr=" + numStr);
                int key =  Integer.parseInt(_this.keyField.getText());
                int value = Integer.parseInt(_this.dataField.getText());
                _this.historyKeys.add(key);
//                _this.tree.insert(key, value);

                _this.update(_this.tree);
                JScrollBar vbar = _this.scrollPane.getVerticalScrollBar();
                //Tricky!
                vbar.setValue(vbar.getMaximum());

            }
        });

        this.reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _this.keyField.setValue(null);
                _this.dataField.setValue(null);
            }
        });
    }


    @Override
    public void display(IGraphicTree tree) {

    }

    @Override
    public void update(IGraphicTree tree) {
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        String []fontFamilies = ge.getAvailableFontFamilyNames();
//        for(String ff: fontFamilies){
//            System.out.println(ff);
//        }
        this.canvalPanel.repaintTree(tree);
    }

    public static void main(String[] args) {
        new SwingTreeGraphicer(null);
    }

    static class CanvalPanel extends JPanel{

        SwingTreeGraphicer graphicer = null;

        IGraphicTree tree = null;

        int nextTreeOffset = 0;


        CanvalPanel(SwingTreeGraphicer graphicer){
            this.graphicer = graphicer;
            this.tree = this.graphicer.tree;
        }

        void repaintTree(IGraphicTree tree){
            this.tree = tree;
            this.repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //reset offset
            this.nextTreeOffset = 0;

            if(null == this.tree){
                System.err.println("Tree is null");
                return;
            }

            this.paintTree(g, this.tree);

            IGraphicTree _tree = this.tree.newEmptyTree();
            int size = graphicer.historyKeys.size();
            for(int i = 0; i < size; i++){
                _tree.insert(graphicer.historyKeys.get(i), null);
                paintStepString(g, i, graphicer.historyKeys, _tree);
                this.paintTree(g, _tree);
            }
        }



        void paintTree(Graphics g, IGraphicTree tree){
            System.out.println("paintTree...");

            GraphicInfo gi = this.calculateGraphicInfo(tree);

            this.paintNodes(g, 1, gi.pareNodes, gi.parePoints, gi.minNodeGap, gi.depth, gi.node_gap_v,
                    gi.node_width, gi.node_height, gi);

            this.updateNextTreeOffset(gi.tree_height + 50);
        }

        GraphicInfo calculateGraphicInfo(IGraphicTree tree){
            IGraphicTreeNode root = tree.getRoot();
            if(null == root){
                System.out.println("root is null.");
                return null;
            }
            List<IGraphicTreeNode> lay1 = new ArrayList<IGraphicTreeNode>();
            List<IGraphicTreeNode> lay2 = new ArrayList<IGraphicTreeNode>();
            int depth = 0;
            int size = 0;
            lay1.add(root);
            while(!lay1.isEmpty()){
                depth++;
                size = lay1.size();
                for(int i = 0; i < size; i++){
                    if(null != lay1.get(i)){
                        lay2.addAll(lay1.get(i).getAllChildrenIncludeNull());
                    }
                }
                lay1 = lay2;
                lay2 = new ArrayList<>();
            }
            depth--;
            final int WIDTH_PER_NUM = 10;
            final int GAP_PER_NUM = 5;
            final int GAP_V_PER_NODE = 25;
            final int GAP_H_PER_NODE = 5;
            final int NODE_HEIGH = 25;
            int nodeWidth = (WIDTH_PER_NUM + GAP_PER_NUM) * root.getMaxChildNum();
            int treeHeight = (NODE_HEIGH + GAP_V_PER_NODE) * depth;
//            int maxLeafs = (int)Math.pow(root.getMaxChildNum(), depth - 1);
            int treeWidth = (int)((nodeWidth + GAP_H_PER_NODE) * Math.pow(root.getMaxChildNum(), depth - 1));

            this.inflatePaneSize(treeWidth, treeHeight);

            int x_Offset = 50;
            int y_Offset = 50;

            lay1.clear();
            lay2 = null;
            lay1.add(root);
            int x_root = x_Offset + treeWidth / 2;
            int y_root = y_Offset + this.nextTreeOffset;
            Point p = new Point(x_root, y_root);

            List<Point> nodesPonits = new ArrayList<Point>();
            nodesPonits.add(p);

            final int minNodeGap = nodeWidth + GAP_H_PER_NODE;

            GraphicInfo gi = new GraphicInfo();
            gi.depth = depth;
            gi.minNodeGap = minNodeGap;
            gi.node_gap_v = NODE_HEIGH + GAP_V_PER_NODE;
            gi.node_width = nodeWidth;
            gi.node_height = NODE_HEIGH;
            gi.pareNodes = lay1;
            gi.parePoints = nodesPonits;

            gi.tree_height = treeHeight;
            return gi;
        }

        void paintNodes(Graphics g, int lay,
                        List<IGraphicTreeNode> pareNodes, List<Point> nodesPonits,
                        final int minNodeGap,
                        final int maxLay,
                        final int node_v_gap, final int node_width, final int node_height,
                        GraphicInfo gi){
            int size = pareNodes.size();
            List<IGraphicTreeNode> children = new ArrayList<IGraphicTreeNode>();
            List<Point> childrenPoints = new ArrayList<Point>();
            int maxChildNum = 0;
            for(int i = 0; i < size; i++){
                if(null != pareNodes.get(i)){
                    maxChildNum = pareNodes.get(i).getMaxChildNum();
                    break;
                }
            }
            int currentFirstChildOffset = ((int)Math.pow(maxChildNum, maxLay - lay -1))
                    * (maxChildNum - 1) * minNodeGap / 2;
            int nodeGap = ((int)Math.pow(maxChildNum, maxLay - lay - 1))
                    * minNodeGap;
            for(int i = 0; i < size; i++){
                IGraphicTreeNode pare = pareNodes.get(i);
                if(null == pare){
                    continue;
                }
                List<IGraphicTreeNode> childs = pare.getAllChildrenIncludeNull();
                Point parePoint = nodesPonits.get(i);
                for(int j = 0; j < maxChildNum; j++){

                    Point p = new Point();
                    childrenPoints.add(p);

                    IGraphicTreeNode child = null == childs ? null : childs.get(j);
                    children.add(child);

                    p.x = parePoint.x - currentFirstChildOffset + j * nodeGap;
                    p.y = parePoint.y + node_v_gap;
                    int x = p.x - node_width / 2;
                    int y = p.y - node_height / 2;

                    if(null != child){
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawLine(parePoint.x, parePoint.y, p.x, p.y);
                    }
                }
                //draw line to child first and then draw this node, this can avoid line diplay above on node
                g.setColor(Color.DARK_GRAY);
                g.fillOval(rec_x(parePoint, node_width), rec_y(parePoint, node_height),
                        node_width, node_height);
                g.setColor(Color.CYAN);
                g.drawString(getNodeKeyString(pare).toString(),
                        text_x(parePoint, node_width), text_y(parePoint, node_height));

            }
            if(lay < maxLay){
                this.paintNodes(g, lay + 1, children, childrenPoints,
                        minNodeGap, maxLay,
                        node_v_gap, node_width, node_height, gi);
            }
        }

        void paintStepString(Graphics g, int keyIndex, List<Integer> histroyKeys, IGraphicTree _tree){
            g.setColor(Color.RED);
            g.drawLine(0, this.nextTreeOffset + 10, this.getWidth(), this.nextTreeOffset + 10);
            g.drawString("Step[" + (keyIndex + 1) + "] insert:" + histroyKeys.get(keyIndex), 0, this.nextTreeOffset + 30);
            this.updateNextTreeOffset(30);
        }

        static StringBuilder getNodeKeyString(IGraphicTreeNode node){
            List keys = node.getKeys();
            StringBuilder buff = new StringBuilder();
            for(Object key: keys){
                buff.append(null == key ? "╳" : key).append(",");
            }
            return buff;
        }

        static int rec_x(Point nodePoint, int nodeWidth){
            return nodePoint.x - nodeWidth / 2;
        }

        static int rec_y(Point nodePoint, int nodeHeight){
            return nodePoint.y - nodeHeight / 2;
        }

        static int text_x(Point nodePoint, int nodeWidth){
            return nodePoint.x - nodeWidth / 2 + 5;
        }

        static int text_y(Point nodePoint, int nodeHeight){
            return nodePoint.y + 5;
        }

        void updateNextTreeOffset(int offSetIncriment){
            this.nextTreeOffset += offSetIncriment;
        }

        void inflatePaneSize(int treeWidth, int treeHeight){
            int w = this.getWidth();
            int h = this.getHeight();
            System.out.println("w=" + w + ", h=" + h + ", treeWidth=" + treeWidth + ", treeHeight=" + treeHeight
                    + ", nextTreeOffset=" + nextTreeOffset);
            int h_ = this.nextTreeOffset + treeHeight;

            w = w < treeWidth ? treeWidth + 50 : w;
            h = h < h_ ? h_ + 50: h;
            System.out.println("w=" + w + ", h=" + h + ", treeWidth=" + treeWidth + ", treeHeight=" + treeHeight
                    + ", nextTreeOffset=" + nextTreeOffset);
            this.setPreferredSize(new Dimension(w, h));
            this.revalidate();
        }

    }


    static class GraphicInfo{
        List<IGraphicTreeNode> pareNodes = null;
        List<Point> parePoints = null;
        int minNodeGap = -1;
        int depth = -1;
        int node_gap_v = -1;
        int node_width = -1;
        int node_height = -1;

        int tree_height = -1;
    }

}
