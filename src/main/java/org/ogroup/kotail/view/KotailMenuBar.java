package org.ogroup.kotail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;
import org.ogroup.kotail.model.Application;
import org.ogroup.kotail.model.Group;
import org.ogroup.kotail.model.Session;
import org.ogroup.kotail.view.dialog.ApplicationDialog;

/**
 *
 * @author dseymore
 */
public class KotailMenuBar extends JMenuBar{

    public KotailMenuBar() {
        super();
        
        JMenu m = new JMenu("Menu");
        JMenuItem addGroupItem = new JMenuItem("Add Group");
        addGroupItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Group name",
                    "New Group",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    "");
                Group g = new Group();
                g.setName(s);
                Session.getInstance().getRoot().add(g);
                ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload();
            }
        });
        m.add(addGroupItem);
        
        JMenuItem addAppItem = new JMenuItem("Add Application");
        addAppItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationDialog ad = new ApplicationDialog();
                if (ad.getRet() > 0){
                    Group g = ad.getSelectedGroup();
                    Application a = new Application();
                    a.setName(ad.getApplicationName());
                    a.setParent(g);
                    g.insert(a, 1);
                    ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload(g);
                }
            }
        });
        m.add(addAppItem);
        
        JMenuItem addInstanceItem = new JMenuItem("Add Instance");
        m.add(addInstanceItem);
        
        
        //The Exit Menu
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //normal Exit
                System.exit(1);
            }
        });
        m.add(exitItem);
        //add the Normal menu bar       
        this.add(m);
        
        //The Help!
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        this.add(help);
    }
}
