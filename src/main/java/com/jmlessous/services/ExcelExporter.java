package com.jmlessous.services;

import com.jmlessous.entities.Amortissement;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Amortissement> listAmortissement;


    public ExcelExporter(List<Amortissement> listAmortissement) {
        this.listAmortissement=listAmortissement;
        workbook = new XSSFWorkbook();

    }

    private void createCell(Row row,int columnCount, Object value,CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);

        if(value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else if(value instanceof Double) {
            cell.setCellValue((Double)value);
        }
        else if(value instanceof Float) {
            cell.setCellValue((Double)value);
        }
        else {

            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeHeaderLine() {
        sheet=workbook.createSheet("Amortissement");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        createCell(row,0,"Credit Information",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        font.setFontHeightInPoints((short)(10));

        row=sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);


        createCell(row, 0, "Echéance N°", style);
        createCell(row, 1, "Montant restant dù\r\n", style);
        createCell(row, 2, "Interet", style);
        createCell(row, 3, "Amortissement ", style);
        createCell(row, 4, "Mensualité", style);

    }

    private void writeDataLines() {
        int rowCount=2;

        CellStyle style=workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        //int o=0;
        for(Amortissement stu:listAmortissement) {
            //listAmortissement.get(i).toString();
            Row row=sheet.createRow(rowCount++);
            int columnCount=0;
            createCell(row, columnCount++,rowCount-2, style);
            createCell(row, columnCount++,stu.getMontantR(), style);
            createCell(row, columnCount++, stu.getInterest(),style);
            createCell(row, columnCount++,stu.getAmortissement(), style);
            createCell(row, columnCount++,stu.getMensualite(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }



}
