package threads;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import au.com.systemic.framework.utils.DateUtils;

public class SIFMessageConsumer implements Runnable
{
	private BlockingQueue<String> queue;
	private String consumerID;
	
	public SIFMessageConsumer(BlockingQueue<String> queue, String consumerID)
	{
		this.queue = queue;
		this.consumerID = consumerID;
	}
	
	private void consume()
	{
		while (true)
		{
			try
			{
				String value = queue.take();
				Date now = new Date();
				System.out.println(consumerID+": Message received at "+DateUtils.dateToString(now, "dd/MM/yyyy HH:mm:ss.SSS")+": "+value);
				Thread.sleep(500);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}	
	}
	
	@Override
    public void run()
    {
		consume();
    }
	
	
	
}
