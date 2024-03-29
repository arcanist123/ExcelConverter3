package excel.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;
import java.util.function.Consumer;

public class Columns implements Iterable<Column> {

    public static final String materialDescription = "MaterialDescription";
    public static final String materialVendorCode = "MaterialVendorCode";
    public static final String attributeDescription = "AttributeDescription";
    public static final String attributeSantimeters = "AttributeSantimeters";
    public static final String attributePrice = "AttributePrice";
    public static final String attributeDiscount = "AttributeDiscount";
    public static final String attributePriceWithDiscount = "AttributePriceWithDiscount";
    public static final String attributeQuantity = "AttributeQuantity";
    public static final String attributeSoldQuantity = "AttributeSoldQuantity";
    public static final String materialCountryOfOrigin = "MaterialCountryOfOrigin";
    public static final String materialComposition = "MaterialComposition";
    public static final String materialGroup = "MaterialGroup";
    public static final String attributeSum = "AttributeSum";
    public static final String originalRow = "OriginalRow";
    public static final String materialPicture = "MaterialPicture";

    private ArrayList<Column> columns;
    private static final String HEADER_MATERIAL_VENDOR_CODE = "Артикул";
    private static final String HEADER_MATERIAL_DESCRIPTION = "Описание";
    private static final String HEADER_ATTRIBUTE_DESCRIPTION = "Размер";
    private static final String HEADER_ATTRIBUTE_SANTIMETERS = "Сантиметры";
    private static final String HEADER_ATTRIBUTE_PRICE = "Цена";
    private static final String HEADER_DISCOUNT = "Скидка";
    private static final String HEADER_PRICE_WITH_DISCOUNT = "Цена со скидкой";
    private static final String HEADER_QUANTITY = "Количество";
    private static final String HEADER_SOLD_QUANTITY = "Ваш Заказ";
    private static final String HEADER_COUNTRY = "Страна";
    private static final String HEADER_COMPOSITION = "Состав";
    private static final String HEADER_MATERIAL_GROUP = "Группа";

    @Override
    public Iterator<Column> iterator() {
        return columns.iterator();
    }

    @Override
    public void forEach(Consumer<? super Column> action) {

    }

    @Override
    public Spliterator<Column> spliterator() {
        return null;
    }

    public Column getColumn(String columnTag) {
        for (Column column : columns) {
            if (column.getTag() == columnTag) {
                return column;
            }
        }
        return null;
    }

    public Columns(Row headerRow) {
        columns = new ArrayList<Column>();
        for (Cell cell : headerRow) {
            // check all string cells
            if (cell.getCellType() == CellType.STRING) {
                if (cell.getStringCellValue().toUpperCase().equals(HEADER_MATERIAL_VENDOR_CODE.toUpperCase())) {
                    this.columns
                            .add(new Column(materialVendorCode, HEADER_MATERIAL_VENDOR_CODE, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_MATERIAL_DESCRIPTION.toUpperCase())) {
                    this.columns
                            .add(new Column(materialDescription, HEADER_MATERIAL_DESCRIPTION, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_ATTRIBUTE_DESCRIPTION.toUpperCase())) {
                    this.columns
                            .add(new Column(attributeDescription, HEADER_ATTRIBUTE_DESCRIPTION, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_ATTRIBUTE_SANTIMETERS.toUpperCase())) {
                    this.columns
                            .add(new Column(attributeSantimeters, HEADER_ATTRIBUTE_SANTIMETERS, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_ATTRIBUTE_PRICE.toUpperCase())) {
                    this.columns.add(new Column(attributePrice, HEADER_ATTRIBUTE_PRICE, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_DISCOUNT.toUpperCase())) {
                    this.columns.add(new Column(attributeDiscount, HEADER_DISCOUNT, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_PRICE_WITH_DISCOUNT.toUpperCase())) {
                    this.columns.add(
                            new Column(attributePriceWithDiscount, HEADER_PRICE_WITH_DISCOUNT, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_QUANTITY.toUpperCase())) {
                    this.columns.add(new Column(attributeQuantity, HEADER_QUANTITY, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_SOLD_QUANTITY.toUpperCase())) {
                    this.columns.add(new Column(attributeSoldQuantity, HEADER_SOLD_QUANTITY, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_COUNTRY.toUpperCase())) {
                    this.columns.add(new Column(materialCountryOfOrigin, HEADER_COUNTRY, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_COMPOSITION.toUpperCase())) {
                    this.columns.add(new Column(materialComposition, HEADER_COMPOSITION, cell.getColumnIndex()));

                } else if (cell.getStringCellValue().toUpperCase().equals(HEADER_MATERIAL_GROUP.toUpperCase())) {
                    this.columns.add(new Column(materialGroup, HEADER_MATERIAL_GROUP, cell.getColumnIndex()));
                }
            }
        }
    }
}
