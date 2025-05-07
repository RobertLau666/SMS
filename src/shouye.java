import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class shouye extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					shouye frame = new shouye();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public shouye() 
	{
		super("线路管理");
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 300, 389, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);
		
		JLabel label = new JLabel("\u516C \u4EA4 \u7EBF \u8DEF \u7BA1 \u7406");
		label.setBounds(147, 24, 100, 15);
		contentPane.add(label);
		
		JButton button = new JButton("线路查询");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				xianluchaxun xlcx=new xianluchaxun();
				xlcx.setVisible(true);
			}
		});
		button.setBounds(38, 74, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("线路管理");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xianluguanli xlcx=new xianluguanli();
				xlcx.setVisible(true);
			}
		});
		button_1.setBounds(148, 74, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("返回");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(258, 74, 93, 23);
		contentPane.add(button_2);
	}
}
