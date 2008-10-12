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
public class Application implements MutableTreeNode {

    private Vector<Instance> instances;
    private String name;
    private MutableTreeNode parent;

    @Override
    public Enumeration children() {
        return instances.elements();
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public MutableTreeNode getChildAt(int childIndex) {
        return instances.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return instances.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        if (node instanceof Instance) {
            return instances.indexOf(node);
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
        if (instances == null){
            instances = new Vector<Instance>();
        }
        instances.add((Instance)child);
    }

     @Override
    public void remove(int index) {
        instances.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        instances.remove(node);
    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }
    
    @Override
    public boolean isLeaf() {
        return (instances == null || instances.size() < 1);
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
