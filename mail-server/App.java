package mailServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;
import java.util.stream.Stream;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.DoublyLinkedList.Dlinked_Node;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.ILinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.SingleLinked;
import eg.edu.alexu.csd.datastructure.queue.Queue;

public  class App implements IApp {
	private Set set = new Set();
	public String[] list= new String[10];
	/**
	* Create new account
	* @param contact
	* @return false if the email name already exist
	*/
	public boolean signup(IContact contact) {
		String username=((Contact) contact).getname(),
		mail=((Contact) contact).getemail();
		if(username==null) {
			throw new RuntimeException("You have to enter a username");
		}
		else if(mail==null) {
			throw new RuntimeException("You have to enter an email");
		}
		else if(((Contact) contact).getpassword()==null) {
			throw new RuntimeException("You have to enter a password");
		}
        // Get the file 
        File f = new File("src\\contacts\\"+mail); 
        // Check if the specified user mail exists or not
        if (f.exists()) {
        	throw new RuntimeException("You used this email before !");
        	//System.out.println("exisit mail");
          //  return false; 
        }
        //check if the specified name exists or not
         f = new File("src\\contacts\\"+username+".txt"); 
        if (f.exists()) {
        	throw new RuntimeException("You used this email before !");
        	//System.out.println("exisit name");
          // return false; 
        }
       ((Contact)contact).add();
       try{
       String path ="src/contacts/"+mail+"/";
       new File(path+"inbox").mkdir();
       new File(path+"inbox/index.txt").createNewFile();
       new File(path+"sent").mkdir();
       new File(path+"sent/index.txt").createNewFile();
       new File(path+"draft").mkdir();
       new File(path+"draft/index.txt").createNewFile();
       new File(path+"starred").mkdir();
       new File(path+"starred/index.txt").createNewFile();
       new File(path+"trash").mkdir();
       new File(path+"trash/index.txt").createNewFile();
		} catch (IOException e) {
			System.out.println("error , file wasn't created");
	}
        
        return true;
    } 
	/**
	* Sign in to the application
	* @param email
	* @param password
	* @return false if the email name not exist
	*/
	public boolean signin(String email, String password) {
		email=email.toLowerCase();
		if (email == null || password == null) {
			return false;
		}
		File f1 = new File("src\\contacts\\" + email);
		if (f1.exists()) {
			File f2 = new File("src/contacts/" + email + "/Info.txt");
			String Email = new String();
			String Pass = new String();
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(f2));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				return false;
			}
			
