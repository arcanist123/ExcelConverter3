package excel.converter;

public enum Actions {

    GET_XLSX_FROM_XML("GET_XLSX_FROM_XML"),
    GET_XML_FROM_XLSX("GET_XML_FROM_XLSX");
    
        public static final String GENERATE_PICTURES = "GENERATE_PICTURES";
    private final String text;

    /**
     * @param text
     */
    private Actions(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}

