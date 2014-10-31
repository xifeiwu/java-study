package study.java.util.Collection;

import java.util.*;
import java.util.concurrent.*;
/*
 * BlockingQueue 接口表示它是一个 Queue，意思是它的项以先入先出（FIFO）顺序存储。
 * 在特定顺序插入的项以相同的顺序检索 — 但是需要附加保证，从空队列检索一个项的任何尝试都会阻塞调用线程，
 * 直到这个项准备好被检索。同理，想要将一个项插入到满队列的尝试也会导致阻塞调用线程，直到队列的存储空间可用。
 */

class Producer implements Runnable {
    private BlockingQueue<String> drop;
    List<String> messages = Arrays.asList("Mares eat oats", 
            "Does eat oats", 
            "Little lambs eat ivy",
            "Wouldn't you eat ivy too?");

    public Producer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            for (String s : messages)
                drop.put(s);
            drop.put("DONE");
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> drop;

    public Consumer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            String msg = null;
            while (!((msg = drop.take()).equals("DONE")))
                System.out.println(msg);
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

public class MyArrayBlockingQueue {
    public static void main(String[] args) {
        BlockingQueue<String> drop = new ArrayBlockingQueue(1, true);
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
