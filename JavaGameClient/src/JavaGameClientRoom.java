import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;


public class JavaGameClientRoom extends JFrame{
	
	public int my;
	JRadioButton b1= new JRadioButton("");
	JRadioButton b2= new JRadioButton("");
	
	public JavaGameClientRoom(String username, String ip_addr, String port_no) {

		

		//setTitle("Graphic Game Test");
		setTitle("Graphic");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		
		
		
		
		
		ImageIcon icon = new ImageIcon("bg1.png");
		Image img= icon.getImage();
		Image changeImg = img.getScaledInstance(300, 300, img.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel imgLabel = new JLabel(changeIcon);
		
		imgLabel.setSize(300,300);
		imgLabel.setLocation(30,30);
		c.add(imgLabel);
		
		
		ImageIcon icon2 = new ImageIcon("bg2.png");
		Image img2= icon2.getImage();
		Image changeImg2 = img2.getScaledInstance(300, 300, img2.SCALE_SMOOTH);
		ImageIcon changeIcon2 = new ImageIcon(changeImg2);
		JLabel imgLabel2 = new JLabel(changeIcon2);
		
		imgLabel2.setSize(300,300);
		imgLabel2.setLocation(360,30);
		c.add(imgLabel2);
		
		ButtonGroup g = new ButtonGroup();
		
		
		
		b1.setSize(20,20);
		b2.setSize(20,20);
		b1.setLocation(160,340);
		b2.setLocation(500,340);
		
		b2.setBackground(Color.GRAY);
		b1.setBackground(Color.GRAY);
		
		MyItemListener listener = new MyItemListener();
		
		b1.addItemListener(listener);
		b2.addItemListener(listener);
		
		g.add(b1);
		g.add(b2);
		
		c.add(b1);
		c.add(b2);
		
		//b1.addItemListener(new MuItemListener());
		
		ImageIcon icon3 = new ImageIcon("button.png");
		Image img3= icon3.getImage();
		Image changeImg3 = img3.getScaledInstance(100, 100, img3.SCALE_SMOOTH);
		ImageIcon changeIcon3 = new ImageIcon(changeImg3);
		//JLabel imgLabel3 = new JLabel(changeIcon3);
		
		JButton cb= new JButton("",changeIcon3);
		
		cb.setSize(100,100);
		cb.setLocation(300,420);
		
		cb.setBackground(Color.GRAY);
		
		c.add(cb);
		
		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b= (JButton)e.getSource();
				if(my==1) {
					JavaGameClientView view = new JavaGameClientView(username, ip_addr, port_no);
					setVisible(false);
				}
				else if(my==2) {
					
					JavaGameClientView2 view2 = new JavaGameClientView2(username, ip_addr, port_no);
					setVisible(false);
					
				}
			}
		});
		
		/*
		ImageIcon icon5 = new ImageIcon("g8.jpg");
		Image img5= icon5.getImage();
		Image changeImg5 = img5.getScaledInstance(700, 600, img5.SCALE_SMOOTH);
		ImageIcon changeIcon5 = new ImageIcon(changeImg5);
		JLabel imgLabel5 = new JLabel(changeIcon5);
		
		imgLabel5.setSize(700,600);
		imgLabel5.setLocation(0,0);
		c.add(imgLabel5);
		
		*/
		c.setBackground(Color.GRAY);

		setBounds(0, 0, 714, 637);
	
		setVisible(true);
		
		

		
		
}

class MyItemListener implements ItemListener{
	
	public void itemStateChanged(ItemEvent e) {
		if(b1.isSelected()) {
			
			my=1;
		
		}
		else if(b2.isSelected()){
			
			my=2;
			
		}
			
	}
}



	//public static void main(String [] args) {
		//new JavaGameClientRoom();
	//}

}
