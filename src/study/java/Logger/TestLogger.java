package study.java.Logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestLogger {
    
    private boolean finished;
    public TestLogger(){
        if(this.finished){
            System.out.println("Finished");
        }else{
            System.out.println("Not Finished");            
        }
    }
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
        String oldName = "fd\032safifodafel;safj\032espoafejsa;fke";
        String newName = oldName.replaceAll("\\032", "-");
//        System.out.print("oldName: " + oldName + " * " + "newName: " + newName);
        
        TestLogger logger = new TestLogger();
    }

}
