package mailServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList;

import eg.edu.alexu.csd.datastructure.stack.Stack;

public class Sort implements ISort{
	static String Type;
	static DoublyLinkedList sorted = new DoublyLinkedList();
	static DoublyLinkedList o=new DoublyLinkedList();
	
	public static void setsorted(DoublyLinkedList d,DoublyLinkedList original) {
		sorted = d;
		o= original;
	}
	public void settype(String s) {
		Type= s.toLowerCase();
		if(Type.compareTo("date") != 0 && Type.compareTo("sender") != 0 && Type.compareTo("reciever") != 0  && Type.compareTo("subject") != 0
				&& Type.compareTo("body") != 0 && Type.compareTo("attachment") != 0 && Type.compareTo("priority") != 0) {
			throw new RuntimeException("invalid type");
		}
	}
	 static int partitionDate( int low, int high) throws ParseException 
	 { 
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    String s = (String) sorted.get(high);
		    Date pivot = sdf.parse(s); //pivot 
	     int i = (low-1); // index of smaller element 
	     for (int j=low; j<high; j++) 
	     { 
	         // If current element is smaller than the pivot 
	    	 s=(String) sorted.get(j);
	    	 Date d = sdf.parse(s);
	         if (pivot.compareTo(d)>0) 
	         { 
	             i++; 

	             // swap i and j
	            Object temp = (String) sorted.get(i); 
	            sorted.set(i, sorted.get(j));; 
	             sorted.set(j, temp); 
	             Object temp1 = (String) o.get(i); 
	             o.set(i, o.get(j)); 
	             o.set(j, temp1);
	         } 
	     } 

	     // swap i+1 and high
	    Object temp = (String) sorted.get(i+1); 
	     sorted.set(i+1, sorted.get(high));; 
	     sorted.set(high, temp); 
	    Object temp1 = (String) o.get(i+1); 
	     o.set(i+1, o.get(high));; 
	     o.set(high, temp1); 
	     return i+1; 
	 } 
	 /************************************/
	 static int partition( int low, int high) 
	 { 
	     String pivot = (String) sorted.get(high);  
	     int i = (low-1); // index of smaller element 
	     for (int j=low; j<high; j++) 
	     { 
	         // If current element is smaller than the pivot 
	         if (pivot.compareTo((String) sorted.get(j))>0) 
	         { 
	             i++; 

	             // swap i and j
	             String temp = (String) sorted.get(i); 
	             sorted.set(i, sorted.get(j));; 
	             sorted.set(j, temp); 
	             String temp1 = (String) o.get(i); 
	             o.set(i, o.get(j));; 
	             o.set(j, temp1);
	         } 
	     } 

	     // swap [i+1] and [high] (or pivot) 
	     String temp = (String) sorted.get(i+1); 
	     sorted.set(i+1, sorted.get(high));; 
	     sorted.set(high, temp); 
	     String temp1 = (String) o.get(i+1); 
	     o.set(i+1, o.get(high));; 
	     o.set(high, temp1); 
	     return i+1; 
	 } 

	 
	 void quicksort() throws ParseException 
	 { 
	     // Create an auxiliary stack  
	     Stack s = new Stack();
	     // initialize top of stack 
	     int l,h; 

	     // push initial values of l and h to stack 
	     s.push(1);
	     s.push(sorted.size()); 

	     // Keep popping from stack while is not empty 
	     while (!s.isEmpty()) { 
	         // Pop h and l 
	         h = (int) s.pop(); 
	         l = (int) s.pop(); 
	         int p;
	         // Set pivot element at its correct position 
	         // in sorted linkedlist
	         if(Type=="date") {
	          p = partitionDate(l, h); 
	         }
	         else {
	        	  p = partition(l, h);  
	         }

	         // If there are elements on left side of pivot, 
	         // then push left side to stack 
	         if (p - 1 > l) { 
	             s.push(l);; 
	             s.push(p-1);; 
	         } 

	         // If there are elements on right side of pivot, 
	         // then push right side to stack 
	         if (p + 1 < h) { 
	             s.push(p+1); 
	             s.push(h);  
	         } 
	     } 
	 }
	 public void sortpriority() {
			
			PriorityQueue p = new PriorityQueue();
			while (!o.isEmpty() && !sorted.isEmpty()) {
				p.insert(o.get(1), (int) sorted.get(1));
				o.remove(1);
				sorted.remove(1);
			}
			
			while(!p.isEmpty()) {
			sorted.add(p.head.getkey());	
			o.add(p.removeMin());
			
			}
		}
	 public DoublyLinkedList getSub() {
		 return sorted;
	 }
	 public  DoublyLinkedList getOriginal() {
		 return o;
	 }
	 

}
