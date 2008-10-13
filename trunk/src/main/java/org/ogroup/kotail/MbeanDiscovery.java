/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail;

import java.util.Set;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.tree.DefaultTreeModel;
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Operation;
import org.ogroup.kotail.view.KotailFrame;

/**
 *
 * @author denki
 */
public class MbeanDiscovery {

    public static void discoverMbeans(final Instance app) {
        new Thread(
                new Runnable() {

                    public void run() {
                        try {
                            JMXServiceURL url = new JMXServiceURL("rmi", "", 0, "/jndi/rmi://" + app.getHost() + ":" + app.getPort() + "/jmxrmi");
                            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
                            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
                            Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
                            for (ObjectInstance oi : set) {
                                ObjectName n = oi.getObjectName();
                                Bean b = new Bean();
                                b.setUserObject(n);
                                MBeanInfo info = mbsc.getMBeanInfo(n);
                                //go through all the ops
                                for (MBeanOperationInfo xyz : info.getOperations()){
                                    //only had signatureless non void returning operations
                                    if((xyz.getSignature() == null || xyz.getSignature().length == 0) && (!"void".equalsIgnoreCase(xyz.getReturnType()))){
                                        Operation op = new Operation();
                                        op.setInfo(xyz);
                                        b.add(op);
                                    }
                                }
                                //only show beans with operations
                                if (!b.isLeaf()){
                                    app.add(b);
                                }
                                //reload the tree!
                                ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }
}
