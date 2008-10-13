/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.model;

import javax.management.MBeanServerConnection;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Session {

    private DefaultMutableTreeNode root;
    
    private static Session instance = null;
    private MBeanServerConnection connection = null;
    
    private Session(){
        //hiding for singleton ~_~
    }
    
    public static Session getInstance(){
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }

    public MBeanServerConnection getConnection() {
        return connection;
    }

    public void setConnection(MBeanServerConnection connection) {
        this.connection = connection;
    }
    
    
    
    
}
