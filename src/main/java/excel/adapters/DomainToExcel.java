package excel.adapters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excel.domains.MaterialGroup;
import excel.domains.Document;

public class DomainToExcel {
    public DomainToExcel() {

    }

    byte[] getExcelContent(Document document) throws IOException {
        // create the xlsx document
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("ПРАЙС");
        // ensure that the grouping in this sheet is on top
        sheet.setRowSumsBelow(false);

        this.writeDocument(wb, sheet, document);
        // write the xlsx Price list into the file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        wb.close();
        return outputStream.toByteArray();

    }

    private void writeDocument(Workbook wb, Sheet sheet, Document document) {
        var currentRow = this.writeHeader(wb, sheet);
        for (MaterialGroup group : document.materialGroups()) {
            currentRow = this.writeGroup(wb, sheet, document, group, currentRow);
        }
    }

    private int writeGroup(Workbook wb, Sheet sheet, Document document, MaterialGroup group, int currentRow) {
        Row row = sheet.createRow(currentRow);
        return 0;
    }

    private int writeHeader(Workbook wb, Sheet sheet) {
        return 0;
    }
}
