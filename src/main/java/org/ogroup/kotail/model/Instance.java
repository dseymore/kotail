/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.TreeNode;

/**
 *
 * @author denki
 */
public class Instance implements TreeNode{

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
        if (node instanceof Bean){
            return mbeans.indexOf(node);
        }
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
        return (mbeans == null || mbeans.size() < 1);
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
    
    
}
