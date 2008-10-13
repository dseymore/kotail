package org.ogroup.kotail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultTreeModel;
import org.ogroup.kotail.MbeanDiscovery;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Session;

/**
 *
 * @author dseymore
 */
public class KotailMenuBar extends JMenuBar{

    public KotailMenuBar() {
        super();
        
        JMenu m = new JMenu("Menu");
       
        JMenuItem addInstanceItem = new JMenuItem("Add Instance");
        addInstanceItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Instance i = new Instance();
                i.setName("Ochan");
                i.setHost("localhost");
                i.setPort("1234");
                Session.getInstance().getRoot().add(i);
                ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload();
                //now, lets fire off the mbean discovery
                MbeanDiscovery.discoverMbeans(i);
            }
        });
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
