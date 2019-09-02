package excel.converter;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        try {

            Path path = Paths.get(args[0]);
            List<String> parametersList = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println(parametersList.size());
            for (int i = 0; i < parametersList.size(); i++) {
                System.out.println(parametersList.get(i));
            }

            //depending on the action instantiate different files
            String action = parametersList.get(1);//the first argument is always action
            if (Actions.GET_XLSX_FROM_XML.toString().equals(action)) {

                //we are requested to translate the XMl file into XLSX document. instantiate the translator
                //define source folder

                String sourceXML = parametersList.get(2);
                String targetExcel = parametersList.get(3);

                XML2XLSXConverter xml2XLSXConverter = XML2XLSXConverter.createConverter(sourceXML, targetExcel);
                xml2XLSXConverter.convert();
            } else if (Actions.GET_XML_FROM_XLSX.toString().equals(action)) {

                //we are requested to translate the xlsx file into xml document. instantiate the right translator
                //define source file
                String sourceFile = parametersList.get(2);
                String targetFile = parametersList.get(3);

                XLSX2XMLConverter xlsx2XMLConverter = XLSX2XMLConverter.createConverter(sourceFile, targetFile);
                xlsx2XMLConverter.convert();

            } else if (Actions.GENERATE_PICTURES.toString().equals(action)) {
                //we need to generate the pictures based on the file
                String sourceFile = parametersList.get(2);
                String targetPath = parametersList.get(3);

                PictureParser pictureParser = new PictureParser();
                pictureParser.convert(sourceFile, targetPath);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //the first parameter should be action


    }
}
