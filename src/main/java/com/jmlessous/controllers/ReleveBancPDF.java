package com.jmlessous.controllers;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.TransactionRepository;
import com.jmlessous.repositories.UtilisateurRepository;


public class ReleveBancPDF {
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	CompteCourantRepository compteCourantRepository;
	
	private List<Transaction> listTransaction;
	private List<CompteCourant> listcpt  ;

	public ReleveBancPDF(List<Transaction> listTransaction,List<CompteCourant> cptcs,String rib) {			
		this.listTransaction = listTransaction;
		this.listcpt=cptcs;
		//transactionRepository.getTransactionByRibAccount(rib);	
			 }
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(Color.CYAN);
		
		//cell.setPadding(5);
		//Font font =FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(Color.WHITE);
		
		
		cell.setPhrase(new Phrase("Date de l'opération"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Description"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Emmeteur"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Destinataire"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Montant de la transaction"));
		table.addCell(cell);

	}
	private void writeTableHeader2(PdfPTable table2) {
		  
	        
		PdfPCell cell = new PdfPCell();
	
        
		cell.setPhrase(new Phrase("RIB"));
		table2.addCell(cell);
		cell.setPhrase(new Phrase("IBAN"));
		table2.addCell(cell);
		cell.setPhrase(new Phrase("Solde"));
		table2.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for (Transaction Transaction : listTransaction) {
			table.addCell(String.valueOf(Transaction.getDateTransaction()));
			table.addCell(String.valueOf(Transaction.getMotif()));
			table.addCell(Transaction.getEmetteur());
			table.addCell(Transaction.getDestinataire());
			table.addCell(String.valueOf(Transaction.getMontant()));			
		}
	}
	private void writeTableData2(PdfPTable table2) {
		for (CompteCourant cpts : listcpt) {
			table2.addCell(String.valueOf(cpts.getRib()));
			table2.addCell(String.valueOf(cpts.getIban()));
			table2.addCell(String.valueOf(cpts.getSolde()));
			
		}
	}
	
	 void export(HttpServletResponse response) throws DocumentException, IOException {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();
		//Font font =FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		//font.setColor(Color.BLUE);
		//font.setSize(18);
		Paragraph title2 = new Paragraph("Informations Bancaire");
		document.add(title2);
		PdfPTable table2 = new PdfPTable(3);
		table2.setWidthPercentage(100);
		table2.setSpacingBefore(15);
		writeTableHeader2(table2);
		writeTableData2(table2);
		document.add(table2);
		Paragraph title = new Paragraph("Relevé Bancaire");
		document.add(title);
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
	
		document.close();
		}
		
	 

	
}
