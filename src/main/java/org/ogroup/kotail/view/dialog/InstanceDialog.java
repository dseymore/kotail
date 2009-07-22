/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.view.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ogroup.kotail.MbeanDiscovery;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Session;
import org.ogroup.kotail.view.KotailFrame;

/**
 *
 * @author denki
 */
public class InstanceDialog extends JDialog {
    private static final Log LOG = LogFactory.getLog(InstanceDialog.class);
    
    private JTextField nameField;
    private JTextField host;
    private JTextField port;
	private JTextField username;
	private JPasswordField password;

    public InstanceDialog(Frame owner, boolean modal) {
        super(owner, modal);
        buildMe();
    }

    public InstanceDialog(Frame owner) {
        super(owner);
        buildMe();
    }

    public InstanceDialog() {
        buildMe();
    }

    private void buildMe() {
        this.setLayout(new GridLayout(4, 2));

        nameField = new JTextField();
        host = new JTextField();
        port = new JTextField();
		username = new JTextField();
		password = new JPasswordField();
        JLabel nameLabel = new JLabel("Name: ");
        JLabel hostLabel = new JLabel("Host: ");
        JLabel portLabel = new JLabel("Port: ");
		JLabel usernameLabel = new JLabel("User: ");
		JLabel passwordLabel = new JLabel("Password: ");

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("OK");
                Instance i = new Instance();
                i.setName(getNameField().getText());
                i.setHost(getHost().getText());
                i.setPort(getPort().getText());
				i.setUsername(getUsername().getText());
				i.setPassword(String.valueOf(getPassword().getPassword()));

                Session.getInstance().getRoot().add(i);
                ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload();
                //now, lets fire off the mbean discovery
                MbeanDiscovery.discoverMbeans(i);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("CANCEL");
                dispose();
            }
        });
        this.getContentPane().add(nameLabel);
        this.getContentPane().add(nameField);
        this.getContentPane().add(hostLabel);
        this.getContentPane().add(host);
        this.getContentPane().add(portLabel);
        this.getContentPane().add(port);
		this.getContentPane().add(usernameLabel);
		this.getContentPane().add(username);
		this.getContentPane().add(passwordLabel);
		this.getContentPane().add(password);
        this.getContentPane().add(okButton);
        this.getContentPane().add(cancelButton);
        pack();
    }

    public JTextField getNameField() {
        return nameField;
    }
    
    public JTextField getHost() {
        return host;
    }

    public JTextField getPort() {
        return port;
    }

	public JPasswordField getPassword() {
		return password;
	}

	public JTextField getUsername() {
		return username;
	}



}
