/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.SimpleType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;

/**
 * The registry is responsible for keeping the connection and related ObjectName and operation connected.
 * It also connects that grouping to a dataset.
 * @author denki
 */
public class Registry {

    private static final Log LOG = LogFactory.getLog(Registry.class);
    private Map<String, Watchable> backend;
    private Map<DefaultMultiValueCategoryDataset, List<Watchable>> frontend;
    private static Registry instance;

    private Registry() {
        backend = new HashMap<String, Watchable>();
        frontend = new HashMap<DefaultMultiValueCategoryDataset, List<Watchable>>();
    }

    public static Registry getInstance() {
        if (instance == null) {
            instance = new Registry();
        }
        return instance;
    }

    public static String getPathName(Instance instance, ObjectName objectName, MBeanFeatureInfo operationInfo, String name) {
        return instance.getName() + ":" + objectName.toString() + "/" + operationInfo.getName() + ":" + name;
    }

    /**
     * We modify the dataset passed in
     * @param current
     * @return for convinience we also return it
     */
    public DefaultMultiValueCategoryDataset refresh(DefaultMultiValueCategoryDataset current) {
        List<Watchable> todo = frontend.get(current);
        Date now = new Date();
        for (Watchable what : todo) {
            try {
				Object result = null;
				if (what.getFeatureInfo() instanceof MBeanOperationInfo){
					result = what.getConnection().invoke(what.getObjectName(), what.getFeatureInfo().getName(), new Object[]{}, new String[]{});
				}else{
					result = what.getConnection().getAttribute(what.getObjectName(), what.getFeatureInfo().getName());
					if (result instanceof CompositeDataSupport){
						CompositeDataSupport support = (CompositeDataSupport)result;
						CompositeType type = support.getCompositeType();
						result = support.get(what.getName());
					}
				}
                LOG.debug("Result: (" + what.getLabel() + ")(" + result + ")");
                List<Object> values = new ArrayList<Object>();
                values.add(result);
                //new data!
                current.add(values, what.getLabel(), now);
            } catch (Exception e) {
                LOG.error("Inserting -1 for unavaiable value:" + what.getObjectName(), e);
                List<Object> values = new ArrayList<Object>();
                values.add(new Long(-1));
                //unable to invoke that dude.. insert a -1
                current.add(values, what.getObjectName(), now);
            }
        }
        return current;
    }

    public void newWatch(String label, String name, DefaultMultiValueCategoryDataset graph, MBeanServerConnection connection, MBeanFeatureInfo featureInfo, ObjectName objectName) {
        Watchable dude = new Watchable(label, name, objectName, featureInfo, connection);
        if (backend.get(label) == null) {
            backend.put(label, dude);
        } else {
            dude = backend.get(label);
        }
        //and add to the existing graph or register a new graph
        if (frontend.get(graph) != null) {
            List<Watchable> list = frontend.get(graph);
            list.add(dude);
        } else {
            List<Watchable> list = new ArrayList<Watchable>();
            list.add(dude);
            frontend.put(graph, list);
        }
    //ok, dude is now registered.
    }

    /**
     * hidden internal class to handle grouping related things
     */
    class Watchable {

		private String label;
        private String name;
        private ObjectName objectName;
        private MBeanFeatureInfo featureInfo;
        private MBeanServerConnection connection;

        public Watchable(String label, String name, ObjectName objectName, MBeanFeatureInfo operationInfo, MBeanServerConnection connection) {
            this.label = label;
			this.name = name;
            this.objectName = objectName;
            this.featureInfo = operationInfo;
            this.connection = connection;
        }

        public MBeanServerConnection getConnection() {
            return connection;
        }

        public void setConnection(MBeanServerConnection connection) {
            this.connection = connection;
        }

        public ObjectName getObjectName() {
            return objectName;
        }

        public void setObjectName(ObjectName objectName) {
            this.objectName = objectName;
        }

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

		

		public MBeanFeatureInfo getFeatureInfo() {
			return featureInfo;
		}

		public void setFeatureInfo(MBeanFeatureInfo featureInfo) {
			this.featureInfo = featureInfo;
		}


    }
}

