import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.io.*;

public class Main1 extends JFrame implements ActionListener,TableModelListener, MouseListener
{
	private ImageIcon I[]=new ImageIcon[6],h;
	private JPanel panel=new JPanel(new GridLayout(2,3,0,0));
	private JPanel P[]=new JPanel[6],p;
	private JLabel L[]=new JLabel[6],l;
	public Main1()
	{
		super("公交管理系统");

		for(int i=0;i<6;i++)
		{
			I[i]=new ImageIcon("Main1images/"+i+".jpg");
			I[i].setImage(I[i].getImage().getScaledInstance(490,490,Image.SCALE_DEFAULT));//设置图片大小
			L[i]=new JLabel(I[i]);
			L[i].setBounds(0, 0,I[i].getIconWidth(),I[i].getIconHeight());
			P[i]=new JPanel();
			P[i].add(L[i]);
			L[i].addMouseListener(this);
			panel.add(P[i]);
		}

		getContentPane().add(panel);

		this.setBackground(Color.WHITE);
		this.setBounds(200,100,1500,1040);
		this.setResizable(false);//不可改框架大小
		this.setVisible(true);//框架可见
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		Object o=arg0.getSource();
		for(int i=1;i<6;i++)
			if(o==L[i])
				P[i].setBorder(new LineBorder(new Color(27, 194, 246),1));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		Object o=arg0.getSource();
		for(int i=1;i<6;i++)
			if(o==L[i])
				P[i].setBorder(null);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		Object o=arg0.getSource();
		if(o==L[0])
		{
//啥都没有
		}
		else if(o==L[1])
		{
			new Man();
		}
		else if(o==L[2])
		{
			shouye s=new shouye();
			s.setVisible(true);
		}
		else if(o==L[3])
		{
			try 
			{
				new SMS();
			} 
			catch (SQLException e) 
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else if(o==L[4])
		{
			new DD();
		}
		else//o==L[5]
		{
			dispose();//dispose:"文档销毁",close:"关闭"(指文件等处理结束后的收拾和整理)
			new Login();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根

	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		//仅限这一个类内使用，若是其他类调用，则无用
		//总程序只需一个main即可，所以一个login调用即可，其他可省去
		Main1 m1=new Main1();
	}
}
