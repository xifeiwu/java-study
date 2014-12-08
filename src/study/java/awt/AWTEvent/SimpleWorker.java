
package study.java.awt.AWTEvent;

import java.lang.Thread;
import java.awt.Toolkit;
import java.awt.EventQueue;

/**
 * Simple worker send an AWT event to the main Swing application.
 * Written by S.Kauss
 */
public class SimpleWorker extends Thread 
{
    public  static int MAXWORK    = 10;
    private EventQueue eventQueue = null;
    private boolean    running    = true;
    private int        count      = 0;

    // The component which will receive the event.
    private java.awt.Component  target     = null;

    /**
     * The event destination is target !
     */
    SimpleWorker( java.awt.Component target )
    {
        this.target = target;
    }

    /**
     * stopRunning stop the SimpleWorker thread.
     */
    public void stopRunning()
    {
        running = false;
    }

    /**
     * run do the work.
     */
    public void run( )
    {
        int percent = 0;
        String msg  = "";
        System.out.println("SimpleWorker.run : Thread " + 
                Thread.currentThread().getName() + " started" );

        eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();

        while ( running )
        {
            count++;
            if ( count >= MAXWORK)
                 running = false;

            msg = "Message from " + Thread.currentThread().getName() +
                  " : " + count;

            eventQueue.postEvent( new SimpleAWTEvent( target, msg, count ));

            try
            {
                this.sleep( 800 );
            }
            catch ( Exception e)
            {
                System.out.println( "E:run : " + e.toString());
            }
        }

        eventQueue.postEvent( new SimpleAWTEvent( target, "Work done..",count));

        System.out.println("SimpleWorker.run : Thread " + 
                            Thread.currentThread().getName() + " stoped" );
    }                       // run

}                           // Worker

