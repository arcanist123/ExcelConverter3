package excel.converter;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.xml.sax.SAXException;

import excel.adapters.DomainToExcel;
import excel.adapters.JsonToDomainAdapter;
import excel.domains.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class XML2XLSXConverter {
    private static XML2XLSXConverter instance = null;
    private String sourceXML;
    private String targetExcel;

    private XML2XLSXConverter() {
        // Exists only to defeat instantiation.
    }

    public static XML2XLSXConverter getInstance() {
        if (instance == null) {
            instance = new XML2XLSXConverter();
        }
        return instance;
    }

    public static XML2XLSXConverter createConverter(String sourceXML, String targetExcel) {
        XML2XLSXConverter instance = XML2XLSXConverter.getInstance();
        instance.sourceXML = sourceXML;
        instance.targetExcel = targetExcel;
        return instance;
    }

    public String getSourceXML() {
        return sourceXML;
    }

    public void convert() throws ParserConfigurationException, IOException, SAXException {
        // convert file to domain
        var sourceFile = new File(sourceXML);
        var sourceFileString = Files.readString(sourceFile.toPath());
        var adapter = new JsonToDomainAdapter(sourceFileString);
        Document document = adapter.getDocument();

        // convert domain to result
        var docToXlsxAdapter = new DomainToExcel(null);
        var result = docToXlsxAdapter.getExcelContent(document);
        var targetFile = new File(targetExcel);
        Files.write(targetFile.toPath(), result);


    }

}