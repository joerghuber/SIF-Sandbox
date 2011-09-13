package threads;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import au.com.systemic.framework.utils.DateUtils;

public class SIFMessageProducer
{
	private static final int numThreads = 1;
    private static boolean implementShutdownHook = true;
    ExecutorService service = null;
    
	private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(numThreads);
	
	private void initConsumers()
	{
		service = Executors.newFixedThreadPool(numThreads);
		for (int i = 0; i < numThreads; i++)
		{
			SIFMessageConsumer consumer = new SIFMessageConsumer(queue, "Consumer "+(i+1));
			service.execute(consumer);
		}
	}
	
	private void produce()
	{
		for (int i = 0; i<100; i++)
		{
			try
			{
				Date now = new Date();
				queue.put("Message "+(i+1)+" Produced "+DateUtils.dateToString(now, "dd/MM/yyyy HH:mm:ss.SSS"));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void stopProducer()
	{
		if (service != null)
		{
			service.shutdown();
			// Wait until all threads are finish
			while (!service.isTerminated()) {}
		}
        System.out.println("======================= End Test Multi-Threaded Producer/Consumer ===========================");
	}
	
    public static void main(String[] args)
    {
        System.out.println("======================= Start Test Multi-Threaded Producer/Consumer ===========================");
        SIFMessageProducer producer = new SIFMessageProducer();
        producer.initConsumers();
        
        if (implementShutdownHook)
        {
            // Install a shutdown hook to cleanup when Ctrl+C is pressed
            System.out.println("Installing shutdown hook");
            final SIFMessageProducer tmpProducer = producer;
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                public void run()
                {
                	tmpProducer.stopProducer();
                }
            });
            System.out.println("Total Threads: "+Thread.activeCount());
            System.out.println("Agent is running (Press Ctrl-C to stop)");
        } 
        producer.produce();
    }
}
