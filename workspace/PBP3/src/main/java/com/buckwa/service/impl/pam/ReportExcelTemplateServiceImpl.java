package com.buckwa.service.impl.pam;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.pam.ReportKpiSummary;
import com.buckwa.service.intf.pam.ReportExcelTemplateService;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Taechapon Himarat (Su)
@Create : Dec 2, 2012 4:15:11 PM
 */
@Service("reportExcelTemplateService")
public class ReportExcelTemplateServiceImpl implements ReportExcelTemplateService {
	
	private static Logger logger = LoggerFactory.getLogger(ReportExcelTemplateServiceImpl.class);
	
	
	private static final ResourceBundle rbMessage = ResourceBundle.getBundle("messages", BuckWaDateUtils.thaiLocale);
	
	
	@Override
	public void createKpiSummaryReport(BuckWaRequest request) {
		logger.info("- start");
		
		Workbook workbook = (Workbook) request.get("workbook");
		String sheetName = (String) request.get("sheetName");
		String topicName = (String) request.get("topicName");
		String subTopicName = (String) request.get("subTopicName");
		List<ReportKpiSummary> kpiSummaryList = (List<ReportKpiSummary>) request.get("kpiSummaryList");
		//BigDecimal sumMarkLevelScore = (BigDecimal) request.get("sumMarkLevelScore");
		//BigDecimal sumEvaluateScore  = (BigDecimal) request.get("sumEvaluateScore");
		BigDecimal sumWeightScore    = (BigDecimal) request.get("sumWeightScore");
		
		Sheet sheet = workbook.createSheet(sheetName);
		sheet.setColumnWidth(0, 2 * 256);
		sheet.setColumnWidth(1, 18 * 256);
		sheet.setColumnWidth(2, 38 * 256);
		sheet.setColumnWidth(3, 12 * 256);
		sheet.setColumnWidth(4, 9 * 256);
		sheet.setColumnWidth(5, 12 * 256);
//		sheet.setColumnWidth(6, 9 * 256);
		
		sheet.setMargin(Sheet.RightMargin, 0.5);
		sheet.setMargin(Sheet.LeftMargin, 0.5);
		
		/*
		 * Initial Workbook
		 */
		// Create a new font and alter it.
		Font font16 = workbook.createFont();
		font16.setFontHeightInPoints((short) 16);
		font16.setFontName("Angsana New");
		
		Font font16Bold = workbook.createFont();
		font16Bold.setFontHeightInPoints((short) 16);
		font16Bold.setFontName("Angsana New");
		font16Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Font font22Bold = workbook.createFont();
		font22Bold.setFontHeightInPoints((short) 22);
		font22Bold.setFontName("Angsana New");
		font22Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// Create Style
		CellStyle cellStyleTopic = workbook.createCellStyle();
		cellStyleTopic.setFont(font22Bold);
		cellStyleTopic.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(font16Bold);
		cellStyleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setWrapText(true);
		
		CellStyle cellStyleBody = workbook.createCellStyle();
		cellStyleBody.setFont(font16);
		cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBody.setWrapText(true);
		
		CellStyle cellStyleBodyCenter = workbook.createCellStyle();
		cellStyleBodyCenter.setFont(font16);
		cellStyleBodyCenter.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleBodyCenter.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setWrapText(true);
		
		CellStyle cellStyleBodyRight = workbook.createCellStyle();
		cellStyleBodyRight.setFont(font16);
		cellStyleBodyRight.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleBodyRight.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleSummary = workbook.createCellStyle();
		cellStyleSummary.setFont(font16Bold);
		cellStyleSummary.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleSummary.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderBottom(CellStyle.BORDER_THIN);
		
		
		// initial
		int rowIndex = 0;
		Row row = null;
		Cell cell = null;
		
		
		/*
		 * Start create Row and Cell
		 */
		// Row
		rowIndex++;
		
		
		// Row - Title
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(topicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex(),
			cell.getColumnIndex() + 5
		));
		rowIndex++;
		
		
		// Row
		if (subTopicName != null && !"".equals(subTopicName)) {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(1);
			cell.setCellValue(subTopicName);
			cell.setCellStyle(cellStyleTopic);
			// Merge
			sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex(),
				cell.getColumnIndex() + 5
			));
			rowIndex++;
		}
		
		
		// Row
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.parentKpi"));
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(2);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.childKpi"));
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(3);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.unit"));
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(4);
		cell.setCellValue("น้ำหนัก (%)");
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(5);
		cell.setCellValue("คะแนน\nถ่วงน้ำหนัก");
		cell.setCellStyle(cellStyleHeader);
		
		rowIndex++;
		
		
		// Row
		for (int i = 0; i < kpiSummaryList.size(); i++) {
			ReportKpiSummary kpiSummary = kpiSummaryList.get(i);
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(1);
			cell.setCellValue(kpiSummary.getName());
			cell.setCellStyle(cellStyleBody);
			
			cell = row.createCell(2);
			cell.setCellValue(kpiSummary.getNameWithNo());
			cell.setCellStyle(cellStyleBody);
			
			cell = row.createCell(3);
			cell.setCellValue(kpiSummary.getMarkDesc());
			cell.setCellStyle(cellStyleBodyCenter);
			
			// Weight
			cell = row.createCell(4);
			if (kpiSummary.getWeight() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getWeight()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// Weight Score
			cell = row.createCell(5);
			if (kpiSummary.getWeightScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getWeightScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			rowIndex++;
		}
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.common.sum"));
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(2);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(4);
		cell.setCellStyle(cellStyleSummary);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex() - 3,
			cell.getColumnIndex()
		));
		cell = row.createCell(5);
		cell.setCellValue(sumWeightScore.toString());
		cell.setCellStyle(cellStyleSummary);
		
	}
	
	@Override
	public void createKpiSummaryPersonReport(BuckWaRequest request) {
		logger.info("- start");
		
		Workbook workbook = (Workbook) request.get("workbook");
		String sheetName = (String) request.get("sheetName");
		String topicName = (String) request.get("topicName");
		//String subTopicName = (String) request.get("subTopicName");
		String year = (String) request.get("year");
		List<ReportKpiSummary> kpiSummaryList = (List<ReportKpiSummary>) request.get("kpiSummaryList");
		BigDecimal sumMarkLevelScore = (BigDecimal) request.get("sumMarkLevelScore");
		BigDecimal sumEvaluateScore  = (BigDecimal) request.get("sumEvaluateScore");
		BigDecimal sumWeightScore    = (BigDecimal) request.get("sumWeightScore");
		
		Sheet sheet = workbook.createSheet(sheetName);
		sheet.setColumnWidth(0, 2 * 256);
		sheet.setColumnWidth(1, 18 * 256);
		sheet.setColumnWidth(2, 38 * 256);
		sheet.setColumnWidth(3, 14 * 256);
		sheet.setColumnWidth(4, 9 * 256);
		sheet.setColumnWidth(5, 9 * 256);
		sheet.setColumnWidth(6, 9 * 256);
		sheet.setColumnWidth(13, 12 * 256);
		sheet.setColumnWidth(14, 12 * 256);
		
		sheet.setMargin(Sheet.RightMargin, 0.5);
		sheet.setMargin(Sheet.LeftMargin, 0.5);
		
		/*
		 * Initial Workbook
		 */
		// Create a new font and alter it.
		Font font16 = workbook.createFont();
		font16.setFontHeightInPoints((short) 16);
		font16.setFontName("Angsana New");
		
		Font font16Bold = workbook.createFont();
		font16Bold.setFontHeightInPoints((short) 16);
		font16Bold.setFontName("Angsana New");
		font16Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Font font22Bold = workbook.createFont();
		font22Bold.setFontHeightInPoints((short) 22);
		font22Bold.setFontName("Angsana New");
		font22Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// Create Style
		CellStyle cellStyleTopic = workbook.createCellStyle();
		cellStyleTopic.setFont(font22Bold);
		cellStyleTopic.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(font16Bold);
		cellStyleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setWrapText(true);
		
		CellStyle cellStyleBody = workbook.createCellStyle();
		cellStyleBody.setFont(font16);
		cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBody.setWrapText(true);
		
		CellStyle cellStyleBodyCenter = workbook.createCellStyle();
		cellStyleBodyCenter.setFont(font16);
		cellStyleBodyCenter.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleBodyCenter.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setWrapText(true);
		
		CellStyle cellStyleBodyRight = workbook.createCellStyle();
		cellStyleBodyRight.setFont(font16);
		cellStyleBodyRight.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleBodyRight.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleSummary = workbook.createCellStyle();
		cellStyleSummary.setFont(font16Bold);
		cellStyleSummary.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleSummary.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderBottom(CellStyle.BORDER_THIN);
		
		
		// initial
		int rowIndex = 0;
		Row row = null;
		Cell cell = null;
		
		
		/*
		 * Start create Row and Cell
		 */
		// Row
		rowIndex++;
		
		
		// Row - Title
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(topicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex(),
			cell.getColumnIndex() + 13
		));
		rowIndex++;
		
		
		// Row
