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
public class Group implements MutableTreeNode{
    private Vector<Application>  applications;
    private String name;
    private MutableTreeNode parent;

    @Override
    public Enumeration children() {
        return applications.elements();
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return applications.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return applications.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        if (node instanceof Application){
            return applications.indexOf(node);
        }
        //not found
        return -1;
    }

    @Override
    public MutableTreeNode getParent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return (applications == null || applications.size() < 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString(){
        return name;
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        if (applications == null){
            applications = new Vector<Application>();
        }
        applications.add((Application)child);
    }

    @Override
    public void remove(int index) {
        applications.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        applications.remove(node);
    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public void setUserObject(Object object) {
        return;
    }
    
    
    
}
