- Spring Boot with gradle
- using jdbcTemplate for database object
- using apatchPOI for excel read and write

To run this application, you need:
1. clone this repository to your local
2. add related tables and views to your database schema (see database/README.txt)
3. run the program

   (1)  open terminal and goto this-application-root
   
   		$ cd your-path/SBG30jdbcApatchPOI
   		
   (2)  create excel file (default month)
     
     $ gradle bootRun
     
   (3)  get the customized month report (with command line arguments: yyyy=2017, mm=09)
   
	 $ gradle run -PmyArgs="['yyyy','mm']"

4. Open the created excel file

   The created xlsx file is located at xlsx-files/xxxSEP2017.xlsx.
