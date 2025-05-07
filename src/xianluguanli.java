

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class xianluguanli extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String xx;
	private JTextField t[]=new JTextField[8];
	private String s[]=new String[8];

	String[][] body=new String[202][202];
	JTable table;
	Statement st;
	ResultSet rs;

	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					xianluguanli frame = new xianluguanli();
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
	public xianluguanli() 
	{
		super("公交线路管理");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 300, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("线路编号:");
		label.setBounds(30, 4, 86, 15);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(114, 1, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("查询");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				xx=textField.getText();
				if(xx.equals(""))
					JOptionPane.showInputDialog("无查询项！");
				else
				{
					try
					{
						stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//
						sql="select * from busxianlu where number='"+xx+"';";
						rset=stmt.executeQuery(sql);
						while(rset.next())
						{
							for(int i=1;i<=8;i++)
							{
								s[i-1]=rset.getString(i+1);
								t[i-1].setText(s[i-1]);
							}
						}
						rset.close();
						stmt.close();
						//数据连接对象conn不可关闭，否则只能查询一次
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(190, 0, 93, 23);
		contentPane.add(btnNewButton);

		for(int i=0;i<4;i++)
		{
			t[i]=new JTextField();
			t[i].setBounds(130, 42+35*i, 66, 21);
			contentPane.add(t[i]);
			t[i].setColumns(10);
		}

		for(int i=4;i<8;i++)
		{
			t[i]=new JTextField();
			t[i].setBounds(300, 42+35*(i-4), 66, 21);
			contentPane.add(t[i]);
			t[i].setColumns(10);
		}

		Label label_1 = new Label("线路名称:");
		label_1.setBounds(30, 42, 69, 23);
		contentPane.add(label_1);

		Label label_2 = new Label("运行时间:");
		label_2.setBounds(30, 77, 69, 23);
		contentPane.add(label_2);

		Label label_3 = new Label("途径站点数量:");
		label_3.setBounds(30, 108, 69, 23);
		contentPane.add(label_3);

		Label label_4 = new Label("途径站点名称:");
		label_4.setBounds(30, 141, 69, 23);
		contentPane.add(label_4);


		Label label_5 = new Label("首发时间:");
		label_5.setBounds(214, 42, 69, 23);
		contentPane.add(label_5);

		Label label_6 = new Label("末发时间:");
		label_6.setBounds(214, 77, 69, 23);
		contentPane.add(label_6);

		Label label_7 = new Label("单程票价:");
		label_7.setBounds(214, 108, 69, 23);
		contentPane.add(label_7);

		Label label_8 = new Label("间隔:");
		label_8.setBounds(214, 141, 69, 23);
		contentPane.add(label_8);

		JButton button = new JButton("保存");
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String X="";
				xx=textField.getText();
				for(int i=0;i<8;i++)
					s[i]=t[i].getText();
				X="set name=N'"+s[0]+"',yunxingtime='"+s[1]+"',count='"+s[2]+"',zhandianname=N'"+s[3]+"',shoufatime='"+s[4]+"',mofatime='"+s[5]+"',price='"+s[6]+"',jiange='"+s[7]+"'";
				try                        //添加到数据库
				{
					stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					String sql="update busxianlu "+X+" where number='"+xx+"';";
					/*String sql=
							"insert into busxianlu(number,name,age,phone,carnumber,classfy,service,pingji,zhuangtai)"+
									"VALUES('"+S[0]+"',N'"+S[1]+"','"+S[2]+"','"+S[3]+"',N'"+S[4]+"','"+S[5]+"','"+S[6]+"','"+S[7]+"',N'"+S[8]+"');";*/
					stmt.executeUpdate(sql);
					//int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等   
					/*若出现：com.microsoft.sqlserver.jdbc.SQLServerException: 将截断字符串或二进制数据 
					则数据库中表数据的设置长度太小*/
					stmt.close();
					JOptionPane.showInputDialog(this, "保存成功！");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
					JOptionPane.showInputDialog(this, "保存失败！");
				}
			}
		});
		button.setBounds(49, 190, 93, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("删除");
		button_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					sql="delete from busxianlu where number='"+xx+"';";
					stmt.executeUpdate(sql);
					stmt.close();
					for(int i=0;i<8;i++)
						t[i].setText("");
					//for(i=0;i<count.length;i++)
					//	tablemodel.removeRow(count[i]);//在表格中删除,且在数据库中删除之后再删除，否则行号改变
					//数据连接对象conn不可关闭，否则只能查询一次
					JOptionPane.showInputDialog(this, "删除成功！");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
					JOptionPane.showInputDialog(this, "删除失败！");
				}
			}
		});
		button_1.setBounds(152, 190, 93, 23);
		contentPane.add(button_1);

		JButton btnNewButton_1 = new JButton("返回");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(255, 190, 93, 23);
		contentPane.add(btnNewButton_1);
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
