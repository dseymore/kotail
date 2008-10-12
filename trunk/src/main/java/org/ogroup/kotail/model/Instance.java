/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.model;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author denki
 */
public class Instance implements MutableTreeNode {

    private String name;
    private TreeNode parent;
    private Vector<Bean> mbeans;

    @Override
    public Enumeration children() {
        return mbeans.elements();
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return mbeans.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return mbeans.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        if (node instanceof Bean) {
            return mbeans.indexOf(node);
        }
        //not found
        return -1;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public void setUserObject(Object object) {
        return;
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        mbeans.add((Bean) child);
    }

    @Override
    public void remove(int index) {
        mbeans.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        mbeans.remove(node);
    }

    @Override
    public void removeFromParent() {
//        parent.remove(this);
    }

    @Override
    public boolean isLeaf() {
        return (mbeans == null || mbeans.size() < 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
