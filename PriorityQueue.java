package eg.edu.alexu.csd.datastructure.mailServer.cs76_88;

public class PriorityQueue implements IPriorityQueue {

	Node head;
	Node tail;
	int size;
	
	public PriorityQueue() {
		head=new Node(0,null,null);
		tail=new Node(0,null,null);
		size=0;
	}
	@Override
	public void insert(Object item, int key) {
		 if (key <= 0) {
	            throw new UnsupportedOperationException();
	        }
	        if (size == 0) {
	            Node n = new Node(key, item, null);
	            head = n;
	            tail = n;
	            size++;
	        } else if (size == 1) {
	            Node n = new Node(key, item, null);
	            if (key >= head.getkey()) {
	                tail = n;
	                head.setnext(tail);
	                size++;
	            } else if (key <= head.getkey()) {
	                n.setnext(head);
	                head = n;
	                size++;
	            }
	        } else {
	            Node n = new Node(key, item, null);
	            if (key >= tail.getkey()) {
	                tail.setnext(n);
	                tail = n;
	                size++;
	            } else if (key < head.getkey()) {
	                n.setnext(head);
	                head = n;
	                size++;
	            } else {
	                Node h = head;
	                while (key >= (h.getnext()).getkey()) {
	                    h = h.getnext();
	                }
	                n.setnext(h.getnext());
	                h.setnext(n);
	                size++;
	            }

	        }
		
	}

	@Override
	public Object removeMin() {
	      if (size == 0) {
	            throw new UnsupportedOperationException("empty");
	        } else {
	            Object temp = head.getelement();
	            head = head.getnext();
	            size--;
	            return temp;
	        }
	}

	@Override
	public Object min() {
		if (size == 0) {
            throw new UnsupportedOperationException("empty");
        }
        return head.getelement();
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public int size() {
		return size;
	}

	

}
