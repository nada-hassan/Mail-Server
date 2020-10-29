package mailServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList.Dlinked_Node;
import eg.edu.alexu.csd.datastructure.stack.Stack;

public class Filter implements IFilter {
	String fmode;
	public static String semode;
	public String skey;
	String fkey;
	DoublyLinkedList body=new DoublyLinkedList(), org= new DoublyLinkedList(),
			sel=new DoublyLinkedList();
	
	public void setFilter(String f,String k) {
		f=f.toLowerCase();
		k=k.toLowerCase();
		fmode=f;
		fkey=k;
	}
	public void setSearch(String s , String k) {
		s=s.toLowerCase();
		k=k.toLowerCase();
		semode = s;
		skey=k;
	}
	public void setList(DoublyLinkedList x) {
		sel=x;
	}
	public DoublyLinkedList getList() {
		return sel;
	}

	
    int binarySearch(DoublyLinkedList arr, String x) throws ParseException 
    { 
    	Stack s = new Stack();
    	s.push(1);
    	s.push(arr.size());
    	int l,r,res;
        while (!s.isEmpty()) { 
        	r=(int) s.pop();
        	l=(int) s.pop();
        	if(l>r) {
        		break;
        	}
            int m = l + (r - l) / 2; 
            if(semode=="date") {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    String s1 = (String) arr.get(m);
		    Date mid= sdf.parse(s1); 
		    Date x1 = sdf.parse(x);
		    res = x1.compareTo(mid); 
            }
            else {
            	res = x.compareTo((String) arr.get(m)); 
            }
            // Check if x is present at mid 
            if (res == 0) 
                return m; 
  
            // If x greater, ignore left half 
            if (res > 0) 
                l = m + 1; 
  
            // If x is smaller, ignore right half 
            else
                r = m - 1; 
            s.push(l);
            s.push(r);
        } 
  
        return -1; 
    } 
    public void setLists(DoublyLinkedList b,DoublyLinkedList o) {
    	body=b;
    	org=o;
    }
    public DoublyLinkedList searchBody() throws ParseException {
    	DoublyLinkedList z = new DoublyLinkedList();
    	Dlinked_Node curr = body.head.next;
    	Sort y = new Sort();
    	int j=1;
    	while(curr.element!=null) {
    		String[] str = ((String) curr.element).split("\\s+");
    		DoublyLinkedList temp = new DoublyLinkedList();
    		for(int i=0;i<str.length;i++) {
    			temp.add(str[i]);
    		}
    		y.setsorted(temp, temp);
    		y.settype("body");
    		y.quicksort();
    		int x=binarySearch(temp,skey);
    		if(x>0) {
    			z.add(org.get(j));
    		
    		}
    		j++;
    		curr=curr.next;
    	}
    	
    	return z;
    }
    public void remove(File inputFile,String lineToRemove) {
		
		File tempFile = new File("src/contacts/myTempFile.txt");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) {
			    	reader.readLine();
			    	continue;
			    }
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 
			inputFile.delete();
			 tempFile.renameTo(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void replace(File inputFile,String lineToReplace,int count) {
		
		File tempFile = new File("src/contacts/myTempFile.txt");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			int c=0;
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(c==count) {
			    writer.write(lineToReplace + System.getProperty("line.separator"));
			    c++;
			    continue;
			    }
			    writer.write(currentLine + System.getProperty("line.separator"));
			    c++;
			}
			writer.close(); 
			reader.close(); 
			inputFile.delete();
			 tempFile.renameTo(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
