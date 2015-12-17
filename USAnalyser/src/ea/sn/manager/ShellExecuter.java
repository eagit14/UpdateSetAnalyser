package ea.sn.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ShellExecuter {  

	public static void main(String args[]) {  

		Process process;  

		try  
		{ 
			//Process proc = Runtime.getRuntime().exec(command);
//			String command = "perl /Users/elie.abdelnour/scripts/perl/create_incident.pl";
//			String command = "ls -ltr /Users/elie.abdelnour/scripts/";
			String command = "/usr/local/bin/wget \"https://lafamrdev.service-now.com/u_labor_type.do?XML\"  --user=\"sn\" --password=\"sn\"";
			//String[] aCmdArgs = { "perl" , "pl newprg.pl" };
			process = Runtime.getRuntime().exec(command);  
			BufferedReader read = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
			process.waitFor();

			if(process.exitValue() == 0)  
			{  
				System.out.println("Command Successful");  
			}  
			else  
			{  
				System.out.println("Command Failure");  
			}  

			while ( read.ready() ) { 
				String line = read.readLine() ;
				System.out.println(line);
			}

			process.destroy();  
		}
		catch(Exception e)  
		{  
			System.out.println("Exception: "+ e.toString());  
		}  
	}  
} 