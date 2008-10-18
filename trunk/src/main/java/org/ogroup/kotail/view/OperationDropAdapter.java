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
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Operation;
import org.ogroup.kotail.model.Registry;

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
            Rectangle tab = KotailFrame.getTabs().getUI().getTabBounds(KotailFrame.getTabs(), KotailFrame.getTabs().getTabCount() -1 );
            Rectangle fullWidth = new Rectangle(
                    //upper left X
                    Double.valueOf(KotailFrame.getTabs().getBounds().getLocation().getX()).intValue(), 
                    //upper left Y
                    Double.valueOf(KotailFrame.getTabs().getBounds().getLocation().getY()).intValue(), 
                    Double.valueOf(KotailFrame.getTabs().getWidth()).intValue(), 
                    Double.valueOf(tab.getHeight()).intValue());
            Point glassPt = dtde.getLocation();
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);  
            Transferable t = dtde.getTransferable();
            //our operation! (The droped item)
            Operation o = (Operation)t.getTransferData(Operation.FLAVOR);
            MBeanOperationInfo opInfo = o.getInfo();
            Bean parent = ((Bean)o.getParent());
            ObjectName on = (ObjectName)parent.getUserObject();
            Instance app = ((Instance)parent.getParent());
            String operationName = Registry.getPathName(app, on, opInfo);
            //Logic for new or reused panel
            if (fullWidth.contains(glassPt)){
                LOG.debug("NEW TAB");

                //start a dataset.. 
                DefaultMultiValueCategoryDataset dataset = new DefaultMultiValueCategoryDataset();
                //and link it with this panel somehow..
                DataPanel p = new DataPanel();
                p.setDataset(dataset);

                JFreeChart chart = ChartFactory.createLineChart("Title", "Label 1", "Label 2", dataset, PlotOrientation.VERTICAL, true, false, false);
                BufferedImage image = chart.createBufferedImage(500,300);
                JLabel lblChart = new JLabel();
                lblChart.setIcon(new ImageIcon(image));
                p.setLayout(new FlowLayout());
                p.add(lblChart);
                KotailFrame.getTabs().add(p);


                Timer timer = new Timer();
                TimerTask graphRefresh  = new RefreshThread(lblChart,on,opInfo,dataset, ((MBeanServerConnection)app.getUserObject()), operationName);
                timer.scheduleAtFixedRate(graphRefresh, new Date(), 3000);
            }else{
                //register this bean with whatever dataset we're currently looking at. 
                DataPanel dataPanel = ((DataPanel)KotailFrame.getTabs().getSelectedComponent());
                Registry.getInstance().newWatch(operationName, dataPanel.getDataset(), ((MBeanServerConnection)app.getUserObject()), opInfo, on);
            }
            
            LOG.info("returning");
            
        }catch(Exception e){
            LOG.error("Error!",e);
        }
    }

    
}
