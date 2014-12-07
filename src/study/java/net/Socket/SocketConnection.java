package study.java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



public class SocketConnection {

    private String mAddress;
    private int nPort = 0;
    private UserFrame mFrame;
    public SocketConnection(UserFrame frame){
        mFrame = frame;
        mAddress = getLocalIP();
    }
    private void myLog(String msg){
        mFrame.myLog(msg);
    }

    private Thread ServerThread;
    public void startServerSocket(int port){
        nPort = port;
        serverThreadFlag = true;
        ServerThread = new Thread(ServerRunnable);
        ServerThread.start();
    }
    public void stopServerSocket(){
        ServerThread.interrupt();
        try {
            if(null != mServerSocket){
                mServerSocket.close();
            }
        } catch (IOException ioe) {
            myLog("Error when stop ServerSocket.");
            ioe.getStackTrace();
        }
        this.stopChatting();
    }
    private ServerSocket mServerSocket;
//    private Socket mClientSocket;
    private Socket remoteSocket;
    private Map<String, Socket> socketPool = new HashMap<String,Socket>();
    private boolean serverThreadFlag = false;
    private Runnable ServerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if((null == mAddress) || (0 == nPort)){
                    myLog("ServerSocket address or port is not set.");
                    return;
                }
                myLog("ServerSocket " + mAddress + ":" + nPort + " has started");
                mServerSocket = new ServerSocket(nPort);
                mServerSocket.setReuseAddress(true);
                while (!Thread.currentThread().isInterrupted() && serverThreadFlag) {
                    remoteSocket = mServerSocket.accept();
                    String clientIP;
                    int clientPort;
                    clientIP = remoteSocket.getInetAddress().toString().substring(1);
                    clientPort = remoteSocket.getPort();
                    myLog("Client Socket " + clientIP + ":" + clientPort + " has connected.");
                    socketPool.put(clientIP + ":" + clientPort, remoteSocket);
                    serverThreadFlag = false;
                }
                startChatting(remoteSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void startClientSocket(String address, int port){
        try {
            remoteSocket = new Socket(address, port);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            myLog("connectToServer Error: UnknownHostException");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            myLog("connectToServer Error: IOException");
            e.printStackTrace();
        }
        startChatting(remoteSocket);
    }
    public void stopClientSocket(){
        try {
            if(null != remoteSocket){
            remoteSocket.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.stopChatting();
    }


    private Socket destSocket;
    private String destName;
    private Thread ReceivingThread;
    private void startChatting(Socket socket){
        destSocket = socket;
        String address = socket.getInetAddress().toString().substring(1);
        int port = socket.getPort();
        destName = address + ":" + port;
        ReceivingThread = new Thread(ReceivingRunnable);
        ReceivingThread.start();
    }
    private void stopChatting(){
        try {
            destSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            myLog("stopChatting Error: IOException");
            e.printStackTrace();
        }
        ReceivingThread.interrupt();
    }
    private Runnable ReceivingRunnable = new Runnable() {
        @Override
        public void run() {
            BufferedReader input;
            try {
                input = getReader(destSocket);
                while (!Thread.currentThread().isInterrupted()) {
                    String messageStr = null;
                    messageStr = input.readLine();
                    if (messageStr != null) {
                        myLog(destName + ": " + messageStr);
                    } else {
//                        break;
                    }
                }
                input.close();
            } catch (IOException e) {
                myLog("ReceivingThread Error:" + e);
            }
        }
    };

    public void sendMessage(String msg) {//Socket socket,
        try {
            if (destSocket == null) {
                myLog("destSocket is null");
            } else if (destSocket.getOutputStream() == null) {
                myLog("destSocket output stream is null.");
            }
            PrintWriter out = this.getWriter(destSocket);
            out.println(msg);
            out.flush();
            myLog("I say: " + msg);
        } catch (UnknownHostException e) {
            myLog("Unknown Host:" + e);
        } catch (IOException e) {
            myLog("I/O Exception" + e);
        } catch (Exception e) {
            myLog("Exception:" + e);
        }
    }

    private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream stream = socket.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
        PrintWriter pw = new PrintWriter(bufferedWriter);
        return pw;
//        return new PrintWriter(
//                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), 
//                        true);
    }
    private BufferedReader getReader(Socket socket) throws IOException{
        InputStream stream = socket.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        return bufferedReader;
//        return new BufferedReader(new InputStreamReader(
//                socket.getInputStream()));
    }
    
    private String getLocalIP(){
        String address = null;
        try {
            for (Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces(); nifs.hasMoreElements();) {
                if(null != address){
                    break;
                }
                NetworkInterface nif = nifs.nextElement();
//                    myLog("name of network interface: " + nif.getName());
                for (Enumeration<InetAddress> iaenum = nif.getInetAddresses(); iaenum.hasMoreElements();) {
                    InetAddress interfaceAddress = iaenum.nextElement();
                      if (!interfaceAddress.isLoopbackAddress()) {
                          if (interfaceAddress instanceof Inet4Address) {
//                                  myLog(interfaceAddress.getHostName() + " -- " + interfaceAddress.getHostAddress());
                              address = interfaceAddress.getHostAddress();
                              break;
                          }
                      }
                }
            }
        } catch (SocketException se) {
            se.printStackTrace();
        }
        return address;
    }
}