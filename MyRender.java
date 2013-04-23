/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checktubes;

    import javax.swing.*;
    import java.awt.*;
     
    class MyRenderer extends JLabel implements ListCellRenderer
    {
        public MyRenderer() {   // �����������
             setOpaque(true);   // �� ����, ����� ��� ����, ���� �� ������� :)
        }
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            
            if (isSelected) // ���� ������� ������� - ���������� ��� ���� �����, ���� ��� - ������
            {
                setBackground(list.getSelectionBackground());
                //setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                //setBackground(list.getBackground());
                //setForeground(list.getForeground());                          
            }
            
            /*
            if (((InfoProbirka)value).getId_probirki() == 0  ) {
                //setForeground(list.getForeground()); 
                setForeground(Color.red);  
            } else {
                setForeground(new Color(0, 153, 0) );                       
            }
            * */
            
            //setFont();
            //getFont().;
            Font displayFont = new Font("Tahoma", Font.PLAIN, 14);
            setFont(displayFont);
            setText(((InfoProbirka)value).examid + "  " + ((InfoProbirka)value).name );   // ��� �� ������ ����� ��������!!
     
            return this;
        }
    }
