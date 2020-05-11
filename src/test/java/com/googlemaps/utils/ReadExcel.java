package com.googlemaps.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.googlemaps.common.GoogleMapsAPIBase;

public class ReadExcel {
	
	public static FileInputStream fis=null;
	public static Properties prop=null;
	public static String propFilePath=GoogleMapsAPIBase.PROPFILEPATH;
	
	public static Object[][] getDataFromExcel(String testClassName) throws IOException
	{
		Properties prop=getProperty(propFilePath);
		String workbookName=prop.get("Workbook").toString();
		XSSFWorkbook workbook=getWorkBook(workbookName);
		XSSFSheet sheet=getSheet(workbook,testClassName);
		List<Object> testdataList=getCellData(sheet, propFilePath);
		int lastColIndex=sheet.getLastRowNum();
		Object[][] testdata=new Object[1][lastColIndex];
		for(int i=0;i<testdataList.size();i++)
		{
			testdata[0][i]=testdataList.get(i);
		}
		return testdata;
		
	}
	public static String getFilePath(String fileName)
	{
		String filePath=System.getProperty("user.dir")+"//resource//"+fileName;
		return filePath;
	}
	
	public static Properties getProperty(String propFilePath) throws IOException
	{
		fis=new FileInputStream(propFilePath);
		prop=new Properties();
		prop.load(fis);
		return prop;
	}
	
	public static XSSFWorkbook getWorkBook(String wbName) throws IOException
	{
		fis=new FileInputStream(getFilePath(wbName));
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		return workbook;
	}
	
	public static int getSheetCount(XSSFWorkbook wb)
	{
		int sheetCount=wb.getNumberOfSheets();
		return sheetCount;
	}
	
	/*
	 * public static XSSFSheet getSheet(XSSFWorkbook wb,String sheetName) {
	 * XSSFSheet workSheet=wb.getSheet(sheetName); return workSheet; }
	 */
	public static XSSFSheet getSheet(XSSFWorkbook wb,String testClassName)
	{
		XSSFSheet sheet=null;
		Set<Object> propKeys=prop.keySet();
		Iterator<Object> itrProps=propKeys.iterator();
		while(itrProps.hasNext())
		{
			Object key=itrProps.next();
			String testName=key.toString();
			if(testName.equalsIgnoreCase(testClassName))
			{
				String sheetName=prop.get(testName).toString();
				System.out.println(sheetName);
				sheet=wb.getSheet(sheetName);
				break;
			}
		}
		return sheet;
	}
	
	public static List<Object> getCellData(XSSFSheet sheet,String propertyFilePath) throws IOException
	{
		Properties prop=ReadExcel.getProperty(propertyFilePath);
		String tcColName=prop.get("TestcaseColumn").toString();
		System.out.println("Test case column name:"+tcColName);
		Iterator<Row> rowItr=sheet.iterator();
		Row heading=rowItr.next();
		Iterator<Cell> cellItr= heading.cellIterator();
		int k=0;
		int column=-1;
		while(cellItr.hasNext())
		{
			String tcHeading=cellItr.next().getStringCellValue();
			System.out.println(tcHeading);
			if(tcHeading.equalsIgnoreCase(tcColName))
			{
				column=k;
				break;
			}
			k++;
		}
		System.out.println(k);
		String testcase=null;
		Row row=null;
		while(rowItr.hasNext())
		{
			row=rowItr.next();
			testcase=row.getCell(column).getStringCellValue();
			if(testcase.equalsIgnoreCase("Verify distance"))
			{
				break;
			}
		}
		
		int lastColIndex=row.getLastCellNum()-1;
		List<Object> cellData=new ArrayList<Object>();
		for(int i=column+1;i<=lastColIndex;i++)
		{
			Cell cell=row.getCell(i);
			CellType type=cell.getCellType();
			if(type==CellType.NUMERIC)
			{
				cellData.add(cell.getNumericCellValue());
			}
			else if(type==CellType.STRING)
			{
				cellData.add(cell.getStringCellValue());
			}
			else if(type==CellType.BOOLEAN)
			{
				cellData.add(cell.getBooleanCellValue());
			}
			else
			{
				System.out.println("unknown cell type format");
			}
		}
		return cellData;
	}
}