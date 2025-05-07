import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class zhandianguanli extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	String[][] body=new String[202][202];
	JTable table;
	Statement st;
	ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					zhandianguanli frame = new zhandianguanli();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public zhandianguanli() 
	{
		super("站点管理");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 300, 386, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u7AD9\u70B9\u7F16\u53F7:");
		label.setBounds(36, 61, 54, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(103, 58, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7AD9\u70B9\u540D\u79F0:");
		label_1.setBounds(36, 92, 54, 15);
		contentPane.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(103, 89, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_2 = new JLabel("\u7AD9\u70B9\u7C7B\u578B:");
		label_2.setBounds(36, 123, 54, 15);
		contentPane.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(103, 120, 66, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton button = new JButton("\u4FDD\u5B58");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button.setBounds(226, 19, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5220\u9664");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_1.setBounds(226, 57, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\u8FD4\u56DE");//返回
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(226, 100, 93, 23);
		contentPane.add(button_2);
		
		JLabel label_3 = new JLabel("\u8981\u67E5\u8BE2\u7684\u7AD9\u70B9:");
		label_3.setBounds(0, 0, 90, 15);
		contentPane.add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(22, 20, 66, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("\u67E5\u8BE2");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String str = " actionPerformed* from stuacce";
			        filltable(str);
			}
		});
		btnNewButton.setBounds(103, 19, 66, 23);
		contentPane.add(btnNewButton);
	}
	
	private void filltable(String str) {
        // TODO Auto-generated method stub
        try {
            for(int x=0;x<body.length;x++){
                body[x][0]=null;
                body[x][1]=null;
                body[x][2]=null;
            }
            int i = 0;
            rs = st.executeQuery(str);
            while(rs.next()){
                body[i][0]=rs.getString("zhandianbianhao");
                body[i][1]=rs.getString("zhandianmingcheng");
                body[i][2]=rs.getString("zhandianleixing");
                i=i+1;
            }
            this.repaint(i);
            textField.setText(body[0][0]);
            textField_1.setText(body[0][1]);
            textField_1.setText(body[0][2]);
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }
	private void button_1() {
        // TODO Auto-generated method stub
        try {
            int row = table.getSelectedRow();
            String str = "delete stuacce where zhandianbianhao = '" + body[row][0] + "'";
            st.executeUpdate(str);
        } catch (SQLException ex) {} ;
    }
}
	/* public void executeUpdate(String sql){
		  Connection conn = null;
		  Statement stmt = null;
		  try {
		   conn = startConn(conn);
		   stmt = conn.createStatement();
		   stmt.executeUpdate(sql);
		  } 

}*/
