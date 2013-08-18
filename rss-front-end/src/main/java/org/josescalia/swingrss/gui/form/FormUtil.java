/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josescalia.swingrss.gui.form;

import java.awt.Component;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Josescalia
 */
/**
 * A Class contains a set of methods to handle Swing Framework behaviour
 * */
public class FormUtil {
    
    public static final int ACTION_ADD = 1;
    public static final int ACTION_EDIT = 2;
    public static final int ACTION_CANCEL = 3;
    
    public static final String[] btnTitleAddArray = new String[]{"Add","Tambah","Rekam","Rekam Baru"};
    public static final String[] btnTitleEditArray = new String[]{"Edit","Ubah"};
    public static final String[] btnTitleCancelArray = new String[]{"Cancel","Batal"};
    public static final String[] btnTitleSaveArray = new String[]{"Save","Simpan"};
    public static final String[] btnTitleExitArray = new String[]{"Exit","Keluar"};

    /**
     * static function to enable nor clear the editable component on a panel
     * @param isEnabled boolean enable or disable the editable component
     * @param isClear boolean clear or uncler the editable component
     * @param panel parent panel of the component to clear and enable action
     * */
    public static void isEnableAndClear(boolean isEnabled, boolean isClear, JPanel panel) {
        Component[] comps = panel.getComponents();
        for (Component comp : comps) {
            //enable component
            if (comp instanceof JLabel) {
                comp.setEnabled(true);
            } else {
                comp.setEnabled(isEnabled);
            }
            //clear component
            if (isClear) {
                if (comp instanceof JTextField) {
                    ((JTextField) comp).setText("");
                } else if (comp instanceof JComboBox) {
                    ((JComboBox) comp).setSelectedIndex(0);
                } else if (comp instanceof JTextArea) {
                    ((JTextArea) comp).setText("");
                } else {
                    //un defined
                }
            }
        }
        
    }

    /**
     * static function to setup set of button on Swing Panel User Interface
     * @param action referred to static variable on this class
     * @param btnPanel a parent panel contains button to set*/
    public static void btnSetup(int action, JPanel btnPanel) {
        Component[] comps = btnPanel.getComponents();
        if (action == ACTION_ADD || action == ACTION_EDIT) {
            for (Component comp : comps) {
                if (comp instanceof JButton) {
                    JButton btnTemp = (JButton) comp;
                    if (Arrays.asList(btnTitleAddArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(false);
                    }
                    if (Arrays.asList(btnTitleEditArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(false);
                    }
                    if (Arrays.asList(btnTitleCancelArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(true);
                    }
                    if (Arrays.asList(btnTitleSaveArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(true);
                    }
                    if (Arrays.asList(btnTitleExitArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(false);
                    }
                }
            }
        }
        if (action == ACTION_CANCEL) {
            for (Component comp : comps) {
                if (comp instanceof JButton) {
                    JButton btnTemp = (JButton) comp;
                    if (Arrays.asList(btnTitleAddArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(true);
                    }
                    if (Arrays.asList(btnTitleEditArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(true);
                    }
                    if (Arrays.asList(btnTitleCancelArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(false);
                    }
                    if (Arrays.asList(btnTitleSaveArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(false);
                    }
                    if (Arrays.asList(btnTitleExitArray).contains(btnTemp.getText())) {
                        btnTemp.setEnabled(true);
                    }
                }
            }
        }
    }

    /**
     * static function to setup the button, use this as the 1st procedure to set the button<br>
     * the state of start up button are:
     * <ul type='circle'>
     *     <li><b>Add</b> button is enable</li>
     *     <li><b>Edit</b> button is enable</li>
     *     <li><b>Cancel</b> button is disabled</li>
     *     <li><b>Save</b> button is disabled</li>
     *     <li><b>Exit</b> button is enabled</li>
     * </ul>
     * @param btnPanel panel to setup the button*/
    public static void setupStartUpBtn(JPanel btnPanel) {
        btnSetup(ACTION_CANCEL, btnPanel);
        
    }
}
