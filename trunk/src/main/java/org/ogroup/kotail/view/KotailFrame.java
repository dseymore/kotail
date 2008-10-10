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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author dseymore
 */
public class KotailFrame extends JFrame{

    public KotailFrame() {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages");
            
        //ok, left side is a Tree
        JTree tree = new JTree(new ApplicationTreeModel(new DefaultMutableTreeNode()));
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

}
