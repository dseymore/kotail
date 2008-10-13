/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ogroup.kotail.model.Operation;

/**
 *
 * @author denki
 */
public class OperationDropAdapter extends DropTargetAdapter{

    private static final Log LOG = LogFactory.getLog(OperationDropAdapter.class);
    
    @Override
    public void drop(DropTargetDropEvent dtde) {
        try{
            DropTargetContext context = dtde.getDropTargetContext();
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable t = dtde.getTransferable();
            //i'm not using the same flavor.. BasicTransferable doesn't know what LocalJVM ref was.. 
            String o = (String)t.getTransferData(Operation.FLAVOR);
            LOG.info(o);
        }catch(Exception e){
            LOG.error("Error!",e);
        }
    }

    
}
