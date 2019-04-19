package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.leansoft.bigqueue.IBigQueue;
import com.leansoft.bigqueue.IFanOutQueue;

import util.BytesUtil;
import util.FileUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IndexManager {
	
	private static int updatePostThreadLimit = 1000;
	
	private static HashMap<String, Word> vocabulary = new HashMap<String, Word>(); 
	
	public static void createIndexes() {
		// TODO: Delete previous post index and empty big queue
		vocabulary = new HashMap<String, Word>();
		try {
			Queue.getInstance().removeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ClassLoader classLoader = new IndexManager().getClass().getClassLoader();
		URL documents = classLoader.getResource("./documents");
		
		try (Stream<Path> walk = Files.walk(Paths.get(documents.getPath()))) {
			List<String> result = walk
					.filter(Files::isRegularFile)
					.map(x -> x.toString())
					.filter(x -> x.endsWith(".txt"))
					.collect(Collectors.toList());
			
			HashMap<String, Word> innerVocabulary = new HashMap<String, Word>();
			for (String path : result) {
				String data = GetFileContent(new File(path));
				
				// Creates data array by replacing all non alphabet characters
				// with an empty space, transforming it to lower case and 
				// splitting it by all empty space
				String[] words = data
						.replaceAll("[^A-Za-z]+", " ")
						.toLowerCase()
						.split("\\s");
				
				innerVocabulary = calculateInnerVocabulary(words);
				String[] pathArray = path.split("/"); 
				String fileName = pathArray[pathArray.length - 1];
				
				long start = System.currentTimeMillis();
				
				// For each word, create or update it's index in post
				// TODO: Implement concurrent send message maybe?
				for(Word word : innerVocabulary.values()) {
					SendMessageTask task = new SendMessageTask(word, fileName);
					task.run();
				}
				
				long end = System.currentTimeMillis();
				float sec = (end - start); 
				System.out.println(sec + " ms");
				System.out.println(String.format("Processed %s", fileName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TODO: Remove log
		IFanOutQueue queue = Queue.getInstance();
		System.out.println(queue.size());
		
		loadVocabulary();
	}
	 
	public static void loadVocabulary() {
		/*
		Iterator it = getDBIterator();
		if (it == null) {
			System.out.println("Error loading global vocabulary");
			return;
		}
		
		while (it.hasNext()) {
			Post entry = (Post) it.next();
			Word word = new Word(
					entry.word, 
					entry.documents.size(), 
					entry.documents.get(0).termFrequency
			);
			
			vocabulary.put(entry.word, word);
		}*/
	}
	
	public static HashMap<String, Word> getVocabulary() {
		return vocabulary;
	}
	
	private static HashMap<String, Word> calculateInnerVocabulary(String[] words) {
		HashMap<String, Word> innerVocabulary = new HashMap<String, Word>();
		
		for (String word: words) {
			if (word.length() <= 1) {
				continue;
			}
			if (!innerVocabulary.containsKey(word)) {
				innerVocabulary.put(word, new Word(word, 1, 1));
				continue;
			}
			Word wordData = innerVocabulary.get(word);
			wordData.termFrequency += 1;
		}
		
		return innerVocabulary;
	}
	
	private static String GetFileContent(File file) {
		try {			
			InputStream is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			
			while(line != null){
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	
	
}
