package excel.aligner;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

class RowGroup {
    public HashMap<Integer, XSSFRow> rows = new HashMap<Integer, XSSFRow>();

    public Set<Map.Entry<Integer, XSSFRow>> getRowSet() {
        return rows.entrySet();
    }

    private void copyRow(XSSFRow sourceRow, XSSFRow destinationRow) {

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            var oldCell = sourceRow.getCell(i);
            var newCell = destinationRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {

                case BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

    }

    public void reshuffle(XSSFRow placeholder) {
        var isReshuffleRequired = false;
        var firstRow = rows.get(0);
        XSSFRow secondRow = rows.get(0);
        for (int i = 0; i < rows.entrySet().size(); i++) {
            if (i == 0) {
                firstRow = rows.get(i);
            } else {
                secondRow = rows.get(i);

                if (this.isCorrectOrder(firstRow, secondRow)) {
                    firstRow = secondRow;
                } else {
                    this.exchangeRows(firstRow, secondRow, placeholder);
                    firstRow = rows.get(0);
                    i = 0;
                }
            }
        }
    }

    private void exchangeRows(XSSFRow firstRow, XSSFRow secondRow, XSSFRow placeholder) {
        this.copyRow(firstRow, placeholder);
        this.copyRow(secondRow, firstRow);
        this.copyRow(placeholder, secondRow);
    }

    private int getIndex(String attribute) {
        return switch (attribute) {
            case "XXS" -> 0;
            case "XS" -> 1;
            case "S" -> 2;
            case "M" -> 3;
            case "L" -> 4;
            case "XL" -> 5;
            case "XXL" -> 6;
            case "XXXL" -> 7;

            default -> -1;
        };
    }

    private boolean isCorrectOrder(XSSFRow firstRow, XSSFRow secondRow) {
        return this.getIndex(this.getAttributeValue(firstRow)) > this.getIndex(this.getAttributeValue(secondRow));
    }

    private String getAttributeValue(XSSFRow firstRow) {
        return firstRow.getCell(5).getStringCellValue();

    }
}
