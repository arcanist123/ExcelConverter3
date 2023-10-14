package excel.adapters;

import org.apache.poi.ss.usermodel.CellStyle;

public record CellDescription(int columnIndex, String fieldName, String columnDescription, CellStyle style) {
}