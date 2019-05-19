package api;

import static spark.Spark.*;
import spark.Filter;
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
		
		after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:8080");
            response.header("Access-Control-Allow-Methods", "GET");
        });
		
		get("/back/search", (req, res) -> {
			res.type("application/json");
			String query = req.queryParams("q");
			int limit = req.queryParams("limit") != null ? Integer.parseInt(req.queryParams("limit")) : 20;
			int offset = req.queryParams("offset") != null ? Integer.parseInt(req.queryParams("offset")) : 0;
			
			return new Gson().toJson(searchService.searchDocuments(query, offset, limit));
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
