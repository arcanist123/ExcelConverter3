package excel.adapters;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import excel.domains.Material;
import excel.domains.MaterialAttribute;
import excel.domains.Document;
import excel.domains.MaterialGroup;

public class JsonToDomainAdapter {
    private class Constants {
        public final static String materialGroupGuid = "groupName";
        public final static String materialGroupName = "groupGuid";
        public final static String materialGuid = "materialGuid";
        public final static String materialAttibuteGuid = "materialAttributeGuid";
        public final static String materialName = "materialName";
        public static final String vendorCode = null;
        public static final String materialPicture = null;
        public static final String materialAttributeName = null;
        public static final String materialAttibuteQuantity = null;
        public static final String materialAttibuteSoldQuantity = null;
        public static final String materialAttibutePrice = null;
        public static final String materialAttibuteDiscount = null;
        public static final String materialAttibutePriceWithDiscount = null;
        public static final String materialAttibuteSize = null;

    }

    private String json;

    public JsonToDomainAdapter(String json) {
        this.json = json;
    }

    public Document getDocument() {
        JSONArray ja = new JSONArray(json);
        var document = new Document(new ArrayList<MaterialGroup>());
        for (Object materialObject : ja) {
            var currentJsonRecord = (JSONObject) materialObject;
            var currentGroup = this.insertGroupIntoDocument(document, currentJsonRecord);
            var material = this.insertMaterialIntoGroup(currentGroup, currentJsonRecord);
            var materialAttribute = this.insertAttributeIntoMaterial(material, currentJsonRecord);
        }
        return null;
    }

    private MaterialAttribute insertAttributeIntoMaterial(Material material, JSONObject currentJsonRecord) {
        var guid = currentJsonRecord.getString(Constants.materialAttibuteGuid);
        // check if attribute exists
        var attribute = material.materialAttributes().stream()
                .filter(filterAttribute -> guid.equals(filterAttribute.attributeGUID()))
                .findAny()
                .orElse(null);
        if (attribute == null) {
            var name = currentJsonRecord.getString(Constants.materialAttributeName);
            var quantity = currentJsonRecord.getString(Constants.materialAttibuteQuantity);
            var soldQuantity = currentJsonRecord.getString(Constants.materialAttibuteSoldQuantity);
            var price = currentJsonRecord.getString(Constants.materialAttibutePrice);
            var discount = currentJsonRecord.getString(Constants.materialAttibuteDiscount);
            var priceWithDiscount = currentJsonRecord.getString(Constants.materialAttibutePriceWithDiscount);
            var size = currentJsonRecord.getString(Constants.materialAttibuteSize);
        var barcode = currentJsonRecord.getString(json)
        }
        return attribute;
    }

    private Material insertMaterialIntoGroup(MaterialGroup currentGroup, JSONObject currentJsonRecord) {
        var guid = currentJsonRecord.getString(Constants.materialGuid);
        var material = currentGroup.materials().stream()
                .filter(filterMaterial -> guid.equals(filterMaterial.materialGUID()))
                .findAny()
                .orElse(null);
        if (material == null) {
            var name = currentJsonRecord.getString(Constants.materialName);
            var vendorCode = currentJsonRecord.getString(Constants.vendorCode);
            var picture = currentJsonRecord.getString(Constants.materialPicture);
            material = new Material(guid, name, vendorCode, new ArrayList<MaterialAttribute>(),
                    picture);
            currentGroup.materials().add(material);
        }
        return material;
    }

    private MaterialGroup insertGroupIntoDocument(Document document, JSONObject currentJsonRecord) {
        var name = currentJsonRecord.getString(Constants.materialGroupName);
        var guid = currentJsonRecord.getString(Constants.materialGroupGuid);

        // check if group exists
        var group = document.materialGroups().stream()
                .filter(filterGroup -> guid.equals(filterGroup.groupGUID()))
                .findAny()
                .orElse(null);
        if (group == null) {
            group = new MaterialGroup(name, guid, new ArrayList<Material>());
            document.materialGroups().add(group);
        }
        return group;

    }

}
