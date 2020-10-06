import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;

public class box extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    //菜单栏
    private JMenu menu_File,menu_Search,menu_Edit,menu_Help;
    //菜单栏内的菜单
    private JMenuItem item_new,item_open,item_save,item_exit,item_print;
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
     * 对menubar进行初始化，这里有一些小的快捷方式的设置
     * 大家可以考虑对所有的组件都设置一下
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
        menu_File.add(item_new);
        menu_File.add(item_open);
        menu_File.add(item_save);
        menu_File.add(item_print);
        menu_File.add(item_exit);

        //File 菜单

        menu_Search = new JMenu("Search");
        menu_Search.setMnemonic('s');
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
        menu_Search.addActionListener(this);
        item_new.addActionListener(this);
        item_open.addActionListener(this);
        item_save.addActionListener(this);
        item_exit.addActionListener(this);
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
        }else if (e.getSource() == menu_Search) {
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

    /**
     * 点击新建按item时 打开JFileChooser对话框
     * 并且对文件读取进行处理
     */

    public static void main(String[] args) {
        box b = new box();
    }
}
