/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.model;

import javax.management.ObjectName;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Bean extends DefaultMutableTreeNode implements Comparable<Bean> {
    
    //the toString of the super does userObject.toString which does what this comparator does.
    @Override
    public int compareTo(Bean o) {
        return ((ObjectName) this.getUserObject()).toString().compareTo(((ObjectName) o.getUserObject()).toString());
    }

    
}
