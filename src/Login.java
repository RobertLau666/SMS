import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;
//import java.util.TimerTask;

public class Login extends JFrame implements ActionListener, MouseListener
{
	protected static JTextField username;
	private JPasswordField password;
	private JPanel imagePanel,p1,p2,p21,p22,p23,p24;
	private ImageIcon background;
	static ImageIcon head;//默认静态可视
	private ImageIcon load;
	private JLabel l3,label1,label2,signup;
	private static String style[]={"管理员","员工"};
	private JComboBox<String>Style;
	private int tag;
	private String user,pwd;//获取的用户名、密码
	
	//int min=0;和Timer t ;的声明要放到方法外的类中作为全局变量，不然你的
	//public void actionPerformed(ActionEvent e)是无法访问的
	int min=0;
	Timer t;

	static Connection conn;//数据连接对象
	/*private Statement stmt;//语句执行对象
	private String sql;//执行语句
	private ResultSet rset;//结果集
	private ResultSetMetaData rsmd;//表属性
	 */	

	public Login()
	{
		super("公交管理系统登录");

		//C:\\Users\\123\\Desktop\\
		background=new ImageIcon("1.jpg");
		background.setImage(background.getImage().getScaledInstance(1090,600,Image.SCALE_DEFAULT));//设置图片大小
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());

		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel=(JPanel) this.getContentPane();
		imagePanel.setOpaque(false); 

		imagePanel.setLayout(new GridLayout(2,1,0,0));
		//this.getLayeredPane().setLayout(null);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		//imagePanel.add(p);

		p1=new JPanel();
		p2=new JPanel(new GridLayout(4,1,0,0));
		p21=new JPanel();
		p22=new JPanel();
		p23=new JPanel();
		p24=new JPanel();

		p1.setOpaque(false);
		p2.setOpaque(false); 
		p21.setOpaque(false); 
		p22.setOpaque(false); 
		p23.setOpaque(false); 
		p24.setOpaque(false); 

		imagePanel.add(p1);//加入头像
		head=new ImageIcon("2.png");
		head.setImage(head.getImage().getScaledInstance(240,240,Image.SCALE_DEFAULT));//设置图片大小
		label1=new JLabel(head);
		label1.setBounds(0,0, head.getIconWidth(),head.getIconHeight());
		p1.add(label1);

		JLabel l1=new JLabel("账  号：");
		l1.setFont(new java.awt.Font("宋体",Font.PLAIN,17));
		l1.setForeground(Color.yellow);
		p21.add(l1);//JLabel是透明的标签
		username=new JTextField(10);
		p21.add(username);
		/*
JLabel的字体样式，大小，颜色设置用到以下两个方法：
//设置字体
		 * jlabel.setFont(new java.awt.Font("Dialog",   1,   17));
“dialog”代表字体，1代表样式(1是粗体，0是平常的）17是字号
//设置颜色
jlabel.setForeground(Color.red);
		 * */
		JLabel l2=new JLabel("密  码：");
		l2.setFont(new java.awt.Font("宋体",Font.PLAIN,17));
		l2.setForeground(Color.yellow);
		p22.add(l2);
		password=new JPasswordField(10);
		//password.setEchoChar('*');
		//pfPassword.setEchoChar('\0');密码可见
		p22.add(password);

		l3=new JLabel();
		l3.setFont(new java.awt.Font("宋体",Font.PLAIN,20));
		l3.setForeground(Color.white);
		p23.add(l3);

		JLabel l0=new JLabel("登录类别：");
		l0.setFont(new java.awt.Font("宋体",Font.PLAIN,17));
		l0.setForeground(Color.yellow);
		p24.add(l0);//JLabel是透明的标签
		p24.add(Style=new JComboBox<String>(style));
		Style.addActionListener(this);

		JButton b1=new JButton("登录");
		b1.addActionListener(this);
		p24.add(b1);

		JButton b2=new JButton("重置");
		b2.addActionListener(this);
		p24.add(b2);

		signup=new JLabel("注册");
		signup.setFont(new java.awt.Font("宋体",Font.PLAIN,17));
		signup.setForeground(Color.yellow);
		signup.addMouseListener(this);
		signup.setVisible(false);
		p24.add(signup);

