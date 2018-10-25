package excel.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.w3c.dom.Element;

public class MaterialAttribute {

    private String attributeName;
    private String attributeGUID;
    private int quantity;
    private double soldQuantity;
    private double price;
    private double discount;
    private double priceWithDiscount;
    private String santimeters;


    private MaterialAttribute() {

    }

    public static MaterialAttribute createMaterialAttribute(Element materialAttributeElement) {

        //create an instance of material attribute
        MaterialAttribute materialAttribute = new MaterialAttribute();

        //get the name of attribute
        materialAttribute.attributeName = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_NAME.toString()).item(0).getTextContent();

        //get the santimeters of attribute
        materialAttribute.santimeters = materialAttributeElement.getElementsByTagName(Tag.SANTIMETERS.toString()).item(0).getTextContent();

        //get the guid of the attribute
        materialAttribute.attributeGUID = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_GUID.toString()).item(0).getTextContent();

        //get the price of the attribute
        String priceString = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_PRICE.toString()).item(0).getTextContent();
        materialAttribute.price = Double.parseDouble(priceString);

        //get the discount of the attribute
        String discountString = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_DISCOUNT.toString()).item(0).getTextContent();
        materialAttribute.discount = Double.parseDouble(discountString);

        //get the price with discount
        String priceWithDiscountString = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_PRICE_WITH_DISCOUNT.toString()).item(0).getTextContent();
        materialAttribute.priceWithDiscount = Double.parseDouble(priceWithDiscountString);

        //get the quantity
        String quantityString = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_QUANTITY.toString()).item(0).getTextContent();
        materialAttribute.quantity = Integer.parseInt(quantityString);

        //get the sold quantity
        String soldQuantityString = materialAttributeElement.getElementsByTagName(Tag.ATTRIBUTE_SOLD_QUANTITY.toString()).item(0).getTextContent();
        materialAttribute.soldQuantity = Integer.parseInt(soldQuantityString);

        return materialAttribute;
    }

    public short writeToXLSX(Sheet mainSheet, short currentRow, Styles stylesCache) {
        //write the current material attribute
        Row row = mainSheet.createRow(currentRow);

        //output attribute guid
        Cell attributeGuidCell = row.createCell(ColumnIndex.HEADER_ATTRIBUTE_GUID);
        attributeGuidCell.setCellValue(this.attributeGUID);

        //output attribute name
        Cell attributeNameCell = row.createCell(ColumnIndex.HEADER_ATTRIBUTE_NAME);
        attributeNameCell.setCellValue(this.attributeName);
        CellStyle attributeNameCellStyle = stylesCache.getAttributeNameCellStyle();
        attributeNameCell.setCellStyle(attributeNameCellStyle);

        //output santimeters
        Cell attributeSantimetersCell = row.createCell(ColumnIndex.HEADER_SANTIMETERS);
        attributeSantimetersCell.setCellValue(this.santimeters);
        CellStyle attributeSantimetersCellStyle = stylesCache.getAttributeNameCellStyle();
        attributeSantimetersCell.setCellStyle(attributeSantimetersCellStyle);

        //output attribute price
        Cell attributePriceCell = row.createCell(ColumnIndex.HEADER_PRICE);
        attributePriceCell.setCellValue(this.price);
        CellStyle attributePriceCellStyle = stylesCache.getAttributePriceCellStyle();
        attributePriceCell.setCellStyle(attributePriceCellStyle);

        //output attribute discount
        Cell attributeDiscountCell = row.createCell(ColumnIndex.HEADER_DISCOUNT);
        attributeDiscountCell.setCellValue(this.discount);
        CellStyle attributeDiscountCellStyle = stylesCache.getAttributeDiscountCellStyle();
        attributeDiscountCell.setCellStyle(attributeDiscountCellStyle);

        //output attribute price with discount
        Cell attributePriceWithDiscountCell = row.createCell(ColumnIndex.HEADER_PRICE_WITH_DISCOUNT);
        attributePriceWithDiscountCell.setCellValue(this.priceWithDiscount);
        //depending, whether there is a discount or not, show the price with white colour or not
        CellStyle attributePriceWithDiscountCellStyle;
        if (this.discount == 0) {
            //get the standard style
            attributePriceWithDiscountCellStyle = stylesCache.getAttributePriceCellStyle();

        } else {
            attributePriceWithDiscountCellStyle = stylesCache.getAttributePriceWithDiscountCellStyle();
        }
        attributePriceWithDiscountCell.setCellStyle(attributePriceWithDiscountCellStyle);

        //output quantity
        Cell attributeQuantityCell = row.createCell(ColumnIndex.HEADER_QUANTITY);
        attributeQuantityCell.setCellValue(this.quantity);
        CellStyle attributeQuantityCellStyle = stylesCache.getQuantityStyle();
        attributeQuantityCell.setCellStyle(attributeQuantityCellStyle);

        //output sold quantity
        Cell attributeOrderCell = row.createCell(ColumnIndex.HEADER_SOLD_QUANTITY);
        if (this.soldQuantity != 0){
            attributeOrderCell.setCellValue(this.soldQuantity);
        }
        CellStyle attributeOrderCellStyle = stylesCache.getAttributeOrderCellStyle();
        attributeOrderCell.setCellStyle(attributeOrderCellStyle);

        //output sum
        Cell attributeSumCell = row.createCell(ColumnIndex.HEADER_SUM);
        int currentRowForFormula = currentRow + 1;//since apache poi is having 0 based counting and excel is 1 based counting - add 1 to convert
        String formula = "M" + currentRowForFormula + "*" + "K" + currentRowForFormula;
        attributeSumCell.setCellFormula(formula);//multiply quantity with sum with discount
        CellStyle attributeSumCellStyle = stylesCache.getSumCellStyle();
        attributeSumCell.setCellStyle(attributeSumCellStyle);


        //we have sent the current row to the sheet. increase the counter to indicate that
        currentRow++;

        return currentRow;
    }
}
