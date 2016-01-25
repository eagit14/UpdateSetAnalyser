package ea.sn.manager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ExcelManager {

	private WritableCellFormat times10ptFont, times10ptBoldItalicFont , 
	times12ptBoldUnderlineBlueFont , times12ptBoldBackgroundGrayFont ,
	times10ptBoldFont, times12ptBoldBackgroundGreenFont, times12ptBackgroundGreenFont,
	times12ptBackgroundBlueFont, times12ptBackgroundGrayFont, times12ptFont;
	private String outputFile ;
	private String inputFile ;
	int excelCol = 0 , excelRow = 0;
	static JTextArea area  = null;
	
	public void setOutputFile(String oFile) {
		this.outputFile = oFile;
	}
	
	public void setInputFile(String iFile) {
		this.inputFile = iFile;
	}

	

	public static JTextArea getArea() {
		return area;
	}

	public void setArea(JTextArea area) {
		this.area = area;
	}

	
	public static void appendText(String newString) {
		String currentString = getArea().getText();
		String finalString = currentString + "\n" + newString ;
		getArea().setText(finalString);
	}
	
	
	private void setFonts() { 
		try {	
			WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
			WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
			WritableFont times10ptBoldItalic = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
			times10ptBoldItalic.setItalic(true);
			WritableFont times12pt = new WritableFont(WritableFont.TIMES, 12);
			WritableFont times12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);
			WritableFont times12ptBoldUnderlineBlue = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
			times12ptBoldUnderlineBlue.setColour(Colour.BLUE);


			times10ptFont = new WritableCellFormat(times10pt);
			times10ptFont.setWrap(false);

			times12ptFont = new WritableCellFormat(times10pt);
			times12ptFont.setWrap(false);
			
			times10ptBoldItalicFont = new WritableCellFormat(times10ptBoldItalic);
			times10ptBoldItalicFont.setWrap(false);

			times10ptBoldFont = new WritableCellFormat(times10ptBold);
			times10ptBoldFont.setWrap(false);

			times12ptBoldUnderlineBlueFont = new WritableCellFormat(times12ptBoldUnderlineBlue);
			times12ptBoldUnderlineBlueFont.setWrap(false);

			times12ptBoldBackgroundGrayFont = new WritableCellFormat(times12pt);
			times12ptBoldBackgroundGrayFont.setBackground(Colour.GREY_25_PERCENT);

			times12ptBackgroundGrayFont = new WritableCellFormat(times12pt);
			times12ptBackgroundGrayFont.setBackground(Colour.GREY_25_PERCENT);
			
			times12ptBoldBackgroundGreenFont = new WritableCellFormat(times12pt);
			times12ptBoldBackgroundGreenFont.setBackground(Colour.LIGHT_GREEN);

			times12ptBackgroundGreenFont = new WritableCellFormat(times12pt);
			times12ptBackgroundGreenFont.setBackground(Colour.LIGHT_GREEN);

		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 


	private void addCell(WritableSheet sheet, int column, int row, String s , WritableCellFormat font) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, font);
		sheet.addCell(label);
	}

	public void write() throws IOException, WriteException {
		
		File file = new File(outputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		setFonts();

		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("TDS", 0);
		WritableSheet mySheet = workbook.getSheet(0);
		buildSheet(mySheet);
		workbook.write();
		workbook.close();
	}

	private void buildSheet(WritableSheet sheet) throws RowsExceededException, WriteException  {
		try {
			    FileReader fr = null;
				fr = new FileReader(inputFile);
				BufferedReader br = new BufferedReader (fr);
				String line ;
				int linesNumber = 0 ;
				WritableCellFormat localFormat = times10ptFont ;
				addCell(sheet, 1, excelRow, "Type", times12ptBoldBackgroundGreenFont);
				addCell(sheet, 2, excelRow, "Name", times12ptBoldBackgroundGreenFont);
				addCell(sheet, 3, excelRow, "Table", times12ptBoldBackgroundGreenFont);
				addCell(sheet, 4, excelRow, "Description", times12ptBoldBackgroundGreenFont);
				addCell(sheet, 5, excelRow, "Delete?", times12ptBoldBackgroundGreenFont);
				addCell(sheet, 6, excelRow++, "sysid", times12ptBoldBackgroundGreenFont);
				
				String type = null ;
				String name = null ;
				
				while ( ( line = br.readLine()) != null ) {

					linesNumber =  linesNumber + 1 ;
					localFormat = times12ptFont;
//					if (excelRow % 2 == 0 )  { 
//						localFormat = times12ptFont;
//					} else { 
//						localFormat = times12ptBackgroundGrayFont ;
//					}
					
					if (line.contains("sys_script_25c0985d28c5710018c6bd8764e7ee57")) {
                                                System.out.println("BREAKPOINT");
					}
					
					if ( line.startsWith("<type>") ) { 
						type = getTag(line, "type") ;
						addCell(sheet, 1, excelRow, type, localFormat);
					} else if ( line.startsWith("<target_name>") ) { 
						name = getTag(line, "target_name") ; 
						addCell(sheet, 2, excelRow, name, localFormat);
					} else if (line.startsWith("<table>")) {
						addCell(sheet, 3, excelRow, getTag(line, "table"), localFormat);
					} else if (line.startsWith("<sys_id>")) {
						addCell(sheet, 6, excelRow, getTag(line, "sys_id"), localFormat);
					} else if (line.contains("<description>") || line.contains("&lt;description&gt") ) {
						addCell(sheet, 4, excelRow, getTagFromRegularExpression(line , "description"), localFormat);
					} else if (line.contains("<short_description>")) {
						addCell(sheet, 4, excelRow, getTagFromRegularExpression(line , "short_description"), localFormat);
					} else if (line.contains("<comments>") || line.contains("&lt;comments&gt") ) {
						addCell(sheet, 4, excelRow, getTagFromRegularExpression(line , "comments"), localFormat);
					} else if (line.startsWith("<action>")) {
						String action = getTag(line, "action");
						if (action.equals("DELETE")) {
							addCell(sheet, 5, excelRow, "YES", localFormat);
						} 
					} else if (line.startsWith("</sys_update_xml")) {
						excelRow++;
						if (type != null && !type.isEmpty() && name !=null && !name.isEmpty() ){
							appendText("line ( " + linesNumber + ") : Found " + type + " " + name);
						}
						name = null;
						type = null;
					}


				}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private String getTag(String line , String tagName) {
		int index1, index2;
		index1 = line.indexOf("<" + tagName + ">");
		index2 = line.indexOf("</" + tagName+ ">");
		
		return line.substring(index1 + tagName.length() + 2, index2);
	}

	private String getTagFromRegularExpression(String line , String tagName) {
		   String rValue = null;
		   Pattern patt1 = Pattern.compile(".*<" + tagName + ">" + "(.*)" + "</" + tagName + ">.*");
	   	   Matcher m1 = patt1.matcher(line);
	       if (m1.find()) { 
	    	   rValue = m1.group(1);
	       }
	       
	       if (rValue == null || rValue =="") {
	    	   Pattern patt2 = Pattern.compile(".*&lt;" + tagName + "&gt" + "(.*)" + "&lt;/" + tagName + "&gt.*");
		   	   Matcher m2 = patt2.matcher(line);
		       if (m2.find()) { 
		    	   rValue = m2.group(1);
		       }
	       }
		return rValue;
	}
	
} 