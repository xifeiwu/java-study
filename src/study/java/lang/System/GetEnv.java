package study.java.lang.System;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetEnv {
    public static void main(String[] args){
        Logger logger = Logger.getLogger(GetEnv.class.getName());
//        logger.setLevel(Level.WARNING);
        String ip = System.getProperty("user.name");
        logger.info("ip:" + ip);
        try {
            InetAddress addr = InetAddress.getLocalHost();
            logger.info("InetAddress.getLocalHost: " + addr.toString());
            if(addr.isLoopbackAddress()){
                logger.info("address isLoopbackAddress.");
            }else{
                logger.info("address is Not LoopbackAddress.");                
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        logger.info(System.getenv().toString());
    }

}
