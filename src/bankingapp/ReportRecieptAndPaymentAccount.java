/*
 * ReportGeneralLedger.java
 *
 * Created on July 11, 2010, 12:50 AM
 */
package bankingapp;

import bankingapp.utils.ExcelExporter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.swing.JFileChooser;
import javax.swing.JTable.PrintMode;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.application.Action;

/**
 *
 * @author  s
 */
public class ReportRecieptAndPaymentAccount extends javax.swing.JInternalFrame {

    private EntityManager BankingPUEntityManager;

    /** Creates new form ReportGeneralLedger */
    public ReportRecieptAndPaymentAccount() {
        BankingPUEntityManager = javax.persistence.Persistence.createEntityManagerFactory("BankingAppPU").createEntityManager();
        initComponents();
        populateTable(null, null);
    }

    private void populateTable(String from, String to) {
        //String dd = "2010-07-16";
        String whereClause="";
        if(from!=null&&to!=null){
             whereClause = "where transactions.timestamp between '"+from+"' and '"+to+"'";
        }
        String[] columns = {"Reciepts", "Amount", "Payments", "Amount"};
        String sql = "SELECT concat(ledger_heads.ledger_head,'-',ifnull(name,'')),sum(deposit),concat(ledger_heads.ledger_head,'-',ifnull(name,'')),sum(withdrawal) from transactions left join (select transactions_id,name from mds_payments  inner join mds_details on mds_details.id = mds_payments.mds_details_id inner join mds on mds.id = mds_details.mds_id )as t1 on transactions.id = t1.transactions_id inner join ledger_heads on ledger_heads.id = transactions.lledger_head_id "+whereClause+" group by lledger_head_id,name order by ledger_heads.ledger_head";
        System.out.println(sql);
        Query q = BankingPUEntityManager.createNativeQuery(sql);
        List l = q.getResultList();
        String[][] data = new String[l.size()][4];
        int i = 0;
        for (Iterator it = l.iterator(); it.hasNext();) {
            Object object = it.next();
            Vector v = (Vector) object;
            data[i][0] = v.get(0) == null ? " " : v.get(0).toString();
            data[i][1] = v.get(1) == null ? " " : v.get(1).toString();
            data[i][2] = v.get(2) == null ? " " : v.get(2).toString();
            data[i][3] = v.get(3) == null ? " " : v.get(3).toString();

            i++;
        }
        DefaultTableModel dtm = new DefaultTableModel(data, columns);
        jTable1.setModel(dtm);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveFileChooser = new javax.swing.JFileChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        fromSpinner = new javax.swing.JSpinner();
        toSpinner = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        saveFileChooser.setName("saveFileChooser"); // NOI18N

        setClosable(true);
        setMaximizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bankingapp.BankingApp.class).getContext().getResourceMap(ReportRecieptAndPaymentAccount.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        fromSpinner.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(946742400000L), null, null, java.util.Calendar.MONTH));
        fromSpinner.setEditor(new javax.swing.JSpinner.DateEditor(fromSpinner, "dd-MM-yyyy"));
        fromSpinner.setName("fromSpinner"); // NOI18N

        toSpinner.setModel(new javax.swing.SpinnerDateModel());
        toSpinner.setEditor(new javax.swing.JSpinner.DateEditor(toSpinner, "dd-MM-yyyy"));
        toSpinner.setName("toSpinner"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bankingapp.BankingApp.class).getContext().getActionMap(ReportRecieptAndPaymentAccount.class, this);
        jButton2.setAction(actionMap.get("populateDated")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jButton3.setAction(actionMap.get("exportToExcel")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 655, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fromSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(toSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3))
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fromSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            /*
            jTable1.print();                                        
             */
            org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bankingapp.BankingApp.class).getContext().getResourceMap(ReportCashBook.class);
            //jTable1.print(PrintMode.FIT_WIDTH,new MessageFormat(resourceMap.getString("print.first")+"-{0}-"+resourceMap.getString("print.second")+resourceMap.getString("print.third")),null);

            PrinterJob job = PrinterJob.getPrinterJob();
            MessageFormat[] header = new MessageFormat[6];
            header[0] = new MessageFormat("");
            header[1] = new MessageFormat("                      " + resourceMap.getString("print.first"));
            header[2] = new MessageFormat("                      " + resourceMap.getString("print.second"));
            header[3] = new MessageFormat("                      " + resourceMap.getString("print.third"));
            header[4] = new MessageFormat("");
            header[5] = new MessageFormat("                                                  Reciept and Payment Account");
            MessageFormat[] footer = new MessageFormat[1];
            footer[0] = new MessageFormat("--{0}--");
            job.setPrintable(new MyTablePrintable(jTable1, PrintMode.FIT_WIDTH, header, footer));
            job.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ReportCashBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    @Action
    public void populateDated() {
        Date dateFrom = (Date) fromSpinner.getValue();
        Date dateTo = (Date) toSpinner.getValue();
        java.util.Date s = dateFrom;
        String dateStringFrom = (s.getYear() + 1900) + "-" + ((s.getMonth() < 10) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "-" + s.getDate();
        s = dateTo;
        String dateStringTo = (s.getYear() + 1900) + "-" + ((s.getMonth() < 10) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "-" + s.getDate();
        populateTable(dateStringFrom,dateStringTo);

    }

    @Action
    public void exportToExcel() {
         try {
            if (saveFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = saveFileChooser.getSelectedFile();
                ExcelExporter ee = new ExcelExporter();
                ee.exportTable(jTable1, f);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportCashBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner fromSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JFileChooser saveFileChooser;
    private javax.swing.JSpinner toSpinner;
    // End of variables declaration//GEN-END:variables
}