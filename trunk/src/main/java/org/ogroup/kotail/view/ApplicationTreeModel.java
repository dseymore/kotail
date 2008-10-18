package org.ogroup.kotail.view;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author dseymore
 */
public class ApplicationTreeModel extends DefaultTreeModel {

    public ApplicationTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public ApplicationTreeModel(TreeNode root) {
        super(root);
    }

    
    
}
