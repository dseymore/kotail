package org.ogroup.kotail.model;

import javax.management.MBeanAttributeInfo;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author dseymore
 */
public class Attribute extends DefaultMutableTreeNode implements Comparable<Attribute> {


    //the toString of the super does userObject.toString which does what this comparator does.
    @Override
    public int compareTo(Attribute o) {
        return ((MBeanAttributeInfo) this.getUserObject()).toString().compareTo(((MBeanAttributeInfo) o.getUserObject()).toString());
    }

    @Override
    public String toString() {
        return ((MBeanAttributeInfo) this.getUserObject()).getName();
    }

}
