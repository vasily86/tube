/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checktubes;

    import javax.swing.*;
    import java.awt.*;
     
    class MyRenderer extends JLabel implements ListCellRenderer
    {
        public MyRenderer() {   // Конструктор
             setOpaque(true);   // не знаю, зачем это надо, взял из примера :)
        }
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            
            if (isSelected) // если элемент выделен - выставляем ему одни цвета, если нет - другие
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
            setText(((InfoProbirka)value).examid + "  " + ((InfoProbirka)value).name );   // ТУТ МЫ ЗАДАЕМ ТЕКСТ ЭЛЕМЕНТА!!
     
            return this;
        }
    }
