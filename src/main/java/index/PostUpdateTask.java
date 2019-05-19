package index;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.leansoft.bigqueue.IFanOutQueue;

import util.BytesUtil;

public class PostUpdateTask  implements Runnable {
	
	@Override
	public void run() {
		long lastMessageProcessed = System.currentTimeMillis();
		System.out.println("Worker created!!!!");
		
		while (true) {
			if (lastMessageProcessed - System.currentTimeMillis() >= 10000) {
				System.out.println("More than 10 seconds have passed since last message, destroying worker");
				break;
			}
			
			try {
				if(!Queue.getInstance().isEmpty("data")) {
					lastMessageProcessed = System.currentTimeMillis();
					
					IFanOutQueue queue = Queue.getInstance();
					byte[] data = queue.dequeue("data");
					if (data == null) {
						continue;
					}
					
					// Every new entry contains a new word and a list with exactly one 
					// element consisting of the document that sent the update with it's
					// corresponding term frequency
					Post entry = (Post) BytesUtil.toObject(data);
					
					// Dao
					PostDao dao = PostDao.getInstance();
					Post post = dao.getPost(entry.word);
					
					if (post != null) {
						PostDocumentData toSave = entry.documents[0];
						post.addDocument(toSave);
						Arrays.sort(post.documents);
						
						dao.update(post);
					} else {						
						dao.persist(entry);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.err.println("Error unmarshalling post entry");
				e.printStackTrace();
			}
		}
	}
	
	
}
