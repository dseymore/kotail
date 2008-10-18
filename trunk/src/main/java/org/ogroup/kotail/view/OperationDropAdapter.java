/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Timer;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Operation;

/**
 *
 * @author denki
 */
public class OperationDropAdapter extends DropTargetAdapter{

    private static final Log LOG = LogFactory.getLog(OperationDropAdapter.class);
    
    @Override
    public void drop(DropTargetDropEvent dtde) {
        try{
            DropTargetContext context = dtde.getDropTargetContext();
            //so, we have the thing we're dropping onto... how would I determine if it is the tab 'tab' or the panel.
            LOG.debug(context.getComponent().getClass());
            Rectangle tab = KotailFrame.getTabs().getUI().getTabBounds(KotailFrame.getTabs(), KotailFrame.getTabs().getSelectedIndex());
//            Point tabPt = dtde.getLocation();
//            SwingUtilities.convertPointFromScreen(tabPt, KotailFrame.getTabs());
            Point glassPt = dtde.getLocation();
            if (tab.contains(glassPt)){
                LOG.debug("NEW TAB");
            }
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable t = dtde.getTransferable();

            //our operation! (The droped item)
            Operation o = (Operation)t.getTransferData(Operation.FLAVOR);
            MBeanOperationInfo opInfo = o.getInfo();
            ObjectName on = (ObjectName)((Bean)o.getParent()).getUserObject();
           
            //start a dataset.. (TODO - make this more dynamic)
            DefaultMultiValueCategoryDataset dataset = new DefaultMultiValueCategoryDataset();
            
            JFreeChart chart = ChartFactory.createLineChart("Title", "Label 1", "Label 2", dataset, PlotOrientation.VERTICAL, true, false, false);
            BufferedImage image = chart.createBufferedImage(500,300);
            JLabel lblChart = new JLabel();
            lblChart.setIcon(new ImageIcon(image));
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            p.add(lblChart);
            KotailFrame.getTabs().add(p);
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshThread(lblChart,on,opInfo,dataset), new Date(), 3000);
            
            LOG.info("returning");
            
        }catch(Exception e){
            LOG.error("Error!",e);
        }
    }

    
}
