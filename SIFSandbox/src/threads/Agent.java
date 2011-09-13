package threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * See http://www.insaneprogramming.be/?p=308 for some details on ScheduledExecutorService.
 */
public class Agent
{
    private static boolean implementShutdownHook = true;
    
    private void stopAgent()
    {
        System.out.println("Shutting down agent and release all resources... DONE.");        
        System.out.println("======================= End Test Multi-Threading ===========================");
    }
    
    private void startProviders()
    {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new StudentPersonalProvider(), 0, 10, TimeUnit.SECONDS);
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new StudentEnrolmentProvider(), 0, 30, TimeUnit.SECONDS);        
    }
    
    public static void main(String[] args)
    {
        System.out.println("======================= Start Test Multi-Threading ===========================");
        Agent provider = new Agent();
        provider.startProviders();
        
        if (implementShutdownHook)
        {
            // Install a shutdown hook to cleanup when Ctrl+C is pressed
            // System.out.println("Installing shutdown hook");
            System.out.println("Installing shutdown hook");
            final Agent tmpAgent = provider;
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                public void run()
                {
                    tmpAgent.stopAgent();
                }
            });
            System.out.println("Total Threads: "+Thread.activeCount());
            System.out.println("Agent is running (Press Ctrl-C to stop)");
        }     
    }
}
