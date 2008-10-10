package org.ogroup.kotail.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author dseymore
 */
public class KotailMenuBar extends JMenuBar{

    public KotailMenuBar() {
        super();
        
        JMenu m = new JMenu("Menu");
        JMenuItem addGroupItem = new JMenuItem("Add Group");
        JMenuItem addAppItem = new JMenuItem("Add Application");
        JMenuItem addInstanceItem = new JMenuItem("Add Instance");
        JMenuItem exitItem = new JMenuItem("Exit");
        m.add(addGroupItem);
        m.add(addAppItem);
        m.add(addInstanceItem);
        m.add(exitItem);
        this.add(m);
        
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        this.add(help);
    }
}
