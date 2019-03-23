import java.util.Scanner;

public class Protocolc {

	
	public String Thing() {
		
		
		while(true) {
			
			System.out.println("Give your id \n");	
			String id = this.keyread(0);
			
			this.Choice();		
			String clientmsg = this.keyread(0);
			//analhpsh			
			if(clientmsg.contains("1")) {
				System.out.println("Give the quantity you wish to withdraw: \n");	
				String number= this.keyread(0);
				int num = Integer.parseInt(number);
				if(num <= 420) {
					this.keyread(1);
					return "1." + id + "." + number;
					
				}else {
					// o client zhthse parapanw apo to orio opote mynhma la8ous kai ksanarxizei thn diadikasia
					System.out.println("Can't withdraw above the weekly amount\n");
					continue;					
				}
					
			}
			//kata8esh
			else if(clientmsg.contains("2")) {
				System.out.println("Give the quantity you wish to store: \n");
				//diavazei enan ari8mo kai ton stelnei pisw na stalei ston server opou kai 8a ginoun oi katalhles energies
				String s = this.keyread(0);
				this.keyread(1);
				return "2." + id + "." + s;
				
			}
			//enimerwsh logariasmou stelenei mono oti 8elei pisw ta stoixeia tou logariasmou
			else if(clientmsg.contains("3")) {
				this.keyread(1);
				return "3." + id;
			}else if(clientmsg.contains("4")) {
				this.keyread(1);
				return "exit";
			}
			//an den epilegei mia apo tis epiloges tote epanerxete sthn prwth katastash
			else {
				System.out.println("Please give one of the options: \n");
				continue;
				
			}
	
		}
		
		
	}
	
	public void Choice() {
		System.out.println("\nHello and welcome ,client");
		System.out.println("Please choose one of the below \n");
		System.out.println("1. Withdrawal");
		System.out.println("2. Deposit");
		System.out.println("3. Info of the account");
		System.out.println("4. Exit");
	}
	
@SuppressWarnings("resource")
public String keyread(int flag0){
		
		Scanner keyboard = new Scanner(System.in);
		//exoume kai ena flag to opoio otan ginei otidipote pera apo 0 tote kleinei to Scanner keyboard
		if(flag0 == 0){			 
		return keyboard.nextLine();
		}else{
			keyboard.close();
		return null; 
		}
	}
	
	 
}
