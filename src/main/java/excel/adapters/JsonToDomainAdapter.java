package excel.adapters;

import org.json.JSONArray;
import org.json.JSONObject;

import excel.domains.Document;

public class JsonToDomainAdapter {
    private String json;

    public JsonToDomainAdapter(String json) {
        this.json = json;
    }

    public Document getDocument() {
        JSONArray ja = new JSONArray(json);
        var length = ja.length();

        for (Object material : ja) {
            var jo = new JSONObject(material.toString());
var aaa = jo.getJSONObject("material");
        }
        return null;
    }

}
