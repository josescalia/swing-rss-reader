/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josescalia.swingrss.gui.form;

import org.apache.log4j.Logger;
import org.jdesktop.observablecollections.ObservableCollections;
import org.josescalia.rss.model.ApplicationConfig;
import org.josescalia.swingrss.model.LandFWrapper;
import org.josescalia.swingrss.service.impl.ApplicationConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author josescalia
 */
@Component
public class LandFForm extends javax.swing.JPanel {

    static Logger logger = Logger.getLogger(LandFForm.class.getName());
    
    
    @Autowired
    private ApplicationConfigServiceImpl configService;
    private ApplicationConfig lfTemp = null;
    private List<LandFWrapper> lookAndFeelList = new ArrayList<LandFWrapper>();
    private LandFWrapper selectedLF;

    public void showForm(){

        initComponents();
        setLookAndFeelList(ObservableCollections.observableList(getLandFList()));
        Map<String,Object> mapParam = new HashMap<String, Object>();
        mapParam.put("configName", "DEFAULT_LF");
        try {
            lfTemp = configService.findExact(mapParam).get(0);
            System.out.println(lfTemp.toString());
            //the textField cannot be bound when 1st initialize, so must set manually
            txtSelectedLF.setText(lfTemp.getConfigValue().split("#")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Creates new form LandFForm
     */
    public LandFForm() {
        selectedLF = new LandFWrapper();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtSelectedLF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSaveLF = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${lookAndFeelList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lfName}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lfValue}"));
        columnBinding.setColumnName("Class");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedLF}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        txtSelectedLF.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedLF.lfName}"), txtSelectedLF, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel1.setText("Look and Feel");

        btnSaveLF.setText("Save");
        btnSaveLF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSelectedLF, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveLF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSelectedLF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveLF))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveLFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLFActionPerformed
        ApplicationConfig config = new ApplicationConfig();
        config.setId(lfTemp.getId());
        config.setConfigName(lfTemp.getConfigName());
        config.setConfigValue(selectedLF.getLfName() + "#" + selectedLF.getLfValue());
        try{
            configService.save(config);
            JOptionPane.showMessageDialog(this,"Save Succeed, you must restart application ");
        }catch (Exception e){
            logger.error(e);
            JOptionPane.showMessageDialog(this,"Error :" + e.getMessage());
        }
    }//GEN-LAST:event_btnSaveLFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSaveLF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtSelectedLF;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables


    public List<LandFWrapper> getLookAndFeelList() {
        return lookAndFeelList;
    }

    public void setLookAndFeelList(List<LandFWrapper> lookAndFeelList) {
        List<LandFWrapper> old = this.lookAndFeelList;
        this.lookAndFeelList = lookAndFeelList;
        firePropertyChange("lookAndFeelList", old, lookAndFeelList);
    }

    private List<LandFWrapper> getLandFList(){
        List<LandFWrapper> rList = new ArrayList<LandFWrapper>();
        for(UIManager.LookAndFeelInfo lfInfo : Arrays.asList(UIManager.getInstalledLookAndFeels())){
            rList.add(new LandFWrapper(lfInfo.getName(), lfInfo.getClassName()));
        }
        return rList;
        
    }

    public LandFWrapper getSelectedLF() {
        return selectedLF;
    }

    public void setSelectedLF(LandFWrapper selectedLF) {
        LandFWrapper old = this.selectedLF;
        this.selectedLF = selectedLF;
        firePropertyChange("selectedLF", old, selectedLF);
        logger.info(selectedLF);
    }
}
