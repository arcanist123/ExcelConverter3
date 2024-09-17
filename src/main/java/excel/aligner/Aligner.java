package excel.aligner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.graphbuilder.struc.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import excel.adapters.GiveSourceStream;
import excel.adapters.SendSourceStream;

public class Aligner {

    class ExcelLine {
        public String groupGuid;
        public String materialGuid;
        public String attributeGuid;
        public String attributeValue;
    }

    class Groups {
        ArrayList<RowGroup> groups = new ArrayList<RowGroup>();

        public void reshuffle(XSSFRow row) {
            for (var group : groups) {
                group.reshuffle(row);
            }
        }

    }

    private GiveSourceStream source;
    private SendSourceStream target;

    public Aligner(GiveSourceStream source, SendSourceStream target) {
        this.source = source;
        this.target = target;
    }

    public void AlignSource() throws IOException {
        var bytes = source.readSource();
        var input = new ByteArrayInputStream(bytes);
        var wb = new XSSFWorkbook(input);

        var sheet = wb.getSheetAt(0);

        var groups = getGroups(sheet);
        groups.reshuffle(sheet.getRow(0));

        wb.close();

    }

    private Groups getGroups(XSSFSheet sheet) {
        var row_counter = 0;
        var groups = new Groups();
        var currentGroup = new RowGroup();
        for (var row : sheet) {
            var myRow = (XSSFRow) row;
            var previousRow = new ExcelLine();
            var currentRow = new ExcelLine();

            currentRow.groupGuid = row.getCell(0).getStringCellValue();
            currentRow.materialGuid = row.getCell(1).getStringCellValue();
            currentRow.attributeGuid = row.getCell(0).getStringCellValue();
            currentRow.attributeValue = row.getCell(6).getStringCellValue();

            if (previousRow.groupGuid == currentRow.groupGuid && previousRow.materialGuid == currentRow.materialGuid) {
                currentGroup.rows.put(row_counter, myRow);

            } else {
                if (!currentGroup.rows.isEmpty()) {
                    groups.groups.add(currentGroup);
                }
                currentGroup = new RowGroup();
            }

        }
        return groups;
    }

}
