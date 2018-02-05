package useApachePOI;

// ss for both HSSF and XSSF
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellReference;

import java.io.FileInputStream; // or java.io.InputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ProcessBook {
	
	final ArrayList<String> SHEET_LIST = new ArrayList<String>( Arrays.asList("SheetA", "SheetB", "SheetC", "SheetD", "SheetE"));

	private Workbook wb;

	private Sheet sheet;
	private String sheetName;	
	
	DatabaseOperations dbObj;

	public ProcessBook(String argInputFile, ConfigurableApplicationContext context) {
	
		DatabaseOperations databaseOperations = (DatabaseOperations) context
                .getBean("databaseOperations");
		
		try {
			FileInputStream inputFile = new FileInputStream(argInputFile);
	
			this.wb    = WorkbookFactory.create(inputFile);
			this.dbObj = databaseOperations; //new UseJdbcTemplate1();
			
			if ( argInputFile.indexOf("Template") >= 0 ) {
				ClearBook();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		
	}

	private void ClearBook() {
	    
	    System.out.println(" *** processBook ClearBook() ***");
	    
	    SqlRowSet rs;
	    String sheetName;
	    
	    for (int i=0; i<SHEET_LIST.size(); i++ ) {
	    	
	    	sheetName = SHEET_LIST.get(i);
	    	System.out.println("  - initialize sheet:  " + sheetName);
	    	
	    	sheet = wb.getSheet(sheetName);
	    	if (sheetName == "SheetE") {
	    		rs = dbObj.getRsSheetCells("SheetE"); 
	    	}
	    	else {
	    		rs = dbObj.getRsSheetCells(sheetName); 
	    	}
	    	ProcessSheet.initializeSheetToBlank(sheet, rs);
	    	
	    	// get recordset count
	    	// rs.last();
	    	// System.out.println("Sheet cell count: " + rs.getRow());
	    }
   
	}

	public void outputBook(String argOutputFile) {
		
		System.out.println("\n *** processBook outputBook() ***");
		
		wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
		
		try {
			// write wb (memory-object) to output stream -> argOutputFile 
			FileOutputStream outputFile = new FileOutputStream(argOutputFile);
			wb.write(outputFile);
			outputFile.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	
	}
	
	public void setMonthDateRange(int year, int month) {
		
    	System.out.println("\n *** processBook setMonthDateRange() ***");
    	
		try {

			String firstDate = Integer.toString(year) + "-" + Integer.toString(month) + "-01";
			String lastDate  = getLastDayOfMonth(year, month);
			
	        System.out.println("  - First Date of Month: " + firstDate);
	        System.out.println("  - Last  Date of Month: " + lastDate);
	        
	        String monthName = Month.of(month).name();
		    String monthAbbr = monthName.substring(0, 3);

			dbObj.updateDateRange(year, monthAbbr, firstDate, lastDate); 
			
		} catch (Exception e) {
        	e.printStackTrace();
        }	
	
	}
	
    public static String getLastDayOfMonth(int year, int month) throws Exception{
        
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = sdf.parse(year+"-"+(month<10?("0"+month):month)+"-01");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        return sdf.format(lastDayOfMonth);
    }	

	public void WriteBook(String monthAbbr) {
		
		System.out.println("\n *** processBook WriteBook() ***");
		
	    SqlRowSet rs;
	    
	    // loop sheets	    
	    for (int i=0; i<SHEET_LIST.size(); i++ ) {
	    	
	    	sheetName = SHEET_LIST.get(i);
	    	System.out.println("\n  Write to Sheet: " + sheetName);
	    		    	
	    	sheet = wb.getSheet(sheetName);
  	
	    	if ( sheetName != "SheetE") {

		    	System.out.println("\n   initialize (0) sheet: " + sheetName);
		    	rs    = dbObj.getRsSheetMonthCells(sheetName, monthAbbr); 
		    	ProcessSheet.initializeSheetToZero(sheet, rs);
    		
		    	// fill grid XXXX-MAIN on monthAbbr
		    	rs = dbObj.getRsMain(sheetName); 
		    	ProcessSheet.fillCells(sheet, rs);
		   		
		    	
	    		// fill grid VIP-MISSION-TOTAL and other MISSION-COUNT
	    		rs = dbObj.getRsMissions(sheetName); 
	    		ProcessSheet.fillCells(sheet, rs);
	
	    		// fill grid XXXX-DIGITAL and XXXX-MISSIONS-DIGITAL
		    	if ( sheetName != "SheetA") {
		    		rs = dbObj.getRsDigitalA(sheetName); 
		    		ProcessSheet.fillCells(sheet, rs);
		    		
		    		rs = dbObj.getRsDigitalM(sheetName); 
		    		ProcessSheet.fillCells(sheet, rs);
		    	}  
	    	}
	    	else if ( sheetName == "SheetE") {

		    	System.out.println("\n  initialize (0) sheet: " + sheetName);
		    	rs    = dbObj.getRsSheetMonthCells("SheetE", monthAbbr); 
		    	ProcessSheet.initializeSheetToZero(sheet, rs);

	    		rs = dbObj.getRsSupportStation(); 
	    		ProcessSheet.fillCells(sheet, rs);

	    	}
		}

    }
	
	// this for test purpose
	public void ReadBookToConsole(String sheetName, String monthAbbr) {

  		System.out.println("\n *** processBook ReadBookToConsole " + sheetName + " on " + monthAbbr + " ***");
  		
	    SqlRowSet rs = dbObj.getRsSheetMonthCells(sheetName, monthAbbr); 
	    sheet = wb.getSheet(sheetName);
	   	ProcessSheet.writeCellToConsole(sheet, rs);
    }
	
	// this for test purpose
	public String getCityGrid() {

  		System.out.println("\n *** processBook getCityGrid() ***");
  		
	    SqlRowSet rs = dbObj.getRsCityGrid(); 
	    rs.last();
	    return "\n - getCityGrid getRow(): " + rs.getRow();
	}
	
}

   
