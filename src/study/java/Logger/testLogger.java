package study.java.Logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class testLogger {
    public static void main(String[] args) {
        Logger log = Logger.getLogger("lavasoft");
        log.setLevel(Level.WARNING);//Level.FINE
        Logger log1 = Logger.getLogger("lavasoft1");
        System.out.println(log==log1);     //true 
        
        Logger log2 = Logger.getLogger("lavasoft.blog");
        log2.setLevel(Level.FINEST);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        log2.addHandler(handler);
        
        log.info("aaa");
        log2.info("bbb");
        log2.config("fine");
    }

}
