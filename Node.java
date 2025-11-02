public class Node {
	private int line;
    private String code;
	private Node next; 
	
	public Node() {
		this(null, null, null);
	}
	
	public Node(int line, String code, Node next) {
        this.line = line;
        this.code = code;
        this.next = next;
	}
    
	public Node getNext()   { return next; };
	
	public int getLine()    { return line; };

    public String getCode() { return code; };
	
	public void setNext(Node next)   { this.next = next; };

	public void setLine(int line)    { this.line = line; };	

    public void setCode(String code) { this.code = code; };
}
