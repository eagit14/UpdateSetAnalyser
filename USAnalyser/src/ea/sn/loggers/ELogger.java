package ea.sn.loggers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ELogger {
	private final String fileName = "logs/trace_" + getDateTime() + ".log";

	private static FileWriter writer;
	private static BufferedWriter buffer ;

	
	public FileWriter getWriter() {
		return writer;
	}

	public void setWriter(FileWriter writer) {
		ELogger.writer = writer;
	}

	public static BufferedWriter getBuffer() {
		return buffer;
	}

	public static void setBuffer(BufferedWriter buffer) {
		ELogger.buffer = buffer;
	}


	public String getFileName() {
		return fileName;
	}

	public ELogger() {
		   try {
				writer = new FileWriter(fileName, true);
				buffer = new BufferedWriter (writer);
			} catch (IOException e) {
				System.out.println("Could not open file to insert");
				e.printStackTrace();
			}
	}
	   
	public void log(String s, Boolean printOut)  { 
		try {
                        if (printOut) {
                            System.out.println(s);
                        }
			buffer.write(getTimeStamp() + " - " + s + "\n");
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printSeparator() throws IOException { 
		for (int i=0; i<100; i++) { 
			buffer.write("+");	
		}
		buffer.write("\n");
		buffer.flush();
	}
	
    public static String getDateTime() {
    	Date today;
    	String output;
    	SimpleDateFormat formatter;
    	formatter = new SimpleDateFormat("ddMMyyyy__HH'h'mm'min'ss's'", new Locale("en","US"));
    	today = new Date();
    	output = formatter.format(today);
    	return output ;
    }
    
	public static Date getTimeStamp() {
    	Date timeStamp = new Date();
    	return timeStamp;
	}
	
	public void close() {
		try {
			buffer.close();
		} catch (IOException e) {
			System.out.println("Logger file Closing has failed!");
		}
	}

}
