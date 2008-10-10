package org.ogroup.kotail;

import org.ogroup.kotail.view.KotailFrame;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
//            JMXServiceURL url = new JMXServiceURL("rmi","",0,"/jndi/rmi://"+"acfsrv2.rnsolutions.com"+":"+"6789"+"/jmxrmi");
//            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
//            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
//            Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
//            for (ObjectInstance oi : set){
//                ObjectName n = oi.getObjectName();
//                System.out.println(n.getCanonicalName());
//            }
            
        JFrame frame = new KotailFrame();
        frame.pack();
        frame.setVisible(true);        
        
        
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
