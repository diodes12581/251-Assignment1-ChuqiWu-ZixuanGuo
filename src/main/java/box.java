import com.lowagie.text.DocumentException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;

public class box extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    //菜单栏
    private JMenu menu_File,menu_Search,menu_Edit,menu_Help;
    //菜单栏内的菜单
    private JMenuItem item_new,item_open,item_save,item_exit,item_print,item_export, item_search;
    //对于file菜单的子项
    private JMenuItem item_undo,item_cut,item_copy,item_stick;
    //对于edit菜单的子项
    private JMenuItem item_about;
    //对于help菜单的子项
    private static JTextArea edit_text_area;
    //编辑区域
    private JScrollPane scroll_bar;
    //可滚动的pane 里面添加edit_text_area就可以变为一个可以滚动的文本框，JScrollPane是一个pane，同时可以设置方向

    public box() {
        initMenuBar();
        initEditArea();
        initListener();
        this.setJMenuBar(menuBar);
        this.setSize(800,600);
        DateFormat df = DateFormat.getDateTimeInstance();
        String current = df.format(System.currentTimeMillis());
        this.setTitle("Editor             " + current);
        this.add(scroll_bar);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * 两种形式：
     * menu_File.setMnemonic('f'); 对menu
     * item_word_format.setAccelerator(KeyStroke.getKeyStroke('F',java.awt.Event.CTRL_MASK,false)); 对item
     */
    public void initMenuBar() {
        menuBar = new JMenuBar();
        menu_File = new JMenu("File");
        menu_File.setMnemonic('f');//f+alt打开
        item_new = new JMenuItem("New");
        item_open = new JMenuItem("Open");
        item_save = new JMenuItem("Save");
        item_exit = new JMenuItem("Exit");
        item_print = new JMenuItem("Print");
        item_export = new JMenuItem("Export2PDF");
        menu_File.add(item_new);
        menu_File.add(item_open);
        menu_File.add(item_save);
        menu_File.add(item_print);
        menu_File.add(item_export);
        menu_File.add(item_exit);

        //File 菜单

        menu_Search = new JMenu("Search");
        menu_Search.setMnemonic('s');
        item_search = new JMenuItem("Search");
        menu_Search.add(item_search);
        //Search 菜单

        menu_Edit = new JMenu("Edit");
        menu_Edit.setMnemonic('e');
        item_undo = new JMenuItem("Undo");
        item_cut = new JMenuItem("Cut");
        item_copy = new JMenuItem("Copy");
        item_stick = new JMenuItem("Paste");
        menu_Edit.add(item_undo);
        menu_Edit.add(item_cut);
        menu_Edit.add(item_copy);
        menu_Edit.add(item_stick);
        //Edit 菜单

        menu_Help = new JMenu("Help");
        menu_Help.setMnemonic('h');
        item_about = new JMenuItem("About");
        menu_Help.add(item_about);
        //Help 菜单

        menuBar.add(menu_File);
        menuBar.add(menu_Search);
        menuBar.add(menu_Edit);
        menuBar.add(menu_Help);
    }
    /**
     * 初始化编辑区域
     * 用scrollpane装textarea
     * 同时对pane设置方向
     */
    public void initEditArea() {
        edit_text_area = new JTextArea();
        scroll_bar = new JScrollPane(edit_text_area);
        scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }


    public void initListener() {
        item_search.addActionListener(this);
        item_new.addActionListener(this);
        item_open.addActionListener(this);
        item_save.addActionListener(this);
        item_exit.addActionListener(this);
        item_export.addActionListener(this);
        item_print.addActionListener(this);
        item_undo.addActionListener(this);
        item_cut.addActionListener(this);
        item_copy.addActionListener(this);
        item_stick.addActionListener(this);
        item_about.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == item_new) {
            new box();
        }else if (e.getSource() == item_exit) {
            this.dispose();
        }else if (e.getSource() == item_open) {
            fileFunction.open(edit_text_area, scroll_bar);
        }else if (e.getSource() == item_save) {
            fileFunction.save(edit_text_area, scroll_bar);
        }else if (e.getSource() == item_print) {
            fileFunction.print();
        }else if (e.getSource() == item_export) {
            try {
                fileFunction.export(edit_text_area, scroll_bar);
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == item_search) {
            search();
        }else if (e.getSource() == item_undo) {
            editFunction.undo(edit_text_area);
        }else if (e.getSource() == item_cut) {
            editFunction.cut(edit_text_area);
        }else if (e.getSource() == item_copy) {
            editFunction.copy(edit_text_area);
        }else if (e.getSource() == item_stick) {
            editFunction.paste(edit_text_area);
        }else if (e.getSource() == item_about) {
            new helpFunction.about_Window();
        }
    }


    public void search()
    {   final JDialog findDialog=new JDialog(this,"查找",false);//false时允许其他窗口同时处于激活状态(即无模式)
        Container con=findDialog.getContentPane();//返回此对话框的contentPane对象
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel findContentLabel=new JLabel("查找内容(N)：");
        final JTextField findText=new JTextField(15);
        JButton findNextButton=new JButton("查找下一个(F)：");
        final JCheckBox matchCheckBox=new JCheckBox("区分大小写(C)");
        ButtonGroup bGroup=new ButtonGroup();
        final JRadioButton upButton=new JRadioButton("向上(U)");
        final JRadioButton downButton=new JRadioButton("向下(U)");
        downButton.setSelected(true);
        bGroup.add(upButton);
        bGroup.add(downButton);
        /*ButtonGroup此类用于为一组按钮创建一个多斥（multiple-exclusion）作用域。
        使用相同的 ButtonGroup 对象创建一组按钮意味着“开启”其中一个按钮时，将关闭组中的其他所有按钮。*/
        /*JRadioButton此类实现一个单选按钮，此按钮项可被选择或取消选择，并可为用户显示其状态。
        与 ButtonGroup 对象配合使用可创建一组按钮，一次只能选择其中的一个按钮。
        （创建一个 ButtonGroup 对象并用其 add 方法将 JRadioButton 对象包含在此组中。）*/
        JButton cancel=new JButton("取消");
        //取消按钮事件处理
        cancel.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
        {   findDialog.dispose();
        }
        });
        //"查找下一个"按钮监听
        findNextButton.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
        {   //"区分大小写(C)"的JCheckBox是否被选中
            int k=0,m=0;
            final String str1,str2,str3,str4,strA,strB;
            str1=edit_text_area.getText();
            str2=findText.getText();
            str3=str1.toUpperCase();
            str4=str2.toUpperCase();
            if(matchCheckBox.isSelected())//区分大小写
            {   strA=str1;
                strB=str2;
            }
            else//不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找
            {   strA=str3;
                strB=str4;
            }
            if(upButton.isSelected())
            {   //k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
                if(edit_text_area.getSelectedText()==null)
                    k=strA.lastIndexOf(strB,edit_text_area.getCaretPosition()-1);
                else
                    k=strA.lastIndexOf(strB, edit_text_area.getCaretPosition()-findText.getText().length()-1);
                if(k>-1)
                {   //String strData=strA.subString(k,strB.getText().length()+1);
                    edit_text_area.setCaretPosition(k);
                    edit_text_area.select(k,k+strB.length());
                }
                else
                {   JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else if(downButton.isSelected())
            {   if(edit_text_area.getSelectedText()==null)
                k=strA.indexOf(strB,edit_text_area.getCaretPosition()+1);
            else
                k=strA.indexOf(strB, edit_text_area.getCaretPosition()-findText.getText().length()+1);
                if(k>-1)
                {   //String strData=strA.subString(k,strB.getText().length()+1);
                    edit_text_area.setCaretPosition(k);
                    edit_text_area.select(k,k+strB.length());
                }
                else
                {   JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        });//"查找下一个"按钮监听结束
        //创建"查找"对话框的界面
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel directionPanel=new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
        //设置directionPanel组件的边框;
        //BorderFactory.createTitledBorder(String title)创建一个新标题边框，使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。
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
        findDialog.setResizable(false);//不可调整大小
        findDialog.setLocation(230,280);
        findDialog.setVisible(true);
    }//查找方法结束


    public static void main(String[] args) {
        box b = new box();
    }
}
