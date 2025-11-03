public class LinkedList {
	private Node head;
	private int size; 
                      
	public LinkedList() {
		head = null;
		size = 0;
	}

	public boolean isEmpty() {
		return getHead() == null;
	}

	public boolean isFull() {
		Node aux = new Node();
		return aux == null;
	}

	public int getSize() {
		return size;
	}

	public Node getHead() {
		return head;
	}

	public Node get(int pos) {
		if (isEmpty()) return null;
		if (pos <= 0 || pos > size) return null;
		int cont = 1;
		Node pAnda = head;
		while (cont != pos){
			pAnda = pAnda.getNext();
			cont++;
		}
		return pAnda;
	}

	public boolean insert(int line, String code) {
		Node aux; 
		Node pAnda; 	
		Node pAnt = null;  

		if (line <= 0) return false; 
	    if (!isFull()){ 
	      aux = new Node(line, code, null);
	      if (isEmpty()){ 
	    	head = aux;
	      } else if (line >= size+1) {
	    	insertTail(line, code);
	      } else {
	    	int cont = 1;
	        pAnda = head;   
	        while (pAnda.getNext() != null && cont != line){
	           pAnt = pAnda;
	           pAnda = pAnda.getNext();
	           cont++;
	        }
        	aux.setNext(pAnda);
	        if (cont == 1) { 
	        	head = aux;
	        } else { 
	        	pAnt.setNext(aux);
	        }
	      }
		  size++;
		  return true; 
	    }
	    else return false; 
	};

	public boolean insertHead(int line, String code){
		Node aux; 
	    if (!isFull()){ 
	      aux = new Node(line, code, null);
	      if (isEmpty()){ 
	        head = aux;
	      }else { 
	      	aux.setNext(head);
	      	head = aux;
	      }
    	  size++;
	      return true; 
	    }
	    else return false;   
	};

	public boolean insertTail(int line, String code){
		Node aux; 
		Node pAnda;	
	    if (!isFull()){
	      aux = new Node(line, code, null);
	      if (isEmpty()){ 
	        head = aux;
	      }else { 
	        pAnda = head;   
	        while (pAnda.getNext() != null)
	           pAnda = pAnda.getNext();
	        pAnda.setNext( aux );
	      }
    	  size++;
		  return true;  
	    }
	    else return false;  
	};

	public Node search(int line){
		Node pAnda; 	
	    if (isEmpty()) {
			return null; 
	    }else{
	      pAnda = head;

	      while ((pAnda != null) && (pAnda.getLine() != line))
	        pAnda = pAnda.getNext();
	      return pAnda; 
	    }
	}

	public boolean remove(int line){
		Node pAnda; 		
		Node pAnt = null; 
	    if (isEmpty()) return false; 
	    else{  
	      pAnda = head;

	      while ((pAnda != null) && (pAnda.getLine() != line)){
	        pAnt = pAnda;
	        pAnda = pAnda.getNext();
	      }
	      if (pAnda == null) return false; 
	      else {  
	      	if ((head == pAnda)) {
			  head = pAnda.getNext();
		    } else{ 
		    	pAnt.setNext(pAnda.getNext());
			}
	      	pAnda = null;
	    	size--;
	      	return true;    
	      }
	    }
	}

	public int pollFirst(){
	    if (isEmpty()) return -1; 
	    else{  
		  Node pAux = head;
	      head = head.getNext();
    	  size--;
	      return pAux.getLine();
	    }
	}	

	public int pollLast(){
	    if (isEmpty()) return -1; 
	    else{  
		  Node pAnda = head, pAnt = null;

	      while ((pAnda.getNext() != null)){
	        pAnt = pAnda;
	        pAnda = pAnda.getNext();
	      }   
	      pAnt.setNext(null);
    	  size--;
	      return pAnt.getLine();
	    }
	}	

	public void print(){
		Node pAnda; 
	    pAnda = head;
	    while (pAnda != null) {
	      System.out.println("Linha:" + pAnda.getLine() + "Código:" + pAnda.getCode());
	      pAnda = pAnda.getNext();
	    }
	}

	public void clear(){
		Node pAnt; 
		Node pAnda = head;  
                               
		while(pAnda != null){
			pAnt = pAnda;  
			pAnda = pAnda.getNext();
			pAnt.setNext(null);
			pAnt = null;
		}
		size = 0;
		head = null; 	 
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		int qtde = 0;
		sb.append("\n[Lista]\n");
	
	    sb.append("L: [ ");
	    Node pAnda = head;
	    while (pAnda != null) {
	      sb.append(pAnda.getLine()+" "+pAnda.getCode()+" ");
	      qtde++;
	      pAnda = pAnda.getNext();
	    }
	    sb.append("]\n");
	    
	    sb.append("Qtde.: " + qtde);
	    sb.append("\n");
	    
	    return sb.toString();
	}
	
}

