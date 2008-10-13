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

    @Override
    public int compareTo(Bean o) {
        return ((ObjectName) this.getUserObject()).getCanonicalName().compareTo(((ObjectName) o.getUserObject()).getCanonicalName());
    }
}
