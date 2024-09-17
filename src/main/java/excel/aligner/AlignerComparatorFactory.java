package excel.aligner;

import java.util.HashMap;

public class AlignerComparatorFactory {
    class ComparatorXL implements AlignerComparator {
        HashMap<String, Integer> possibleValues;

        @Override
        public int compare(String o1, String o2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'compare'");
        }

    }

    AlignerComparator createComparator(RowGroup attributes) {

        var rowSet = attributes.getRowSet();
        for (var row : rowSet) {
            var attributeCell = row.getValue().getCell(7);
            var stringValue = attributeCell.getStringCellValue();
        }
        return new ComparatorXL();
    }

}
