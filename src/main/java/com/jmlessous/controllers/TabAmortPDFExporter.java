package com.jmlessous.controllers;

import com.jmlessous.entities.Amortissement;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TabAmortPDFExporter {
	private List<Amortissement> TabAmortissement;

	public TabAmortPDFExporter(List<Amortissement> listInvestesment) {
		this.TabAmortissement = TabAmortissement;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.CYAN);
		
		//cell.setPadding(5);
		//Font font =FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(Color.WHITE);
		
		
		cell.setPhrase(new Phrase("montant restant"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Taux"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Invesstissor Email"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Invesstissor Name"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Invesstissor Second Name"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Invesstissor Profession"));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for (Amortissement investesment : TabAmortissement) {
			table.addCell(String.valueOf(investesment.getMontantR()));
			table.addCell(String.valueOf(investesment.getInterest()));
			table.addCell(String.valueOf(investesment.getMensualite()));
			table.addCell(String.valueOf(investesment.getAmortissement()));

		}
	}
	
	 void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();
		//Font font =FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		//font.setColor(Color.BLUE);
		//font.setSize(18);
		Paragraph title = new Paragraph("List of all investissement");
		document.add(title);
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
	}
	
}
