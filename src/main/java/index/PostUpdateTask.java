package index;

import java.io.IOException;

import com.leansoft.bigqueue.IFanOutQueue;

import util.BytesUtil;

public class PostUpdateTask  implements Runnable {
	
	@Override
	public void run() {
		long lastMessageProcessed = System.currentTimeMillis();
		while (true) {
			if (lastMessageProcessed - System.currentTimeMillis() >= 120000) {
				System.out.println("More than 2 minutes have passed since last message, destroying worker");
				break;
			}
			
			try {
				if(!Queue.getInstance().isEmpty("-")) {
					lastMessageProcessed = System.currentTimeMillis();
					
					IFanOutQueue queue = Queue.getInstance();
					byte[] data = queue.dequeue("-");
					if (data == null) {
						continue;
					}
					
					Post entry = (Post) BytesUtil.toObject(data);
					PostDao.getInstance().persist(entry);
				}
				
			} catch (IOException e) {
				System.err.println("Error obtaining queue");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.err.println("Error unmarshalling post entry");
				e.printStackTrace();
			}
		}
	}
	
	
}
