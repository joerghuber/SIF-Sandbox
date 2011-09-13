package threads;

import java.util.Date;

import au.com.systemic.framework.utils.DateUtils;

public class StudentPersonalProvider implements Runnable
{
    private int counter=1;
    
    @Override
    public void run()
    {
        Date now = new Date();
        System.out.println(DateUtils.dateToString(now, "dd/MM/yyyy HH:mm:ss.SSS")+": "+this.getClass().getSimpleName()+" called "+(counter++)+". Wait 3 seconds...");
        try
        {
            Thread.sleep(3000);
        }
        catch (Exception ex) {}
    }

}
