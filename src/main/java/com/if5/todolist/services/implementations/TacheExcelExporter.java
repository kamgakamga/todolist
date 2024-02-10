package com.if5.todolist.services.implementations;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.if5.todolist.models.entities.Tache;

public class TacheExcelExporter {
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Tache> listTaches;
    
    
    public TacheExcelExporter(List<Tache> listTaches) {
        this.listTaches = listTaches;
        workbook = new XSSFWorkbook();
    }
  
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        font.setUnderline(FontUnderline.SINGLE);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBottomBorderColor(IndexedColors.MAROON.getIndex());
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        
        style.setFont(font);
        ((Cell) style).getCellStyle().setLocked(true);
         
        createCell(row, 0, "Libelle", style);      
        createCell(row, 1, "Responsable", style);       
        createCell(row, 2, "Statut", style);    
        createCell(row, 3, "DateDebut", style);
        createCell(row, 4, "DateFin", style);
        createCell(row, 5, "Parent", style);      
        createCell(row, 6, "Sous-Tache", style);       
        createCell(row, 7, "Sprint", style); 
        
        
    }
     
    private void writeDataLines() {
    	
    	DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
		        DateFormat.SHORT,
		        DateFormat.SHORT);
    	
        int rowCount = 1;
 
        CellStyle styles = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
   //     font.setBold(true);
        styles.setAlignment(HorizontalAlignment.CENTER);
        styles.setFont(font);
        styles.setLocked(true);
                 
        for (Tache tache : listTaches) {
        	
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell2(row, columnCount++, tache.getLibelle(), styles);
            createCell2(row, columnCount++, tache.getDescription(), styles);
            createCell2(row, columnCount++, tache.getStatut(), styles);
            createCell2(row, columnCount++, shortDateFormat.format(tache.getDateDebut()).toString(), styles);
            createCell2(row, columnCount++, shortDateFormat.format(tache.getDateFin()), styles);
            if(tache.getTacheParente()!=null) {
            	createCell2(row, columnCount++, tache.getTacheParente().getLibelle(), styles);
             }else {
            	 createCell2(row, columnCount++, "/", styles);
             }
            
            if(tache.getListSousTache().size() != 0) {
            	createCell2(row, columnCount++, tache.getListSousTache().stream().map(e->e.getLibelle()
                		).collect(Collectors.toList()).toString(), styles);
             }else {
            	 createCell2(row, columnCount++, "/", styles);
             }
            createCell2(row, columnCount++, tache.getSprint().getLibelle(), styles);
            for(int i = 1;  i < 1; i++) {
            	 sheet.autoSizeColumn(i);
            }
        }
      //  sheet.protectSheet("");
        
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String)value);
        }
        cell.setCellStyle(style);
    }
    
    private void createCell2(Row row, int columnCount, Object value, CellStyle styles) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String)value);
        }
        sheet.autoSizeColumn(columnCount);
        cell.setCellStyle(styles);
    }
     

}
