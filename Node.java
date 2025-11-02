public class Node <T>{
	private T dado;
	private Node<T> prox; 
	
	public Node() {
		this(null, null);
	}
	
	public Node(T dado, Node<T> prox) {
		this.dado = dado;
		this.prox = prox;
	}
    
	public Node<T> getProx() { return prox; };
	
	public T getDado(){ return dado; };
	
	public void setProx(Node<T> prox) { this.prox = prox; };

	public void setDado(T dado) { this.dado = dado;	};	
}
