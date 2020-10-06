import javax.swing.*;
import java.io.*;

public class fileFunction {
    public static void open(JTextArea edit_text_area, JScrollPane scroll_bar) {
        File file = null;
        int result ;
        JFileChooser fileChooser = new JFileChooser("F:\\");
        fileChooser.setApproveButtonToolTipText("确定"); // 设置确认按钮的现实文本
        fileChooser.setDialogTitle("打开文件"); // 设置title
        result = fileChooser.showOpenDialog(scroll_bar); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }

        if(file.isFile() && file.exists()) {
            BufferedReader reader = null;
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
                reader = new BufferedReader(inputStreamReader);

                String readLine = "";
                while ((readLine = reader.readLine()) != null) { // 对BufferedReader数据一行行读
                    edit_text_area.append(readLine+'\n');  //对edit_text_area 一行行加
                }

                reader.close(); // 关闭reader

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void save(JTextArea edit_text_area, JScrollPane scroll_bar) {
        File file = null;
        int result ;
        JFileChooser fileChooser = new JFileChooser("F:\\");
        fileChooser.setApproveButtonToolTipText("保存"); // 设置确认按钮的现实文本
        fileChooser.setDialogTitle("保存文件"); // 设置title
        result = fileChooser.showOpenDialog(scroll_bar); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }

        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); // 对字符进行编码转换
            BufferedWriter writer = new BufferedWriter(write);
            String content = edit_text_area.getText();
            writer.write(content);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }


}
