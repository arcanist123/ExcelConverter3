package excel.converter;

public class Column {
    private String tag;
    private int columnIndex;

    public Column(String tag, String headerText, int columnIndex) {
        this.tag = tag;
        this.columnIndex = columnIndex;
    }

    public String getTag() {
        return tag;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
