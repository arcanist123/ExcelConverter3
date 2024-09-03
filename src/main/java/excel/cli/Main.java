package excel.cli;

import java.util.HashMap;

import excel.adapters.FileSource;
import excel.adapters.FileTarget;
import excel.converter.XML2XLSXConverter;

public class Main {
    private static final String actionGetXlsxFromXml = "GET_XLSX_FROM_XML";
    private static final String actionXlsxAlighSequence = "XLSX_ALIGH_SEQUENCE";

    public static void main(String[] args) {
        new Main().run(args);

    }

    private void run(String[] args) {
        if (args.length == 0) {
            System.out.println("you need to provide some parameters");
            return;
        }
        if ((args.length % 2) != 0) {
            System.out.println("you need to provide parameters in pairs ");
            return;
        }

        var parameters = this.getParametersDictionary(args);

        try {

            // get the action
            var action = parameters.get("--action");
            if (action == null) {
                System.out.println("action must be defined with --action parameter");
                return;
            }

            switch (action) {
                case actionGetXlsxFromXml:

                    String sourceXML = parameters.get("--source");
                    String targetExcel = parameters.get("--target");

                    XML2XLSXConverter xml2XLSXConverter = XML2XLSXConverter.createConverter(sourceXML, targetExcel);
                    xml2XLSXConverter.convert();
                    break;

                case actionXlsxAlighSequence:
                    var sourceExcel = parameters.get("--source");
                    targetExcel = parameters.get("--target");

                    var fileSource = new FileSource(sourceExcel);
                    var FileTarget = new FileTarget(targetExcel);

                default:
                    break;
            }
            /*
             * // depending on the action instantiate different files
             * String action = parametersList.get(1);// the first argument is always action
             * if (Actions.GET_XLSX_FROM_XML.toString().equals(action)) {
             * 
             * // we are requested to translate the XMl file into XLSX document. instantiate
             * // the translator
             * // define source folder
             * 
             * String sourceXML = parametersList.get(2);
             * String targetExcel = parametersList.get(3);
             * 
             * XML2XLSXConverter xml2XLSXConverter =
             * XML2XLSXConverter.createConverter(sourceXML, targetExcel);
             * xml2XLSXConverter.convert();
             * } else if (Actions.GET_XML_FROM_XLSX.toString().equals(action)) {
             * 
             * // we are requested to translate the xlsx file into xml document. instantiate
             * // the right translator
             * // define source file
             * String sourceFile = parametersList.get(2);
             * String targetFile = parametersList.get(3);
             * 
             * XLSX2XMLConverter xlsx2XMLConverter =
             * XLSX2XMLConverter.createConverter(sourceFile, targetFile);
             * xlsx2XMLConverter.convert();
             * 
             * } else if (Actions.GENERATE_PICTURES.toString().equals(action)) {
             * // we need to generate the pictures based on the file
             * String sourceFile = parametersList.get(2);
             * String targetPath = parametersList.get(3);
             * 
             * PictureParser pictureParser = new PictureParser();
             * pictureParser.convert(sourceFile, targetPath);
             * }
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        // the first parameter should be action

    }

    private HashMap<String, String> getParametersDictionary(String[] args) {
        var parametersDictionary = new HashMap<String, String>();
        var parameterName = "";
        var parameterValue = "";
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                // this is the name of parameter
                parameterName = args[i];
            } else {
                // this is the value of parameter
                parameterValue = args[i];
                parametersDictionary.put(parameterName, parameterValue);
            }
        }
        return parametersDictionary;
    }
}
