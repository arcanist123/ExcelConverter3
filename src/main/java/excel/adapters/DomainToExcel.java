package excel.adapters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excel.domains.MaterialGroup;
import excel.domains.Document;
import excel.domains.Material;
import excel.domains.MaterialAttribute;

public class DomainToExcel {
    private CellDescriptions cellDescriptions;

    public DomainToExcel(CellDescriptions cellDescriptions) {
        this.cellDescriptions = cellDescriptions;
    }

    public byte[] getExcelContent(Document document) throws IOException {
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
        var currentRowIndex = this.writeHeader(wb, sheet);
        for (MaterialGroup group : document.materialGroups()) {
            currentRowIndex = this.writeGroup(wb, sheet, document, group, currentRowIndex);
        }
    }

    private int writeMaterialAttribute(Workbook wb, Sheet sheet, Document document, MaterialAttribute materialAttribute,
            int currentRow) {
        this.outputCell(sheet, currentRow,
                this.cellDescriptions.getCellDescription(CellType1C.materialAttributeBarcode),
                materialAttribute.attributeGUID());
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attibuteName));
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attributeBarcode));
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attributePrice));
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attributeQuantity));
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attributeOrder));
        // this.outputCell(sheet, currentRow,
        // this.cellDescriptions.getCellDescription(CellType1C.attributeSum));
        return 0;
    }

    private void outputCell(Sheet sheet, int currentRow, CellDescription cellDescription, String value) {
        var row = sheet.getRow(currentRow);
        if (row == null) {
            row = sheet.createRow(currentRow);
        }
        var cell = row.createCell(cellDescription.columnIndex());
        cell.setCellValue(value);
    }

    private int writeMaterial(Workbook wb, Sheet sheet, Document document, Material material, int currentRow) {
        var startRow = currentRow;
        for (MaterialAttribute materialAttribute : material.materialAttributes()) {
            currentRow = this.writeMaterialAttribute(wb, sheet, document, materialAttribute, currentRow);
        }
        this.outputCell(sheet, startRow, this.cellDescriptions.getCellDescription(CellType1C.materialGuid),
                material.materialGUID());
        this.outputCell(sheet, startRow, this.cellDescriptions.getCellDescription(CellType1C.materialVendorCode),
                material.materialVendorCode());
        this.outputCell(sheet, startRow, this.cellDescriptions.getCellDescription(CellType1C.materialName),
                material.materialName());
        this.outputPicture(startRow, currentRow, material.materialPicture());
        this.resizeCellsForMaterial(startRow, currentRow);
        return currentRow;
    }

    private void resizeCellsForMaterial(int startRow, int currentRow) {
    }

    private void outputPicture(int startRow, int currentRow, String string) {
    }

    private int writeGroup(Workbook wb, Sheet sheet, Document document, MaterialGroup group, int currentRow) {
        var offset = 1;
        Row row = sheet.createRow(currentRow + offset);
        var groupGuidDescription = this.cellDescriptions.getCellDescription(CellType1C.materialGroupGuid);

        row.createCell(groupGuidDescription.columnIndex())
                .setCellValue(group.groupGUID());

        var groupNameDescription = this.cellDescriptions.getCellDescription(CellType1C.materialGroupName);
        row.createCell(groupNameDescription.columnIndex())
                .setCellValue(group.groupName());
        for (Material material : group.materials()) {
            offset++;
            this.writeMaterial(wb, sheet, document, material, currentRow + offset);
        }
        return currentRow + 2;
    }

    private int writeHeader(Workbook wb, Sheet sheet) {
        var row = sheet.createRow(0);
        var it = this.cellDescriptions.getIterator();
        while (it.hasNext()) {
            Map.Entry<CellType1C, CellDescription> entry = it.next();
            var cellDescription = entry.getValue();
            var cell = row.createCell(cellDescription.columnIndex());
            cell.setCellValue(cellDescription.columnDescription());
        }
        return 1;
    }
}
