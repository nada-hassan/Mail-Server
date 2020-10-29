package mailServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs76_cs88.SingleLinked;
import eg.edu.alexu.csd.datastructure.queue.Queue;

public class Mail implements IMail{
	private String body;
	private String from;
	private String date;
	private Queue to= new Queue();
	private String subject;
	private SingleLinked attachments = new SingleLinked();
	private int priority;
	private String fname;
	public void setFolderName(String x) {
		fname=x;
	}
	public String getFolderName() {
		return fname;
	}
	public void setbody(String b) {
		body = b;
	}
	public void setFrom(String sender) {
		from = sender;
	}
	public void setTo(Queue receiver) {
		while (receiver != null && !receiver.isEmpty()) {
			to.enqueue((String)receiver.dequeue());
		}
	}
	public void setSubject(String sub) {
		subject = sub;
	}
	public void setAttachments(SingleLinked attach) {
		attachments = attach;
	}
	public void setPriority(int pr) {
		if(pr>0 && pr<5) {
		priority = pr;}
		else {throw new RuntimeException("invalid priority");}
	}

	public String getbody() {
		return body;
	}
	public String getFrom() {
		return from;
	}
	public Queue getTo() {
		return to;
	}
	public String getSubject() {
		return subject;
	}
	public SingleLinked getAttachments() {
		return attachments;
	}
	public int getPriority() {
		return priority;
	}
	
	public boolean send() {
		
		if (from == null || subject == null || to.size() == 0
				|| to == null) {
			return false;
		}
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat();
		dateFormatter = new SimpleDateFormat("d-M-y");
		setDate(dateFormatter.format(now).toString());
				
		int k = 0;
		while (k < to.size()) {
			Folder f1=new Folder();
			f1.setMail(from);
			f1.setFolderName("sent");
			f1.readIndex();
			int index1=f1.getIndex();
		//make a new file in the sender's sent
			
			String p ="src/contacts/"+from+"/sent/"+index1;
		    File f = new File(p);
		    new File(p).mkdir();
		    try {
				new File(p+"/body.txt").createNewFile();
				new File(p+"/info.txt").createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			Folder f2 = new Folder();
			String rto=(String) to.dequeue();
		    File att = new File(p+"/attachments/");
		    att.mkdir();
		    
		    
		    int i=0;
			 while (i < attachments.size()) {
					try {
						var source = new RandomAccessFile(((File)attachments.get(i)), "r");
				        var dest = new RandomAccessFile(att+"/"+((File)attachments.get(i)).getName(), "rw");
				        try (var sfc = source.getChannel();
				             var dfc = dest.getChannel()) {

				            dfc.transferFrom(sfc, 0, sfc.size());
				        }
						}catch(IOException e) {
							
						}
			i++;
			 }
		    
		    
		    
		
		
		//write in index of sent
		
		String path="src\\contacts\\"+from+"\\sent";
		new File(path).mkdir();
		try {
			FileWriter data = new FileWriter(path+"\\index.txt",true);
			data.append(from+"/"+subject+"/"+date+"/"+rto+
					"/"+index1+ "/"+priority+"/");
			int j=0;
			 while (j < attachments.size()) {
			data.append(((File)attachments.get(j)).getName());
			if(j<attachments.size()-1) {
				data.append("+");
			}
			j++;
			 }
			
			data.append("\n"+ body+"\n");

			data.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//write in body and info
		
		try {
			FileWriter data1 = new FileWriter(p+"\\body.txt",true);
			data1.append(body+"\n");
			data1.close();

			FileWriter data2 = new FileWriter(p+"\\info.txt",true);
			data2.append(from+"\n"+subject+"\n"+date+"\n"+rto+"\n"+index1+"\n"+priority+
					"\n");
			data2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String p1 ="src/contacts/"+rto+"/inbox/";
	    File fi = new File(p1);
	    Folder z = new Folder();
	    z.setMail(from);
	    z.setFolderName("sent");
	    z.des.setFolderName("inbox");
	    z.des.setMail(rto);
	    z.des.readIndex();
        z.copy(f, fi);
        int index2 = f2.getIndex();
        File sourceFile = new File("src/contacts/"+rto+"/inbox/"+ index1);
        File destFile = new File("src/contacts/"+rto+"/inbox/"+index2);
        sourceFile.renameTo(destFile);
		//copy mail information in reciever"s inbox
		f2.setMail(rto);
		f2.setFolderName("inbox");
		f2.readIndex();
		
		String path2="src\\contacts\\"+rto+"\\inbox";
		 new File(path2).mkdir();
		try {
			FileWriter data3 = new FileWriter(path2+"\\index.txt",true);
			data3.append(from+"/"+subject+"/"+date+"/"+rto+
					"/"+index2+ "/"+priority+"/");
			int j=0;
			 while (j < attachments.size()) {
			data3.append(((File)attachments.get(j)).getName());
			if(j<attachments.size()-1) {
				data3.append("+");
			}
			j++;
			 }
			
			data3.append("\n"+ body+"\n");
			data3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			k++;
		}
		return true;	
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}