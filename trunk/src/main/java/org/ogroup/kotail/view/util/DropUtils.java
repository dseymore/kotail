package org.ogroup.kotail.view.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ogroup.kotail.view.KotailFrame;

/**
 *
 * @author dseymore
 */
public class DropUtils {

	private static final Log LOG = LogFactory.getLog(DropUtils.class);

	public static boolean isDragToNewTab(DropTargetDropEvent dtde){
		DropTargetContext context = dtde.getDropTargetContext();
		//so, we have the thing we're dropping onto... how would I determine if it is the tab 'tab' or the panel.
		LOG.debug(context.getComponent().getClass());
		Rectangle tab = KotailFrame.getTabs().getUI().getTabBounds(KotailFrame.getTabs(), KotailFrame.getTabs().getTabCount() -1 );
		Rectangle fullWidth = new Rectangle(
				//upper left X
				Double.valueOf(KotailFrame.getTabs().getBounds().getLocation().getX()).intValue(),
				//upper left Y
				Double.valueOf(KotailFrame.getTabs().getBounds().getLocation().getY()).intValue(),
				Double.valueOf(KotailFrame.getTabs().getWidth()).intValue(),
				Double.valueOf(tab.getHeight()).intValue());
		Point glassPt = dtde.getLocation();
		return fullWidth.contains(glassPt);
	}

}
