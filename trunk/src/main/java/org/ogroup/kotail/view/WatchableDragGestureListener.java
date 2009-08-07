/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author denki
 */
public class WatchableDragGestureListener implements DragGestureListener {
    
    private static final Log LOG = LogFactory.getLog(WatchableDragGestureListener.class);
    
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        LOG.info("Start drag gesture");
        JTree tree = (JTree) dge.getComponent();
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            // Nothing selected, nothing to drag
            LOG.info("Nothing selected - beep");
            tree.getToolkit().beep();
            } else {
                DefaultMutableTreeNode selection = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (selection.isLeaf()) {
                    dge.startDrag(DragSource.DefaultCopyDrop, (Transferable)selection);
                } else {
                    LOG.info("Not a leaf - beep");
                    tree.getToolkit().beep();
                }
            }
    }
    
}
