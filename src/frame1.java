import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class frame1 extends JFrame implements ActionListener
{
	private ImageIcon background;
	static ImageIcon head;//默认静态可视
	private ImageIcon load;
	JPanel content1 = new JPanel();//人员管理图片
	JPanel content2 = new JPanel();//人员管理数据库（文本框）
	JPanel content3 = new JPanel();//驾驶员信息录入  修改   删除  查询   
	JPanel content4 = new JPanel();

	private JButton jb,jb1,jb2,jb3;
	private JPanel imagePanel;
	public frame1()
	{
		super("公交管理系统");

		background=new ImageIcon("background.jpg");
		background.setImage(background.getImage().getScaledInstance(750,500,Image.SCALE_DEFAULT));//设置图片大小
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());

		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel=(JPanel) this.getContentPane();
		imagePanel.setOpaque(false);

		//imagePanel.setLayout(new GridLayout(4,1,0,0));
		//this.getLayeredPane().setLayout(null);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		this.setLayout(new GridLayout(4,1));
		this.add(content1);
		this.add(content2);
		this.add(content3);
		this.add(content4);
		content1.setOpaque(false);
		content2.setOpaque(false); 
		content3.setOpaque(false); 
		content4.setOpaque(false); 

		//信息查询
		jb=new JButton("信息查询");
		jb.addActionListener(this);
		content1.add(jb);
		//工资查询
		jb1=new JButton("工资查询");
		jb1.addActionListener(this);
		content2.add(jb1);
		//线路查询
		jb2=new JButton("线路查询");
		jb2.addActionListener(this);
		content3.add(jb2);
		//退出
		jb3=new JButton("退出");
		jb3.addActionListener(this);
		content4.add(jb3);

		this.setBounds(600,400,background.getIconWidth(),background.getIconHeight());
		this.setResizable(false);//不可改框架大小
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getSource()==jb)
			new frame2();

		if(e.getSource()==jb1)
			new frame3();

		if(e.getSource()==jb2)
			new frame4();

		if(e.getSource()==jb3)
		{
			dispose();//dispose:"文档销毁",close:"关闭"(指文件等处理结束后的收拾和整理)
			new Login();
		}
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		frame1 frame=new frame1();
	}
}