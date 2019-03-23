 import java.net.*;
 import java.io.*;
 
public class server {
	
	private  String[] ooo;
	
	
	public static void main(String args[]) throws IOException{
		ServerSocket connectionSocket = null;
		int connectionCount = 0;
		
		
		
		try {
            connectionSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            //System.exit(1);
        }
		
        while(true) { 
		Socket dataSocket = null;
        	
		 try {
			   dataSocket  = connectionSocket.accept();
	           
	        } catch (IOException e) {
	            System.err.println("Accept failed.");
	          //  System.exit(1);
	        }
		connectionCount++;
		System.out.println("Received " + connectionCount + " request from " + connectionSocket.getInetAddress());
			
		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os, true);
		//diadikastika anoigoume input k output 
		
		String line = in.readLine();
		System.out.println("Received message from client: " + line);
		
		server serv = new server();
		serv.ooo = serv.splits(line);
			
		//an pernei ton xarakthra != stamataei thn sundesh
		if(line.equals("!=")) {
			dataSocket.close();
			connectionSocket.close();
			System.out.println("Data socket closed");
		}
		
		int flag = 0;
		
		
		//main 
		while(!line.equals("!=")){			
			
			if(!serv.ooo[0].equals("+") && !serv.ooo[0].equals("-") && !serv.ooo[0].equals("/") && !serv.ooo[0].equals("*")) {
				flag++;
			}
			if(!serv.ooo[1].matches("\\d+") && !serv.ooo[2].matches("\\d+"))
				flag++;
			
			
			int ari8mos1 = Integer.parseInt(serv.ooo[1]);
			int ari8mos2 = Integer.parseInt(serv.ooo[2]);
			int praksh = 0;
			
			
			if(flag == 2 || flag == 1) {
				out.printf("wrong String ,send again!\n");
				break;
			}else {
				
				if(serv.ooo[0].equals("+")) {
					praksh = ari8mos1 + ari8mos2;
					
				}else if(serv.ooo[0].equals("-")) {
					praksh = ari8mos1 - ari8mos2;
					
				}else if(serv.ooo[0].equals("/")) {
					praksh = ari8mos1 / ari8mos2;
					
				}else if(serv.ooo[0].equals("*")) {
					praksh = ari8mos1 * ari8mos2;
					
				}
			}
			
			if(flag != 2) {
		      out.printf("The result of the operator %s with the numbers %s and %s is %d\n",
		    		  serv.ooo[0], serv.ooo[1], serv.ooo[2], praksh );
		      
		      dataSocket.close();
		      connectionSocket.close();
		      System.out.println("Data socket closed");
		      
		      break;
		    }
			
			  dataSocket.close();
		      connectionSocket.close();
		      System.out.println("Data socket closed");
		}
				
		//to break einai gia na mhn menei se katastash anamonhs o server ksana , otan 8eloume 
		//na trexei monima o server(katastash anamonhs xrhsth) to svinoume to break kai tote mporoume na kanoume kleisimo
		//apo ton user stelnontas ton xarakthra !=
		break;
		}
				
	}
	
	public  String[]  splits(String s) {
        
	       //diavazei dedomena se style  praksh ari8mos ari8mos 
	       String[] y = s.split(" ");
	        
	        return y;
		
	}

}
