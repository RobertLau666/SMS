import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Man extends JFrame implements ActionListener, TableModelListener
{
	JPanel content1 = new JPanel();//人员管理图片
	JPanel content2 = new JPanel();//人员管理数据库（文本框）
	JPanel content3 = new JPanel();//驾驶员信息录入  修改   删除  查询   
	JPanel content4 = new JPanel();//返回

	JPanel Picture,Text,J3;//图片	
	JButton a,b,c,d,e,f;//数据库	
	JScrollPane scpDemo;
	JTableHeader jth;
	JTable tabDemo;
	static JTable table;
	static DefaultTableModel tablemodel;
	private String[] D=new String[9];//或者String D[];

	//private Connection conn;//数据连接对象//共用一个即可
	//下面出现NullOfPointer错误是因为conn为空 还要再写一个注册try/catch,公用一个就不为空
	//剩下的这些就无所谓了 也可共用一个 但还要加上例如Login.stmt 索性嫌麻烦不加
	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	public Man()
	{
		super("人员管理系统");

		this.setLayout(new GridLayout(4,1,0,0));
		this.add(content1);
		this.add(content2);
		this.add(content3);
		this.add(content4);

		//第一部分
		JPanel imagePanel,p1;
		JLabel label1;
		ImageIcon head;//默认静态可视
		ImageIcon load;
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel=(JPanel) this.getContentPane();
		imagePanel.setOpaque(false);

		p1=new JPanel();
		p1.setOpaque(false);
		imagePanel.add(p1);//加入头像
		head=new ImageIcon("7.jpg");
		head.setImage(head.getImage().getScaledInstance(818,71,Image.SCALE_DEFAULT));//设置图片大小
		label1=new JLabel(head);
		label1.setBounds(0,5, head.getIconWidth(),head.getIconHeight());
		p1.add(label1);
		content1.add(p1);

		String titles[]={"司机编号","姓名","年龄","电话","车牌","职务类别","每月业务量","能力评级","状态"};
		tablemodel=new DefaultTableModel(titles,0);
		tablemodel.addTableModelListener(this);
		table=new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(818, 100));
		content2.add(new JScrollPane(table));

		//第三部分 content3
		//(1)驾驶员信息录入
		JButton a = new JButton("录入");//查询
		content3.add(a);
		a.addActionListener(this);//注册监听器	
		content3.add(a);

		//(2)驾驶员信息修改
		JButton b = new JButton("修改");//查询
		content3.add(b);
		b.addActionListener(this);//注册监听器	
		content3.add(b);

		//(3)删除
		JButton c = new JButton("删除");//查询
		content3.add(c);
		c.addActionListener(this);//注册监听器	
		content3.add(c);

		//(4)驾驶员信息查询
		JButton d = new JButton("查询");//查询
		content3.add(d);
		d.addActionListener(this);//注册监听器	
		content3.add(d);

		//第四部分 显示司机信息、返回
		//(1)显示信息
		JButton f = new JButton("显示全部");//查询
		content4.add(f);
		f.addActionListener(this);//注册监听器		
		content4.add(f);

		//(2)返回
		JButton e = new JButton("返回");//查询
		content4.add(e);
		e.addActionListener(this);
		content4.add(e);

		this.setBounds(400,230,818, 500);
		this.setResizable(false);//不可改框架大小
		this.setVisible(true);
		//this.setDefaultCloseOperation(3);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点关闭按钮时退出
	}

	public void tableChanged(TableModelEvent e)
	{
		int w=e.getType();
		if(w!=TableModelEvent.UPDATE)//更新时才触发，插入和删除时不触发
			return;
		int r=e.getFirstRow();//修改的行数
		int c=e.getColumn();//修改的列数
		String number=(String)table.getValueAt(r, 0);//获取编辑行的员工号
		String x="",y=(String)table.getValueAt(r, c);//更改前、后的值
		String s="";//更改的列名

		switch(c)
		{
		case 0:s="number";break;
		case 1:s="name";break;
		case 2:s="age";break;
		case 3:s="phone";break;
		case 4:s="carnumber";break;
		case 5:s="classfy";break;
		case 6:s="service";break;
		case 7:s="pingji";break;
		case 8:s="zhuangtai";break;
		}

		try//查询x
		{
			stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			sql="select "+s+" from Person"+" where number='"+number+"';";
			rset=stmt.executeQuery(sql);
			rset.next();//指向第一行
			x=rset.getString(1);
			rset.close();
			stmt.close();
			//数据连接对象conn不可关闭，否则只能查询一次
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		if(!x.equals(y))
		{
			int k=JOptionPane.showConfirmDialog(this,"确认修改？","确认",JOptionPane.YES_NO_OPTION);//是0否1
			if(k==0)
			{
				try
				{
					stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					sql="update Person set "+s+"='"+y+"' where number='"+number+"';";
					stmt.executeUpdate(sql);
					stmt.close();
					//for(i=0;i<count.length;i++)
					//	tablemodel.removeRow(count[i]);//在表格中删除,且在数据库中删除之后再删除，否则行号改变
					//数据连接对象conn不可关闭，否则只能查询一次
					//JOptionPane.showInputDialog("更改成功！");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
					//JOptionPane.showInputDialog("更改失败！");
				}
			}
			else//否 复原
				table.setValueAt(x, r, c);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int i;

		String label=e.getActionCommand();//获得内容
		// TODO Auto-generated method stub
		switch(label)
		{
		case "录入":new Search();break;
		case "修改":
			JOptionPane.showMessageDialog(this, "双击需修改处编辑即可！");
			break;
		case "删除":
			if(tablemodel.getRowCount()==0)
				JOptionPane.showMessageDialog(this, "表格框为空，不能删除！");
			else
			{
				int count[]=table.getSelectedRows();//获取选中的行数，多项删除
				String[] value = new String[100];
				//String value[];
				if(count.length==0)
					JOptionPane.showMessageDialog(this, "请选中表格框数据项！");
				else//有选中
				{
					int k=JOptionPane.showConfirmDialog(this, "确认删除？");//是0否1取消2
					if(k==0)//是
					{
						for(i=0;i<count.length;i++)
							value[i]=(String) tablemodel.getValueAt(count[i], 0);//得到count[i]行，0列的值 即编号

						//删除数据库中数据
						try
						{
							stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
							for(i=0;i<count.length;i++)
							{
								sql="delete from Person where number='"+value[i]+"';";
								stmt.executeUpdate(sql);
							}
							stmt.close();
							//for(i=0;i<count.length;i++)
							//	tablemodel.removeRow(count[i]);//在表格中删除,且在数据库中删除之后再删除，否则行号改变
							//数据连接对象conn不可关闭，否则只能查询一次
							JOptionPane.showMessageDialog(this, "删除成功！");
						}
						catch (SQLException e1)
						{
							e1.printStackTrace();
							JOptionPane.showMessageDialog(this, "删除失败！");
						}
					}
					else if(k==1)
						table.clearSelection();//取消选中项
				}
			}
			break;
		case "查询":new chaxun();break;
		case "显示全部":
			//清空表格
			tablemodel=(DefaultTableModel)table.getModel();
			tablemodel.setRowCount(0);

			try
			{
				stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//
				sql="select * from Person;";
				rset=stmt.executeQuery(sql);
				String s;
				while(rset.next())
				{
					for(i=1;i<=9;i++)
						D[i-1]=rset.getString(i);
					tablemodel.addRow(D);
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
			break;
		case "返回":this.dispose();break;
		}
	}

	public static void main(String[] args) 
	{
		// TODO 自动生成的方法存根
		Man m=new Man();
	}
}
