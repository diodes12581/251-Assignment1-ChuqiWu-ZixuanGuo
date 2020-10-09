import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.DocumentException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class box extends JFrame implements ActionListener {

    String path = ReadJson.class.getClassLoader().getResource("package.json").getPath();
    String json = ReadJson.readJsonFile(path);
    JSONObject jobj = JSON.parseObject(json);

    private JMenuBar menuBar;
    //Right-click to bring up a menu item
    JPopupMenu popupMenu;
    JMenuItem popupMenu_Cut,popupMenu_Copy,popupMenu_Paste,popupMenu_SelectAll;
    //The menu bar
    private JMenu menu_File,menu_Search,menu_Edit,menu_Help;
    //The menu in the menu bar
    private JMenuItem item_new,item_open,item_save,item_exit,item_print,item_export, item_search;
    //For subitems of the File menu
    private JMenuItem item_selectAll,item_cut,item_copy,item_stick;
    //For children of the Edit menu
    private JMenuItem item_about;
    //For the children of the Help menu
    private static JTextPane edit_text_pane;
    //Editing area
    private JScrollPane scroll_bar;
    //Add edit_text_area to a scrollable Pane to make it a scrollable text box.
    // A JScrollPane is an pane and can set the direction

    public box() {
        initMenuBar();
        initEditArea();
        initListener();
        this.setJMenuBar(menuBar);
        this.setSize((int)jobj.get("width"),(int)jobj.get("height"));
        DateFormat df = DateFormat.getDateTimeInstance();
        String current = df.format(System.currentTimeMillis());
        this.setTitle(jobj.get("title") + current);
        this.add(scroll_bar);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Two shortcuts：
     * menu_File.setMnemonic('f'); for menu
     * item_word_format.setAccelerator(KeyStroke.getKeyStroke('F',java.awt.Event.CTRL_MASK,false)); for item
     */
    public void initMenuBar() {

        //Create a right-click pop-up menu
        popupMenu=new JPopupMenu();
        popupMenu_Cut=new JMenuItem("Cut(T)");
        popupMenu_Copy=new JMenuItem("Copy(C)");
        popupMenu_Paste=new JMenuItem("Paste(P)");
        popupMenu_SelectAll=new JMenuItem("SelectAll(A)");


        //Adds a menu item and separator to a right-click menu
        popupMenu.add(popupMenu_Cut);
        popupMenu.add(popupMenu_Copy);
        popupMenu.add(popupMenu_Paste);
        popupMenu.add(popupMenu_SelectAll);

        menuBar = new JMenuBar();
        menu_File = new JMenu("File");
        menu_File.setMnemonic('f');//f+alt to open
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

        //The File menu

        menu_Search = new JMenu("Search");
        menu_Search.setMnemonic('s');
        item_search = new JMenuItem("Search");
        menu_Search.add(item_search);
        //The Search menu

        menu_Edit = new JMenu("Edit");
        menu_Edit.setMnemonic('e');
        item_cut = new JMenuItem("Cut");
        item_copy = new JMenuItem("Copy");
        item_stick = new JMenuItem("Paste");
        item_selectAll = new JMenuItem("SelectAll");
        menu_Edit.add(item_cut);
        menu_Edit.add(item_copy);
        menu_Edit.add(item_stick);
        menu_Edit.add(item_selectAll);
        //The Edit menu

        menu_Help = new JMenu("Help");
        menu_Help.setMnemonic('h');
        item_about = new JMenuItem("About");
        menu_Help.add(item_about);
        //The Help menu

        menuBar.add(menu_File);
        menuBar.add(menu_Search);
        menuBar.add(menu_Edit);
        menuBar.add(menu_Help);
    }
    /**
     * Initializes the edit area
     * Install the TextArea with the ScrollPane
     * Also set the orientation on the Pane
     */
    public void initEditArea() {
        edit_text_pane = new JTextPane();
        edit_text_pane.getDocument().addDocumentListener(new SyntaxHighlighter(edit_text_pane));
        scroll_bar = new JScrollPane(edit_text_pane);
        scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }


    public void initListener() {
        popupMenu_Cut.addActionListener(this);
        popupMenu_Copy.addActionListener(this);
        popupMenu_Paste.addActionListener(this);
        popupMenu_SelectAll.addActionListener(this);

        //The text editor area registers right-click menu events
        edit_text_pane.addMouseListener(new MouseAdapter()
        { public void mousePressed(MouseEvent e)
        { if(e.isPopupTrigger())//Returns whether this mouse event triggered an event for the platform's pop-up menu
        { popupMenu.show(e.getComponent(),e.getX(),e.getY());//The positions X and Y in the component caller's coordinate space display the pop-up menu
        }
            edit_text_pane.requestFocus();//The edit area gets focus
        }
            public void mouseReleased(MouseEvent e)
            { if(e.isPopupTrigger())//Returns whether this mouse event triggered an event for the platform's pop-up menu
            { popupMenu.show(e.getComponent(),e.getX(),e.getY());//The positions X and Y in the component caller's coordinate space display the pop-up menu
            }
                edit_text_pane.requestFocus();//The edit area gets focus
            }
        });//Text edit area registration right-click menu event ends

        item_search.addActionListener(this);
        item_new.addActionListener(this);
        item_open.addActionListener(this);
        item_save.addActionListener(this);
        item_exit.addActionListener(this);
        item_export.addActionListener(this);
        item_print.addActionListener(this);
        item_cut.addActionListener(this);
        item_copy.addActionListener(this);
        item_stick.addActionListener(this);
        item_selectAll.addActionListener(this);
        item_about.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == item_new) {
            new box();
        }else if (e.getSource() == item_exit) {
            this.dispose();
        }else if (e.getSource() == item_open) {
            System.out.println(json);
            fileFunction.open(edit_text_pane, scroll_bar);
        }else if (e.getSource() == item_save) {
            fileFunction.save(edit_text_pane, scroll_bar);
        }else if (e.getSource() == item_print) {
            fileFunction.print();
        }else if (e.getSource() == item_export) {
            try {
                fileFunction.export(edit_text_pane, scroll_bar);
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == item_search) {
            search_function.search(this,edit_text_pane);
        }else if (e.getSource() == item_cut) {
            editFunction.cut(edit_text_pane);
        }else if (e.getSource() == item_copy) {
            editFunction.copy(edit_text_pane);
        }else if (e.getSource() == item_stick) {
            editFunction.paste(edit_text_pane);
        }else if (e.getSource() == item_selectAll){
            editFunction.selectAll(edit_text_pane);
        }
        else if (e.getSource() == item_about) {
            new helpFunction.about_Window(jobj);
        }else if (e.getSource() == popupMenu_Cut){
            editFunction.cut(edit_text_pane);
        }else if (e.getSource() == popupMenu_Copy){
            editFunction.cut(edit_text_pane);
        }else if (e.getSource() == popupMenu_Paste){
            editFunction.paste(edit_text_pane);
        }else if (e.getSource() == popupMenu_SelectAll){
            editFunction.selectAll(edit_text_pane);
        }
    }

    public class SyntaxHighlighter implements DocumentListener {
        private Set<String> keywords1;
        private Set<String> keywords2;
        private Set<String> keywords3;
        private Style keywordStyle1;
        private Style KeywordStyle2;
        private Style KeywordStyle3;
        private Style normalStyle;

        public SyntaxHighlighter(JTextPane editor) {

            // Prepare the style for coloring
            keywordStyle1 = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
            KeywordStyle2 = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
            KeywordStyle3 = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
            normalStyle = ((StyledDocument) editor.getDocument()).addStyle("Keyword_Style", null);
            StyleConstants.setForeground(keywordStyle1, Color.orange);
            StyleConstants.setForeground(KeywordStyle2, Color.yellow);
            StyleConstants.setForeground(KeywordStyle3, Color.red);
            StyleConstants.setForeground(normalStyle, Color.BLACK);


            // Prepare keywords
            keywords1 = new HashSet<String>();
            keywords2 = new HashSet<String>();
            keywords3 = new HashSet<String>();

            JSONArray ja = jobj.getJSONArray("keys");

            for (int i=0;i<ja.size();i++){
                JSONObject jo = ja.getJSONObject(i);
                if(i == 0){
                    Iterator iterator = jo.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry entry = (Map.Entry) iterator.next();
                        String value = entry.getValue().toString();
                        keywords1.add(value);
                    }
                }else if (i == 1){
                    Iterator iterator = jo.entrySet().iterator();
                    while(iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        String value = entry.getValue().toString();
                        keywords2.add(value);
                    }
                }else if (i == 2){
                    Iterator iterator = jo.entrySet().iterator();
                    while(iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        String value = entry.getValue().toString();
                        keywords3.add(value);
                    }
                }
            }

        }

        public void colouring(StyledDocument doc, int pos, int len) throws BadLocationException {

            // Gets the word affected after insert or delete.
            // For example "public" insert a space after b and it becomes "public" and there are two words to deal with :"pub" and "lic"
            // The range to get is the position before P in pub and after c in lic
            int start = indexOfWordStart(doc, pos);
            int end = indexOfWordEnd(doc, pos + len);

            char ch;
            while (start < end) {
                ch = getCharAt(doc, start);
                if (Character.isLetter(ch) || ch == '_') {
                    // If it begins with a letter or an underscore, it's a word
                    // Pos is the last subscript after processing
                    start = colouringWord(doc, start);
                } else {
                    SwingUtilities.invokeLater(new ColouringTask(doc, start, 1, normalStyle));

                    ++start;
                }
            }
        }

        /**
         * Color the word and return the index at the end of the word.
         */
        public int colouringWord(StyledDocument doc, int pos) throws BadLocationException {

            int wordEnd = indexOfWordEnd(doc, pos);
            String word = doc.getText(pos, wordEnd - pos);

            if (keywords1.contains(word)) {

            // If it is a keyword, color the keyword; otherwise, use normal coloring.
            // One thing to note here is that during the method calls of insertUpdate and removeUpdate, the doc properties cannot be modified.
            // But we want to be able to modify the doc properties, so we want to do this outside of this method.
            // For this purpose, you can use new threads, but it is easier to put them into swing event queues.
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, keywordStyle1));
            }else if(keywords2.contains(word)){
                SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, KeywordStyle2));

            }else if(keywords3.contains(word)){
                SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, KeywordStyle3));
            }
            else {
                SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));
            }
            return wordEnd;
        }

        /**
         * Gets the character subscripted at POS in the document..
         * If the pos is doc.getLength(), it returns the end of a document and does not throw an exception.If pos&lt;0, an exception is thrown.
         * So the valid value for pos is [0, doc.getLength()]
         */
        public char getCharAt(Document doc, int pos) throws BadLocationException {

            return doc.getText(pos, 1).charAt(0);
        }


        public int indexOfWordStart(Document doc, int pos) throws BadLocationException {

            // Find the first non-word character forward from the POS..
            for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);
            return pos;
        }

        public int indexOfWordEnd(Document doc, int pos) throws BadLocationException {

            // Find the first non-word character forward from the POS..

            for (; isWordCharacter(doc, pos); ++pos);

            return pos;
        }
        /**
         * Returns true if a character is alphabetic, numeric, or underlined.
         */

        public boolean isWordCharacter(Document doc, int pos) throws BadLocationException {

            char ch = getCharAt(doc, pos);
            if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') { return true; }
            return false;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }

        @Override
        public void insertUpdate(DocumentEvent e) {

            try {
                colouring((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                // Length is not needed because the cursor is immediately after the word is deleted
                colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }

        /**
         * Complete the coloring task
         */
        private class ColouringTask implements Runnable {
            private StyledDocument doc;
            private Style style;
            private int pos;
            private int len;

            public ColouringTask(StyledDocument doc, int pos, int len, Style style) {

                this.doc = doc;
                this.pos = pos;
                this.len = len;
                this.style = style;
            }

            public void run() {
                try {
                    // In this case, I'm coloring the characters
                    doc.setCharacterAttributes(pos, len, style, true);
                } catch (Exception e) {}
            }
        }
    }

    public static void main(String[] args) {
        box b = new box();
    }

}

