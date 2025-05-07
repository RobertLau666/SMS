import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class frame2 extends JFrame implements ActionListener, TableModelListener
{
	private static JPanel p[]= new JPanel[3];
	private static JButton a,b;
	static JTable table;
	static DefaultTableModel tablemodel;
	private String[] D=new String[9];
	private static ImageIcon head;
	private static JLabel label1;

	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性
	
	public frame2()
	{
		super("信息查询");

		this.setLayout(new BorderLayout());
		for(int i=0;i<3;i++)
			p[i]=new JPanel(); 

		head=new ImageIcon("f2.jpg");
		head.setImage(head.getImage().getScaledInstance(818,71,Image.SCALE_DEFAULT));//设置图片大小
		label1=new JLabel(head);
		label1.setBounds(0,5, head.getIconWidth(),head.getIconHeight());
		p[0].add(label1);
		this.add(p[0],"North");

		String titles[]={"司机编号","姓名","年龄","电话","车牌","职务类别","每月业务量","能力评级","状态"};
		tablemodel=new DefaultTableModel(titles,0);
		table=new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(818, 300));
		p[1].add(new JScrollPane(table));
		this.add(p[1]);

		a = new JButton("信息显示");
		a.addActionListener(this);
		p[2].add(a);
		b = new JButton("返回");
		b.addActionListener(this);
		p[2].add(b);
		this.add(p[2],"South");

		this.setVisible(true);
		this.setResizable(false);//不可改框架大小
		this.setBounds(400, 300, 820, 500);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("返回"))
			this.dispose();
		int i;

		String label=e.getActionCommand();//获得内容
		// TODO Auto-generated method stub
		if(label.equals("信息显示"))
		{
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
		}
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO 自动生成的方法存根

	}
}
