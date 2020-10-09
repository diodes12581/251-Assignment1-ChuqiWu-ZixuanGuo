import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class search_function {
    
    public static void search(box editor,JTextPane edit_text_pane)
    {   final JDialog findDialog=new JDialog(editor,"Search",false);//False allows other Windows to be active at the same time (i.e., no mode)
        Container con=findDialog.getContentPane();//Returns the contentPane object of this dialog box
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel findContentLabel=new JLabel("find what(N)：");
        final JTextField findText=new JTextField(15);
        JButton findNextButton=new JButton("find next(F)：");
        final JCheckBox matchCheckBox=new JCheckBox("Case sensitivity(C)");
        ButtonGroup bGroup=new ButtonGroup();
        final JRadioButton upButton=new JRadioButton("up(U)");
        final JRadioButton downButton=new JRadioButton("down(U)");
        downButton.setSelected(true);
        bGroup.add(upButton);
        bGroup.add(downButton);

        JButton cancel=new JButton("cancel");
        //Cancel button event handling
        cancel.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
        {   findDialog.dispose();
        }
        });
        //The "Find next" button listens
        findNextButton.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
        {   //Whether the "case sensitive (C)" JCheckBox is checked
            int k=0;
            final String str1,str2,str3,str4,strA,strB;
            str1= edit_text_pane.getText().replaceAll("\r", "");
            str2=findText.getText();
            str3=str1.toUpperCase();
            str4=str2.toUpperCase();
            if(matchCheckBox.isSelected())//Case sensitivity
            {   strA=str1;
                strB=str2;
            }
            else//Case insensitive, at this point, the selection is all uppercase (or lowercase) for easy lookup
            {   strA=str3;
                strB=str4;
            }
            if(upButton.isSelected())
            {   //k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
                if(edit_text_pane.getSelectedText()==null){
                    k=strA.lastIndexOf(strB, edit_text_pane.getCaretPosition()-1);
                    }
                else
                    k=strA.lastIndexOf(strB, edit_text_pane.getCaretPosition()-findText.getText().length()-1);
                if(k>-1)
                {   //String strData=strA.subString(k,strB.getText().length()+1);
                    edit_text_pane.setCaretPosition(k);
                    edit_text_pane.select(k,k+strB.length());
                }
                else
                {   JOptionPane.showMessageDialog(null,"What you are looking for cannot be found！","find",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else if(downButton.isSelected())
            {   if(edit_text_pane.getSelectedText()==null){
                k=strA.indexOf(strB, 1);
            }
                else
                k=strA.indexOf(strB, edit_text_pane.getCaretPosition()-findText.getText().length()+1);
                System.out.println(k);
                if(k>-1)
                {   //String strData=strA.subString(k,strB.getText().length()+1);
                    edit_text_pane.setCaretPosition(k);
                    edit_text_pane.select(k,k+strB.length());
                }
                else
                {   JOptionPane.showMessageDialog(null,"What you are looking for cannot be found！","find",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        });
        //Interface to create the Find dialog box
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel directionPanel=new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
        //Sets the border of the directionPanel component;
        //BorderFactory.createTitledBorder(String title)
        // Create a new title border with the default border (embossed),
        // default text position (on the top line), default adjustment (Leading),
        // and the default font and text color determined by the current look and feel,
        // and specify the title text.
        directionPanel.add(upButton);
        directionPanel.add(downButton);
        panel1.setLayout(new GridLayout(2,1));
        panel1.add(findNextButton);
        panel1.add(cancel);
        panel2.add(findContentLabel);
        panel2.add(findText);
        panel2.add(panel1);
        panel3.add(matchCheckBox);
        panel3.add(directionPanel);
        con.add(panel2);
        con.add(panel3);
        findDialog.setSize(410,180);
        findDialog.setResizable(false);//Unresizable
        findDialog.setLocation(230,280);
        findDialog.setVisible(true);
    }
}