//		if (subTopicName != null && !"".equals(subTopicName)) {
//			row = sheet.createRow(rowIndex);
//			cell = row.createCell(1);
//			cell.setCellValue(subTopicName);
//			cell.setCellStyle(cellStyleTopic);
//			// Merge
//			sheet.addMergedRegion(new CellRangeAddress(
//					row.getRowNum(),
//					row.getRowNum(),
//					cell.getColumnIndex(),
//					cell.getColumnIndex() + 5
//					));
//			rowIndex++;
//		}
		
		
		// Row
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		// Header
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.parentKpi"));
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(2);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.childKpi"));
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(3);
		cell.setCellValue(rbMessage.getString("label.report.kpisummary.header.unit"));
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(4);
		cell.setCellValue(year);
		cell.setCellStyle(cellStyleHeader);
		cell = row.createCell(5);
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex() - 1,
			cell.getColumnIndex()
		));
		
		cell = row.createCell(6);
		cell.setCellValue("น้ำหนัก (%)");
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(7);
		cell.setCellValue("เกณฑ์การให้คะแนน");
		cell.setCellStyle(cellStyleHeader);
		cell = row.createCell(8);
		cell.setCellStyle(cellStyleHeader);
		cell = row.createCell(9);
		cell.setCellStyle(cellStyleHeader);
		cell = row.createCell(10);
		cell.setCellStyle(cellStyleHeader);
		cell = row.createCell(11);
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex() - 4,
			cell.getColumnIndex()
		));
		
		cell = row.createCell(12);
		cell.setCellValue("คะแนนที่ได้");
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(13);
		cell.setCellValue("คะแนน\nตามตัวบ่งชี้");
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		cell = row.createCell(14);
		cell.setCellValue("คะแนน\nถ่วงน้ำหนัก");
		cell.setCellStyle(cellStyleHeader);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum() + 1,
			cell.getColumnIndex(),
			cell.getColumnIndex()
		));
		
		rowIndex++;
		
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(2);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(3);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(4);
		cell.setCellValue("เป้าหมาย");
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(5);
		cell.setCellValue("ผลงาน");
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleHeader);
		
		for (int i = 0; i < 5; i++) {
			cell = row.createCell(7 + i);
			cell.setCellValue(String.valueOf(i + 1));
			cell.setCellStyle(cellStyleHeader);
		}
		
		cell = row.createCell(12);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(13);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(14);
		cell.setCellStyle(cellStyleHeader);
		
		rowIndex++;
		
		
		
		// Row
		for (int i = 0; i < kpiSummaryList.size(); i++) {
			ReportKpiSummary kpiSummary = kpiSummaryList.get(i);
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(1);
			cell.setCellValue(kpiSummary.getName());
			cell.setCellStyle(cellStyleBody);
			
			cell = row.createCell(2);
			cell.setCellValue(kpiSummary.getNameWithNo());
			cell.setCellStyle(cellStyleBody);
			
			cell = row.createCell(3);
			cell.setCellValue(kpiSummary.getMarkDesc());
			cell.setCellStyle(cellStyleBodyCenter);
			
			// Target
			cell = row.createCell(4);
			if (kpiSummary.getTargetScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getTargetScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// Upload Score
			cell = row.createCell(5);
			if (kpiSummary.getUploadScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getUploadScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// Weight
			cell = row.createCell(6);
			if (kpiSummary.getWeight() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getWeight()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// MarkLevel
			if (kpiSummary.getMarkLevel() != null
				&& kpiSummary.getMarkLevel().getMarkLevelDetailList() != null) {
				for (int j = 0; j < 5; j++) {
					cell = row.createCell(7 + j);
					cell.setCellValue(String.valueOf(kpiSummary.getMarkLevel().getMarkLevelDetailList().get(j).getMark()));
					cell.setCellStyle(cellStyleBodyCenter);
				}
			}
			else {
				for (int j = 0; j < 5; j++) {
					cell = row.createCell(7 + j);
					cell.setCellStyle(cellStyleBodyCenter);
				}
			}
			
			// MarkLevel Score
			cell = row.createCell(12);
			if (kpiSummary.getMarkLevelScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getMarkLevelScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// Evaluate Score
			cell = row.createCell(13);
			if (kpiSummary.getEvaluateScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getEvaluateScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			// Weight Score
			cell = row.createCell(14);
			if (kpiSummary.getWeightScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getWeightScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			rowIndex++;
		}
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.common.sum"));
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(2);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(4);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(5);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(7);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(8);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(9);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(10);
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(11);
		cell.setCellStyle(cellStyleSummary);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex() - 10,
				cell.getColumnIndex()
				));
		cell = row.createCell(12);
		cell.setCellValue(sumMarkLevelScore.toString());
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(13);
		cell.setCellValue(sumEvaluateScore.toString());
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(14);
		cell.setCellValue(sumWeightScore.toString());
		cell.setCellStyle(cellStyleSummary);
		
	}

	@Override
	public void createKpiSummaryByTopicReport(BuckWaRequest request) {
		logger.info("- start");
		
		Workbook workbook = (Workbook) request.get("workbook");
		String sheetName = (String) request.get("sheetName");
		String topicName = (String) request.get("topicName");
		String subTopicName = (String) request.get("subTopicName");
		String headerTableName = (String) request.get("headerTableName");
		List<ReportKpiSummary> kpiSummaryList = (List<ReportKpiSummary>) request.get("kpiSummaryList");
		
		
		Sheet sheet = workbook.createSheet(sheetName);
		sheet.setColumnWidth(0, 3 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 40 * 256);
		int headerSize = 0;
		if (kpiSummaryList != null && kpiSummaryList.get(0) != null) {
			if (kpiSummaryList.get(0).getChildList().size() > 0) {
				headerSize = kpiSummaryList.get(0).getChildList().size();
				for (int i = 0; i < headerSize; i++) {
					sheet.setColumnWidth(3 + i, 25 * 256);
				}
			}
			else {
				sheet.setColumnWidth(3, 25 * 256);
			}
		}
		
		/*
		 * Initial Workbook
		 */
		// Create a new font and alter it.
		Font font16 = workbook.createFont();
		font16.setFontHeightInPoints((short) 16);
		font16.setFontName("Angsana New");
		
		Font font16Bold = workbook.createFont();
		font16Bold.setFontHeightInPoints((short) 16);
		font16Bold.setFontName("Angsana New");
		font16Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Font font22Bold = workbook.createFont();
		font22Bold.setFontHeightInPoints((short) 22);
		font22Bold.setFontName("Angsana New");
		font22Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// Create Style
		CellStyle cellStyleTopic = workbook.createCellStyle();
		cellStyleTopic.setFont(font22Bold);
		cellStyleTopic.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(font16Bold);
		cellStyleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setWrapText(true);
		
		CellStyle cellStyleBody = workbook.createCellStyle();
		cellStyleBody.setFont(font16);
		cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBody.setWrapText(true);
		
		CellStyle cellStyleBodyCenter = workbook.createCellStyle();
		cellStyleBodyCenter.setFont(font16);
		cellStyleBodyCenter.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleBodyCenter.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleBodyRight = workbook.createCellStyle();
		cellStyleBodyRight.setFont(font16);
		cellStyleBodyRight.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleBodyRight.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleSummary = workbook.createCellStyle();
		cellStyleSummary.setFont(font16Bold);
		cellStyleSummary.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleSummary.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderBottom(CellStyle.BORDER_THIN);
		
		
		// initial
		int rowIndex = 0;
		Row row = null;
		Cell cell = null;
		
		
		/*
		 * Start create Row and Cell
		 */
		// Row
		rowIndex++;
		
		
		// Row - Title
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(topicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		if (headerSize > 0) {
			sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex(),
				cell.getColumnIndex() + kpiSummaryList.get(0).getChildList().size() + 1
			));
		}
		else {
			sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex(),
				cell.getColumnIndex() + 2
			));
		}
		rowIndex++;
		
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(subTopicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		if (headerSize > 0) {
			sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex(),
				cell.getColumnIndex() + kpiSummaryList.get(0).getChildList().size() + 1
			));
		}
		else {
			sheet.addMergedRegion(new CellRangeAddress(
				row.getRowNum(),
				row.getRowNum(),
				cell.getColumnIndex(),
				cell.getColumnIndex() + 2
			));
		}
		
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		for (int i = 0; i < kpiSummaryList.size(); i++) {
			
			ReportKpiSummary kpiSummary = kpiSummaryList.get(i);
			
			// Table Header
			if (i == 0) {
				row = sheet.createRow(rowIndex);
				cell = row.createCell(1);
				cell.setCellValue(rbMessage.getString("label.report.common.no"));
				cell.setCellStyle(cellStyleHeader);
				cell = row.createCell(2);
				cell.setCellValue(headerTableName);
				cell.setCellStyle(cellStyleHeader);
				if (kpiSummary.getChildList().size() == 0) {
					cell = row.createCell(3);
					cell.setCellValue(kpiSummary.getName());
					cell.setCellStyle(cellStyleHeader);
					rowIndex++;
				}
				else {
					cell = row.createCell(3);
					cell.setCellValue(kpiSummary.getName());
					cell.setCellStyle(cellStyleHeader);
					for (int j = 0; j < kpiSummary.getChildList().size() - 1; j++) {
						cell = row.createCell(4 + j);
						cell.setCellStyle(cellStyleHeader);
					}
					
					// Merge #
					sheet.addMergedRegion(new CellRangeAddress(
						row.getRowNum(),
						row.getRowNum() + 1,
						1,
						1
					));
					
					// Merge Title
					sheet.addMergedRegion(new CellRangeAddress(
						row.getRowNum(),
						row.getRowNum() + 1,
						2,
						2
					));
					
					// Merge Topic
					if (kpiSummary.getChildList().size() > 1) {
						sheet.addMergedRegion(new CellRangeAddress(
							row.getRowNum(),
							row.getRowNum(),
							3,
							3 + kpiSummary.getChildList().size() - 1
						));
					}
					rowIndex++;
					
					// Row
					row = sheet.createRow(rowIndex);
					cell = row.createCell(1);
					cell.setCellStyle(cellStyleHeader);
					int initColumnIndex = 3;
					for (int j = 0; j < kpiSummary.getChildList().size(); j++) {
						cell = row.createCell(initColumnIndex + j);
						cell.setCellValue(kpiSummary.getChildList().get(j).getName());
						cell.setCellStyle(cellStyleHeader);
					}
					rowIndex++;
				}
			}
			
			
			// Table Body
			else {
				row = sheet.createRow(rowIndex);
				cell = row.createCell(1);
				cell.setCellValue(i);
				cell.setCellStyle(cellStyleBodyCenter);
				cell = row.createCell(2);
				cell.setCellValue(kpiSummary.getName());
				cell.setCellStyle(cellStyleBody);
				
				int initColumnIndex = cell.getColumnIndex() + 1;
				if (kpiSummary.getChildList() != null && kpiSummary.getChildList().size() > 0) {
					for (int j = 0; j < kpiSummary.getChildList().size(); j++) {
						cell = row.createCell(initColumnIndex + j);
						cell.setCellValue(String.valueOf(kpiSummary.getChildList().get(j).getWeightScore()));
						cell.setCellStyle(cellStyleBodyRight);
					}
				}
				else {
					cell = row.createCell(initColumnIndex);
					cell.setCellValue(String.valueOf(kpiSummary.getWeightScore()));
					cell.setCellStyle(cellStyleBodyRight);
				}
				rowIndex++;
			}
		}
	}

	@Override
	public void createKpiSummaryByFacultyAndPersonReport(BuckWaRequest request) {
		logger.info("- start");
		
		Workbook workbook = (Workbook) request.get("workbook");
		String sheetName = (String) request.get("sheetName");
		String topicName = (String) request.get("topicName");
		String subTopicName = (String) request.get("subTopicName");
		String headerTableName = (String) request.get("headerTableName");
		List<ReportKpiSummary> kpiSummaryList = (List<ReportKpiSummary>) request.get("kpiSummaryList");
		BigDecimal sumWeightScore = (BigDecimal) request.get("sumWeightScore");
		
		Sheet sheet = workbook.createSheet(sheetName);
		sheet.setColumnWidth(0, 3 * 256);
		sheet.setColumnWidth(1, 9 * 256);
		sheet.setColumnWidth(2, 52 * 256);
		sheet.setColumnWidth(3, 17 * 256);
		
		/*
		 * Initial Workbook
		 */
		// Create a new font and alter it.
		Font font16 = workbook.createFont();
		font16.setFontHeightInPoints((short) 16);
		font16.setFontName("Angsana New");
		
		Font font16Bold = workbook.createFont();
		font16Bold.setFontHeightInPoints((short) 16);
		font16Bold.setFontName("Angsana New");
		font16Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Font font22Bold = workbook.createFont();
		font22Bold.setFontHeightInPoints((short) 22);
		font22Bold.setFontName("Angsana New");
		font22Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// Create Style
		CellStyle cellStyleTopic = workbook.createCellStyle();
		cellStyleTopic.setFont(font22Bold);
		cellStyleTopic.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(font16Bold);
		cellStyleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setWrapText(true);
		
		CellStyle cellStyleBody = workbook.createCellStyle();
		cellStyleBody.setFont(font16);
		cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBody.setWrapText(true);
		
		CellStyle cellStyleBodyCenter = workbook.createCellStyle();
		cellStyleBodyCenter.setFont(font16);
		cellStyleBodyCenter.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleBodyCenter.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyCenter.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleBodyRight = workbook.createCellStyle();
		cellStyleBodyRight.setFont(font16);
		cellStyleBodyRight.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleBodyRight.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBodyRight.setBorderBottom(CellStyle.BORDER_THIN);
		
		CellStyle cellStyleSummary = workbook.createCellStyle();
		cellStyleSummary.setFont(font16Bold);
		cellStyleSummary.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyleSummary.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleSummary.setBorderBottom(CellStyle.BORDER_THIN);
		
		
		// initial
		int rowIndex = 0;
		Row row = null;
		Cell cell = null;
		
		
		/*
		 * Start create Row and Cell
		 */
		// Row
		rowIndex++;
		
		
		// Row - Title
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(topicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex(),
			cell.getColumnIndex() + 3
		));
		rowIndex++;
		
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(subTopicName);
		cell.setCellStyle(cellStyleTopic);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex(),
			cell.getColumnIndex() + 3
		));
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		rowIndex++;
		
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.common.no"));
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(2);
		cell.setCellValue(headerTableName);
		cell.setCellStyle(cellStyleHeader);
		
		cell = row.createCell(3);
		cell.setCellValue("คะแนน\nถ่วงน้ำหนัก");
		cell.setCellStyle(cellStyleHeader);
		
		rowIndex++;
		
		 
		// Row
		for (int i = 0; i < kpiSummaryList.size(); i++) {
			ReportKpiSummary kpiSummary = kpiSummaryList.get(i);
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(1);
			cell.setCellValue((i + 1));
			cell.setCellStyle(cellStyleBodyCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(kpiSummary.getName());
			cell.setCellStyle(cellStyleBody);
			
			cell = row.createCell(3);
			if (kpiSummary.getWeightScore() != null) {
				cell.setCellValue(String.valueOf(kpiSummary.getWeightScore()));
			}
			else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyleBodyRight);
			
			rowIndex++;
		}
		
		// Row
		row = sheet.createRow(rowIndex);
		cell = row.createCell(1);
		cell.setCellValue(rbMessage.getString("label.report.common.sum"));
		cell.setCellStyle(cellStyleSummary);
		cell = row.createCell(2);
		cell.setCellStyle(cellStyleSummary);
		// Merge
		sheet.addMergedRegion(new CellRangeAddress(
			row.getRowNum(),
			row.getRowNum(),
			cell.getColumnIndex() - 1,
			cell.getColumnIndex()
		));
		cell = row.createCell(3);
		cell.setCellValue(sumWeightScore.toString());
		cell.setCellStyle(cellStyleSummary);
	}

}
