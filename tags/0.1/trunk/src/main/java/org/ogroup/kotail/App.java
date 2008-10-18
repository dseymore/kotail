package org.ogroup.kotail;

import org.ogroup.kotail.view.KotailFrame;
import javax.swing.JFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Log LOG = LogFactory.getLog(App.class);
    
    public static void main( String[] args )
    {
        try{
        
        //do normal shit. 
        BasicConfigurator.configure();
        LOG.info("Started log4j");
          
        JFrame frame = new KotailFrame();
        frame.pack();
        frame.setVisible(true);        
        
        
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
