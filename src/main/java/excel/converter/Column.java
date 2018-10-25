package excel.converter;

public class Column {
    private String tag;
    private String headerText;
    private int columnIndex;
    public Column(String tag, String headerText, int columnIndex){
        this.tag = tag;
        this.columnIndex = columnIndex;
        this.headerText = headerText;
    }
    public String getTag(){
        return tag;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
