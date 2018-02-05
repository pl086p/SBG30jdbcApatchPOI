package useApachePOI;


import java.util.Date;
import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		System.out.println("\n##### Application.main() BEGIN #####");

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		int intYear = 2088;
		int intMonth= 1;
		
		if ( (args != null) && (args.length == 2) ) {
			
			System.out.println(" - args[0]: " + args[0]);
			System.out.println(" - args[1]: " + args[1]);

			intYear  = Integer.parseInt(args[0]);
			intMonth = Integer.parseInt(args[1]);
		}
		else {
			Date todayDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(todayDate);		 // set to today
			c.add(Calendar.MONTH, -1);   // set to previous month
			
			intYear = c.get(Calendar.YEAR);        
			intMonth= c.get(Calendar.MONTH) + 1;   // month indexing from zero
		}
		
		System.out.println(" - year: "  + Integer.toString(intYear));
		System.out.println(" - month: " + Integer.toString(intMonth));
		
		ProcessXLSX processXLSX = new ProcessXLSX();
		processXLSX.setBook(intYear, intMonth);
		processXLSX.runProcessBook(context);
		
		System.out.println("\n##### Application.main() End #####");
		
	}
}

