import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

public class DD extends JFrame implements ActionListener 
{
	static DefaultTableModel tablemodel;
	private static JTable table=new JTable();
	private static JTextField text=new JTextField(10);;
	private static JButton b1,b2,b3;
	private static String style[]={"","","","",""};
	private JComboBox<String>Style;

	private static JPanel p;
	private static String x;//文本框里的线路值
	private static String s;//选中的司机
	private static String S[]=new String[4];

	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	public DD()
	{
		super("调度模块");

		this.setLayout(new BorderLayout());

		p=new JPanel();//如果加在上面 就会不重新清空 而是累加
		p.add(new JLabel("调度线路："));

		p.add(text);

		b1= new JButton("查询司机");
		b1.addActionListener(this);
		p.add(b1);

		p.add(new JLabel("司机："));

		p.add(Style=new JComboBox<String>(style));
		Style.addActionListener(this);

		b2= new JButton("发车");
		b2.addActionListener(this);
		p.add(b2);

		b3= new JButton("返回");
		b3.addActionListener(this);
		p.add(b3);

		this.add(p,"North");

		String titles[]={"调度编号","调度线路","调度时间","司机"};
		tablemodel=new DefaultTableModel(titles,0);
		table=new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(818, 300));
		this.add(new JScrollPane(table));

		this.setVisible(true);
		//this.setResizable(false);//不可改框架大小
		this.setBounds(400, 300, 820, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		//清空表格
		tablemodel=(DefaultTableModel)table.getModel();
		tablemodel.setRowCount(0);

		String label=e.getActionCommand();//获得内容
		// TODO Auto-generated method stub
		switch(label)
		{
		case "查询司机":


			x=text.getText();
			if(x.equals(""))
				JOptionPane.showMessageDialog(this, "请填写调度线路");
			else
			{
				try
				{
					stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					sql="select driver from diaodu"+" where luxian='"+x+"';";
					rset=stmt.executeQuery(sql);
					int i=0,j;
					while(rset.next())
						style[i++]=rset.getString(1);
					if(i==0)
						JOptionPane.showMessageDialog(this, "无对应司机！");
					else
					{
						this.Style.removeAllItems();//移除所有项！！！！！！
						for(j=0;j<style.length;j++) //重新添加项
							this.Style.addItem(style[j]);
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
			break;
		case "发车":
			String s=(String)Style.getSelectedItem();//获得选中司机

			Date date=new Date();//获得当前时间
			DateFormat format=new SimpleDateFormat("HH:mm:ss");
			String time=format.format(date);

			//更新时间
			try
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="update diaodu set time='"+time+"' where luxian='"+x+"' and driver=N'"+s+"';";
				//sql="update diaodu set time='"+time+"' where luxian='"+x+"' and driver=N'"+s+"' and driver in(select name from Person where zhuangtai!=N'忙碌')";
				stmt.executeUpdate(sql);
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			//列表显示
			try
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="select * from diaodu"+" where luxian='"+x+"' and driver=N'"+s+"';";
				rset=stmt.executeQuery(sql);
				while(rset.next())
				{
					for(int i=1;i<=4;i++)
						S[i-1]=rset.getString(i);
					tablemodel.addRow(S);
				}
				if(tablemodel.getRowCount()==0)
					JOptionPane.showMessageDialog(this, "无查询项！");
				rset.close();
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			//更新状态
			try
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="update Person set zhuangtai=N'"+"忙碌"+"' where name=N'"+s+"';";
				stmt.executeUpdate(sql);
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			break;
		case "返回":
			dispose();
			break;
		}
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		DD d=new DD();
	}
}
