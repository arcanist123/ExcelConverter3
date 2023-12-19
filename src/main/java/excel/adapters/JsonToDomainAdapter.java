package excel.adapters;

import org.json.JSONArray;

import excel.domains.Document;

public class JsonToDomainAdapter {
    private String json;

    public JsonToDomainAdapter(String json) {
        this.json = json;
    }

    public Document getDocument() {
        JSONArray ja = new JSONArray(json);
        
        return null;
    }

}
