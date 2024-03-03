package com.if5.todolist.controllers.helpers;

import com.if5.todolist.models.entities.Role;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = {"nom"};
	  
	  static String SHEET = "Roles";//nom du fichier Excel à importer
	  public static boolean hasExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	  
	  public static List<Role> excelToTutorials(InputStream is) {
		    try {
		      Workbook workbook = new XSSFWorkbook(is);
		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();
		      List<Role> roles = new ArrayList<Role>();
		      int rowNumber = 0;
		      while (rows.hasNext()) {
		        Row currentRow = rows.next();
		        // skip header
		        if (rowNumber == 0) {
		          rowNumber++;
		          continue;
		        }
		        Iterator<Cell> cellsInRow = currentRow.iterator();
		        Role role = new Role();
		        int cellIdx = 0;
		        while (cellsInRow.hasNext()) {
		          Cell currentCell = cellsInRow.next();
		          switch (cellIdx) {
		          case 0:
		            role.setNom(currentCell.getStringCellValue());
		            break;
		          default:
		            break;
		          }
		          cellIdx++;
		        }
		        roles.add(role);
		      }
		      workbook.close();
		      return roles;
		    } catch (IOException e) {
		      throw new RuntimeException("échec de l'importation des données: " + e.getMessage());
		    }
		  }
}
