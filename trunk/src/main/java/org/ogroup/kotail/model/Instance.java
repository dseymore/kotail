/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denki
 */
public class Instance extends DefaultMutableTreeNode {

    private String name;
    private String host;
    private String port;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    
    
}
