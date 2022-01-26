package com.nrifintech.medico.utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.nrifintech.medico.response.GraphResponse;

@Component
public class ExcelGenerator {
    private FileOutputStream excelFile = null;
    private Workbook workbook = null;
    private Sheet dataSheet = null;
    private Row currentRow = null;
    private int rowCounter = 0;
    public void createFile(String fileName,String label, String y) throws IOException
    {
        excelFile = new FileOutputStream(new File(fileName));
        this.workbook = new XSSFWorkbook();
        this.dataSheet = workbook.createSheet("Admin_Report");
        this.rowCounter = 0;
        this.createHeader(label,y);
    }
    public void createData(List<GraphResponse> objList)
    {
        for(GraphResponse obj : objList)
        {
	        this.currentRow = this.dataSheet.createRow(this.rowCounter++);
	        Cell label = this.currentRow.createCell(0);
	        Cell y = this.currentRow.createCell(1);
	        label.setCellValue(obj.getLabel());
	        y.setCellValue(obj.getY());
        }
    }
    public void saveFile() throws IOException
    {
        this.workbook.write(this.excelFile);
        this.excelFile.close();
    }
    private void createHeader(String label1, String y1){
        this.currentRow =  this.dataSheet.createRow(this.rowCounter++);
        Cell label = this.currentRow.createCell(0);
        Cell y = this.currentRow.createCell(1);
        label.setCellValue(label1);
        y.setCellValue(y1);
    }
}