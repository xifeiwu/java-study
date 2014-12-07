package study.java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserFrame extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenu clientMenu;
	private JMenuItem socketClientMenuItem, httpClientMenuItem, stopClientMenuItem;
    private JMenu serverMenu;
    private JMenuItem socketServerMenuItem, httpServerMenuItem, stopServerMenuItem;

	private Container container;
	private JTextArea historyTextArea;
	private JScrollPane scorllTextArea;
	private JTextField sendTextField;
	private JButton sendBtn;
	private JPanel buttomPanel;

	private Calendar calendar;

	private int clientType;
	private final int NONE = 0, SOCKETCLIENT = 1, HTTPCLIENT = 2;

	private UserFrame instance;

	public UserFrame() {
		this.setTitle("用户界面");
		instance = this;
		menuBar = new JMenuBar();
		container = this.getContentPane();

		clientMenu = new JMenu("客户端参数");
		socketClientMenuItem = new JMenuItem("连接远程ServerSocket");
//		httpClientMenuItem = new JMenuItem("Http客户端");
		stopClientMenuItem = new JMenuItem("关闭连接");
		socketClientMenuItem.addActionListener(this);
//		httpClientMenuItem.addActionListener(this);
		stopClientMenuItem.addActionListener(this);
		clientMenu.add(socketClientMenuItem);
//		clientMenu.add(httpClientMenuItem);
		clientMenu.add(stopClientMenuItem);
        menuBar.add(clientMenu);
		
		serverMenu = new JMenu("服务器参数");
        socketServerMenuItem = new JMenuItem("开启ServerSocket");
//        httpServerMenuItem = new JMenuItem("Http客户端");
//        stopServerMenuItem = new JMenuItem("关闭客户端");
        socketServerMenuItem.addActionListener(this);
//        httpServerMenuItem.addActionListener(this);
//        stopServerMenuItem.addActionListener(this);
        serverMenu.add(socketServerMenuItem);
//        clientMenu.add(httpServerMenuItem);
//        clientMenu.add(stopServerMenuItem);
		menuBar.add(serverMenu);
		
		setJMenuBar(menuBar);

		historyTextArea = new JTextArea();
		historyTextArea.setEditable(false);
		historyTextArea.setBackground(Color.lightGray);
		scorllTextArea = new JScrollPane(historyTextArea);
		scorllTextArea.setBorder(BorderFactory.createEmptyBorder(3, 3, 5, 3));

		sendTextField = new JTextField();
		sendTextField.addActionListener(this);
		sendBtn = new JButton("发送");
		// sendBtn.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
		sendBtn.addActionListener(this);
		buttomPanel = new JPanel();
		buttomPanel.setLayout(new BorderLayout());
		buttomPanel.add(sendTextField, BorderLayout.CENTER);
		buttomPanel.add(sendBtn, BorderLayout.EAST);
		buttomPanel.setBorder(BorderFactory.createEmptyBorder(1, 3, 3, 3));

		container.add(scorllTextArea, BorderLayout.CENTER);
		container.add(buttomPanel, BorderLayout.SOUTH);

		calendar = Calendar.getInstance();
		
		clientType = NONE;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    myLog("退出用户界面。。。");
				closeClient();
				System.exit(0);
			}
		});
	}
	private JDialog mDialog;
	private String host;
	private int port;
	private void initDialog(){
	    JPanel mPanel = new JPanel();
        GridLayout mLayout = new GridLayout(0,2);
        JLabel hostLabel = new JLabel("IP地址");
        JLabel portLabel = new JLabel("端口号");

        JTextField hostField = new JTextField();
        hostField.setColumns(10);
        JTextField portField = new JTextField();
        portField.setColumns(10);
        
        hostLabel.setLabelFor(hostField);
        portLabel.setLabelFor(portField);

        mPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mPanel.setPreferredSize(new Dimension(400, 0));
        mPanel.setLayout(mLayout);
        mPanel.add(hostLabel);
        mPanel.add(hostField);
        mPanel.add(portLabel);
        mPanel.add(portField);
        
        mDialog = new JDialog();
        mDialog.getContentPane().add(mPanel);
        mDialog.pack();
        mDialog.setVisible(true);	    
	}
	private void myLog(String msg){
        System.out.println(msg);
        appendToHistoryTextArea("message:", msg);
	}

    public void appendToHistoryTextArea(String name, String msg){
        int hour, minute, second;
        calendar.setTimeInMillis(System.currentTimeMillis());
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        historyTextArea.append(name + "（" + hour + "：" + minute + "：" + second + "）：" + msg + "\n");
    }
	// private SimpleHttpServer httpServer;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComponent item = (JComponent) e.getSource();
		if (item.equals(socketClientMenuItem)) {
			System.out.println("socketClient");
			clientType = SOCKETCLIENT;
//			String inputValue = JOptionPane.showInputDialog("Input IP and Port for Destination Server:");
			JOptionPane.showMessageDialog(instance, "Eggs aren't supposed to be green.");
//			serverThread = new Thread(clientRun);
//			serverThread.start();
		}

		if (item.equals(httpClientMenuItem)) {
			System.out.println("HttpClient");
			clientType = HTTPCLIENT;
//			serverThread = new Thread(clientRun);
//			serverThread.start();
		}

		if (item.equals(stopClientMenuItem)) {
			System.out.println("stopClient");
			closeClient();
		}

		if (item.equals(sendTextField) || item.equals(sendBtn)) {
			// System.out.println("sendTextField");
			String text = sendTextField.getText();
			if (!text.equals("")) {
				sendTextField.setText("");
				switch (clientType) {
				case SOCKETCLIENT:
//					if (socketClient != null) {
//						socketClient.sendMsg(text);
//					}
					appendToHistoryTextArea("客户端", text);
					break;
				case HTTPCLIENT:
//					if (httpClient != null) {
//						httpClient.sendMsg(text);
//					}
					appendToHistoryTextArea("客户端", text);
					break;
				case NONE:
					appendToHistoryTextArea("提示", "服务器还没有启动");
					break;
				}
			}
		}
	}


//  private SocketClient socketClient;
//  private HttpClient httpClient;
//  private Thread serverThread;
    Runnable clientRun = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            switch(clientType){
            case SOCKETCLIENT:
//              socketClient = new SocketClient(instance);
                break;
            case HTTPCLIENT:
//              httpClient = new HttpClient(instance);
                break;
            }
        }

    };

    private void closeClient(){
        switch (clientType) {
        case SOCKETCLIENT:
//          if (socketClient != null) {
//              socketClient.close();
//          }
            break;
        case HTTPCLIENT:
//          if (httpClient != null) {
//              httpClient.close();
//          }
            break;
        }
    }

	public static void main(String[] args){
	    int WIDTH = 500;
	    int HEIGHT = 650;
        UserFrame userFrame = new UserFrame();
        userFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        userFrame.pack();
//        userFrame.setVisible(true);
        userFrame.initDialog();
	}
}
