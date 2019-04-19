package documents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DocumentCMS implements DocumentDAO {
	
	public Document GetDocument(String id) {
		String filename = String.format("documents/%s.txt", id);
		ClassLoader classLoader = new DocumentCMS().getClass().getClassLoader();
		URL resource = classLoader.getResource(filename);
		if (resource == null) {
			// TODO: Raise not found exception when file isn't found
			return new Document("Not Found", "Not Found");
		}
		File file = new File(resource.getFile());
		
		if (!file.exists()) {
			// TODO: Raise not found exception when file isn't found
			return new Document("Not Found", "Not Found");
		}
		
		return new Document(id, GetFileContent(file));
	}
	
	private String GetFileContent(File file) {
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