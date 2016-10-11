package com.buckwa.dao.impl.uitl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	
//	private static final Logger logger = LogManager.getLogger(ExcelReader.class);
	private Sheet sheet;
	private static Integer MY_MINIMUM_COLUMN_COUNT = 0;
	private ExcelRowMapper<?> excelRowMapper;
	private List<Object> result = new ArrayList<>();
	private FormulaEvaluator evaluator ;
	private int skipRows = 0;
	
	public ExcelReader(InputStream inp,int column) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook wb = WorkbookFactory.create(inp);
		sheet = wb.getSheetAt(0);
		MY_MINIMUM_COLUMN_COUNT = column;
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
	}
	
	public ExcelReader(String file,int column) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream xlsx = new FileInputStream(file);
		Workbook wb = WorkbookFactory.create(xlsx);
		sheet = wb.getSheetAt(0);
		MY_MINIMUM_COLUMN_COUNT = column;
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
	}
	
	public ExcelReader(String file,int column , int skipRowNum) throws EncryptedDocumentException, InvalidFormatException, IOException{
		this(file,column);
		skipRows = skipRowNum;
	}
	
	public ExcelReader(InputStream inp,int column , int skipRowNum) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this(inp,column);
		skipRows = skipRowNum;
	}
	
	private void read(){
//		logger.info("read");
		// Decide which rows to process
	    int rowStart = Math.min(15, sheet.getFirstRowNum());
	    int rowEnd = Math.max(1400, sheet.getLastRowNum());
	    skipRows = Math.max(0, skipRows);

	    for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
	    	
	    	if(rowNum < skipRows){
	    		continue;
	    	}
	    	
	       Row r = sheet.getRow(rowNum);
	       if (r == null) {
	          // This whole row is empty
	          // Handle it as needed
	          continue;
	       }

	       int lastColumn = ( MY_MINIMUM_COLUMN_COUNT > 0)? MY_MINIMUM_COLUMN_COUNT : r.getLastCellNum();
//	       logger.info("lastColumn {} : {}", r.getLastCellNum() , MY_MINIMUM_COLUMN_COUNT);
	       //ResSet
	       List<Object> rsSet = new ArrayList<>();
	       
	       for (int cn = 0; cn < lastColumn; cn++) {
	          Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
	          if (cell == null) {
	             // The spreadsheet is empty in this cell
//	        	  logger.info("empty in this cell");
	        	  rsSet.add(null);
	          } else {
	        	  switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
//	                    logger.info("CELL_TYPE_STRING : {}" , cell.getRichStringCellValue().getString());
	                    rsSet.add(cell.getRichStringCellValue().getString());
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(cell)) {
//	                        logger.info("CELL_TYPE_NUMERIC : DATE {} ", cell.getDateCellValue());
	                        rsSet.add(cell.getDateCellValue());
	                    } else {
//	                        logger.info("CELL_TYPE_NUMERIC {}",cell.getNumericCellValue());
	                        rsSet.add(cell.getNumericCellValue());
	                    }
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
//	                    logger.info("CELL_TYPE_BOOLEAN {}", cell.getBooleanCellValue());
	                    rsSet.add(cell.getBooleanCellValue());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
//	                    logger.info("CELL_TYPE_FORMULA {}", cell.getCellFormula());
	                    Object frs = "";
	                    switch (evaluator.evaluateInCell(cell).getCellType()) {
		                    case Cell.CELL_TYPE_BOOLEAN:
		                    	frs = cell.getBooleanCellValue();
//		                        logger.info("CELL_TYPE_FORMULA CELL_TYPE_BOOLEAN {} " ,cell.getBooleanCellValue());
		                        break;
		                    case Cell.CELL_TYPE_NUMERIC:
		                        if (DateUtil.isCellDateFormatted(cell)) {
//			                        logger.info("CELL_TYPE_FORMULA : DATE {} ", cell.getDateCellValue());
			                        frs = cell.getDateCellValue();
			                    } else {
//			                        logger.info("CELL_TYPE_FORMULA : NUMBER {}",cell.getNumericCellValue());
			                        frs = cell.getNumericCellValue();
			                    }
		                        break;
		                    case Cell.CELL_TYPE_STRING:
//		                    	 logger.info("CELL_TYPE_FORMULA STRING {} ", cell.getStringCellValue());
		                    	 frs = cell.getStringCellValue();
		                        break;
		                    case Cell.CELL_TYPE_BLANK:
//		                    	logger.info("Cell.CELL_TYPE_BLANK:");
		                    	frs = "";
		                        break;
		                    case Cell.CELL_TYPE_ERROR:
//		                    	logger.info("CELL_TYPE_FORMULA ERROR {} ", cell.getErrorCellValue());
		                    	frs = cell.getErrorCellValue();
		                        break;
			                }
	                    
	                    rsSet.add(frs);
	                    
	                    break;
	                default:
//	                    logger.info("default");
	                    rsSet.add("");
	            }
	          }
	       }
	       
	       //Process
	       Object mapper = excelRowMapper.mapper(rsSet, rowNum);
	       if(mapper != null){
	    	   result.add(mapper);
	       }
	    }
	    
//		logger.info("read... END");
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> readExcel(ExcelRowMapper<T> excelRowMapper){
//		logger.info("readExcel...");
		
		this.excelRowMapper = excelRowMapper;
		this.read();
		
		return (List<T>) result;
	}
	
	public interface ExcelRowMapper <T>{
		public T mapper(List<Object> rs , int rowIndex );
	}
	
	public static void main(String[] args) {/*
		logger.info("main...");
		
		try {
			ExcelReader excelReader = new ExcelReader("C:\\Users\\Armdev\\Documents\\excel-text-data-pdt.xlsx", 12,5);
			List<MockPDT001> rs = (List<MockPDT001>) excelReader.readExcel(new ExcelRowMapper<MockPDT001>() {

				@Override
				public MockPDT001 mapper(List<Object> rs, int rowIndex) {
					logger.info("rowIndex {}" , rowIndex);
					MockPDT001 mockPDT001 = new MockPDT001();
					return mockPDT001;
				}
			});
			
			logger.info("SIZE {}", rs.size());
			
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	*/}
	
}

