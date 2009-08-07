/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.management.MBeanFeatureInfo;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Feature extends DefaultMutableTreeNode implements Transferable{

    public static DataFlavor FLAVOR = null;
    
    //i DO hate this. 
    static{
        try{
            FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
        }catch(Exception e){
            //what?!
        }
    }

	private MBeanFeatureInfo info;
	private String name;

    public MBeanFeatureInfo getInfo() {
        return info;
    }

    public void setInfo(MBeanFeatureInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        if (getName() == null){
			return info.getName();
		}
		return getName();
    }
    
     @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
         //return info.getName() + "|" + ((ObjectName)((Bean)this.getParent()).getUserObject()).getCanonicalName();
        return this; 
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(FLAVOR);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
    
}
