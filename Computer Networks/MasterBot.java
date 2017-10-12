/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.*;

import java.time.LocalDateTime;

import java.util.ArrayList;



/**
 *
 * @author Prashanth
 */
public class MasterBot implements Runnable {
	static ArrayList<Socket> Lists= new ArrayList<>();
	static ArrayList<String> slavedate= new ArrayList<>();
	static ArrayList<String> slavenames = new ArrayList<>();
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	
    	 
    	    MasterBot MasterObject = new MasterBot();
    	    Thread t= new Thread(MasterObject);
    	    t.start();
    	    
    	   
    	    
    	    
    	    
    	    
    	    
    		   
    	    try {
    		    ServerSocket s1= new ServerSocket(Integer.parseInt(args[1]));
    		    String name;
    		       		    
    		    while(true){
    		    Socket s = s1.accept();
    		    s.setKeepAlive(true);
    		    Lists.add(s);
    		    slavedate.add( LocalDateTime.now().toLocalDate().toString());
    		    BufferedReader inchannel = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		    name = inchannel.readLine();
    		    slavenames.add(name);
    	        		    
    		    }
    		    }   
    		    catch (IOException e) {System.out.println(e); }
          
          
    	   }

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
		
		
			String a="";
			String[] command;
		Socket s = null;
		while(true){
		try {
			System.out.println("");
			System.out.print(">");
			a=br1.readLine();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		command= a.split(" ");
		if(a.equals("list")){
			if(Lists.size()>0){
			for (int i=0;i<Lists.size();i++){
				s=Lists.get(i);
				System.out.println(slavenames.get(i)+" "+s.getPort()+" "+s.getInetAddress().toString()+" "+slavedate.get(i));
				
			}
			
			}
			else
			{
			System.out.println("no host connected");	
			}
			
		}
		
		else if(command[0].equals("connect")||command[0].equals("disconnect")){
			
			if (command[1].equals("all"))
			{
				for(int i=0;i<Lists.size();i++){
					s = Lists.get(i);
					DataOutputStream dout;
					try {
						dout = new DataOutputStream(s.getOutputStream());
						dout.writeBytes(a+"\n");
						dout.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			else {
				try {
					
					{
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
							
							DataOutputStream dout;
							System.out.println(command[1]+" "+s.getInetAddress().getLocalHost().getHostName()+" "+s.getInetAddress().toString());
							if (command[1].equals(slavenames.get(i)) || ('/'+command[1]).equals(s.getInetAddress().toString())){
								try {
									System.out.println("Sending data to slave!");
								dout = new DataOutputStream(s.getOutputStream());
								dout.writeBytes(a+"\n");
								dout.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						}
						}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
			
				try {
					if (command[1]== s.getInetAddress().getLocalHost().getHostName() || command[1]== s.getInetAddress().toString())
					{
						DataOutputStream dout;
						try {
							dout = new DataOutputStream(s.getOutputStream());
							dout.writeBytes(a+"\n");
							dout.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
					}
					else{
					
}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		else if(command[0].equals("ipscan"))
		{   final String[] com = command;
			final String a1 = a;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(com[1].equals("all"))
					{	
							Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
							DataOutputStream dout;
							try {
							dout = new DataOutputStream(s.getOutputStream());
							dout.writeBytes(a1+"\n");
							dout.flush();    	    
							}catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
}
				}
					else {
						Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
									
							DataOutputStream dout;
							
							if (com[1].equals(slavenames.get(i)) || ('/'+com[1]).equals(s.getInetAddress().toString())){
								try {
									System.out.println("Sending data to slave!");
								dout = new DataOutputStream(s.getOutputStream());
								dout.writeBytes(a1+"\n");
								dout.flush();
								
								BufferedReader in= new BufferedReader( new InputStreamReader(s.getInputStream()));
	                            String info="";
	                            while((info=in.readLine())!=null){
	                            	System.out.println(info);
	                            }
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}}
							}
								}
						}
					
				}).start();
			
		}
		else if(command[0].equals("tcpportscan")){
			final String[] com = command;
			final String a1 = a;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					// TODO Auto-generated method stub
					if(com[1].equals("all"))
					{	
						Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
							DataOutputStream dout;
							try {
							dout = new DataOutputStream(s.getOutputStream());
							dout.writeBytes(a1+"\n");
							dout.flush();    	    
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							}}		
					else {
						Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
									
							DataOutputStream dout;
							
							if (com[1].equals(slavenames.get(i)) || ('/'+com[1]).equals(s.getInetAddress().toString())){
								try {
									System.out.println("Sending tcp port scan command to slave!");
									dout = new DataOutputStream(s.getOutputStream());
									dout.writeBytes(a1+"\n");
									dout.flush();
									
									BufferedReader in= new BufferedReader( new InputStreamReader(s.getInputStream()));
		                            String info="";
		                            while((info=in.readLine())!=null){
		                            	System.out.println(info);
		                            }
								     }  catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						}

							}
				}
			}).start();
		}
		
		else if(command[0].equals("geoipscan"))
		{   final String[] com = command;
			final String a1 = a;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(com[1].equals("all"))
					{	
							Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
							DataOutputStream dout;
							try {
							dout = new DataOutputStream(s.getOutputStream());
							dout.writeBytes(a1+"\n");
							dout.flush();    	    
							}catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
}
				}
					else {
						Socket s;
						for(int i=0;i<Lists.size();i++){
							s = Lists.get(i);
									
							DataOutputStream dout;
							
							if (com[1].equals(slavenames.get(i)) || ('/'+com[1]).equals(s.getInetAddress().toString())){
								try {
									System.out.println("Sending data to slave!");
								dout = new DataOutputStream(s.getOutputStream());
								dout.writeBytes(a1+"\n");
								dout.flush();
								
								BufferedReader in= new BufferedReader( new InputStreamReader(s.getInputStream()));
	                            String info="";
	                            while((info=in.readLine())!=null){
	                            	System.out.println(info);
	                            }
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								
							}}
							}
								}
						}
					
				}).start();
			/* try {
			BufferedReader in= new BufferedReader( new InputStreamReader(s.getInputStream()));
            String info="";
           
				while((info=in.readLine())!=null){
					System.out.println(info);}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	    	
		    
		}
		}
		
		
		
		
		}
	
	
    



    
              	
