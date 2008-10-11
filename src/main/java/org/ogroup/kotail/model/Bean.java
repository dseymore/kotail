/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import java.util.Enumeration;
import javax.swing.tree.TreeNode;

/**
 *
 * @author denki
 */
public class Bean implements TreeNode{

    private String objectName;
    private TreeNode parent;
    
    @Override
    public Enumeration children() {
        return null;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndex(TreeNode node) {
        //not found
        return -1;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    
}
