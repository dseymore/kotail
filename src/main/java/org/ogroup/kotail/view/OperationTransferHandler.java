/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ogroup.kotail.view;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author denki
 */
public class OperationTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        return true;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        
        return true;
    }
    
}
