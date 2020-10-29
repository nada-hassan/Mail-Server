package mailServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList.Dlinked_Node;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.Node;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.SingleLinked;

public class Folder implements IFolder{
   public DoublyLinkedList x=new DoublyLinkedList(),body = new DoublyLinkedList();
    private  String mail;
	private  String Fname;
	static Folder des = new Folder();
	static int flag =0,y1=1;
	public void setDes(IFolder d) {
		des=(Folder) d;
	}
	public IFolder getDes() {
		return des;
	}
    public void setFolderName(String name){
        Fname=name;
    }
    public void setMail(String e){
        mail=e;
    }
    public String getFolderName(){
        return Fname;
    }
    public String getMail(){
        return mail;
    }
    public  void readIndex(){
    	
    	
        File f2 = new File("src/contacts/" + mail +"/"+Fname+ "/index.txt");
        BufferedReader in = null;
		try {
		    in = new BufferedReader(new FileReader(f2));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		try {
		    String line = in.readLine();
		    while (line!=null){
		   // System.out.println(line);
		    x.add(line);
		    line=in.readLine();
		    body.add(line);
		    line = in.readLine();
		    }
			in.close();
			
		} catch (IOException e) {
		// TODO Auto-generated catch block
	    	e.printStackTrace();
		}
		
    }
    public DoublyLinkedList getBody() {
    	return body;
    }
    public DoublyLinkedList getx() {
    	return x;
    }
    public DoublyLinkedList getpriority(){
        DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            y.add(str[5]);
            curr=curr.next;
        }
        return y;
    }
    
    public DoublyLinkedList getattachments(){
        DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            y.add(str[6]);
            curr=curr.next;
        }
        return y;
    }
    public DoublyLinkedList getSender(){
        DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            y.add(str[0]);
            curr=curr.next;
        }
        return y;
    }
    public DoublyLinkedList getSubject(){
        DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            y.add(str[1]);
            curr=curr.next;
        }
        return y;
    }
    
     public DoublyLinkedList getDate(){
         DoublyLinkedList y = new DoublyLinkedList();
         Dlinked_Node curr = x.head.next;
         while(curr.element!=null){
             String[] str = ((String)curr.element).split("/");
             y.add(str[2]);
             curr=curr.next;
         }
        return y;
    }
    public DoublyLinkedList getReciever(){
        DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            y.add(str[3]);
            curr=curr.next;
        }
        return y;
    }
    public int getIndex(){
      //  DoublyLinkedList y = new DoublyLinkedList();
        Dlinked_Node curr = x.head.next;
        while(curr.element!=null){
            String[] str = ((String)curr.element).split("/");
            try {
            y1=Integer.parseInt(str[4]);
            }catch(NumberFormatException e) {
            	System.out.println("error  "+str[4]);
            }
            curr=curr.next;
        }
        if(!x.isEmpty()) {
        	y1++;
        }
        return y1 ;
    }
    public void setIndex(int x) {
    	y1=x;
    }
    
    
    public void create(String name){
               try{
       String path ="src/contacts/"+mail+"/"+name;
       File f = new File(path);
       if(!f.exists()){
       new File(path).mkdir();
       new File(path+"/index.txt").createNewFile();
       }
       else{
    	   throw new RuntimeException("this name is already exisited");
       }
		} catch (IOException e) {
			System.out.println("error , file wasn't created");
	}
    
     }
    public void rename(String old ,String n){
        old=old.toLowerCase();
        String[] str={"inbox","sent","draft","trash","starred"};
        for(int i=0;i<str.length;i++){
            if(str[i]==old){
                System.out.println("You can't rename main folders");
                return;
            }
        }
        File sourceFile = new File("src/contacts/"+mail+"/"+old);
        if(!sourceFile.exists()) {
        	System.out.println("Couldn't find folder with such name");
        	return;
        }
        File destFile = new File("src/contacts/"+mail+"/"+n);
       if (sourceFile.renameTo(destFile)) {
            System.out.println("Folder renamed successfully");
        } 
        else {
            System.out.println("Failed to rename Folder");
        }
        
    }
    public void delete(String old){
        old=old.toLowerCase();
        String[] str={"inbox","sent","draft","trash","starred"};
        for(int i=0;i<str.length;i++){
            if(str[i]==old){
                System.out.println("You can't delete main folders");
                return;
            }
        }
        File sourceFile = new File("src/contacts/"+mail+"/"+old);
        if(!sourceFile.exists()) {
        	System.out.println("Couldn't find folder with such name");
        	return;
        }
       try {
		if (deleteDirectory(sourceFile)) {
		        System.out.println("Folder deleted successfully");
		    } 
		    else {
		        System.out.println("Failed to delete Folder");
		    }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
        
    }
    boolean deleteDirectory(File file) throws IOException {
    	  if (file.isDirectory()) {
    	    File[] entries = file.listFiles();
    	    if (entries != null) {
    	      for (File entry : entries) {
    	        deleteDirectory(entry);
    	      }
    	    }
    	  }
    	  if (!file.delete()) {
    	    return false;
    	  }
    	  else {return true;}
    	}
    public static void copy(File src,File de) {
		if(flag==0) {
			flag++;
			int s= des.getIndex() ;
			new File(de+"/"+s).mkdir();
			String d = de.toString()+"/"+s;
			de=new File(d);
			
		}
		File[]arr= src.listFiles();
		for(int i=0;i<arr.length;i++) {
			if(arr[i].isDirectory()) {				
				new File(de+"/"+arr[i].getName()).mkdir();
				String d = de.toString()+"/"+arr[i].getName();
				File dd=new File(d);
				copy(arr[i],dd);
				
			}
			else {
				try {
				var source = new RandomAccessFile(arr[i], "r");
		        var dest = new RandomAccessFile(de+"/"+arr[i].getName(), "rw");
		        try (var sfc = source.getChannel();
		             var dfc = dest.getChannel()) {

		            dfc.transferFrom(sfc, 0, sfc.size());
		        }
				}catch(IOException e) {
					
				}
			}
		}
		flag=0;
	}
    public String[] getnames() {
    	File y = new File("src/contacts/"+mail);
    	String[] z=y.list();
    	String[] x= new String[y.list().length-1];
    	for(int i= 0,j=0;i<x.length;i++) {
    		if(z[j].compareTo("info.txt")==0) {
    			j++;
    		}
    		x[i]="  "+z[j]+"  ";
    		j++;
    	}
    	
    	return x;
    }
    
    	

}
