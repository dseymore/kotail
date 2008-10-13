/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import javax.management.MBeanOperationInfo;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Operation extends DefaultMutableTreeNode{

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
    
    
    
}
