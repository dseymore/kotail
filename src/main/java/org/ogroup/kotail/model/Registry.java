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
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
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

    public static String getPathName(Instance instance, ObjectName objectName, MBeanOperationInfo operationInfo) {
        return instance.getName() + ":" + objectName.toString() + "/" + operationInfo.getName();
    }

    /**
     * We modify the dataset passed in
     * @param current
     * @return for convinience we also return it
     */
    public DefaultMultiValueCategoryDataset refresh(DefaultMultiValueCategoryDataset current) {
        List<Watchable> todo = frontend.get(current);
        for (Watchable what : todo) {
            try {
                Object result = what.getConnection().invoke(what.getObjectName(), what.getOperationInfo().getName(), new Object[]{}, new String[]{});
                List<Object> values = new ArrayList<Object>();
                values.add(result);
                //new data!
                current.add(values, what.getName(), new Date());
            } catch (Exception e) {
                LOG.error("Inserting -1 for unavaiable value:" + what.getObjectName(), e);
                List<Object> values = new ArrayList<Object>();
                values.add(new Long(-1));
                //unable to invoke that dude.. insert a -1
                current.add(values, what.getObjectName(), new Date());
            }
        }
        return current;
    }

    public void newWatch(String nameMe, DefaultMultiValueCategoryDataset graph, MBeanServerConnection connection, MBeanOperationInfo operation, ObjectName objectName) {
        Watchable dude = new Watchable(nameMe, objectName, operation, connection);
        if (backend.get(nameMe) == null) {
            backend.put(nameMe, dude);
        } else {
            dude = backend.get(nameMe);
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

        private String name;
        private ObjectName objectName;
        private MBeanOperationInfo operationInfo;
        private MBeanServerConnection connection;

        public Watchable(String name, ObjectName objectName, MBeanOperationInfo operationInfo, MBeanServerConnection connection) {
            this.name = name;
            this.objectName = objectName;
            this.operationInfo = operationInfo;
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

        public MBeanOperationInfo getOperationInfo() {
            return operationInfo;
        }

        public void setOperationInfo(MBeanOperationInfo operationInfo) {
            this.operationInfo = operationInfo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

