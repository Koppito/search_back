package search;

public class Paging {

	public int offset;
	public int limit;
	public int total;
	
	public Paging(int offset, int limit, int total) {
		this.total = total;
		this.offset = offset;
		this.limit = limit;
	}
	
	
}
