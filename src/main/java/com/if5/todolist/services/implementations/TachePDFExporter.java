package com.if5.todolist.services.implementations;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.text.DateFormat;

import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.repositories.AttributionRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class TachePDFExporter {
	
	private List<Tache> listOfTask;
	
	@Autowired
    private AttributionRepository attributionRepository;
  
	public TachePDFExporter(List<Tache> listOfTask) {
		this.listOfTask = listOfTask;
	}
	
	
	private void writePDFHeader(PdfPTable table) {
		
		PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        //cell.setColspan(5);
        
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);
        
        cell.setPhrase(new Phrase("Libelle", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Responsable", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Statut", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Date de debut", font));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("Date de fin", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Parent", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Sous-Tache", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Sprint", font));
        table.addCell(cell);
         
		
		
	}
	
	private void writeTableData(PdfPTable table) {
		
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
		        DateFormat.SHORT,
		        DateFormat.SHORT);
        for (Tache tache : listOfTask) {
        	
        	//List<Tache> t = tache.getListSousTache();
            table.addCell(tache.getLibelle());
            
            System.out.println("******= "+ tache.getResponsableTache().toString());
          //  Utilisateur responsable = attributionRepository.findResponsableTache(tache.getId());
            if(!Objects.isNull(tache.getResponsableTache())) {
        		//Utilisateur responsable = attributionRepository.findResponsableTache(tache.getId());
        	//	table.addCell(responsable.getNom().toUpperCase());          
            }else {	
            	table.addCell("    /");
            }
            
            table.addCell(tache.getStatut());
            table.addCell(String.valueOf(shortDateFormat.format(tache.getDateDebut())));
            table.addCell(String.valueOf(shortDateFormat.format(tache.getDateFin())));
            if(tache.getTacheParente()!=null) {
               table.addCell(tache.getTacheParente().getLibelle());
            }else {
            	 table.addCell("    /");
            }
            if(tache.getListSousTache().size() != 0) {
            	
                table.addCell(tache.getListSousTache().stream().map(e->e.getLibelle()
                		).collect(Collectors.toList()).toString());
             }else {
             	 table.addCell("    /");
             }
            
            table.addCell(tache.getSprint().getLibelle());
        }
           // table.addCell(t.stream().map(e->e.getLibelle()).collect(Collectors.toList())));
            
            
        }
	
	
	
        
        public void export(HttpServletResponse response) throws DocumentException, IOException {
            
        	
        	Document document = new Document(PageSize.A4.rotate());
        
            PdfWriter.getInstance(document, response.getOutputStream());
             
            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(13);
            font.setColor(Color.BLUE);
             
            Paragraph p = new Paragraph("LISTE DES TACHES", font);
            p.setAlignment(Paragraph.ALIGN_CENTER);
             
            document.add(p);
             
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {1.8f,2.1f, 3.2f,2.6f, 2.6f, 1.8f, 3.8f, 1.7f});
            table.setSpacingBefore(3);
             
            writePDFHeader(table);
            writeTableData(table);
             
            document.add(table);
             
            document.close();
             
        }
    }
	

