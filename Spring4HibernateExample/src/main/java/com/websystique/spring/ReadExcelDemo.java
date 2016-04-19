package com.websystique.spring;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelDemo {

	private List<Row> dataRows = new ArrayList<Row>();;

	public List<Row> getDataRows() {
		return dataRows;
	}

	public void setDataRows(List<Row> dataRows) {
		this.dataRows = dataRows;
	}

	public void addDataRow(Row dataRow) {
		this.dataRows.add(dataRow);
	}

	public static void main(String[] args) {

	}

	public DynamicClass readModel() {
		DynamicClass dynamicClass = new DynamicClass();
		try {
			FileInputStream file = new FileInputStream(new File(
					"C:/Users/Mallampati Suresh/Desktop/Spring4HibernateExample/src/main/resources/dynamic_columns_demo.xls"));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Row tableRow = sheet.getRow(0);
			Cell tabeCell = tableRow.getCell(0);
			System.out.println(tabeCell.getStringCellValue());
			dynamicClass.setName(tabeCell.getStringCellValue());
			Row columnsRow = sheet.getRow(1);
			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				addDataRow(sheet.getRow(i));
			}

			Iterator<Cell> cellIterator = columnsRow.cellIterator();

			while (cellIterator.hasNext()) {

				Cell columnCell = cellIterator.next();
				dynamicClass.addField(new Field(columnCell.getStringCellValue(), String.class));

			}
			System.out.println("");

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dynamicClass;
	}
}