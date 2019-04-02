package api;

import static spark.Spark.*;

public class Main {
	
	public static void main(String[] args) {
		port(8080);
		get("/ping", (req, res) -> "pong");
		
		get("/search", (req, res) -> {
			return "Not implemented";
		});
		
		get("/document/:id", (req, res) -> {
			return "Not implemented";
		});
	}
	
}
