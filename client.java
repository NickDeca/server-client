import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;


public class client {
	
	private static final int PORT = 1234;
	private  String[] ooo;
	
	public static void main(String[] args) throws IOException
	{	 
		    Socket dataSocket = null;
		    PrintWriter out1 = null;
		    BufferedReader in = null;
		    
		    try {
	        dataSocket = new Socket(InetAddress.getLocalHost(),PORT);
	        System.out.println("Connection established");

	        InputStream is = dataSocket.getInputStream();
	        in = new BufferedReader(new InputStreamReader(is));

	        OutputStream os = dataSocket.getOutputStream();
	         out1 = new PrintWriter(os, true);
		    } catch (UnknownHostException e) {
	            System.err.println("Don't know about host: localhost.");
	            System.exit(1);
		    }  catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to: localhost.");
	            System.exit(1);
	        }
	        
            //arxiko string p grafei o client apo keyboard gia na mpei sto loop
	      	
	        client c = new client();
	      	String s = c.keyread();
	      	c.ooo = c.splits(s);

	        int flag = 0;
	        
	        if(s.equals("!=")) {
	        	out1.println(s);	        	
	        }
	        
	        //main 
	        while(!s.equals("!=")){
	        	
	        	//elenxos tou string p grafei o client
	        	if(!c.ooo[0].equals("+") && !c.ooo[0].equals("-") && !c.ooo[0].equals("/") && !c.ooo[0].equals("*")) {
	        		System.out.println("Wrong operator");
           		    s = c.keyread();
	        		c.ooo = c.splits(s);
	        		continue;
	        		//edw allazei to string mesa sto loop kai ksanapaei sthn arxh toy loop
	        	}else {
	        		System.out.printf("the opperator chosen is %s\n",c.ooo[0]);
	        		flag++;
	        		
	        	}
	        	
	        	if(!c.ooo[1].matches("\\d+") && !c.ooo[2].matches("\\d+")){

	        		System.out.println("the operation requires a number\n");
	        		s = c.keyread();
	        		c.ooo = c.splits(s);
	        		continue;
	        	}else {
	        		System.out.printf("the numbers given are %s and %s\n",c.ooo[1],c.ooo[2]);
	        		flag++;
	        	}
	        	
	        	//an perasei kai apo tous 2 elenxous kanei break kai stelnei ston client to string
	        	if(flag == 2) {
	        		out1.println(s);
	        		break;
	        	}else 
	        		//estw peraasei apo ton prwto elenxo k to flag ginei 1 kai dn perasei apo ton 2o
	        		//tote allazei to string p elenxoume erxete edw k kanei reset to flag ksekinontas
	        		//apo thn arxh , to idio k an apotuxei ton 2o elenxo mono
	        		flag = 0;
	        }
	        
	        String line = in.readLine();
			System.out.println("\nReceived message from server: " + line);
			
			//kleishmo tou socket kai termatismos epikoinwnias me server
	      	dataSocket.close();
	        System.out.println("\nData socket closed");

	}

//diavazei apo keyboard	
	public String keyread()  {
		
		String s = null;
		
		Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the value:\t");
        
        s = keyboard.nextLine();

        if(s.equals("!=")) {
        	return "!=";
        }
 
        return s;
       
	}
	
//kaneis split ena string sta kena
	public  String[]  splits(String s) {
	        
	       //diavazei dedomena se style  praksh ari8mos ari8mos 
	       String[] y = s.split(" ");
	        
	        return y;
		
	}

}
