import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.TimerTask;
import javax.swing.*;

import org.apache.commons.httpclient.HttpException;
public class Signup extends JFrame implements ActionListener, MouseListener
{
	//用户名
	//密码
	//手机号、获取验证码
	//验证码
	//注册
	private JPanel p,p1,p2,p3,p4,p5;
	private JTextField j1,j3,j4;
	private JPasswordField j2;
	private ImageIcon background,head,load;
	public static JLabel l1,l2,l3,test,_l1,_l2,_l3,_l4;
	private JButton b1;
	public String s1,s2;
	public static String s3="s3";
	public String s4;
	private static String iden_code="236703";//验证码

	public static String gets3()
	{
		return s3;
	}
	public Signup() 
	{
		// TODO 自动生成的构造函数存根
		super("注册");
		p=new JPanel();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p5=new JPanel();

		p.setLayout(new GridLayout(5,1,0,0));
		l1=new JLabel("用户名：");
		p1.add(l1);//JLabel是透明的标签
		j1=new JTextField(10);
		p1.add(j1);
		_l1=new JLabel("");
		_l1.setForeground(Color.red);
		p1.add(_l1);//JLabel是透明的标签
		p.add(p1);

		l2=new JLabel("密    码：");
		p2.add(l2);//JLabel是透明的标签
		j2=new JPasswordField(10);
		p2.add(j2);
		_l2=new JLabel("");
		_l2.setForeground(Color.red);
		p2.add(_l2);//JLabel是透明的标签
		p.add(p2);

		l3=new JLabel("手机号：");
		p3.add(l3);//JLabel是透明的标签
		j3=new JTextField(10);
		p3.add(j3);
		_l3=new JLabel("");
		_l3.setForeground(Color.red);
		p3.add(_l3);//JLabel是透明的标签
		p.add(p3);

		j4=new JTextField(6);
		p4.add(j4);
		test=new JLabel("获取验证码");
		test.setForeground(Color.gray);
		test.addMouseListener(this);
		p4.add(test);
		_l4=new JLabel("");
		_l4.setForeground(Color.red);
		p4.add(_l4);//JLabel是透明的标签
		p.add(p4);

		b1=new JButton("注册");
		b1.addActionListener(this);
		p5.add(b1);

		p.add(p5);
		getContentPane().add(p);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点关闭按钮时退出，不止这一个
		setBounds(630,350,500,300);
		setResizable(false);//不可改框架大小
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO 自动生成的方法存根
		String label=e.getActionCommand();
		if(label.equals("注册"))
		{
			s1=j1.getText();
			s2=new String(j2.getPassword());
			s3=j3.getText();//获取手机号
			s4=j4.getText();//验证码
			String S=s1+" "+s2+" "+s3;

			int k=0;
			if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals(""))
			{
				if(s1.equals(""))
					_l1.setText("用户名不能为空");
				if(s2.equals(""))
					_l2.setText("密码不能为空");
				else 
					if(s2.length()<6)
						_l2.setText("密码至少要6位");
				if(s3.equals(""))
					_l3.setText("手机号不能为空");
				if(s4.equals(""))
					_l4.setText("验证码不能为空");
			}
			else//皆不为空
			{
				try//检测是否已注册
				{
					File file=new File("signup.txt");
					if(file.exists())//文件存在
					{
						FileReader fr=new FileReader("signup.txt");//首次登陆找不到文件，故catch，需手动新建
						BufferedReader br=new BufferedReader(fr);
						String line;
						while((line=br.readLine())!=null)
						{
							String[] t=line.split(" ");
							if(t[0].equals(s1)||t[2].equals(s3))
							{
								JOptionPane.showMessageDialog(this, "用户名或手机号已注册");
								k=1;
								break;//跳出当前while循环
							}
						}
						br.close();
						fr.close();
					}
					else//文件不存在
						file.createNewFile();
				}
				catch (IOException e1) 
				{
					JOptionPane.showMessageDialog(this, "查询失败！");
				}
				if(iden_code.equals(s4))//equals
				{
					if(k==0)//可注册
					{
						String filename="signup.txt";
						try
						{
							FileWriter fw=new FileWriter(filename,true);
							BufferedWriter bw=new BufferedWriter(fw);
							bw.write(S);
							bw.flush();//清空
							bw.newLine();//另起一行保存

							bw.close();
							fw.close();
							JOptionPane.showMessageDialog(this, "注册成功！");
							setVisible(false);
						}
						catch (IOException e1)
						{
							JOptionPane.showMessageDialog(this, "注册失败！");
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "验证码错误！");
				}
			}
		}
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
	public void mousePressed(MouseEvent arg0) //获取验证码
	{
		// TODO 自动生成的方法存根
		s3=j3.getText();//获取手机号
		
		try 
		{
			new Send();
		} 
		catch (HttpException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根
	}

	/*public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Signup s=new Signup();
	}*/
}
