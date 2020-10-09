import com.alibaba.fastjson.JSONObject;

import javax.swing.*;

public class helpFunction {


    public static class about_Window extends JFrame {

        private JButton btn_ok;
        private JLabel about_label;
        private JLabel about_label_author_1;
        private JLabel about_label_author_2;

        private JPanel panel ;
        private BoxLayout boxlayout;

        /**
         * The constructor for the window
         */
        public about_Window(JSONObject jobj) {
            panel = new JPanel();
            boxlayout = new BoxLayout(panel,BoxLayout.Y_AXIS);
            panel.setLayout(boxlayout);

            btn_ok = new JButton("OK");
            btn_ok.setAlignmentX(CENTER_ALIGNMENT);
            about_label = new JLabel("Authors: "+jobj.get("author_1")+" and "+jobj.get("author_2"));
            about_label_author_1 = new JLabel(jobj.get("author_1")+" ID "+jobj.get("author_1_id"));
            about_label_author_2 = new JLabel(jobj.get("author_2")+" ID "+jobj.get("author_2_id"));
            about_label.setAlignmentX(CENTER_ALIGNMENT);

            panel.add(new JLabel("\t"));
            panel.add(new JLabel("\t"));
            panel.add(about_label);
            panel.add(new JLabel("\t"));
            panel.add(about_label_author_1);
            panel.add(about_label_author_2);
            panel.add(new JLabel("\t"));
            panel.add(new JLabel("\t"));
            panel.add(btn_ok);


            this.add(panel);
            this.setBounds((int)jobj.get("about_windows_x"), (int)jobj.get("about_windows_y"), (int)jobj.get("about_windows_width"), (int)jobj.get("about_windows_height"));
            this.setSize((int)jobj.get("about_windows_width"),(int)jobj.get("about_windows_height"));
            this.setTitle("About");
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            btn_ok.addActionListener(e->{
                this.dispose();
            });
        }
    }
}
