/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Operation;
import org.ogroup.kotail.model.Session;

/**
 *
 * @author denki
 */
public class OperationTransferHandler extends TransferHandler {

    private static final Log LOG = LogFactory.getLog(OperationTransferHandler.class);
    
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        return true;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try{
            //i'm not using the same flavor.. BasicTransferable doesn't know what LocalJVM ref was.. 
            Operation o = (Operation)t.getTransferData(new DataFlavor("text/html;class=java.lang.String"));
            MBeanOperationInfo opInfo = o.getInfo();
            ObjectName on = (ObjectName)((Bean)o.getParent()).getUserObject();
            Object result = Session.getInstance().getConnection().invoke(on, opInfo.getName(), new Object[]{}, new String[]{});
            System.out.println("Result: " + result);
        }catch(Exception e){
            LOG.error("Unable to import drag & drop",e);
        }
        return true;
    }
    
}
