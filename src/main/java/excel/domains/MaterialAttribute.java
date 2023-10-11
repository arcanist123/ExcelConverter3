package excel.domains;

public record MaterialAttribute(String attributeName, String attributeGUID, double quantity, double soldQuantity,
        double price, double discount, double priceWithDiscount, String santimeters, String barcode) {

}
