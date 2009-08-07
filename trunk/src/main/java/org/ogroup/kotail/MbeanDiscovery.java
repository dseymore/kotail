/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ogroup.kotail.model.Attribute;
import org.ogroup.kotail.model.Bean;
import org.ogroup.kotail.model.Instance;
import org.ogroup.kotail.model.Feature;
import org.ogroup.kotail.view.KotailFrame;

/**
 *
 * @author denki
 */
public class MbeanDiscovery {

	private static final Log LOG = LogFactory.getLog(MbeanDiscovery.class);

    public static void discoverMbeans(final Instance app) {
        new Thread(
                new Runnable() {

                    public void run() {
                        try {
                            JMXServiceURL url = new JMXServiceURL("rmi", "", 0, "/jndi/rmi://" + app.getHost() + ":" + app.getPort() + "/jmxrmi");
							Map environment = null;
							if (app.getUsername() != null){
								environment = new HashMap();
								String[]  credentials = new String[] {app.getUsername(), app.getPassword()};
								environment.put (JMXConnector.CREDENTIALS, credentials);
							}
                            JMXConnector jmxc = JMXConnectorFactory.connect(url, environment);
                            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
                            //the connection gets stored in the instance
                            app.setUserObject(mbsc);
                            Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
                            List<Bean> beans = new ArrayList<Bean>();
                            for (ObjectInstance oi : set) {
                                ObjectName n = oi.getObjectName();
                                Bean b = new Bean();
                                b.setUserObject(n);
                                MBeanInfo info = mbsc.getMBeanInfo(n);
                                //go through all the ops
                                for (MBeanOperationInfo xyz : info.getOperations()){
                                    //only had signatureless non void returning operations
                                    if((xyz.getSignature() == null || xyz.getSignature().length == 0) && (!"void".equalsIgnoreCase(xyz.getReturnType()))){
                                        Feature op = new Feature();
                                        op.setInfo(xyz);
                                        b.add(op);
                                    }
                                }
								for(MBeanAttributeInfo zyx : info.getAttributes()){
									if ((CompositeData.class.getCanonicalName().equals(zyx.getType()))){
										LOG.info("WE need to capture a composite named: " + n + "." + zyx.getName());
										Object value = mbsc.getAttribute(n, zyx.getName());
										//add this attribute info to our bean
										Attribute attribute = new Attribute();
										attribute.setUserObject(zyx);
										if (value instanceof CompositeDataSupport){
											CompositeDataSupport support = (CompositeDataSupport)value;
											CompositeType type = support.getCompositeType();
											for (String key : type.keySet()){
												OpenType openType = type.getType(key);
												if (openType instanceof SimpleType){
													SimpleType stype = (SimpleType)openType;
													LOG.debug(key + ":" + stype.getClassName());
													if ("java.lang.Integer".equals(stype.getClassName()) || "java.lang.Long".equals(stype.getClassName())){
														Feature result = new Feature();
														result.setName(key);
														result.setInfo(zyx);
														attribute.add(result);
													}
												}
											}
											//if it has attribute values we can graph, we're good to go
											if (!attribute.isLeaf()){
												b.add(attribute);
											}
										}
										LOG.info("value: " + value);
									}
								}
                                //only show beans with operations or attributes
                                if (!b.isLeaf()){
                                    beans.add(b);
                                }
                            }
                            Collections.sort(beans);
                            //now they should be sorted, add them to the tree
                            for (Bean b : beans){
                                app.add(b);
                            }
                            //reload the tree!
                            ((DefaultTreeModel)(KotailFrame.getTree().getModel())).reload();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }
}
