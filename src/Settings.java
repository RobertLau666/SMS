import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Settings extends JFrame implements ActionListener
{

	JPanel p,p1,p2,p3;
	JTextField T[]=new JTextField[3],t[]=new JTextField[3];
	static String S[]=new String[3],s[]=new String[3];
	String X;
	JButton b1,b2;

	public Settings() {
		// TODO 自动生成的构造函数存根
		super("设置");

		JPanel p=new JPanel(new GridLayout(3,1,0,0));
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();

		p1.setBorder(new TitledBorder("经验级别"));
		for(int i=0;i<3;i++)
		{
			switch(i)
			{
			case 0:X="A：";break;
			case 1:X="B：";break;
			case 2:X="C：";break;
			}
			p1.add(new Label(X));
			T[i]=new JTextField(6);
			p1.add(T[i]);
			p1.add(new Label("¥"));
		}
		p.add(p1);

		p2.setBorder(new TitledBorder("加班类别"));
		for(int i=0;i<3;i++)
		{
			switch(i)
			{
			case 0:X="a：";break;
			case 1:X="b：";break;
			case 2:X="c：";break;
			}
			p2.add(new Label(X));
			t[i]=new JTextField(6);
			p2.add(t[i]);
			p2.add(new Label("¥"));
		}
		p.add(p2);

		b1=new JButton("确定");
		b1.addActionListener(this);
		p3.add(b1);

		b2=new JButton("取消");
		b2.addActionListener(this);
		p3.add(b2);
		p.add(p3);

		try
		{
			FileReader fr=new FileReader("settings.txt");
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			String[] l=line.split(" ");
			for(int i=0;i<3;i++)
			{
				S[i]=l[i];
				T[i].setText(S[i]);

				s[i]=l[i+3];
				t[i].setText(s[i]);
			}
			br.close();
			fr.close();
		} 
		catch (IOException e1) 
		{
			JOptionPane.showMessageDialog(this, "查询失败！");
		}

		getContentPane().add(p);
		setBounds(630,350,520,220);
		setResizable(true);//不可改框架大小
		setVisible(true);//框架可见
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO 自动生成的方法存根
		String label=e.getActionCommand();
		String H="";
		int k=0;
		if(label.equals("确定"))
		{
			for(int i=0;i<3;i++)
			{
				S[i]=T[i].getText();
				s[i]=t[i].getText();
			}

			for(int i=0;i<2;i++)//如果i<3,则数组越界，因为没有S[3]
			{
				if(Integer.parseInt(S[i])<=Integer.parseInt(S[i+1]))
				{
					k=1;
					break;
				}
				if(Integer.parseInt(s[i])<=Integer.parseInt(s[i+1]))
				{
					k=2;
					break;
				}
			}
			if(k==1||k==2)
			{
				if(k==1)
					H="A到C";
				else
					H="a到c";
				JOptionPane.showMessageDialog(this, "确保从"+H+"依次递减");
			}
			else//k=0
			{
				StringBuffer s1 = new StringBuffer(),s2 = new StringBuffer();
				for(int i = 0; i < 3; i++){
					s1. append(S[i]+" ");
					s2. append(s[i]+" ");
				}
				String SS = s1.toString()+s2.toString();

				try//确定，保存
				{
					File file=new File("settings.txt");
					/*if(!file.exists())//不存在则新建
					file.createNewFile();*/

					/*取需要删除意外的数据，写入同名txt，以前的txt被覆盖
					 * int lineDel=4;
					FileReader fr=new FileReader("settings.txt");
					BufferedReader br=new BufferedReader(fr);
					StringBuffer sb=new StringBuffer();
					String temp=null;
					int line=0;
					while((temp=br.readLine())!=null)
					{
						line++;
						if(line==lineDel)
							continue;
						sb.append(temp).append( "\r\n ");
					}
					br.close();
					fr.close();
					 */
					FileWriter fw=new FileWriter("settings.txt");
					BufferedWriter bw=new BufferedWriter(fw);
					bw.write(SS);
					bw.close();
					fw.close();
				}
				catch (IOException e1) 
				{
					JOptionPane.showMessageDialog(this, "查询失败！");
				}
				SMS.S=S;
				SMS.s=s;
				SMS.basic.setText(SMS.S[0]);
				SMS.z.setText(SMS.s[0]);
				SMS.r2[0].setSelected(true);
				SMS.r3[0].setSelected(true);
			}
		}
		else//取消
		{
			for(int i=0;i<3;i++)
			{
				T[i].setText(S[i]);
				t[i].setText(s[i]);
			}
		}
		if(k==0)
			this.setVisible(false);
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Settings S=new Settings();
	}
}
