package view.components;

import javax.swing.*;
//import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DialogComponent extends JDialog {
    private Object tag;
    private Map<String, JComponent> componentMap = new HashMap<>();
    
    public DialogComponent(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void setTag(Object tag) {
        this.tag = tag;
    }
    
    public Object getTag() {
        return tag;
    }
    
    public void registerComponent(String key, JComponent component) {
        componentMap.put(key, component);
    }
    
    public JComponent getComponent(String key) {
        return componentMap.get(key);
    }
    
    public void setFieldValue(String key, String value) {
        JComponent component = componentMap.get(key);
        if (component instanceof JTextField) {
            ((JTextField) component).setText(value);
        } else if (component instanceof JTextArea) {
            ((JTextArea) component).setText(value);
        }
    }
    
    public String getFieldValue(String key) {
        JComponent component = componentMap.get(key);
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        } else if (component instanceof JTextArea) {
            return ((JTextArea) component).getText();
        }
        return null;
    }
    
    public void setComboBoxValue(String key, Object value) {
        JComponent component = componentMap.get(key);
        if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setSelectedItem(value);
        }
    }
    
    public Object getComboBoxValue(String key) {
        JComponent component = componentMap.get(key);
        if (component instanceof JComboBox) {
            return ((JComboBox<?>) component).getSelectedItem();
        }
        return null;
    }
    
    public void setCheckBoxValue(String key, boolean value) {
        JComponent component = componentMap.get(key);
        if (component instanceof JCheckBox) {
            ((JCheckBox) component).setSelected(value);
        }
    }
    
    public boolean getCheckBoxValue(String key) {
        JComponent component = componentMap.get(key);
        if (component instanceof JCheckBox) {
            return ((JCheckBox) component).isSelected();
        }
        return false;
    }
    
    public void clearFields() {
        for (JComponent component : componentMap.values()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JTextArea) {
                ((JTextArea) component).setText("");
            } else if (component instanceof JComboBox) {
                if (((JComboBox<?>) component).getItemCount() > 0) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            } else if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(false);
            }
        }
    }
}