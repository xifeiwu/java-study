package study.java.net;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

public class NetworkInterfaceDemo {
    public static Logger logger = Logger.getLogger("NetworkInterfaceDemo");
    public static void getLocalIP(){
        try {
            InetAddress localhost;
            localhost = InetAddress.getLocalHost();
            logger.info(localhost.getHostName() + ":" + localhost.getHostAddress());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // get a list of all network interfaces
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                int flags = 0;
                Enumeration<InetAddress> addressEnum = networkInterface.getInetAddresses();
                List<InetAddress> addresses = new ArrayList<InetAddress>();
                while (addressEnum.hasMoreElements()) {
                    InetAddress address = addressEnum.nextElement();
                    if(address instanceof java.net.Inet4Address && !address.isLoopbackAddress()){
                        logger.info(address.getHostName() + ":" + address.getHostAddress());
                    }
//                    addresses.add(address);
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException {
        getLocalIP();
        getInterfaceInfo();
    }

    public static void getInterfaceInfo(){
        
        // NetworkInterface implements a static method that returns all the interfaces on the PC,
        // which we add on a list in order to iterate over them.
        ArrayList<NetworkInterface> interfaces;
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());            
            System.out.println("Printing information about the available interfaces...\n");
            for (NetworkInterface iface : interfaces) {
                
                // Due to the amount of the interfaces, we will only print info
                // about the interfaces that are actually online.
                if (iface.isUp()) {
                    
                    // Display name
                    System.out.println("Interface name: " + iface.getDisplayName());
                    
                    // Interface addresses of the network interface
                    System.out.println("\tInterface addresses: ");
                    for (InterfaceAddress addr : iface.getInterfaceAddresses()) {
                        System.out.println("\t\t" + addr.getAddress().toString());
                    }
                    
                    // MTU (Maximum Transmission Unit)
                    System.out.println("\tMTU: " + iface.getMTU());
                    
                    // Subinterfaces
                    System.out.println("\tSubinterfaces: " + Collections.list(iface.getSubInterfaces()));
                    
                    // Check other information regarding the interface
                    System.out.println("\tis loopback: " + iface.isLoopback());
                    System.out.println("\tis virtual: " + iface.isVirtual());
                    System.out.println("\tis point to point: " + iface.isPointToPoint());
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}