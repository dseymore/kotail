/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.FlowLayout;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanFeatureInfo;
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
import org.ogroup.kotail.model.Attribute;
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Feature;
import org.ogroup.kotail.model.Registry;
import org.ogroup.kotail.view.util.DropUtils;

/**
 *
 * @author denki
 */
public class FeatureDropAdapter extends DropTargetAdapter{

    private static final Log LOG = LogFactory.getLog(FeatureDropAdapter.class);
    
    @Override
    public void drop(DropTargetDropEvent dtde) {
        try{
            DropTargetContext context = dtde.getDropTargetContext();
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);  
            Transferable t = dtde.getTransferable();
            //our operation! (The droped item)
            Feature o = (Feature)t.getTransferData(Feature.FLAVOR);
            MBeanFeatureInfo opInfo = o.getInfo();
			Bean parent = null;
			if (opInfo instanceof MBeanAttributeInfo){
				parent = ((Bean)((Attribute)o.getParent()).getParent());
			}else{
				parent = ((Bean)o.getParent());
			}
            ObjectName on = (ObjectName)parent.getUserObject();
            Instance app = ((Instance)parent.getParent());
            String operationName = Registry.getPathName(app, on, opInfo, o.getName());
            //Logic for new or reused panel
            if (DropUtils.isDragToNewTab(dtde)){
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
                TimerTask graphRefresh  = new RefreshThread(lblChart,on,opInfo,dataset, ((MBeanServerConnection)app.getUserObject()), operationName, o.getName());
                timer.scheduleAtFixedRate(graphRefresh, new Date(), 3000);
            }else{
                //register this bean with whatever dataset we're currently looking at. 
                DataPanel dataPanel = ((DataPanel)KotailFrame.getTabs().getSelectedComponent());
                Registry.getInstance().newWatch(operationName, o.getName(), dataPanel.getDataset(), ((MBeanServerConnection)app.getUserObject()), opInfo, on);
            }
            
            LOG.info("returning");
            
        }catch(Exception e){
            LOG.error("Error!",e);
        }
    }

    
}
