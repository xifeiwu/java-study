package study.java.net.DatagramSocket;

import java.net.DatagramPacket;  
import java.net.InetAddress;  
import java.net.MulticastSocket;  
import java.util.Date;  
  
/** 
 * 组播的客户端 
 * @author Bird 
 * 
 */  
public class MulticastReceive {  
  
      
    public static void main(String[] args) throws Exception {  
        test();  
    }  
      
    public static void test() throws Exception{  
        InetAddress group = InetAddress.getByName("224.0.100.2");//组播地址  
        int port = 8888;  
        MulticastSocket msr = null;//创建组播套接字  
        try {  
            msr = new MulticastSocket(port);  
            msr.joinGroup(group);//加入连接  
            byte[] buffer = new byte[8192];  
            System.out.println("接收数据包启动！(启动时间: "+new Date()+")");  
            while(true){  
                //建立一个指定缓冲区大小的数据包  
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);  
                msr.receive(dp);  
                String s = new String(dp.getData(),0,dp.getLength());  
                //解码组播数据包  
                System.out.println(s);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            if(msr!=null){  
                try {  
                    msr.leaveGroup(group);  
                    msr.close();  
                } catch (Exception e2) {  
                    // TODO: handle exception  
                }  
            }  
        }  
    } 
}