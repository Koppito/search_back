package index;

import java.io.IOException;

import com.leansoft.bigqueue.FanOutQueueImpl;
import com.leansoft.bigqueue.IFanOutQueue;

/**
 * 
 * @author mkopp
 *	Singleton class containing reference to big queue
 */
public class Queue {
	
	public static IFanOutQueue instance;
	
	
	public static IFanOutQueue getInstance() {
		if (instance == null) {
			try {				
				instance = new FanOutQueueImpl("./", "messages"); 
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return instance;
	}
	
}
