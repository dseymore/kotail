/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Operation extends DefaultMutableTreeNode implements Transferable{

    public static DataFlavor FLAVOR = null;
    
    //i DO hate this. 
    static{
        try{
            FLAVOR = new DataFlavor("text/html;class=java.lang.String");
        }catch(Exception e){
            //what?!
        }
    }
    
    private MBeanOperationInfo info;

    public MBeanOperationInfo getInfo() {
        return info;
    }

    public void setInfo(MBeanOperationInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return info.getName();
    }
    
     @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return info.getName() + "|" + ((ObjectName)((Bean)this.getParent()).getUserObject()).getCanonicalName();
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(FLAVOR);
    }
    
}
