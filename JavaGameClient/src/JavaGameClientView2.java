
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JList;
import java.awt.Canvas;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



//import MainFrame.GamePanel;

//import MainFrame.GamePanel;

//import MainFrame.GamePanel;


import javax.swing.JComboBox;

public class JavaGameClientView2 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	// private JTextArea textArea;
	private JTextPane textArea;

	private Frame frame = new JFrame();
	private FileDialog fd;
	private JButton imgBtn;

	public GamePanel panel;
	public GamePanel mypanel;
	
	private JLabel lblMouseEvent;
	private Graphics gc;
	private int pen_size = 2; // minimum 2
	
	private String[] brush = {"circle","line","rectangle"};
	private String[] color= {"pink","green","blue"};
	
	//private Vector<Point> vStart = new Vector<Point>();
	//private Vector<Point> vEnd = new Vector<Point>();
	private Vector<Color> myc = new Vector<Color>();
	private int pp,my=0;
	private Point s,d;
	private int erase;
	
	public final static int UP_PRESSED		=0x001;
	public final static int DOWN_PRESSED	=0x002;
	public final static int LEFT_PRESSED	=0x004;
	public final static int RIGHT_PRESSED	=0x008;
	public final static int JUMP_PRESSED	=0x010;
	
	private static final int FRAME_WIDTH = 750;
	private static final int FRAME_HEIGHT = 650;
	
	int keybuff=0;
	int mydegree;
	
	public int mycheck;
	public String mc;
	
	
	public int b1=1,b2=1, b3=1, b4=1, b5=1;
	public int r1=1,r2=1, r3=1, r4=1, r5=1;
	//private JComboBox<String> comboBox_1 = new JComboBox<String>(color);
	
	public int mytime;
	
	public int ms=0, mm=1, mss=0;
	public int stop=0;
	public int msuccess=0;
	
	public int tr=5, tb=5;
	
	/**
	 * Create the frame.
	 * @throws BadLocationException 
	 */
	public JavaGameClientView2(String username, String ip_addr, String port_no)  {
		
		
		contentPane = new JPanel();
		
		
		setTitle("Graphic Game Test");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//setBounds(0, 0, 714, 637);
		//setBounds(0, 0, 600, 600);
		setBounds(0, 0, 900, 637);

		//setResizable(false);

		contentPane = new JPanel();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(700, 0, 186, 500);
		contentPane.add(scrollPane);
		
		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);
		
		txtInput = new JTextField();
		txtInput.setBounds(700, 509, 186, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.setFont(new Font("굴림", Font.PLAIN, 14));
		btnSend.setBounds(761, 559, 69, 40);
		contentPane.add(btnSend);
		

		panel = new GamePanel();
		
		mypanel=panel;

		
		

		panel.setBounds(0, 0, 700, 600);
		
		//panel.requestFocus();
		contentPane.add(panel);
		
		panel.requestFocus();

		setVisible(true);

		UserName=username;

		
	
		

		

		//프레임에 키보드 입력에 반응하는 keyListner 등록

		panel.addKeyListener(new KeyListener() {			
			

			@Override

			public void keyTyped(KeyEvent e) {

				// TODO Auto-generated method stub

				

			}			

			@Override

			public void keyReleased(KeyEvent e) {

				//눌러진 키가 무엇인지 알아내기 

				int keyCode = e.getKeyCode();

				SendMoveReleasedEvent(keyCode);
				
				/*
				switch(keyCode){
				case KeyEvent.VK_SPACE:
					keybuff&=~FIRE_PRESSED;
					panel.dx = 0;
					panel.dy = 0;
					
					break;
				case KeyEvent.VK_LEFT:
					keybuff&=~LEFT_PRESSED;//멀티키의 떼기 처리
					panel.dx = 0;
					
					break;
				case KeyEvent.VK_UP:
					keybuff&=~UP_PRESSED;
					panel.dy = 0;
					
					break;
				case KeyEvent.VK_RIGHT:
					keybuff&=~RIGHT_PRESSED;
					panel.dx = 0;
					
					break;
				case KeyEvent.VK_DOWN:
					keybuff&=~DOWN_PRESSED;
					panel.dy = 0;
					
					break;
				}*/
				
				
				
				//방향키 4개 구분

			}			

			@Override

			public void keyPressed(KeyEvent e) {

				//눌러진 키가 무엇인지 알아내기 

				int keyCode = e.getKeyCode();

				switch(keyCode){
				case KeyEvent.VK_SPACE:
					keybuff|=JUMP_PRESSED;
					SendMoveEvent(keybuff);
					break;
				case KeyEvent.VK_LEFT:
					keybuff|=LEFT_PRESSED;//멀티키의 누르기 처리
					SendMoveEvent(keybuff);
					break;
				case KeyEvent.VK_UP:
					keybuff|=UP_PRESSED;
					SendMoveEvent(keybuff);
					break;
				case KeyEvent.VK_RIGHT:
					keybuff|=RIGHT_PRESSED;
					SendMoveEvent(keybuff);
					break;
				case KeyEvent.VK_DOWN:
					keybuff|=DOWN_PRESSED;
					SendMoveEvent(keybuff);
					break;
				
				/*case KeyEvent.VK_1:
					System.out.println("이펙트 테스트");
					Effect effect=new Effect(0, RAND(30,gScreenWidth-30)*100,RAND(30,gScreenHeight-30)*100, 0);
					effects.add(effect);
					break;*/
				
				}

				//keyprocess(keybuff);
				
				
				//방향키 4개 구분				

			}
			
			public void keyprocess(int k){
				
				
				
						switch(k){
						
						case JUMP_PRESSED:
							new Thread(new Runnable() {
								
								
								public void run() {
									int i=2;
									while(i!=0) {
										if(i==2) {
										panel.dy=-100;
										panel.move();
										panel.repaint();}
										else if(i==1){
											panel.dy=100;
											panel.move();
											panel.repaint();
										}
										i--;
										
										try {
											Thread.sleep(50);
										}catch(InterruptedException e) {
											e.printStackTrace();
										}
									} 
								
								}
								
							}).start();
							
							break;
						
						case UP_PRESSED:
							new Thread(new Runnable() {
								
								
								public void run() {
									int i=2;
									while(i!=0) {
										if(i==2) {
										panel.dy=-50;
										panel.move();
										panel.repaint();}
										else if(i==1){
											panel.dy=50;
											panel.move();
											panel.repaint();
										}
										i--;
										
										try {
											Thread.sleep(50);
										}catch(InterruptedException e) {
											e.printStackTrace();
										}
									} 
								
								}
								
							}).start();
							break;
						
						case LEFT_PRESSED:
							panel.dx = -8; //원랜 getsetter 만들어야함
							panel.move();
							panel.repaint();
							break;
							
						case LEFT_PRESSED|JUMP_PRESSED:
								new Thread(new Runnable() {
								
								
								public void run() {
									int i=2;
									while(i!=0) {
										if(i==2) {
										panel.dy=-50;
										panel.dx=-30;
										panel.move();
										panel.repaint();}
										else if(i==1){
											panel.dy=50;
											panel.dx=-30;
											panel.move();
											panel.repaint();
										}
										i--;
										
										try {
											Thread.sleep(50);
										}catch(InterruptedException e) {
											e.printStackTrace();
										}
									} 
								
								}
								
							}).start();
								
							break;
							
						case LEFT_PRESSED|UP_PRESSED:
							new Thread(new Runnable() {
							
							
							public void run() {
								int i=2;
								while(i!=0) {
									if(i==2) {
									panel.dy=-100;
									panel.dx=-30;
									panel.move();
									panel.repaint();}
									else if(i==1){
										panel.dy=100;
										panel.dx=-30;
										panel.move();
										panel.repaint();
									}
									i--;
									
									try {
										Thread.sleep(50);
									}catch(InterruptedException e) {
										e.printStackTrace();
									}
								} 
							
							}
							
						}).start();
							
						break;
						
						case RIGHT_PRESSED:
							panel.dx = 8; //원랜 getsetter 만들어야함
							panel.move();
							panel.repaint();
							break;
						case RIGHT_PRESSED|JUMP_PRESSED:
								new Thread(new Runnable() {
								
								
								public void run() {
									int i=2;
									while(i!=0) {
										if(i==2) {
										panel.dy=-50;
										panel.dx=30;
										panel.move();
										panel.repaint();}
										else if(i==1){
											panel.dy=50;
											panel.dx=30;
											panel.move();
											panel.repaint();
										}
										i--;
										
										try {
											Thread.sleep(50);
										}catch(InterruptedException e) {
											e.printStackTrace();
										}
									} 
								
								}
								
							}).start();
							break;
							
						case RIGHT_PRESSED|UP_PRESSED:
							new Thread(new Runnable() {
							
							
							public void run() {
								int i=2;
								while(i!=0) {
									if(i==2) {
									panel.dy=-100;
									panel.dx=30;
									panel.move();
									panel.repaint();}
									else if(i==1){
										panel.dy=100;
										panel.dx=30;
										panel.move();
										panel.repaint();
									}
									i--;
									
									try {
										Thread.sleep(50);
									}catch(InterruptedException e) {
										e.printStackTrace();
									}
								} 
							
							}
							
						}).start();
						break;
						
						
						case DOWN_PRESSED:
							panel.dy = 8; //원랜 getsetter 만들어야함
							panel.move();
							panel.repaint();
							break;
						
						
						}
					
					
				
			
				
			}

		});

	
		
		
		
		
		/*comboBox_1.setBounds(386, 600, 80, 23);
		
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb =(JComboBox<String>)e.getSource();
				
				int index = cb.getSelectedIndex();
				if(index==0) {
				gc.setColor(Color.PINK);
				
				}
				else if(index==1) {
					gc.setColor(Color.GREEN);
				}
				else if(index==2){
					gc.setColor(Color.BLUE);
				}
				
				
			}
		});
		
		contentPane.add(comboBox_1);*/

		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
//			is = socket.getInputStream();
//			dis = new DataInputStream(is);
//			os = socket.getOutputStream();
//			dos = new DataOutputStream(os);

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			// SendMessage("/login " + UserName);
			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();
			
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			//txtInput.requestFocus();
			/*
			ImageSendAction action2 = new ImageSendAction();
			imgBtn.addActionListener(action2);
			MyMouseEvent mouse = new MyMouseEvent();
			panel.addMouseMotionListener(mouse);
			panel.addMouseListener(mouse);
			MyMouseWheelEvent wheel = new MyMouseWheelEvent();
			panel.addMouseWheelListener(wheel);
			*/
			panel.requestFocus();
			

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}

	}

	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {

					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s]\n%s", cm.UserName, cm.data);
						mc=cm.UserName;
					} else
						continue;
					switch (cm.code) {
					case "200": // chat message
						if (cm.UserName.equals(UserName))
							AppendTextR(msg); // 내 메세지는 우측에
						else
							AppendText(msg);
						break;
					case "300": 
						keyprocess(cm.kb);				
						break;
					case "500":
						keyreleased(cm.kc);
						break;
					case "700":
						BlueWall();
						break;
					case "701":
						RedWall();
						break;
					case "803":
						Success();
						break;
					case "900":
						TimerThread th= new TimerThread();
						th.start();
						break;
						
					
					}
				} catch (IOException e) {
					//AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}
	
	public synchronized void keyreleased(int keyCode) {

		//눌러진 키가 무엇인지 알아내기 

		

		switch(keyCode){
		case KeyEvent.VK_SPACE:
			keybuff&=~JUMP_PRESSED;
			panel.dx = 0;
			panel.dy = 0;
			break;
		case KeyEvent.VK_LEFT:
			keybuff&=~LEFT_PRESSED;//멀티키의 떼기 처리
			panel.dx = 0;
			break;
		case KeyEvent.VK_UP:
			keybuff&=~UP_PRESSED;
			panel.dx = 0;
			panel.dy = 0;
			break;
		case KeyEvent.VK_RIGHT:
			keybuff&=~RIGHT_PRESSED;
			panel.dx = 0;
			break;
		case KeyEvent.VK_DOWN:
			keybuff&=~DOWN_PRESSED;
			panel.dy = 0;
			break;
		}
		
		
		//방향키 4개 구분

	}
	
	
	public synchronized void keyprocess(int k){
		
		
		
		switch(k){
		
		case JUMP_PRESSED:
			new Thread(new Runnable() {
				
				
				public synchronized void run() {
					int i=3;
					while(i!=0) {
						if(i==3) {
						panel.dy=-100;
						panel.move();
						panel.repaint();
						}
						else if(i==2) {
							panel.dy=50;
							panel.move();
							panel.repaint();
							}
						else if(i==1) {
							panel.dy=50;
							panel.move();
							panel.repaint();
							}
						
						i--;
						
						try {
							Thread.sleep(50);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					} 
				
				}
				
			}).start();
			
			break;
		
		case UP_PRESSED:
			new Thread(new Runnable() {
				
				
				public synchronized void run() {
					int i=2;
					while(i!=0) {
						if(i==2) {
						panel.dy=-50;
						panel.move();
						panel.repaint();}
						else if(i==1){
							panel.dy=50;
							panel.move();
							panel.repaint();
						}
						i--;
						
						try {
							Thread.sleep(50);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					} 
				
				}
				
			}).start();
			break;
		
		case LEFT_PRESSED:
			panel.dx = -8; //원랜 getsetter 만들어야함
			panel.move();
			panel.repaint();
			break;
			
		case LEFT_PRESSED|JUMP_PRESSED:
				new Thread(new Runnable() {
				
				
				public synchronized void run() {
					int i=3;
					while(i!=0) {
						if(i==3) {
						panel.dy=-100;
						panel.dx=-30;
						panel.move();
						panel.repaint();
						}
						else if(i==2) {
							panel.dy=50;
							panel.dx=-15;
							panel.move();
							panel.repaint();
							}
						else if(i==1){
							panel.dy=50;
							panel.dx=-15;
							panel.move();
							panel.repaint();
						}
						i--;
						
						try {
							Thread.sleep(50);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					} 
				
				}
				
			}).start();
				
			break;
			
		case LEFT_PRESSED|UP_PRESSED:
			new Thread(new Runnable() {
			
			
			public synchronized void run() {
				int i=2;
				while(i!=0) {
					if(i==2) {
					panel.dy=-50;
					panel.dx=-30;
					panel.move();
					panel.repaint();}
					else if(i==1){
						panel.dy=50;
						panel.dx=-30;
						panel.move();
						panel.repaint();
					}
					i--;
					
					try {
						Thread.sleep(50);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				} 
			
			}
			
		}).start();
			
		break;
		
		case RIGHT_PRESSED:
			panel.dx = 8; //원랜 getsetter 만들어야함
			panel.move();
			panel.repaint();
			break;
		case RIGHT_PRESSED|JUMP_PRESSED:
				new Thread(new Runnable() {
				
				
				public synchronized void run() {
					int i=3;
					while(i!=0) {
						if(i==3) {
						panel.dy=-100;
						panel.dx=30;
						panel.move();
						panel.repaint();
						}
						else if(i==2) {
							panel.dy=50;
							panel.dx=15;
							panel.move();
							panel.repaint();
							}
						else if(i==1){
							panel.dy=50;
							panel.dx=15;
							panel.move();
							panel.repaint();
						}
						i--;
						
						try {
							Thread.sleep(50);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					} 
				
				}
				
			}).start();
			break;
			
		case RIGHT_PRESSED|UP_PRESSED:
			new Thread(new Runnable() {
			
			
			public synchronized void run() {
				int i=2;
				while(i!=0) {
					if(i==2) {
					panel.dy=-50;
					panel.dx=30;
					panel.move();
					panel.repaint();}
					else if(i==1){
						panel.dy=50;
						panel.dx=30;
						panel.move();
						panel.repaint();
					}
					i--;
					
					try {
						Thread.sleep(50);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				} 
			
			}
			
		}).start();
		break;
		
		
		case DOWN_PRESSED:
			panel.dy = 8; //원랜 getsetter 만들어야함
			panel.move();
			panel.repaint();
			break;
		
		
		}
	
	




	
	



}
	
	public void RedWall() {
		panel.x=100;
		panel.y=550;
		panel.repaint();
	}
	
	public void BlueWall() {
		panel.x2=100;
		panel.y2=450;
		panel.repaint();
	}
	
	public void Success() {
		msuccess=1;
		panel.repaint();
	}
	

	// Mouse Event 수신 처리
	public void DoMouseEvent(ChatMsg cm) {
		Color c;
		Point s,d;
		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다.
			return;
		c = cm.color; // 다른 사람 것은 Red
		gc.setColor(c);
		
		s=cm.startP;
		d=cm.endP;
		
		if(cm.erase==1) {
			gc.setColor(Color.WHITE);
			
			gc.fillOval(cm.mouse_e.getX() - 5, cm.mouse_e.getY() - 5, 10, 10);
			gc.setColor(c);
		}
		else {
			gc.drawLine((int)s.getX(),(int)s.getY(),(int)d.getX(),(int)d.getY());
		}
		//
	}

	public synchronized void SendMoveEvent(int kb) {
		ChatMsg cm = new ChatMsg(UserName, "300", "MOVE");
		cm.kb=kb;

		SendObject(cm);
	}
	public synchronized void SendMoveReleasedEvent(int kc) {
		ChatMsg cm = new ChatMsg(UserName, "500", "MOVER");
		cm.kc=kc;

		SendObject(cm);
	}
	public synchronized void SendStop() {
		ChatMsg cm = new ChatMsg(UserName, "400", "STOP");
		
		SendObject(cm);
		
	}
	public synchronized void SendTotalBlue() {
		ChatMsg cm = new ChatMsg(UserName, "600", "totalBlue");
		
		SendObject(cm);
		
	}
	public synchronized void SendTotalRed() {
		ChatMsg cm = new ChatMsg(UserName, "601", "totalRed");
		
		SendObject(cm);
		
	}
	
	public synchronized void SendBlueWall() {
		ChatMsg cm = new ChatMsg(UserName, "700", "BlueWall");
		
		SendObject(cm);
		
	}
	
	public synchronized void SendRedWall() {
		ChatMsg cm = new ChatMsg(UserName, "701", "RedWall");
		
		SendObject(cm);
		
	}
	
	public synchronized void SendBlueDoor() {
		ChatMsg cm = new ChatMsg(UserName, "800", "BlueDoor");
		
		SendObject(cm);
		
	}
	
	public synchronized void SendRedDoor() {
		ChatMsg cm = new ChatMsg(UserName, "801", "RedDoor");
		
		SendObject(cm);
		
	}
	public synchronized void SendStart() {
		ChatMsg cm = new ChatMsg(UserName, "901", "start");
		
		SendObject(cm);
		
	}
	
	
	
	
	
	
	
	

	class MyMouseWheelEvent implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
				if (pen_size < 20)
					pen_size++;
			} else {
				if (pen_size > 2)
					pen_size--;
			}
			lblMouseEvent.setText("mouseWheelMoved Rotation=" + e.getWheelRotation() 
				+ " pen_size = " + pen_size + " " + e.getX() + "," + e.getY());

		}
		
	}
	// Mouse Event Handler
	
	class MyMouseEvent implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseDragged " + e.getX() + "," + e.getY());// 좌표출력가능
			//Color c = new Color(0,0,255);
			//gc.setColor(c);
			//if(e.getButton() == MouseEvent.BUTTON1) {
			Color myColor = gc.getColor();
			//int mysize=pen_size;
			if(my==1) {
				gc.setColor(Color.WHITE);
				//pen_size=10;
				gc.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
			
			gc.setColor(myColor);
			//pen_size=mysize;
			//gc.drawLine((int))
			erase=1;
			//SendMouseEvent(e);
			erase=0;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseMoved " + e.getX() + "," + e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseClicked " + e.getX() + "," + e.getY());
			//Color c = new Color(0,0,255);
			//gc.setColor(c);
			Color myColor = gc.getColor();
			//int mysize=pen_size;
			if(e.getButton() == MouseEvent.BUTTON3) {
				gc.setColor(Color.WHITE);
				my=1;
				//pen_size=10;
				gc.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
			
			gc.setColor(myColor);
			//pen_size=mysize;
			erase=1;
			//SendMouseEvent(e);
			erase=0;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseEntered " + e.getX() + "," + e.getY());
			// panel.setBackground(Color.YELLOW);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseExited " + e.getX() + "," + e.getY());
			// panel.setBackground(Color.CYAN);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mousePressed " + e.getX() + "," + e.getY());

			
			if(e.getButton() == MouseEvent.BUTTON1) {
				 s= e.getPoint();
				//vStart.add(startP);
				my=0;
				//myc.add(gc.getColor());
				//SendMouseEvent(e);
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			lblMouseEvent.setText(e.getButton() + " mouseReleased " + e.getX() + "," + e.getY());
			// 드래그중 멈출시 보임
			
			d= e.getPoint();
			//vEnd.add(endP);
			
			//for(int i=0; i<vStart.size();i++) {
				
				//Point s = vStart.elementAt(0);
				//Point d = vEnd.elementAt(0);
				
				
				if(e.getButton() == MouseEvent.BUTTON1) {
				
					//gc.setColor(myc.elementAt(i));
					gc.drawLine((int)s.getX(),(int)s.getY(),(int)d.getX(),(int)d.getY());
					my=1;
					
					//SendMouseEvent(e);
				}
			//}
			//vStart.removeAllElements();
			//vEnd.removeAllElements();

		}
	}

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				//txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				panel.requestFocus();
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == imgBtn) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				// System.out.println(fd.getDirectory() + fd.getFile());
				if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					obcm.img = img;
					SendObject(obcm);
				}
			}
		}
	}

	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");

	public void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}

	// 화면에 출력
	
	public void AppendText(String msg) {
		// textArea.append(msg + "\n");
		// AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		//textArea.setCaretPosition(len);
		//textArea.replaceSelection(msg + "\n");
		
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), msg+"\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 화면 우측에 출력
	public void AppendTextR(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.	
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLUE);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		try {
			doc.insertString(doc.getLength(),msg+"\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		Image new_img;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else {
			textArea.insertIcon(ori_icon);
			new_img = ori_img;
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		// ImageViewAction viewaction = new ImageViewAction();
		// new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
		gc.drawImage(ori_img, 0, 0, panel.getWidth(), panel.getHeight(), this);
	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			//AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			//AppendText("SendObject Error");
		}
	}
	
	
	class GamePanel extends JPanel { //게임화면 그려낼 Panel

		

		//화면에 보여질 이미지 객체 참조변수 - 멤버변수

		Image imgBack, imgPlayer, imgEnemy;
		Image imgPlayer2, rdoor, bdoor, blue, red, rwall, bwall;
		Image wall;
		
		Image t0, t1, t2, t3, t4, t5, t6, t7, t8, t9;
		
		Image gameover;
		Image success;

		int width, height;//패널 사이즈 가져오기

		int x, y, w, h;//xy : 플레이어의 중심 좌표 / wh : 이미지 절반폭;
		
		int x2,y2, w2,h2;

		int dx = 0, dy = 0;//플레이어 이미지의 이동속도, 이동방향

		//적군 객체 참조변수, 여러마리일수있으므로 ArrayList(유동적 배열) 활용

		//ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
		ArrayList<Wall> walls = new ArrayList<Wall>();

		

		int score; //점수

		

		

		public GamePanel() {

			//GUI 관련 프로그램의 편의를 위해 만들어진 도구상자(Toolkit) 객체 

			Toolkit toolkit = Toolkit.getDefaultToolkit();

			//생성자에서 사이즈를 구하려하면 무조건 0임, 아직 패널이 안붙어서 사이즈를 모르기때문

			//width = getWidth(); 

			//height = getHeight();

			

			imgBack = toolkit.getImage("black.png");//배경 이미지

			imgPlayer = toolkit.getImage("water.png");//플레이어 이미지 객체
			imgPlayer2 = toolkit.getImage("fire.png");

 
			imgEnemy = toolkit.getImage("zom.png");//적군 이미지 객체 
			
			wall = toolkit.getImage("wall2.png");
			
			blue = toolkit.getImage("blue.png");
			red = toolkit.getImage("red.png");
			rwall = toolkit.getImage("rwall2.png");
			bwall = toolkit.getImage("bwall2.png");
			rdoor = toolkit.getImage("rdoor.png");
			bdoor = toolkit.getImage("bdoor.png");
			
			
			t0 = toolkit.getImage("0.png");
			t1 = toolkit.getImage("1.png");
			t2 = toolkit.getImage("2.png");
			t3 = toolkit.getImage("3.png");
			t4 = toolkit.getImage("4.png");
			t5 = toolkit.getImage("5.png");
			t6 = toolkit.getImage("6.png");
			t7 = toolkit.getImage("7.png");
			t8 = toolkit.getImage("8.png");
			t9 = toolkit.getImage("9.png");
			
			
			gameover= toolkit.getImage("gameover.png");
			success= toolkit.getImage("msuccess.png");

		}//생성자		



		//화면에 보여질때  보여질 내용물 작업을 수행하는 메소드 : 자동 실행(콜백 메소드)

		@Override

			protected synchronized void paintComponent(Graphics g) {

			//화면에 보여질 작업 코딩

			if( width == 0 || height == 0) { //처음 호출시엔 느려서 안보이다 이후 보임

				width = getWidth();

				height = getHeight();
				
				

				//리사이징

				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);

				imgPlayer = imgPlayer.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				imgPlayer2 = imgPlayer2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				
				t0 = t0.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t1 = t1.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t2 = t2.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t3 = t3.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t4 = t4.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t5 = t5.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t6 = t6.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t7 = t7.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t8 = t8.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				t9 = t9.getScaledInstance(25, 50, Image.SCALE_SMOOTH);
				
				gameover = gameover.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
				
				success = success.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
				//x = width/2;//플레이어의 좌표 계산

				//y = height - 100;
				
				
				x = width/2 -250;    y = height -50;  
				
				x2= width/2-250;  y2=height -150;
				
				w2= 50; h2= 50;
				
				

				//w = 64;

				//h = 64;
				
				w = 50; h = 50;
				
				
				wall = wall.getScaledInstance(50, 51, Image.SCALE_SMOOTH);
				rwall = rwall.getScaledInstance(50, 51, Image.SCALE_SMOOTH);
				bwall = bwall.getScaledInstance(50, 51, Image.SCALE_SMOOTH);
				
				
				//walls.add(new Wall(wall,0,0));
				//walls.add(new Wall(wall, 50, 0));
				
				//walls.add(new Wall(wall, 500, 300));
				//walls.add(new Wall(wall, 450, 250));
				
				/*
				walls.add(new Wall(wall, 0, 0));
				walls.add(new Wall(wall, 50, 0));
				walls.add(new Wall(wall, 100, 0));
				walls.add(new Wall(wall, 150, 0));
				walls.add(new Wall(wall, 200, 0));
				walls.add(new Wall(wall, 250, 0));
				walls.add(new Wall(wall, 300, 0));
				walls.add(new Wall(wall, 350, 0));
				walls.add(new Wall(wall, 400, 0));
				walls.add(new Wall(wall, 450, 0));
				walls.add(new Wall(wall, 500, 0));
				walls.add(new Wall(wall, 550, 0));
				walls.add(new Wall(wall, 600, 0));
				walls.add(new Wall(wall, 650, 0));
				
				walls.add(new Wall(wall, 0,49));
				walls.add(new Wall(wall, 0,98));
				walls.add(new Wall(wall, 0,147));
				walls.add(new Wall(wall, 0,196));
				walls.add(new Wall(wall, 0,245));
				walls.add(new Wall(wall, 0,294));
				walls.add(new Wall(wall, 0,343));
				walls.add(new Wall(wall, 0,392));
				walls.add(new Wall(wall, 0,441));
				walls.add(new Wall(wall, 0,490));
				walls.add(new Wall(wall, 500, 539));
				
				walls.add(new Wall(wall, 50, 539));
				walls.add(new Wall(wall, 100,539));
				walls.add(new Wall(wall, 150,539));
				walls.add(new Wall(wall, 200, 539));
				walls.add(new Wall(wall, 250,539));
				walls.add(new Wall(wall, 300,539));
				walls.add(new Wall(wall, 450,539));
				walls.add(new Wall(wall, 500,539));
				walls.add(new Wall(wall,550, 539));
				walls.add(new Wall(wall,600, 539));
				walls.add(new Wall(wall, 650, 539));
				
				walls.add(new Wall(wall, 650,49));
				walls.add(new Wall(wall, 650,98));
				walls.add(new Wall(wall, 650,147));
				walls.add(new Wall(wall, 650,196));
				walls.add(new Wall(wall,650,245));
				walls.add(new Wall(wall, 650,294));
				walls.add(new Wall(wall, 650,343));
				walls.add(new Wall(wall, 650,392));
				walls.add(new Wall(wall,650,441));
				walls.add(new Wall(wall,650,490));
				walls.add(new Wall(wall, 650, 539));
				
				*/

				walls.add(new Wall(wall, 600,500));
				walls.add(new Wall(wall, 50,450)); 
				walls.add(new Wall(wall, 100,450));
				walls.add(new Wall(wall, 150,450));//bwall
				walls.add(new Wall(wall, 200,450));
				//walls.add(new Wall(wall, 550,400));
				//walls.add(new Wall(wall, 500,400));
				
				
				walls.add(new Wall(wall, 600,500));
				
				walls.add(new Wall(wall, 500,450));
				walls.add(new Wall(wall, 450,450));
				
				walls.add(new Wall(wall, 450,400)); 
				walls.add(new Wall(wall, 400,400)); 
				
				
				
				walls.add(new Wall(wall, 350,400));
				walls.add(new Wall(wall, 350,350));
				walls.add(new Wall(wall, 300,350));
				walls.add(new Wall(wall, 300,300));
				walls.add(new Wall(wall, 250,300));
				walls.add(new Wall(wall, 200,300));
				walls.add(new Wall(wall, 150,300));
				walls.add(new Wall(wall, 100,300));
				walls.add(new Wall(wall, 50,300));//rwall
				walls.add(new Wall(wall, 50,200));
			
				walls.add(new Wall(wall, 200,200));
				walls.add(new Wall(wall, 250,200));
				walls.add(new Wall(wall, 250,150));
				walls.add(new Wall(wall, 300,150));
				walls.add(new Wall(wall, 350,150));
				walls.add(new Wall(wall, 350,100));
				walls.add(new Wall(wall, 400,100)); 
				walls.add(new Wall(wall, 450,100));
				walls.add(new Wall(wall, 500,100));
				walls.add(new Wall(wall, 550,100));
				walls.add(new Wall(wall, 600,100));
				 
	
				SendStart();
				
				
			}			
			

			//이곳에 화가객체가 있으므로 그림 그리는 작업은 무조건 여기서			
   
			g.drawImage(imgBack, 0, 0, this);//배경 그리기			

			//for(Enemy t : enemies) {

				//g.drawImage(t.img, t.x-t.w, t.y-t.h, this);

			//}
			
			
			
			
			
			
			
			//g.drawImage(wall, 450, 250,this);
			
			
			g.drawImage(wall, 0, 0,this);
			g.drawImage(wall, 50, 0,this);
			g.drawImage(wall, 100, 0,this);
			g.drawImage(wall, 150, 0,this);
			g.drawImage(wall, 200, 0,this);
			g.drawImage(wall, 250, 0,this);
			
			
			
			
			g.drawImage(t0, 300, 0,this);
			
			if(mm==1) {
			g.drawImage(t1, 325, 0,this);
			}
			if(mm==0) {
				g.drawImage(t0, 325, 0,this);
				}
			
			
			
			if(ms==6) {
				g.drawImage(t6, 350, 0,this);
			}
			if(ms==5) {
				g.drawImage(t5, 350, 0,this);
				}
			if(ms==4) {
				g.drawImage(t4, 350, 0,this);
				}
			if(ms==3) {
				g.drawImage(t3, 350, 0,this);
				}
			if(ms==2) {
				g.drawImage(t2, 350, 0,this);
				}
			if(ms==1) {
				g.drawImage(t1, 350, 0,this);
				}
			if(ms==0) {
				g.drawImage(t0, 350, 0,this);
				}
			
			
			if(mss==9) {
				g.drawImage(t9, 375, 0,this);
			}
			if(mss==8) {
				g.drawImage(t8, 375, 0,this);
			}
			if(mss==7) {
				g.drawImage(t7, 375, 0,this);
			}
			if(mss==6) {
				g.drawImage(t6, 375, 0,this);
			}
			if(mss==5) {
				g.drawImage(t5, 375, 0,this);
			}
			if(mss==4) {
				g.drawImage(t4, 375, 0,this);
			}
			if(mss==3) {
				g.drawImage(t3, 375, 0,this);
			}
			if(mss==2) {
				g.drawImage(t2, 375, 0,this);
			}
			if(mss==1) {
				g.drawImage(t1, 375, 0,this);
			}
			if(mss==0) {
				g.drawImage(t0, 375, 0,this);
			}
			
			
			
			
			
			g.drawImage(wall, 400, 0,this);
			g.drawImage(wall, 450, 0,this);
			g.drawImage(wall, 500, 0,this);
			g.drawImage(wall, 550, 0,this);
			g.drawImage(wall, 600, 0,this);
			g.drawImage(wall, 650, 0,this);
			
			
			
			g.drawImage(wall, 0,50,this);
			g.drawImage(wall, 0,100, this);
			
			g.drawImage(wall, 0,150, this);
			g.drawImage(wall, 0,200, this);
			g.drawImage(wall, 0,250, this);
			g.drawImage(wall, 0,300, this);
			g.drawImage(wall, 0,350, this);
			g.drawImage(wall, 0,400, this);
			g.drawImage(wall, 0,450, this);
			g.drawImage(wall, 0,500, this);
			g.drawImage(wall, 0,550, this);
			
			
			
			
			
			
			g.drawImage(wall, 50, 550,this);
			g.drawImage(wall, 100,550, this);
			g.drawImage(wall, 150,550, this);
			g.drawImage(wall, 200, 550,this);
			g.drawImage(wall, 250,550, this);
			g.drawImage(wall, 300,550, this);
			g.drawImage(rwall, 350,550, this);
			g.drawImage(bwall, 400,550, this);
			g.drawImage(wall, 450,550, this);
			g.drawImage(wall, 500,550, this);
			g.drawImage(wall, 550, 550,this);
			g.drawImage(wall, 600, 550,this);
			g.drawImage(wall, 650, 550,this);
			
			
			g.drawImage(wall, 650,50,this);
			g.drawImage(wall, 650,100, this);
			g.drawImage(wall, 650,150, this);
			g.drawImage(wall, 650,200, this);
			g.drawImage(wall, 650,250, this);
			g.drawImage(wall, 650,300, this);
			g.drawImage(wall, 650,350, this);
			g.drawImage(wall, 650,400, this);
			g.drawImage(wall, 650,450, this);
			g.drawImage(wall, 650,500, this);
			g.drawImage(wall, 650,550, this);
			
			
			g.drawImage(wall, 50,450, this); g.drawImage(wall, 100,450, this); g.drawImage(bwall, 150,450, this); g.drawImage(wall, 200,450, this);
			//g.drawImage(wall, 600,500, this);
			//g.drawImage(wall, 550,400, this);
			
			//g.drawImage(wall, 500,400, this);
			g.drawImage(wall, 450,450, this); g.drawImage(wall, 500,450, this);
			g.drawImage(wall, 450,400, this); g.drawImage(wall, 400,400, this);
			g.drawImage(wall, 350,400, this); 
			g.drawImage(wall, 350,350, this); g.drawImage(wall, 300,350, this); 
			g.drawImage(wall, 300,300, this); g.drawImage(wall, 250,300, this); g.drawImage(wall, 200,300, this); g.drawImage(wall, 150,300, this);
			g.drawImage(wall, 100,300, this);g.drawImage(rwall, 50,300, this);
			
			g.drawImage(wall, 50,200, this); //g.drawImage(wall, 150,200, this);
			g.drawImage(wall, 200,200, this);
			//g.drawImage(wall, 200,150, this); 
			g.drawImage(wall, 250,200, this);
			g.drawImage(wall, 250,150, this); g.drawImage(wall, 300,150, this); g.drawImage(wall, 350,150, this);
			g.drawImage(wall, 350,100, this); g.drawImage(wall, 400,100, this); g.drawImage(wall, 450,100, this); g.drawImage(wall, 500,100, this);
			g.drawImage(wall, 550,100, this); g.drawImage(wall, 600,100, this);
			g.drawImage(wall, 600,500, this);
			
			 
			  
			g.drawImage(rdoor, 550,50, this); g.drawImage(bdoor, 600,50, this);
			 
			
			
			
			
			
			if(b5==1) {
				g.drawImage(blue, 500,500, this);
			}
			if(b4==1) {
				g.drawImage(blue, 150,500, this); 
			}
			if(b3==1) {
				g.drawImage(blue, 300,250, this); 
			}
			if(b2==1) {
				g.drawImage(blue, 50,150, this); 
			}
			if(b1==1) {
				g.drawImage(blue, 50,100, this);
			}
			
			
			if(r5==1) {
				g.drawImage(red, 200,400, this);
			}
			if(r4==1) {
				g.drawImage(red, 600,441, this);
			}
			if(r3==1) {
				g.drawImage(red, 50,250, this); 
			}
			if(r2==1) {
				g.drawImage(red, 200,150, this);
			}
			if(r1==1) {
				
				g.drawImage(red, 200,100, this);
			}
			
			
			
			
			

			//g.drawImage(imgPlayer2, 50, 400, this);//플레이어
			g.drawImage(imgPlayer2, x2-w2, y2-h2, this);//플레이어
			
				
			g.drawImage(imgPlayer, x- w, y - h, this);//플레이어
			//g.drawImage(imgPlayer, x - w-50, y - h, this);//플레이어
			//g.drawImage(imgPlayer, 100, 100, this);//플레이어\\
			
			if(stop==1) {
				g.drawImage(gameover,250,250,this );
			}
			
			if(msuccess==1) {
				g.drawImage(success,250,250,this );
			}
			
			//g.setFont(new Font(null, Font.BOLD, 20));//점수 표시하기

			//g.drawString("Score : " + score,10, 30);

			//여러장면 만들기 위해 일정시간마다 다시 그리기(re painting)

		}//paintComponent method                                       

		

		synchronized void move() { //플레이어 움직이기(좌표 변경)

			

			if(mc.equals("water")) {
				
				x += dx;
				y += dy;
				
				if(x>640&&x<=650&&y>90&&y<=100) {
					SendBlueDoor();
				}
				
				if(x>510&&x<540&&y<=550) {
					if(b5==1) {
					b5=0;
					--tb;
					}
				}
				if(x>160&&x<=200&&y>510&&y<=550) {
					if(b4==1) {
					b4=0;
					--tb;
					}
				}
				if(x>310&&x<340&&y<=300) {
					if(b3==1) {
					b3=0;
					--tb;
					}
				}
				if(x<=100&&y<=200) {
					if(b2==1) {
					b2=0;
					--tb;
					}
				}
				if(x<=100&&y<=150) {
					if(b1==1) {
					b1=0;
					--tb;
					}
				}
				if(x<=100&&y>260&&y==300) {
					SendRedWall();
				}
				if(x>=380&&x<=400&&y==550) {
					SendRedWall();
				}
				
				
				
				for(Wall t: walls) {
					
					int left = t.myx;
					int right = t.myx+50;
					int top = t.myy;
					int bottom = t.myy+51;
					
					if(dx>0) {
					if(x>left&&x<right) {
						if(y>top&&y<bottom) {
							x=left;
							
						} 
						//if(y>top+50&&y<bottom+50) {
							//x=left;
							
						//} 
					}
					if(x>=140&&x<=150&&y>=190&&y<=200) {
						y=300;
					}
					
					
					
					}
					
					if(dx<0) {
					if(x-50>left&&x-50<right) {
						if(y>top&&y<bottom) {
							x=right+50;
							
						} 
						//if(y>top+50&&y<bottom+50) {
						//	x=right+50;
							
						//} 
						
						
					}
					}
					
					
					if(dy>0) {
					if(y>top&&y<bottom) {
						if(x>left&&x<right) {
							y=top;
						}
						if(x>left+50&&x<right+50) {
							y=top;
						}
						
						
						
					}
					}
					
					if(dy<0) {
						if(y-50>top&&y-50<bottom) {
							if(x>left&&x<right) {
								y=bottom+50;
							}
							if(x>left+50&&x<right+50) {
								y=bottom+50;
							}
							
							
						}
						}
					
					
				}
			}
			
			else if(mc.equals("fire")) {

				x2+=dx;
				y2+=dy;
				
				
				if(x2>590&&x2<=600&&y2>90&&y2<=100) {
					SendRedDoor();
				}
					
				
				if(x2>210&&x2<=240&&y2>410&&y2<=450) {
					if(r5==1) {
					r5=0;
					--tr;
					}
				}
				if(x2>610&&x2<650&&y2>460&&y2<=500) {
					if(r4==1) {
					r4=0;
					--tr;
					}
				}
				if(x2<=100&&y2>260&&y2<=300) {
					if(r3==1) {
					r3=0;
					--tr;
					}
				}
				if(x2>210&&x2<=250&&y2>160&&y2<=200) {
					if(r2==1) {
					r2=0;
					--tr;
					}
				}
				if(x2>210&&x2<=250&&y2>110&&y2<=150) {
					if(r1==1) {
					r1=0;
					--tr;
					}
				}
				
				if(x2>=430&&x2<=450&&y2>540&&y2<=550) {
					SendBlueWall();
				}
				if(x2>=180&&x2<=200&&y2==450) {
					SendBlueWall();
				}
				
				
				for(Wall t: walls) {
					
					int left = t.myx;
					int right = t.myx+50;
					int top = t.myy;
					int bottom = t.myy+51;
					
					if(dx>0) {
					if(x2>left&&x2<right) {
						if(y2>top&&y2<bottom) {
							x2=left;
							
						} 
						//if(y2>top+50&&y2<bottom+50) {
						//	x2=left;
							
						//} 
						
						
					}
					
					if(x2>=290&&x2<=300&&y2>=440&&y2<=450) {
						y2=550;
					}
					
					}
					
					if(dx<0) {
					if(x2-50>left&&x2-50<right) {
						if(y2>top&&y2<bottom) {
							x2=right+50;
							
						} 
						//if(y2>top+50&&y2<bottom+50) {
						//	x2=right+50;
							
					//	} 
						
						
					}
					
					if(x2>=590&&x2<=600&&y2>=490&&y2<=500) {
						y2=550;
					}
					
					}
					
					
					if(dy>0) {
					if(y2>top&&y2<bottom) {
						if(x2>left&&x2<right) {
							y2=top;
						}
						if(x2>left+50&&x2<right+50) {
							y2=top;
						}
						
						
						
					}
					}
					
					if(dy<0) {
						if(y2-50>top&&y2-50<bottom) {
							if(x2>left&&x2<right) {
								y2=bottom+50;
							}
							if(x2>left+50&&x2<right+50) {
								y2=bottom+50;
							}
							
							
						}
						}
					
					
				}
			}

			//플레이어 좌표가 화면 밖으로 나가지 않도록
			
			
			
			
			
			if(x <w+50) x = w+50;

			if(x > width - w) x = width - w;

			if(y <h+50) y = h+50;

			if(y > height - h) y = height - h;
			
			
			if(x2 <w2+50) x2 = w2+50;

			if(x2 > width - w2) x2 = width - w2;

			if(y2 <h2+50) y2 = h2+50;

			if(y2 > height - h2) y2 = height - h2;
			
			
			if(tb==0) {
				
				SendTotalBlue();
			}
			
			if(tr==0) {
				SendTotalRed();
			}
			
			
			
		}


	}
	
	
	class TimerThread extends Thread{
		
		
		public synchronized void run() {
			mm=1;
			ms=0;
			mss=0;
			
			
			int tss=10;
			int timer=60;
			
			while(true) {
				
				mm=0;
				timer--;
				tss--;
				
				
				if(timer==59) {
					ms=5;
				}
				if(timer==49) {
					ms=4;
				}
				if(timer==39) {
					ms=3;
				}
				if(timer==29) {
					ms=2;
				}
				if(timer==19) {
					ms=1;
				}
				if(timer==9) {
					ms=0;
				}
				
				if(tss==9) {
					mss=9;
				}
				if(tss==8) {
					mss=8;
				}
				if(tss==7) {
					mss=7;
				}
				if(tss==6) {
					mss=6;
				}
				if(tss==5) {
					mss=5;
				}
				if(tss==4) {
					mss=4;
				}
				if(tss==3) {
					mss=3;
				}
				if(tss==2) {
					mss=2;
				}
				if(tss==1) {
					mss=1;
				}
				if(tss==0) {
					mss=0;
					if(timer!=0) {
						tss=10;
					}
				}
				
				if(timer==0) {
					stop=1;
					SendStop();
				}

				panel.repaint();
				
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e) {
					return;
				}
				
			}
		}
	}
}

