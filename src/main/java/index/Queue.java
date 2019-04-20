package index;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.leansoft.bigqueue.FanOutQueueImpl;
import com.leansoft.bigqueue.IFanOutQueue;

import util.BytesUtil;

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
	
	public static void publish(Post entry) {
		try {
			instance.enqueue(BytesUtil.toByteArray(entry));
		} catch (IOException e) {
			System.out.println(String.format("Word (%s) message couldn't be sent", entry.word));
			e.printStackTrace();
		}
	}
	
}