				try {
					Email = in.readLine();
					Pass = in.readLine();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (Email.equals(email) && Pass.equals(password)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	* This function should be called before reading from the index file
	* and apply the sort and search parameters
	* @param folder currently shown, can be null
	* @param filter to apply search, can be null
	* @param sort to apply sort
	*/
public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
		
	    if(folder==null) {
	        return;
	    }
		DoublyLinkedList x = ((Folder) folder).getx();
		if(x.isEmpty()){
		    return;
		}
		if(filter==null&&sort!=null){  //sort only
			 if(Sort.Type==null) {
		        return;
		    }
		   switch(Sort.Type){
		    case "sender":
		        Sort.setsorted(((Folder) folder).getSender(),((Folder) folder).getx());
		        break;
		    case "subject":
		        Sort.setsorted(((Folder) folder).getSubject(),((Folder) folder).getx());
		        break;
		  	case "date":
		        Sort.setsorted(((Folder) folder).getDate(),((Folder) folder).getx());
		        break;
		    case "reciever":
		        Sort.setsorted(((Folder) folder).getReciever(),((Folder) folder).getx());
		        break;
		    case "attachment":
		        Sort.setsorted(((Folder) folder).getattachments(),((Folder) folder).getx());
		        break;
		    case "priority":
		        Sort.setsorted(((Folder) folder).getpriority(),((Folder) folder).getx());
		        break;    
		    default:
		        return;
		   }
		   
	      if(Sort.Type == "priority") {
	    	  ((Sort) sort).sortpriority();;
	      }else {
		   try{
		   ((Sort) sort).quicksort();
		   }catch(ParseException e){
		       
		   }}
		}
		else if(filter!=null&&sort==null){ //filter
			
		    DoublyLinkedList list = new DoublyLinkedList();
		    DoublyLinkedList org = ((Folder) folder).getx();
		    DoublyLinkedList body = ((Folder) folder).getBody();
		    Folder des =(Folder) ((Folder)folder).getDes();
		    int lastIndex=des.getIndex();
		    if(((Filter)filter).fmode==null)
		        return;
		    switch(((Filter)filter).fmode){
		    case "sender":
		      list= ((Folder) folder).getSender();
		      break;
		    case "subject":
		        list=((Folder) folder).getSubject();
		        break;
		    case "selected":
		    	list=((Filter)filter).getList();
		    	break;
		    default:
		        return;
		   }
		   String pathD="src/contacts/"+((Folder)folder).getMail()+"/"+des.getFolderName()+"/";
		   		//get last folder name  
		   if(((Filter)filter).fmode.compareTo("selected")==0) {
			   Dlinked_Node curr = list.head.next;
			   while(curr.element!=null){
				   
				   String pathS = (String) curr.element;
				   File sr = new File(pathS);
				   File de=new File(pathD);
				  ((Folder)folder).copy(sr, de);
				  //get info and add to index
					try {
						FileReader fr = new FileReader(pathS+"/info.txt"); //read from info 
						FileReader fr1 = new FileReader(pathS+"/body.txt");//read body
						BufferedReader br = new BufferedReader(fr);
						BufferedReader br2 = new BufferedReader(fr1);
						FileWriter fw = new FileWriter(pathD+"/index.txt", true);//write in the index of des folder
						String s;
						int i=0;
						while ((s = br.readLine()) != null) { // read a line
							if(i==4) {
								fw.write(lastIndex+"/");
								i++;
								continue;
							}
							fw.write(s+"/"); // write to output file
							fw.flush();
							i++;
						}
						File z=new File(pathS+"/attachments");
						String[]temp=z.list();
						for(int j=0;j<temp.length;j++) {
							fw.write(temp[j]);
							if(j<temp.length-1) {
								fw.write("+");
							}
						}
						fw.append("\n"+br2.readLine()+"\n");
						br2.close();
						br.close();
						fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					File inputFile=new File(pathD+Integer.toString(lastIndex)+"/info.txt");
					((Filter)filter).replace(inputFile, Integer.toString(lastIndex), 4);
				   curr=curr.next;
				   lastIndex++;
			   }
			}
		   else {
			   int x1 =0;
			   String pathS = "src/contacts/"+((Folder) folder).getMail()+"/"+((Folder) folder).getFolderName()+"/";
			   File de= new File(pathD);
			   while(!list.isEmpty()&&x1!=-1){
				   
			       try {
					x1=((Filter)filter).binarySearch(list,((Filter)filter).fkey);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			       if(x1!=-1){
	    		      String[] str= ((String)org.get(x1)).split("/");
	    		      File s=new File(pathS+str[4]);
	    		      str[4]=Integer.toString(lastIndex);
	    		      String temp= str[0]+"/"+str[1]+"/"+str[2]+"/"+str[3]+"/"+str[4]+"/"+str[5]+"/";
	    		      if(str.length>6) {
	    		    	  temp=temp+str[6];
	    		      }
	    		      org.set(x1,temp);
	    		      ((Folder) folder).copy(s,de);
	    		      try {
	    		    	  //write on index destination
	        			FileWriter data1 = new FileWriter(pathD+"\\index.txt",true);
	        			System.out.println(org.get(x1)+"    "+body.get(x1));
	        			data1.append((String)org.get(x1));
	        			data1.append("\n"+(String)body.get(x1)+"\n");
	        			
	    		    	data1.close();
	    	       	} catch (IOException e) {
	    				System.out.println("error");
	    		    }
						File inputFile=new File(pathD+Integer.toString(lastIndex)+"/info.txt");
						((Filter)filter).replace(inputFile, Integer.toString(lastIndex), 4);
	    		      lastIndex++;
			           list.remove(x1);
			           org.remove(x1);
			           body.remove(x1);
			       }
			   }
		   }
		    	   
		    	  }    
		}
	/**
	* You should use setViewingOptions function first
	* @param page to handle paging
	* @return list of emails
	*/
	public IMail[] listEmails(int page) {
		//initialize list for gui
		for(int i =0;i<10;i++) {
		list[i]="  -  ";
		}
		//setview
		DoublyLinkedList sorted =new DoublyLinkedList();
		Folder folder = (Folder) set.getFolder();
		Sort sort = (Sort) set.getSort();
		Filter filter = (Filter) set.getFilter();
		if(filter==null) {
		setViewingOptions(folder,null,sort);
		sorted= sort.getOriginal();
		}
		else {
			sorted = search(folder,filter);
		}
		int start=10*(page-1)+1,end=page*10;
		IMail[] res= new Mail[10] ;
		for(int i=0,j=start;i<10&&j<=end&&j<=sorted.size();i++,j++) {
			String[] str=sorted.get(j).toString().split("/");
			IMail x= new Mail();
			((Mail) x).setFrom(str[0]);
			((Mail) x).setSubject(str[1]);
			((Mail) x).setDate(str[2]);
			Queue z=new Queue();
			z.enqueue(str[3]);
			((Mail) x).setTo(z);
			((Mail) x).setFolderName(str[4]);
			((Mail) x).setPriority(Integer.parseInt(str[5]));
			SingleLinked att=new SingleLinked();
			if(str.length>6) {
			String[] a = str[str.length-1].split("\\+");
			for(int i1=0;i1<a.length;i1++) {
				att.add(a[i1]);
			}
			}
			((Mail)x).setAttachments(att);
			
			//body
			File b = new File("src/contacts/"+str[3]+"/inbox/"+str[4]+"/body.txt");
			try {
				FileReader bb =new FileReader(b);
				BufferedReader bbb = new BufferedReader(bb);
				((Mail) x).setbody(bbb.readLine());
				bbb.close();
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//list used in gui
			if(folder.getFolderName().compareTo("sent")==0) {
				list[i]="   "+str[1]+"	 ................   "+str[3]+"	 ................   "+str[2];
			}
			else {
			list[i]="   "+str[1]+"	 ................   "+str[0]+"	 ................   "+str[2];
			}
			res[i]=x;
		}

		if(folder.x.size()==0||sorted.get(1)==null) {
			for(int i =0;i<10;i++) {
			list[i]="  -  ";
			}
		}
		return res;
	}
	/**
	* You should use setViewingOptions function first
	* @param mails to be moved to trash
	*/
	public void deleteEmails (ILinkedList mails) {
		Folder des = new Folder();
		des.setFolderName("trash");
		moveEmails(mails,des);
	}
	/**
	* You should use setViewingOptions function first
	* @param mails to be moved
	* @param des the destination folder
	*/
	public void moveEmails(ILinkedList mails, IFolder des) {
		File s=new File ((String) ((DoublyLinkedList)mails).get(1));
		Folder folder = new Folder();
		folder.setDes(des);
		folder.setFolderName(s.getParentFile().getName());
		folder.setMail(s.getParentFile().getParentFile().getName());
		folder.readIndex();
		((Folder) des).setMail(folder.getMail());
		folder.des.readIndex();
		Filter filter = new Filter();
		filter.fmode="selected";
		filter.setList((DoublyLinkedList) mails);
		setViewingOptions(folder,filter,null);
		//delete mails from original folder and index
		Dlinked_Node curr = ((DoublyLinkedList)mails).head.next;
		File index = new File("src/contacts/"+folder.getMail()+"/"+folder.getFolderName()+"/index.txt");
		while(curr.element!=null) {
		s= new File((String) curr.element);
		String line ,  // get the line 
				path=folder.getFolderName()+"/"+s.getName() ;
		StringBuilder x =new StringBuilder();
		try {
			FileReader fr1 = new FileReader(curr.element+"/info.txt");
			BufferedReader br2 = new BufferedReader(fr1);
			String s1;
			while ((s1 = br2.readLine()) != null) { // read a line
				x.append(s1);
				x.append("/");
				
			}
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File z=new File(curr.element+"/attachments");
		String[]temp=z.list();
		for(int j=0;j<temp.length;j++) {
			x.append(temp[j]);
			if(j<temp.length-1) {
				x.append("+");
			}
		}
		line =x.toString();
		filter.remove(index,line);//remove info and body
		folder.delete(path);//old=foldername+foldermail
		curr=curr.next;
		 



		}
	}
	/**
	* Send a new email
	* @param email should contain all the information needed
	* sender, list of receivers, list of attachments, email body, ...
	* @return false if something wrong happened like sending to non-existing user.
	*/
	public boolean compose(IMail email) {
    	String to = new String();
    	Queue q=new Queue();
		while (! ((Mail)email).getTo().isEmpty()) {
			to = (String) ((Mail)email).getTo().dequeue();
			q.enqueue(to);
			  File f = new File("src\\contacts\\"+to); 
			if (!f.exists()) {
				return false;
			}
		}
		((Mail)email).setTo(q);
		((Mail)email).send();
		return true;
	}
	@Override
	public void deleteEmails(mailServer.ILinkedList mails) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveEmails(mailServer.ILinkedList mails, IFolder des) {
		// TODO Auto-generated method stub
		
	}
	public void intializeSet(Set s) {
		set=s;
	}
	public String[] stringmail() {
		return list;
	}
	public DoublyLinkedList search(IFolder folder,IFilter filter) {
	    DoublyLinkedList res=new DoublyLinkedList();
	    if(folder==null) {
	        return res;
	    }
		DoublyLinkedList x = ((Filter)filter).org;
		if(x.isEmpty()){
		    return res;
		}
		    DoublyLinkedList list = new DoublyLinkedList();
		    DoublyLinkedList org = ((Folder) folder).getx();
		    if(((Filter)filter).semode==null) {
		        return res;
		    }
		    switch(((Filter)filter).semode){
		    case "sender":
		      list= ((Folder) folder).getSender();
		      break;
		    case "subject":
		        list=((Folder) folder).getSubject();
		        break;
		    case "date":
		    	list =((Folder) folder).getDate();
		    	break;
		    case "body":
		    	try {
					res=((Filter)filter).searchBody();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("something went wrong");
				}
		    	return res;
		    case "reciever":
		    	list =((Folder) folder).getReciever();
		    	break;
		    case "attachments":
		    	list =((Folder) folder).getattachments();
		    	break;
		    default:
		        return res;
		    }
			   int x1 =0;
			   while(!list.isEmpty()&&x1!=-1){
			       try {
					x1=((Filter)filter).binarySearch(list,((Filter)filter).skey);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			       if(x1!=-1){
	    		      res.add(org.get(x1));
			           list.remove(x1);
			           org.remove(x1);
			       }
			   }
		    	  
		
		return res;  
	}


}
