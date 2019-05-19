package api;

import static spark.Spark.*;
import documents.DocumentService;
import search.SearchService;
import index.IndexManager;

import com.google.gson.*;

public class Main {
	
	private static DocumentService documentService = new DocumentService();
	private static SearchService searchService = new SearchService();
	
	public static void main(String[] args) {
		port(8081);
		get("/ping", (req, res) -> "pong");
		
		get("/back/search", (req, res) -> {
			res.type("application/json");
			String query = req.queryParams("q");
			return new Gson().toJson(searchService.searchDocuments(query, 20));
		});
		
		get("/back/documents/:id", (req, res) -> {
			res.type("application/json");
			String id = req.params(":id");
			return new Gson().toJson(documentService.getDocument(id));
		});
		
		post("/back/search/index", (req, res) -> {
			boolean force = (req.queryParams("force") == "");
			IndexManager.createIndexes(force);
			return "done";
		});
	}
	
}
