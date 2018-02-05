package useApachePOI;

//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class DatabaseOperations {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void updateDateRange(int year, String monthAbbr, String firstDate, String lastDate){
		System.out.println("\n  - DatabaseOperations.updateDateRange()");

		String sql = "UPDATE z_data_month_date_range set first_date='" + firstDate 
				+ "', last_date='" + lastDate 
				+ "', year=" + Integer.toString(year) 
				+ ",  month='" + monthAbbr 
				+ "'  where id=1";
		jdbcTemplate.execute(sql);
    	
    }

    // use autowired jdbcTemplate on spring.datasource...
	public SqlRowSet getRsSheetCells(String sheetName) {	
		
		System.out.println("\n== DatabaseOperations.getRsSheetCells() ==");

		String sql = "SELECT * from vw_map_all_cells where sheet_name=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName);

		return rs; 
	}	
	
	public SqlRowSet getRsSheetMonthCells(String sheetName, String monthAbbr) {	
		
		System.out.println("\n  - DatabaseOperations.getRsSheetMonthCells()");

		String sql = "SELECT * from vw_map_all_cells where sheet_name=? and month_abbr=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName, monthAbbr);

		return rs;
	}		
	
	public SqlRowSet getRsMain(String sheetName) {	
		
		System.out.println("  - DatabaseOperations.getRsMain()");

		String sql = "SELECT * from vw_xlsx_main where sheet_name=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName);

		return rs;
	}		
	
	public SqlRowSet getRsDigitalA(String sheetName) {	
		
		System.out.println("  - DatabaseOperations.getRsDigitalA()");

		String sql = "SELECT * from vw_xlsx_digital_a where sheet_name=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName);

		return rs;
	}		

	
	public SqlRowSet getRsDigitalM(String sheetName) {	
		
		System.out.println("  - DatabaseOperations.getRsDigitalM()");

		String sql = "SELECT * from vw_xlsx_digital_m where sheet_name=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName);

		return rs;
	}	
	
	public SqlRowSet getRsMissions(String sheetName) {	
		
		System.out.println("  - DatabaseOperations.getRsTrip()");

		String sql = "SELECT * from vw_xlsx_trip where sheet_name=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sheetName);
		
		return rs;
	}

	
	public SqlRowSet getRsSupportStation() {	
		
		System.out.println("  - DatabaseOperations.getRsSheetE()");

		String sql = "SELECT * from vw_rpt_xlsx_sheetE";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		
		return rs;
	}

	// for test purpose
	public SqlRowSet getRsCityGrid() {	
		
		System.out.println("\n== DatabaseOperations.getRsCityGrid() ==");

		String sql = "SELECT * from z_rpt_xlsx_grid_cell";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

		return rs;
	}		
    
}
