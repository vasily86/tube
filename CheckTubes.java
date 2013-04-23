/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checktubes;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Vasily Glazunov
 */
public class CheckTubes {
        public static DefaultListModel<InfoProbirka> listModel = new DefaultListModel<InfoProbirka>();
        public static FormJFrame form;
        

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        form = new FormJFrame();
        form.setTitle("Проверка прощёлкнутых пробирок");
        
        form.jLabel1.setText("Было отправлено: ");
        form.jLabel2.setText("Пробито по базе: ");
        
        
        ListCellRenderer renderer = new MyRenderer();
        
        form.jList1.setModel(listModel);
        
        form.jList1.setCellRenderer(renderer);
        form.jDateChooser1.setDate( new Date() );
        
        form.jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //пишем в базу
                form.jProgressBar1.setVisible(true);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            repaintProbirka();
                        } catch (Exception e) {

                        }
                    }
                }).start();                                    
            }
        });
        
        form.jList1.addMouseListener( new java.awt.event.MouseAdapter() {            
            public void mousePressed(MouseEvent event) {
                form.jLabel1.setText("Было отправлено: " );
                form.jLabel2.setText("Пробито по базе: ");
                //if (event.getClickCount() == 1) {
                    try {
                        InfoProbirka pr = (InfoProbirka) listModel.get( form.jList1.getSelectedIndex() );
                        form.jLabel1.setText("Было отправлено: " + pr.count);
                        Probirki  prob = new Probirki();
                        prob.init();
                        form.jLabel2.setText("Пробито по базе: " + prob.countProbirka(pr));
                    } catch (Exception e ) {
                        System.out.println(e.getMessage());
                    }
                //}
            }  
        });  
        
        form.pack();
        form.setLocationRelativeTo(null);
        form.setVisible(true);
           
                                
    }
    
    public static void repaintProbirka() throws Exception{
        try {
            Probirki  prob = new Probirki();
            prob.init();

            SimpleDateFormat formatter =
                    new SimpleDateFormat( form.jDateChooser1.getDateFormatString() );

            String dt =  formatter.format(form.jDateChooser1.getDate() );
            ArrayList<InfoProbirka> probirka =  prob.getGoingProbirki(dt);
            //ArrayList<InfoProbirka> probirka2 =  prob.getLaboratoryProbirki();
            listModel.clear();

            for (InfoProbirka infoProbirka : probirka) {

                if ( !prob.checkProbirka(infoProbirka) )  {                
                    //System.out.println( infoProbirka.examid + "  " + infoProbirka.id_tube + " " + infoProbirka.count );    
                    listModel.addElement(infoProbirka);
                }


            }
        } catch (Exception e) {
            throw e;
        } finally {       
            form.jProgressBar1.setVisible(false);
        }

    }
}
