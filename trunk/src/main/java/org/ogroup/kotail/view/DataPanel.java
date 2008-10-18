/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ogroup.kotail.view;

import javax.swing.JPanel;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;

/**
 *
 * @author denki
 */
public class DataPanel extends JPanel {

    private DefaultMultiValueCategoryDataset dataset;

    public DefaultMultiValueCategoryDataset getDataset() {
        return dataset;
    }

    public void setDataset(DefaultMultiValueCategoryDataset dataset) {
        this.dataset = dataset;
    }
}
