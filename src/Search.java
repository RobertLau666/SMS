import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Search extends JFrame implements ActionListener 
{
	JPanel content1 = new JPanel();//司机、编号、员工类型
	JPanel content2 = new JPanel();//年龄、车牌、电话
	JPanel content3 = new JPanel();//备注信息
	JPanel content4 = new JPanel();//文本框
	JPanel content5 = new JPanel();//返回、重置、录入

	JPanel name,number,Search_Text,classfy,age,sign,phone,message,Txt;
	JButton a,b,c;
	JComboBox<String> J5;
	JTextField J[]=new JTextField[6];
	String S[]=new String[9];
	JTextArea J6;

	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	public Search()
	{
		super("录入信息");
		this.setSize(550, 300);
		this.setLayout(new GridLayout(5,6));
		this.add(content1);
		this.add(content2);
		this.add(content3);
		this.add(content4);
		this.add(content5);

		//第一部分：司机、编号、员工类型
		//(1)编号
		JLabel number = new JLabel("司机编号:");
		content1.add(number);
		J[0]= new JTextField(10);//文本框
		content1.add(J[0]);

		//(2)姓名
		JLabel name = new JLabel("姓名:");
		content1.add(name);
		J[1] = new JTextField(10);//文本框
		content1.add(J[1]);

		//第二部分：年龄、车牌、电话
		//(3)年龄
		JLabel age = new JLabel("年龄:");
		content1.add(age);
		J[2] = new JTextField(10);//文本框
		content1.add(J[2]);

		//(4)电话
		JLabel phone = new JLabel("电话:");
		content2.add(phone);
		J[3] = new JTextField(10);//文本框
		content2.add(J[3]);

		//(5)车牌
		JLabel carnumber = new JLabel("车牌:");
		content2.add(carnumber);
		J[4] = new JTextField(10);//文本框
		content2.add(J[4]);

		//(6)员工类型
		JLabel classfy = new JLabel("员工类型:");
		content2.add(classfy);
		String []Search_Text =new String[]{"正式", "实习"};
		J5= new JComboBox<String>(Search_Text);
		content2.add(J5);

		//第三部分：备注
		JLabel message = new JLabel("备注信息:");
		content3.add(message);

		//第四部分：文本框
		J6 = new JTextArea(7, 43);
		content4.add(J6);

		//第五部分：重置、录入、返回
		//(1)录入
		JButton b = new JButton("录入");
		content5.add(b);
		b.addActionListener(this);//注册监听器	
		content5.add(b);

		//(2)重置
		JButton a = new JButton("重置");
		content5.add(a);
		a.addActionListener(this);//注册监听器	
		content5.add(a);

		//(3)返回
		JButton c = new JButton("返回");
		content5.add(c);
		c.addActionListener(this);//注册监听器
		content5.add(c);

		this.setVisible(true);
		this.setResizable(false);
		this.setLocation(430, 240);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		String label=e.getActionCommand();//获得内容
		switch(label)
		{
		case "重置":
			/*
			number    J[0]
			name      J[1]
			age       J[2]
			phone     J[3]
			carnumber J[4]
			classfy J5J[5]
			 */
			for(int i=0;i<5;i++)
				J[i].setText("");
			J5.setSelectedIndex(0);
			J6.setText("");
			break;
		case "录入":
			//清空表格
			Man.tablemodel=(DefaultTableModel)Man.table.getModel();
			Man.tablemodel.setRowCount(0);

			for(int i=0;i<5;i++)
				S[i]=J[i].getText();
			S[5]=(String)J5.getSelectedItem();
			S[6]="0";
			S[7]="0";
			S[8]="暂无";

			try//检测是否已存在
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="select * from Person;";
				rset=stmt.executeQuery(sql);
				String s;
				while(rset.next())
					if(rset.getString(1).equals(S[0]))
					{
						JOptionPane.showMessageDialog(this, "司机已存在！");
						return;//跳出该case
					}
				rset.close();
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			Man.tablemodel.addRow(S);   //在表格中显示
			try                         //添加到数据库
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String sql=
						"insert into Person(number,name,age,phone,carnumber,classfy,service,pingji,zhuangtai)"+
								"VALUES('"+S[0]+"',N'"+S[1]+"','"+S[2]+"','"+S[3]+"',N'"+S[4]+"','"+S[5]+"','"+S[6]+"','"+S[7]+"',N'"+S[8]+"');";
				stmt.executeUpdate(sql);//int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等   
				/*若出现：com.microsoft.sqlserver.jdbc.SQLServerException: 将截断字符串或二进制数据 
				则数据库中表数据的设置长度太小*/
				stmt.close();
				JOptionPane.showMessageDialog(this, "录入成功！");
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "录入失败！");
			}
			break;
		case "返回":dispose();break;
		}
	}
}