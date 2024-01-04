package excel.domains;

public record MaterialAttribute(String attributeGUID, String attributeName, double quantity, double soldQuantity,
                double price, double discount, double priceWithDiscount, String santimeters, String barcode) {

}
