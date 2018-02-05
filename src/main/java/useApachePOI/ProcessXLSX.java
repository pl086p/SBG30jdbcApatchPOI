package useApachePOI;

import java.io.FileInputStream;
import java.time.Month;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ConfigurableApplicationContext;



public class ProcessXLSX {
	
	final String FILE_PATH = "./xlxs_files/";
	final String STANDARD_FILE_NAME = "xlsxMMMYYYY.xlsx";
	final String TEMPLATE_FILE_NAME = "xlsxTemplate.xlsx";
	
	private int year;
	private int monthToFill;
	private String monthAbbr;
	private String inputFileName;
	private String outputFileName;
	
	public ProcessXLSX() {
	    System.out.println("\n $$$$$ process XLSX $$$$$");
    }	
	
    public void setBook(int year, int month) {
 
    	this.year = year;
    	this.monthToFill = month;
    	
	    int previousMonth = month - 1;
	    String temp;
	    String MonthYear;


	    /* set input file name
	     * use the previous month file as input file
	     * for January report (previousMonth == 0), use TEMPLATE_FILE_NAME
	     * if previousMonth doesn't have xlsx file, use TEMPLATE_FILE_NAME
	     */
	    
	    if ( previousMonth >= 1 ) {
	    	temp = Month.of(previousMonth).name();
	    	temp = temp.substring(0, 3);
		    MonthYear = temp + Integer.toString(year);
	    	this.inputFileName = FILE_PATH + STANDARD_FILE_NAME.replace("MMMYYYY", MonthYear);
	    }
	    else {
	    	this.inputFileName = FILE_PATH + TEMPLATE_FILE_NAME; 
	    }
    	
		try {
			FileInputStream inputFileStream = new FileInputStream(this.inputFileName);
		}
		catch(Exception ex) {
			this.inputFileName = FILE_PATH + TEMPLATE_FILE_NAME;
		}	    
	    
	    // set output file name
	    temp = Month.of(monthToFill).name();
	    this.monthAbbr = temp.substring(0, 3);
	    
	    MonthYear = this.monthAbbr + Integer.toString(year);
	    this.outputFileName = FILE_PATH + STANDARD_FILE_NAME.replace("MMMYYYY", MonthYear);
	    
	    
	    System.out.println(this.monthAbbr + "---" + this.year); 
	    System.out.println("inputFileName: " + this.inputFileName);
	    System.out.println("outputFileName: " + this.outputFileName);
	    
    }
    
    public void runProcessBook(ConfigurableApplicationContext context) {
	    
	    // create instance with workbook and jdbctemplate initialization
    	ProcessBook processBook = new ProcessBook(this.inputFileName, context);
    	
    	// Set month date range, fill book
    	processBook.setMonthDateRange(this.year, this.monthToFill);
    	processBook.WriteBook(this.monthAbbr);

    	// #######################################################################
    	// ==== this section for test purpose. may to remove/comment out later 
    	
    	// processBook.ReadBookToConsole("NAOC", "NOV");
    	
    	// ==== get row-count on table city_grid 
    	//System.out.println(processBook.getCityGrid());
    	
    	// ===================== test section end  ===============================
    	// #######################################################################
    	
    	// output data to a new workbook
    	processBook.outputBook(this.outputFileName);

    }

}

