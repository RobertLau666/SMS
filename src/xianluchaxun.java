import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class xianluchaxun extends JFrame
{
	private static JPanel p[]= new JPanel[3];
	private static JButton a,b;
	private JTextField textField;
	private String xx;
	static JTable table;
	static DefaultTableModel tablemodel;
	private String[] D=new String[9];
	private static ImageIcon head;
	private static JLabel label1;


	private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					xianluchaxun frame = new xianluchaxun();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("null")
	public xianluchaxun() 
	{
		super("线路查询");

		this.setLayout(new BorderLayout());
		for(int i=0;i<3;i++)
			p[i]=new JPanel(); 

		JLabel label = new JLabel("输入要查询的数据编号:");
		label.setBounds(39, 10, 113, 15);
		p[0].add(label);

		textField = new JTextField();
		textField.setBounds(159, 7, 66, 21);
		textField.setColumns(10);
		p[0].add(textField);

		JButton button = new JButton("\u67E5\u8BE2");//查询
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				tablemodel=(DefaultTableModel)table.getModel();
				tablemodel.setRowCount(0);

				xx=textField.getText();
				if(xx.equals(""))
					JOptionPane.showInputDialog("无查询项！");
				else
				{
					try
					{
						stmt=Login.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//
						sql="select * from busxianlu where number="+xx+";";
						rset=stmt.executeQuery(sql);
						String s;
						while(rset.next())
						{
							for(int i=1;i<=9;i++)
								D[i-1]=rset.getString(i);
							tablemodel.addRow(D);
						}
						if(tablemodel.getRowCount()==0)
							JOptionPane.showInputDialog("无查询项！");
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
		button.setBounds(240, 6, 93, 23);
		p[0].add(button);
		this.add(p[0],"North");

		String titles[]={"线路编号","线路名称","运行时间","途径站点数量","途径站点名称","首发时间","末发时间","单程票价","间隔"};
		tablemodel=new DefaultTableModel(titles,0);
		table=new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(818, 350));
		p[1].add(new JScrollPane(table));
		this.add(p[1]);

		JButton b = new JButton("\u8FD4\u56DE");//返回
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		b.setBounds(396, 185, 93, 23);
		p[2].add(b);
		this.add(p[2],"South");

		this.setVisible(true);
		this.setResizable(false);//不可改框架大小
		this.setBounds(400, 300, 820, 500);
	}
}
