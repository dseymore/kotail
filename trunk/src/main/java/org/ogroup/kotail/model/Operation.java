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
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Operation extends DefaultMutableTreeNode implements  Transferable{

    private static DataFlavor[] FLAVOR = null;
    
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
        return this;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        //crazy construction.. why isnt this static?
        if (FLAVOR == null){
            synchronized(this){
                try{
                    FLAVOR = new DataFlavor[] {new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType)};
                }catch(Exception e){
                    //what?!
                }
            }
        }
        return FLAVOR;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.getMimeType().equals(DataFlavor.javaJVMLocalObjectMimeType);
    }
    
}
