package ea.sn.loggers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

	   public static String getDateTime() {
	    	Date today;
	    	String output;
	    	SimpleDateFormat formatter;
	    	formatter = new SimpleDateFormat("ddMMyyyy__HH'h'mm'min'ss's'", new Locale("en","US"));
	    	today = new Date();
	    	output = formatter.format(today);
	    	return output ;
	    }
}
