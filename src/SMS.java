import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.*;

public class SMS extends JFrame implements ActionListener,TableModelListener, MouseListener
{
	private JTextField no,name,day[],days,allo,salary,num1,num2;
	static JTextField basic,z;
	private ButtonGroup b1,b2,b3;
	private JRadioButton r1[];//private JRadioButton r1[]=new JRadioButton[3];
	static JRadioButton r2[],r3[];
	private ImageIcon Set,head;//设置
	private JLabel ls,l1,label1;//数据库连接成功与否

	private static String sex[]={"男","女"};
	private static String ex[]={"A","B","C"};//30,20,10
	private static String sty[]={"a","b","c"};
	//private int s;//5,4,3
	private JComboBox<String>combox_province,combox_city,com_search;
	private static String d[]={"年","月","日"};
	private static String provinces[]=Others.P;
	private static String cities[][]=Others.C;
	private DefaultTableModel tablemodel;
	private JTable jtable;

	private String[] D=new String[11];//或者String D[];
	static String[] S=new String[3],s=new String[3];

	private Connection conn;//数据连接对象
	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	public SMS()throws SQLException
	{
		super("工资管理");
		// TODO 自动生成的构造函数存根
		//从settings.txt中获取数据
		try
		{
			FileReader fr=new FileReader("settings.txt");
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			String[] l=line.split(" ");
			for(int i=0;i<3;i++)
			{
				S[i]=l[i];
				s[i]=l[i+3];
			}
			br.close();
			fr.close();
		} 
		catch (IOException e1) 
		{
			JOptionPane.showMessageDialog(this, "查询失败！");
		}

		//界面设计
		JPanel p1=new JPanel(new GridLayout(5,1,0,0));
		JPanel p11=new JPanel();
		JPanel p12=new JPanel();
		JPanel p13=new JPanel();
		JPanel p14=new JPanel();
		JPanel p2=new JPanel(new BorderLayout());
		JPanel p21=new JPanel();
		JPanel p22=new JPanel();

		head=new ImageIcon("3.png");
		head.setImage(head.getImage().getScaledInstance(120,120,Image.SCALE_DEFAULT));//设置图片大小
		label1=new JLabel(head);
		label1.setBounds(0,0, head.getIconWidth(),head.getIconHeight());
		p1.add(label1);
		
		//p11
		p11.setBorder(new TitledBorder("基本信息"));
		p11.add(new Label("员工号："));
		no=new JTextField(10);
		p11.add(no);

		p11.add(new Label("姓名："));
		name=new JTextField(10);
		p11.add(name);

		p11.add(new Label("出生日期："));
		day=new JTextField[d.length];
		for(int i=0;i<day.length;i++)
		{
			p11.add(day[i]=new JTextField(5));
			p11.add(new Label(d[i]));
		}

		JPanel ss=new JPanel(new GridLayout(1,2,0,0));
		b1=new ButtonGroup();
		r1=new JRadioButton[sex.length];
		p11.add(new Label("性别："));
		for(int i=0;i<r1.length;i++)
		{
			r1[i]=new JRadioButton(sex[i]);
			ss.add(r1[i]);
			b1.add(r1[i]);
		}
		p11.add(ss);
		r1[0].setSelected(true);//单选按钮选中

		p11.add(new Label("家庭住址："));
		p11.add(combox_province=new JComboBox<String>(provinces));
		p11.add(combox_city=new JComboBox<String>(cities[0]));
		combox_province.addActionListener(this);
		p1.add(p11);

		//p12
		p12.setBorder(new TitledBorder("等级信息"));

		JPanel e=new JPanel(new GridLayout(1,3,0,0));
		b2=new ButtonGroup();
		r2=new JRadioButton[ex.length];
		p12.add(new Label("经验级别："));
		for(int i=0;i<r2.length;i++)
		{
			r2[i]=new JRadioButton(ex[i]);
			r2[i].addActionListener(this);
			e.add(r2[i]);
			b2.add(r2[i]);
		}
		p12.add(e);
		r2[0].setSelected(true);//单选按钮选中

		p12.add(new Label("基本工资："));
		basic=new JTextField(S[0],6);
		basic.setEditable(false);
		p12.add(basic);
		p12.add(new Label("¥"));
		p1.add(p12);

		//p13
		p13.setBorder(new TitledBorder("加班信息"));
		JPanel t=new JPanel(new GridLayout(1,3,0,0));
		b3=new ButtonGroup();
		r3=new JRadioButton[ex.length];
		p13.add(new Label("加班类别："));
		for(int i=0;i<r3.length;i++)
		{
			r3[i]=new JRadioButton(sty[i]);
			r3[i].addActionListener(this);
			t.add(r3[i]);
			b3.add(r3[i]);
		}
		p13.add(t);
		r3[0].setSelected(true);//单选按钮选中

		//////
		z=new JTextField(s[0],6);//5、4、3
		z.setEditable(false);
		p13.add(z);
		p13.add(new Label("¥/天                      "));

		p13.add(new Label("加班时间："));
		days=new JTextField(5);
		p13.add(days);
		p13.add(new Label("天"));

		p13.add(new Label("津贴："));
		allo=new JTextField(10);
		allo.setEditable(false);
		p13.add(allo);
		p13.add(new Label("¥"));
		p1.add(p13);

		//p14
		p14.setBorder(new TitledBorder("工资信息"));
		p14.add(new Label("总工资："));
		salary=new JTextField(10);
		salary.setEditable(false);
		p14.add(salary);
		p14.add(new Label("¥"));
		//p14.setBounds(0, 0, 200, 200);
		p1.add(p14);

		String titles[]={"员工号","姓名","出生日期","性别","家庭住址","经验级别","基本工资（¥）","加班类别","加班时间（天）","津贴（¥）","总工资（¥）"};
		tablemodel=new DefaultTableModel(titles,0);
		tablemodel.addTableModelListener(this);

		jtable=new JTable(tablemodel)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column)//设置表格第一列不可编辑
			{ 
				if(column==0||column==6||column==9||column==10)
					return false;//不可编辑
				else
					return true;//可编辑
			}
		};
		p2.add(new JScrollPane(jtable));

		JPanel BPanel=new JPanel();
		FlowLayout f=new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		BPanel.setLayout(f);

		BPanel.add(new Label("公司总人数："));
		num1=new JTextField(3);
		num1.setEditable(false);
		BPanel.add(num1);

		BPanel.add(new Label("表格行数："));
		num2=new JTextField(3);
		num2.setEditable(false);
		BPanel.add(num2);

		String str[][]={{"添加","删除","修改"},{"所有","员工号","姓名","性别","经验级别","加班类别"}};
		for(int i=0;i<str[0].length;i++)
		{
			JButton button=new JButton(str[0][i]);
			button.addActionListener(this);
			BPanel.add(button);
		}
		BPanel.add(new JLabel("查询关键字"));
		BPanel.add(com_search=new JComboBox<String>(str[1]));
		//com_search.addActionListener(this);

		JButton b1=new JButton("查询");
		b1.addActionListener(this);
		BPanel.add(b1);
		
		JButton b2=new JButton("返回");
		b2.addActionListener(this);
		BPanel.add(b2);

		Set=new ImageIcon("6.png");
		Set.setImage(Set.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));//设置图片大小
		ls=new JLabel(Set);
		ls.setBounds(0,0, Set.getIconWidth(),Set.getIconHeight());
		ls.addMouseListener(this);
		BPanel.add(ls);

		l1=new JLabel();//数据库连接状态
		BPanel.add(l1);

		p2.add(BPanel,"South");

		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,p1,p2);
		split.setDividerLocation(400);

		getContentPane().add(split);

		/*getContentPane().setLayout(new GridLayout(1,2,0,0));
		getContentPane().add(p1);
		getContentPane().add(p2);*/

		//this.setBackground(Color.WHITE);
		//this.setSize();
		//this.setLocation();//this.pack();//使大小合适,setSize()不起作用
		this.setBounds(80,300,1800,800);
		this.setResizable(false);//不可改框架大小
		this.setVisible(true);//框架可见
		try//注册驱动
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=SMS","sa","123123");
			l1.setText("数据库已连接！");
			l1.setForeground(Color.blue);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
			l1.setText("数据库连接失败！");
			l1.setForeground(Color.red);
		}
	}

	public void tableChanged(TableModelEvent e)
	{
		int w=e.getType();
		if(w!=TableModelEvent.UPDATE)//更新时才触发，插入和删除时不触发
			return;
		int r=e.getFirstRow();
		int c=e.getColumn();
		String sno=(String)jtable.getValueAt(r, 0);//获取编辑行的员工号
		String x="",y=(String)jtable.getValueAt(r, c);//更改前、后的值
		String s="";//更改的列名

		switch(c)
		{
		case 1:s="Sname";break;
		case 2:s="Sdate";break;
		case 3:s="Ssex";break;
		case 4:s="Sadd";break;
		case 5:s="Sexp";break;
		case 7:s="Style";break;
		case 8:s="Days";break;//0、6、9、10不能改
		}

		try//查询x
		{
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			sql="select "+s+" from Tall"+" where Sno='"+sno+"';";
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
					stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					sql="update Tall set "+s+"='"+y+"' where Sno='"+sno+"';";
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
			{
				jtable.setValueAt(x, r, c);
			}
		}
	}

	public void actionPerformed(ActionEvent ev)//throws SQLException
	{
		int i,j;
		for(i=0;i<3;i++)
		{
			if(r2[i].isSelected())
				basic.setText(S[i]);
			if(r3[i].isSelected())
				z.setText(s[i]);
		}

		/*
string->int
1. )int i = Integer.parseInt([String]); 或i = Integer.parseInt([String],[int radix]);
2. )int i = Integer.valueOf(my_str).intValue();
int->string;
1.) String s = String.valueOf(i);
2.) String s = Integer.toString(i);
3.) String s = "" + i;
		 */	
		Object l=ev.getSource();
		if(l==combox_province)//获得当前事件源组件
		{
			i=this.combox_province.getSelectedIndex();
			if(cities!=null&&i!=-1)
			{
				this.combox_city.removeAllItems();//清楚城市组合框中原有的所有内容
				for(j=0;j<cities[i].length;j++)
					this.combox_city.addItem(cities[i][j]);
			}
		}

		String label=ev.getActionCommand();
		if(label.equals("添加"))//获得组件上的标签字符串
		{
			//清空表格
			tablemodel=(DefaultTableModel)jtable.getModel();
			tablemodel.setRowCount(0);

			D[0]=no.getText();

			try
			{
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="select * from Tall;";
				rset=stmt.executeQuery(sql);
				String s;
				while(rset.next())
					if(rset.getString(1).equals(D[0]))
					{
						JOptionPane.showMessageDialog(this, "员工已存在！");
						num2.setText(tablemodel.getRowCount()+"");//表格行数
						return;
					}
				rset.close();
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			D[1]=name.getText();
			for(i=0;i<3;i++)
				if(day[i].getText().equals(""))//不是day[i].equals("")
				{
					D[2]="";
					break;
				}
			if(i==3)
				D[2]=day[0].getText()+"/"+day[1].getText()+"/"+day[2].getText();
			for(i=0;i<r1.length;i++)
				if(r1[i].isSelected())
				{
					D[3]=r1[i].getText();
					break;
				}
			D[4]=(String)combox_province.getSelectedItem()+combox_city.getSelectedItem();
			for(i=0;i<r2.length;i++)
				if(r2[i].isSelected())
				{
					D[5]=r2[i].getText();//ABC
					break;
				}
			D[6]=basic.getText();
			for(i=0;i<r3.length;i++)
				if(r3[i].isSelected())
				{
					D[7]=r3[i].getText();//abc
					break;
				}
			D[8]=days.getText();

			if(D[8].equals(""))
				JOptionPane.showMessageDialog(this, "将基本信息填写完整！");//加班时间不能为空！
			else
			{
				allo.setText(Integer.parseInt(z.getText())*Integer.parseInt(D[8])+"");
				D[9]=allo.getText();

				salary.setText((Integer.parseInt(D[6])+Integer.parseInt(D[9]))+"");
				D[10]=salary.getText();

				for(i=0;i<D.length;i++)
					if(D[i].equals(""))
					{
						JOptionPane.showMessageDialog(this, "将基本信息填写完整！");
						break;
					}
				if(i==D.length)
				{
					tablemodel.addRow(D);   //在表格中显示
					try                     //添加到数据库
					{
						stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						String sql=
								"insert into Tall(Sno,Sname,Sdate,Ssex,Sadd,Sexp,Sbasic,Style,Days,Allo,Salary)"+
										"VALUES('"+D[0]+"',N'"+D[1]+"',N'"+D[2]+"',N'"+D[3]+"',N'"+D[4]+"','"+D[5]+"','"+D[6]+"','"+D[7]+"','"+D[8]+"','"+D[9]+"','"+D[10]+"');";
						stmt.executeUpdate(sql);//int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等   
						/*若出现：com.microsoft.sqlserver.jdbc.SQLServerException: 将截断字符串或二进制数据 
						则数据库中表数据的设置长度太小*/
						stmt.close();
						JOptionPane.showMessageDialog(this, "添加成功！");
					}
					catch (SQLException e)
					{
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, "添加失败！");
					}
				}
			}
		}
		else if(label.equals("删除"))
		{
			if(tablemodel.getRowCount()==0)
				JOptionPane.showMessageDialog(this, "表格框为空，不能删除！");
			else
			{
				int count[]=jtable.getSelectedRows();//获取选中的行数，多项删除
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
							value[i]=(String) tablemodel.getValueAt(count[i], 0);

						//删除数据库中数据
						try
						{
							stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
							for(i=0;i<count.length;i++)
							{
								sql="delete from Tall where Sno='"+value[i]+"';";
								stmt.executeUpdate(sql);
							}
							stmt.close();
							//for(i=0;i<count.length;i++)
							//	tablemodel.removeRow(count[i]);//在表格中删除,且在数据库中删除之后再删除，否则行号改变
							//数据连接对象conn不可关闭，否则只能查询一次
							JOptionPane.showMessageDialog(this, "删除成功！");
						}
						catch (SQLException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(this, "删除失败！");
						}
					}
					else if(k==1)
						jtable.clearSelection();//取消选中项
				}
			}
		}
		else if(label.equals("修改"))
		{
			JOptionPane.showMessageDialog(this, "双击需修改处编辑即可！");
		}
		else if(label.equals("查询"))
		{
			//清空表格
			tablemodel=(DefaultTableModel)jtable.getModel();
			tablemodel.setRowCount(0);

			String a=(String)com_search.getSelectedItem();
			String x="",y="";
			switch(a)
			{
			case"所有":x="";break;
			case"员工号":x="Sno";y=no.getText();break;
			case"姓名":x="Sname";y=name.getText();break;
			case"性别":x="Ssex";
			for(i=0;i<r1.length;i++)
				if(r1[i].isSelected())
				{
					y=r1[i].getText();
					break;
				}
			break;
			case"经验级别":x="Sexp";
			for(i=0;i<r2.length;i++)
				if(r2[i].isSelected())
				{
					y=r2[i].getText();
					break;
				}
			break;
			case"加班类别":x="Style";
			for(i=0;i<r3.length;i++)
				if(r3[i].isSelected())
				{
					y=r3[i].getText();
					break;
				}
			break;
			}
			try
			{
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sql="select * from Tall"+(x.equals("")?";":" where "+x+"='"+y+"';");
				rset=stmt.executeQuery(sql);
				String s;
				while(rset.next())
				{
					for(i=1;i<=11;i++)
						D[i-1]=rset.getString(i);
					tablemodel.addRow(D);
				}
				if(tablemodel.getRowCount()==0)
					JOptionPane.showMessageDialog(this, "无查询项！");
				rset.close();
				stmt.close();
				//数据连接对象conn不可关闭，否则只能查询一次
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else if(label.equals("返回"))
		{
			this.dispose();//关闭当前 显示主界面
		}
		
		//更新数据
		try
		{
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			sql="select * from Tall;";
			rset=stmt.executeQuery(sql);
			rset.last();
			num1.setText(rset.getRow()+"");      //公司总人数
			rset.close();
			stmt.close();
			//数据连接对象conn不可关闭，否则只能查询一次
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		num2.setText(tablemodel.getRowCount()+"");//表格行数
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		new Settings();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}
/*	public static void main(String[] args)throws SQLException
	{
		// TODO 自动生成的方法存根
		SMS s=new SMS();
	}*/
}