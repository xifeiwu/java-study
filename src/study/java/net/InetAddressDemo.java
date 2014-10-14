package study.java.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InetAddressDemo {
    Logger logger = Logger.getLogger(InetAddress.class.getName());
    public InetAddressDemo(){
        logger.setLevel(Level.CONFIG);
    }
    public InetAddress[] getInetAddresses() {
        Set<InetAddress> result = new HashSet<InetAddress>();
        try {

            for (Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces(); nifs.hasMoreElements();) {
                NetworkInterface nif = nifs.nextElement();
//                logger.info("name of network interface: " + nif.getName());
                for (Enumeration<InetAddress> iaenum = nif.getInetAddresses(); iaenum.hasMoreElements();) {
                    InetAddress interfaceAddress = iaenum.nextElement();
//                    if (logger.isLoggable(Level.FINEST)) {
//                    }
                      if (!interfaceAddress.isLoopbackAddress()) {
                          if (interfaceAddress instanceof Inet4Address) {
                              logger.info("Found NetworkInterface/InetAddress: " + nif + " -- " + interfaceAddress.getHostName()
                                      + " -- " + interfaceAddress.getHostAddress());
                          }
                      }
                }
            }
        } catch (SocketException se) {
            logger.warning("Error while fetching network interfaces addresses: " + se);
        }
        return result.toArray(new InetAddress[result.size()]);
    }

    private void showInfo(InetAddress IP){    
            String name = IP.getHostName();
            String address = IP.getHostAddress();
            System.out.println("------------------------------");
            System.out.println(name + ": " + address);    
    }
    public void getIPAddressByInetAddress(){
        //获得本机的InetAddress信息    
        InetAddress IP;
        try {
            IP = InetAddress.getLocalHost();
            showInfo(IP);
            //从名字获得 InetAddress信息
            IP = InetAddress.getByName("www.sina.com.cn");
            showInfo(IP);
            //从IP 地址 获得 InetAddress信息    
            byte[] bs = new byte[]{(byte)61,(byte)172,(byte)201,(byte)194};
            IP = InetAddress.getByAddress(bs);
            showInfo(IP);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        InetAddressDemo study = new InetAddressDemo();
        study.getInetAddresses();
        study.getIPAddressByInetAddress();
    }
}
