package eg.edu.alexu.csd.datastructure.mailServer.cs76_88;

public class Node {
int key;
Object element;
Node next;


public Node(int k, Object o, Node n) {
	key=k;
	element=o;
	next=n;
}
public Object getelement() {
	return element;
}
public Node getnext() {
	return next;
}
public int getkey() {
	return key;
}
public void setelement(Object e) {
	element =e;
}
public void setnext(Node n) {
	next=n;
}
public void setkey(int k) {
	key=k;
}
}
