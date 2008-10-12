/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view.dialog;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.ogroup.kotail.model.Group;
import org.ogroup.kotail.model.Session;

/**
 *
 * @author denki
 */
public class ApplicationDialog extends JDialog {
    
    private Group selectedGroup;
    private String applicationName;
    private int ret;
    
    private JComboBox combo;
    private JTextArea text;

    public ApplicationDialog() {
        super();
        this.setTitle("Add Application");
        init();
        this.setVisible(true);
    }

    private void init(){
        //layout!
        this.setLayout(new GridBagLayout());
        
        Enumeration e = Session.getInstance().getRoot().children();
        Vector<Group> groups = new Vector<Group>();
        while(e.hasMoreElements()){
            groups.add((Group)e.nextElement());
        }
        combo = new JComboBox(groups);
        text = new JTextArea(1, 16);
        JLabel groupLabel = new JLabel("Group:");
        JLabel textLabel = new JLabel("Application Name:");
        
        this.getContentPane().add(groupLabel);
        this.getContentPane().add(combo);
        this.getContentPane().add(textLabel);
        this.getContentPane().add(text);
        
        JButton accept = new JButton("Accept");
        JButton cancel = new JButton("Cancel");
        
        accept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGroup = (Group)combo.getSelectedItem();
                applicationName = text.getText();
                ret = 1;
                dispose();
            }
        });
        
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ret = -1;
                dispose();
            }
        });
        
        this.getContentPane().add(accept);
        this.getContentPane().add(cancel);
        pack();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
    
    
}
