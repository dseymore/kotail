package org.ogroup.kotail.view;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.ogroup.kotail.model.Session;

/**
 *
 * @author dseymore
 */
public class KotailFrame extends JFrame{
    
    private static JTree tree; 
    
    public KotailFrame() {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages");
            
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        Session.getInstance().setRoot(root);
        //ok, left side is a Tree
        tree = new JTree(new ApplicationTreeModel(root));
        tree.setEditable(false);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(false);
        tree.setRootVisible(false);
        
        
        JTabbedPane tabs = new JTabbedPane();
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree, tabs);
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("JavaWorld", new Integer(75));
        pieDataset.setValue("Other", new Integer(25));

        JFreeChart chart = ChartFactory.createPieChart("Sample Pie Chart",  pieDataset,true,false,false);
        BufferedImage image = chart.createBufferedImage(500,300);
        JLabel lblChart = new JLabel();
        lblChart.setIcon(new ImageIcon(image));
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(lblChart);
        tabs.add(p);
        
        this.setTitle(bundle.getString("MainWindow.title"));
        this.setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new KotailMenuBar());
    }

    public static JTree getTree() {
        return tree;
    }


       
    
}
