package study.java.util.Collection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyScheduledExecutorService {
    public static void main(String[] args)
    {
        ScheduledExecutorService ses =
            Executors.newScheduledThreadPool(1);
        Runnable pinger = new Runnable() {
            public void run() {
                System.out.println("PING!");
            }
        };
        ses.scheduleAtFixedRate(pinger, 5, 5, TimeUnit.SECONDS);
    }
}
