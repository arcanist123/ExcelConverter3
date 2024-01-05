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
        public static final String materialVendorCode = "materialVendorCode";
        public static final String materialPicture = "materialPicture";
        public static final String materialAttributeName = "materialAttributeName";
        public static final String materialAttibuteQuantity = "materialAttributeQuantity";
        public static final String materialAttibuteSoldQuantity = "materialAttributeSoldQuantity";
        public static final String materialAttibutePrice = "materialAttributeGuid";
        public static final String materialAttibuteDiscount = "materialAttributeDiscount";
        public static final String materialAttibutePriceWithDiscount = "materialAttributePriceWithDiscount";
        public static final String materialAttibuteSize = "materialAttributeSize";
        public static final String materialAttibuteBarcode = "materialAttributeBarcode";

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
        return document;
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
            var quantity = Double.parseDouble(currentJsonRecord.getString(Constants.materialAttibuteQuantity));
            var soldQuantity = Double.parseDouble(currentJsonRecord.getString(Constants.materialAttibuteSoldQuantity));
            var price = Double.parseDouble(currentJsonRecord.getString(Constants.materialAttibutePrice));
            var discount = Double.parseDouble(currentJsonRecord.getString(Constants.materialAttibuteDiscount));
            var priceWithDiscount = Double
                    .parseDouble(currentJsonRecord.getString(Constants.materialAttibutePriceWithDiscount));
            var size = currentJsonRecord.getString(Constants.materialAttibuteSize);
            var barcode = currentJsonRecord.getString(Constants.materialAttibuteBarcode);
            attribute = new MaterialAttribute(guid, name, quantity, soldQuantity, price, discount, priceWithDiscount,
                    size, barcode);
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
            var vendorCode = currentJsonRecord.getString(Constants.materialVendorCode);
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
