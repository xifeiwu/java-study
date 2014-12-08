
package study.java.awt.AWTEvent;

import java.awt.AWTEvent;

/**
 * This class implement an user defined awt event which will be posted
 * to the AWT EventQueue.
 * S.Kauss
 */

public class SimpleAWTEvent extends AWTEvent
{
    public  static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
    private String           str;
    private int              percent;

    SimpleAWTEvent( Object target, String str, int percent)
    {
        super( target, EVENT_ID);
        this.str     = str; 
        this.percent = percent;
    }

    public String getStr()
    {
        return( str );
    }

    public int getPercent()
    {
        return( percent );
    }
}                                               // MyAWTEvent


