package excel.converter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XLSX2XMLConverter {
    private String sourceFile;
    private String targetFile;

    public static XLSX2XMLConverter createConverter(String sourceFile, String targetFile) {
        // create instance of price list
        // read the provided xlsx document
        XLSX2XMLConverter converter = new XLSX2XMLConverter();
        converter.sourceFile = sourceFile;
        converter.targetFile = targetFile;
        return converter;

    }

    private static Cell findCell(Sheet sheet, String cellContent) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType().equals(CellType.STRING)) {
                    if (cell.getRichStringCellValue().getString().trim().toUpperCase().equals(cellContent)) {
                        return cell;
                    }
                }
            }
        }
        return null;
    }

    public void convert() throws IOException, ParserConfigurationException, TransformerException {
        // in this case we are converting the xlsx document to the xml represenation
        // that can be easilz consumed by
        // any external application
        // get the file
        Workbook wb = new XSSFWorkbook(sourceFile);
        // get sheet
        Sheet sheet = wb.getSheetAt(0);

        // try to find the cell with the vendor code
        Cell vendorCodeCell = findCell(sheet, ColumnHeaderTexts.VENDOR_CODE.toUpperCase());
        if (vendorCodeCell == null) {
            wb.close();
            throw new IOException("Не могу найти код номенклатуры");
        }

        // In case we were able to find the vendor code - try to find the attribute of
        // the vendor code
        Cell materialAttributeCell = findCell(sheet, ColumnHeaderTexts.ATTRIBUTE_NAME.toUpperCase());
        if (materialAttributeCell == null) {
            wb.close();
            throw new IOException("Не могу найти характеристику номенклатуры");
        }

        // ensure that the cells are on the same level
        int headerRowIndex;
        if (materialAttributeCell.getRowIndex() == vendorCodeCell.getRowIndex()) {
            // expected situation
            headerRowIndex = vendorCodeCell.getRowIndex();
        } else {
            wb.close();
            throw new IOException("Код номенклатуры и характеристика номенклатуры не на одной строке");
        }

        // we have the row with the header. get the information about other cells
        DocumentHeader header = new DocumentHeader(sheet, headerRowIndex);

        // we have identified the header. Now create the writer for the actual data
        // section
        MaterialsSection section = new MaterialsSection(sheet, headerRowIndex, header.getColumns());

        // we have instantiated all the objects required to write to XML file. Now
        // create the actual XML documents
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("PriceList");
        doc.appendChild(rootElement);

        // write the header into the XML
        header.writeToXML(rootElement, doc);

        // write the actual data into the XML
        section.writeToXML(rootElement, doc);

        // the XMl document was formulated. Now we can write it to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(targetFile));
        transformer.transform(source, result);

        System.out.println("File saved!");
        wb.close();
    }
}
