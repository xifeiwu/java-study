/*
 * Example shows Java swing with external threads.
 * Using user defined AWT events.
 *
 * By S.Kauss
 */

package study.java.awt.AWTEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import javax.swing.JOptionPane;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * create a new swing frame
 */
public class EventDemo extends JFrame implements ActionListener, WindowListener
{
    protected JTextField   tf          = null;
    protected JProgressBar progressBar = null;
    protected Dimension    defaultSize = new Dimension(200, 200);

    public int wNr  = 0;
    SimpleWorker worker = null;

    public EventDemo() 
    {
        super( "EventDemo" );

        wNr++;

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        addWindowListener( this );

        worker = new SimpleWorker( this );
        worker.setName("Worker " + wNr );

        JMenu menu = new JMenu("Menu");
        menu.setMnemonic( KeyEvent.VK_M );
        JMenuItem item = null;

        //quit
        item = new JMenuItem("Quit");
        item.setMnemonic( KeyEvent.VK_Q);
        item.addActionListener( new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                        if ( worker != null )
                             worker.stopRunning();

                        worker = null;
                        EventDemo.this.setVisible(false);
                        EventDemo.this.dispose();
                    }
                });
        menu.add(item);

        getContentPane().setLayout( new  GridLayout(2,1) );

        JMenuBar menuBar = new JMenuBar();
        menuBar.add( menu );
        setJMenuBar( menuBar );
        setSize( defaultSize );

        tf = new JTextField( );
        getContentPane().add( tf );

        progressBar = new JProgressBar( 0, SimpleWorker.MAXWORK );
        progressBar.setValue( 0);
        progressBar.setStringPainted( true);
        getContentPane().add( progressBar );

        enableEvents( SimpleAWTEvent.EVENT_ID);  // enable my event's

        worker.start();   // start worker thread
        setVisible( true);
    }                       // EventDemo

    /*
     * Over load processEvent which is inherited from class java.awt.Window
     * Our defined SimpleAWTEvent will be handled here.
     */
    protected void processEvent( AWTEvent event)
    {
        if ( event instanceof SimpleAWTEvent )
        {
            SimpleAWTEvent ev = (SimpleAWTEvent) event;

            tf.setText( ev.getStr() );
            progressBar.setValue( ev.getPercent() );
        }
        else    // other events go to the system default process event handler
        {
            super.processEvent( event );   
        }
    }                                     // processEvent

    // ------------------ ActionListener --------------------------

    public void actionPerformed( ActionEvent e)
    {
    }

    // WindowListener --------------------------------------------------

    public void windowClosing( WindowEvent e)
    {
        if ( worker != null )
        {
            worker.stopRunning();
            worker = null;
        }

        try
        {
            Thread.sleep( 300 );
        }
        catch ( Exception ex )
        {
            System.out.println( ex.toString() );
        }
        System.exit( 0 );
    }                       // windowClosing

    public void windowActivated( WindowEvent e)
    {
    }

    public void windowClosed( WindowEvent windowevent)
    {
    }

    public void windowDeactivated( WindowEvent windowevent)
    {
    }

    public void windowDeiconified( WindowEvent windowevent)
    {
    }

    public void windowIconified( WindowEvent windowevent)
    {
    }

    public void windowOpened( WindowEvent windowevent)
    {
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() 
    {
//        JFrame.setDefaultLookAndFeelDecorated( true);
//        JDialog.setDefaultLookAndFeelDecorated( true);

        EventDemo framework = new EventDemo();
    }

    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( new Runnable() 
                {
                    public void run()
                    {
                        createAndShowGUI();
                    }
                });
    }
}           // EventDemo


