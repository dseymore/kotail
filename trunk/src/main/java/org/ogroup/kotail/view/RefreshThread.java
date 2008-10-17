package org.ogroup.kotail.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.ogroup.kotail.model.Session;

/**
 *
 * @author dseymore
 */
public class RefreshThread extends TimerTask {
    
    private static final Log LOG = LogFactory.getLog(RefreshThread.class);
    private JLabel icon;
    private ObjectName on;
    private MBeanOperationInfo opInfo;
    private DefaultMultiValueCategoryDataset dataset;

    public RefreshThread(JLabel icon, ObjectName on, MBeanOperationInfo opInfo, DefaultMultiValueCategoryDataset dataset){
        this.icon = icon;
        this.on = on;
        this.opInfo = opInfo;
        this.dataset = dataset;
    }
    
    @Override
    public void run() {
        try{
            Object result = Session.getInstance().getConnection().invoke(on, opInfo.getName(), new Object[]{}, new String[]{});
            List<Object> values = new ArrayList<Object>();
            values.add(result);
            dataset.add(values, on, new Date());
            
            JFreeChart chart = ChartFactory.createLineChart("X", "Y", "Z", dataset, PlotOrientation.VERTICAL, true, false, false);
            BufferedImage image = chart.createBufferedImage(500,300);
            icon.setIcon(new ImageIcon(image));
        }catch(Exception e){
            
        }
    }

    
    
}
