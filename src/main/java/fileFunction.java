import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;
import java.io.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class fileFunction {
    private static final String FONT = "C:\\Windows\\Fonts\\simhei.ttf";
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    public static void open(JTextPane edit_text_pane, JScrollPane scroll_bar) {
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
                    edit_text_pane.setText(edit_text_pane.getText()+readLine+'\n');  //对edit_text_area 一行行加
                }

                reader.close(); // 关闭reader

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void save(JTextPane edit_text_pane, JScrollPane scroll_bar) {
        File file = null;
        int result ;
        JFileChooser fileChooser = new JFileChooser("F:\\");
        fileChooser.setDialogTitle("保存文件"); // 设置title
        result = fileChooser.showOpenDialog(scroll_bar); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }

        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); // 对字符进行编码转换
            BufferedWriter writer = new BufferedWriter(write);
            String content = edit_text_pane.getText();
            writer.write(content);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();

        }

    }
    public static void print() {
        JFileChooser fileChooser = new JFileChooser(); // 创建打印作业
        int state = fileChooser.showOpenDialog(null);
        if (state == fileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile(); // 获取选择的文件
            // 构建打印请求属性集
            HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            // 设置打印格式，因为未确定类型，所以选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            // 查找所有的可用的打印服务
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            // 定位默认的打印服务
            PrintService defaultService = PrintServiceLookup
                    .lookupDefaultPrintService();
            // 显示打印对话框
            PrintService service = ServiceUI.printDialog(null, 300, 200,
                    printService, defaultService, flavor, pras);
            if (service != null) {
                try {
                    DocPrintJob job = service.createPrintJob(); // 创建打印作业
                    FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
                    DocAttributeSet das = new HashDocAttributeSet();
                    Doc doc = new SimpleDoc(fis, flavor, das);
                    job.print(doc, pras);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void export(JTextPane edit_text_pane, JScrollPane scroll_bar) throws IOException, DocumentException {
        File file = null;
        int result ;
        JFileChooser fileChooser = new JFileChooser("F:\\");
        fileChooser.setApproveButtonToolTipText("保存"); // 设置确认按钮的现实文本
        fileChooser.setDialogTitle("导出"); // 设置title
        result = fileChooser.showOpenDialog(scroll_bar); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }
        Document document = new Document();
        OutputStream os = new FileOutputStream(new File(file.getAbsolutePath()+".pdf"));
        PdfWriter.getInstance(document, os);
        document.open();
        //方法一：使用Windows系统字体(TrueType)
        BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        document.add(new Paragraph(edit_text_pane.getText(), font));
        document.close();
    }
}
