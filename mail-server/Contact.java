package mailServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Contact implements IContact {

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	
	public String getname(){
	  return username;  
	}
	
	public String getpassword(){
	  return password;  
	}
	
	public String getfirstname(){
	  return firstname;  
	}
	
	public String getlastname(){
	  return lastname;  
	}
	
	public String getemail(){
	  return email;  
	}
	
	public void setname(String n){
	  username=n;  
	}
	
	public void setpassword(String p){
	  password=p;  
	}
	
	public void setfirstname(String f){
	  firstname=f;  
	}
	
	public void setlastname(String l){
	  lastname=l;  
	}
	
	public void setemail(String e){
	  email=e.toLowerCase();  
	}
	public void add() {
		String path="src\\contacts\\"+email;
		new File(path).mkdir();
		try {
			new File("src\\contacts\\"+username+".txt").createNewFile();
			
			FileWriter data1 = new FileWriter(path+"\\info.txt",true);
			data1.append(email+"\n"+password+"\n"+username+"\n"+firstname+
					"\n"+lastname+"\n");
			data1.close();
		} catch (IOException e) {
				System.out.println("error , file wasn't created");
		}
	}

}