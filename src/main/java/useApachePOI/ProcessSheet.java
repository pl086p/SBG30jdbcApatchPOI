package useApachePOI;

//import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.springframework.jdbc.support.rowset.SqlRowSet;

class ProcessSheet {
    
    public static void initializeSheetToZero(Sheet sheet, SqlRowSet rs) {
		
		int rowID;
		String cellName;
		Cell cell;
		
    	while (rs.next()) {
    		rowID    = rs.getInt("rownum") - 1;   
    		
    		cellName = rs.getString("cell1");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue(0);
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");

    		}
    		
    		cellName = rs.getString("cell2");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue(0);
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
    		}

    		cellName = rs.getString("cell3");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue(0);
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
   		}

    		cellName = rs.getString("cell4");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				cell.setCellValue(0);
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
    		}
    	}
    } 

    public static void initializeSheetToBlank(Sheet sheet, SqlRowSet rs) {
		
		int rowID;
		String cellName;
		Cell cell;
		
    	while (rs.next()) {
    		rowID    = rs.getInt("rownum") - 1;   
    		
    		cellName = rs.getString("cell1");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue("");
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");

    		}
    		
    		cellName = rs.getString("cell2");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue("");
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
    		}

    		cellName = rs.getString("cell3");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue("");
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
    		}

    		cellName = rs.getString("cell4");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				cell.setCellValue("");
				//System.out.println("=== row-cell: " + rowID + "-" + cellName +" ===");
    		}
    	}
    } 

	public static void writeCellToConsole(Sheet sheet, SqlRowSet rs) {
		
		int rowID;
		String cellName;
		Cell cell;
		
    	while (rs.next()) {
    		rowID    = rs.getInt("rownum") - 1;   
    		
    		cellName = rs.getString("cell1");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				System.out.println("=== " + rs.getString("sheet_grid")  + " row-cell: " + rowID + "-" + cellName +" value: " + cell.toString() + " ===");

    		}
    		
    		cellName = rs.getString("cell2");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				System.out.println("=== " + rs.getString("sheet_grid")  + " row-cell: " + rowID + "-" + cellName +" value: " + cell.toString() + " ===");
    		}

    		cellName = rs.getString("cell3");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				System.out.println("=== " + rs.getString("sheet_grid")  + " row-cell: " + rowID + "-" + cellName+" value: " + cell.toString() +" ===");
    		}

    		cellName = rs.getString("cell4");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
				System.out.println("=== " + rs.getString("sheet_grid")  + " row-cell: " + rowID + "-" + cellName+" value: " + cell.toString() +" ===");
    		}
    	}
     } 
	

	public static void fillCells(Sheet sheet, SqlRowSet rs) {
		
		int rowID;
		String cellName;
		Cell cell;
		
    	while (rs.next()) {
    		rowID    = rs.getInt("rownum") - 1;    		
    		cellName = rs.getString("cell");
    		if ( !cellName.isEmpty() ) {
	    		cell = sheet.getRow(rowID).getCell(CellReference.convertColStringToIndex(cellName));
	    		cell.setCellValue(rs.getInt("count"));
	    		// System.out.println("rowID-cell: "+ rowID + "-" + cellName +" value: " + rs.getInt("count"));
    		}
    	}
     }      	
}

   
