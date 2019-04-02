package api;

import static spark.Spark.*;
import documents.DocumentService;
import com.google.gson.*;

public class Main {
	
	private static DocumentService documentService = new DocumentService();
	
	public static void main(String[] args) {
		port(8081);
		get("/ping", (req, res) -> "pong");
		
		get("/search", (req, res) -> {
			res.type("application/json");
			return "Not implemented";
		});
		
		get("/documents/:id", (req, res) -> {
			res.type("application/json");
			String id = req.params(":id");
			return new Gson().toJson(documentService.GetDocument(id));
		});
	}
	
}