		p2.add(p21);
		p2.add(p22);
		p2.add(p23);
		p2.add(p24);
		imagePanel.add(p2);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点关闭按钮时退出
		setBounds(430,240,background.getIconWidth(),background.getIconHeight());
		setResizable(false);//不可改框架大小
		setVisible(true);

		//第一个参数是计时器触发间隔，1000毫秒，即1秒
		//第二个参数是侦听器，用于编写你的计时器触发后的处理方法
		t= new Timer(1000, new ActionListener()//不要用sleep，用Timer
				{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				min++;
				if(min==3)//延时3秒
				{
					//setVisible(false);//登录窗口不可见
					dispose();//dispose:"文档销毁",close:"关闭"(指文件等处理结束后的收拾和整理)
					min=0;
					t.stop();
					if(tag==0)//管理员
						new Main1();
					else//员工
						new frame1();
					/*	try //切换窗口
					{

					}
					catch (SQLException e1) 
					{
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}*/
				}
			}
				}
				);// 初始化计时器
	}

	public void actionPerformed(ActionEvent ev)//throws SQLException
	{
		Object l=ev.getSource();

		if(l==Style)//获得当前事件源组件
		{
			tag=this.Style.getSelectedIndex();
			if(tag==0)//0管理员
				signup.setVisible(false);
			else//1员工
				signup.setVisible(true);
		}

		String label=ev.getActionCommand();//获得内容
		if(label.equals("登录"))
		{
			user=username.getText();
			char[] s=password.getPassword();
			pwd=new String(s);//字符数组转换为字符串
			int k=0;

			if(user.equals("")||pwd.equals(""))
				l3.setText("请将信息填写完整！");
			else
			{
				if(tag==0)//////////////管理员///////////////
				{
					if(user.equals("admin")&&pwd.equals("admin"))//可以登陆
					{
						l3.setText("登录成功，正在连接数据库");
						connect();//连接数据库
						loadimage("3.png");
						t.start();//执行延迟,后面的代码和函数同时继续执行
					}
					else
						l3.setText("登录失败，账号或密码错误");
				}
				else//////////////////员工//////////////////
				{
					try//检测是否已注册
					{
						File file=new File("signup.txt");
						if(file.exists())//文件存在
						{
							FileReader fr=new FileReader("signup.txt");//首次登录找不到文件，故catch，需手动新建
							BufferedReader br=new BufferedReader(fr);
							String line;
							while((line=br.readLine())!=null)
							{
								String[] t=line.split(" ");
								if(t[0].equals(user))//已注册，可登录
									if(t[1].equals(pwd))
									{
										k=1;
										break;//跳出当前while循环
									}
									else
									{
										l3.setText("密码错误");
										k=2;
									}
							}
							br.close();
							fr.close();
						}
						else//文件不存在，新建
							file.createNewFile();
					}
					catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(this, "查询失败！");
					}

					if(k==1)//登录
					{
						l3.setText("登录成功，正在连接数据库");
						connect();//连接数据库
						loadimage("5.png");
						t.start();//执行延迟,后面的代码和函数同时继续执行
					}
					if(k==0)
						l3.setText("未注册账号");
				}
			}
		}
		if(label.equals("重置"))
		{
			username.setText("");
			password.setText("");
			l3.setText("");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
	} 

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		new Signup();
	}

	public void loadimage(String s)//加载头像
	{
		p1.remove(label1);//删除图片标签
		head=new ImageIcon(s);//头像
		head.setImage(head.getImage().getScaledInstance(240,240,Image.SCALE_DEFAULT));//设置图片大小
		label1=new JLabel(head);
		label1.setBounds(0,0, head.getIconWidth(),head.getIconHeight());
		p1.add(label1);

		load=new ImageIcon("4.gif");//加载图片
		load.setImage(load.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));//设置图片大小
		label2=new JLabel(load);
		label2.setBounds(0,0, load.getIconWidth(),load.getIconHeight());
		p23.add(label2);
	}

	public static void connect()//登陆成功后，连接数据库
	{
		try//注册驱动
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=SMS","sa","123123");
			//System.out.println("数据库已连接！");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
			//System.out.println("数据库连接失败！");
		}
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		//仅限这一个类内使用，若是其他类调用，则无用
		//总程序只需一个main即可，所以一个login调用即可，其他可省去
		Login L=new Login();
	}
}