package org.ogroup.kotail.view;

import java.awt.image.BufferedImage;
import java.util.TimerTask;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.ogroup.kotail.model.Registry;

/**
 *
 * @author dseymore
 */
public class RefreshThread extends TimerTask {
    
    private static final Log LOG = LogFactory.getLog(RefreshThread.class);
    private JLabel icon;
    private DefaultMultiValueCategoryDataset dataset;

    public RefreshThread(JLabel icon, ObjectName on, MBeanOperationInfo opInfo, DefaultMultiValueCategoryDataset dataset, MBeanServerConnection connection, String name){
        this.icon = icon;
        this.dataset = dataset;
        //register me!
        Registry.getInstance().newWatch(name, dataset, connection, opInfo, on);
    }
    
    @Override
    public void run() {
        try{
            JFreeChart chart = ChartFactory.createLineChart("Graph", "Time", "", 
                    Registry.getInstance().refresh(dataset), PlotOrientation.VERTICAL, true, false, false);
            BufferedImage image = chart.createBufferedImage(500,300);
            icon.setIcon(new ImageIcon(image));
        }catch(Exception e){
            LOG.error("Ack!",e);
        }
    }

    
    
}
