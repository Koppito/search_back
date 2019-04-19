package index;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import com.leansoft.bigqueue.IBigQueue;
import com.leansoft.bigqueue.IFanOutQueue;

import util.BytesUtil;

public class SendMessageTask implements Runnable {
	private Word word;
	private String file;
		
	public SendMessageTask(Word word, String file) {
		this.word = word;
		this.file = file;
	}
	
	@Override
	public void run() {
		IFanOutQueue queue = Queue.getInstance();
		
		PostDocumentData data = new PostDocumentData(file, word.termFrequency);
		LinkedList<PostDocumentData> documentData = new LinkedList<PostDocumentData>();
		documentData.add(data);
		
		Post entry = new Post(word.word, documentData);
		try {
			queue.enqueue(BytesUtil.toByteArray(entry));
		} catch (IOException e) {
			System.out.println(String.format("Word (%s) message couldn't be sent", this.word));
			e.printStackTrace();
		}

		/*
		
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		float sec = (end - start); 
		System.out.println(sec + " ms");
		
		
		Options options = new Options().setCreateIfMissing(true);
		RocksDB db = null;
		
		try {
			 db = RocksDB.open(options, "./post");
			 byte[] value = db.get(word.word.getBytes());
			 
			 // If value doesn't exist, save data
			 if (value == null) {
				 // Create document list
				 PostDocumentData data = new PostDocumentData(file, word.termFrequency);
				 LinkedList<PostDocumentData> documentData = new LinkedList<PostDocumentData>();
				 documentData.add(data);
				 
				 // Create and save new entry
				 Post entry = new Post(word.word, documentData);
				 db.put(word.word.getBytes(), BytesUtil.toByteArray(entry));
				 return;
			 }
			 
			 Post entry = (Post) BytesUtil.toObject(value);
			 PostDocumentData data = new PostDocumentData(file, word.termFrequency);
			 entry.documents.add(data);
			 entry.documents.sort((Comparator<PostDocumentData>) 
				 (PostDocumentData a, PostDocumentData b) -> 
					 a.termFrequency - b.termFrequency
			 );
			 
			 // TODO: Remove
			 System.out.println("Updated " + word.word);
			 for (PostDocumentData p : entry.documents) {
				 System.out.println(String.format("%d at %s", p.termFrequency, file));
			 }
			 // TODO: Remove
				 
			 db.put(word.word.getBytes(), BytesUtil.toByteArray(entry));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			
			if (db != null) db.close();
		}*/
	}
	
}