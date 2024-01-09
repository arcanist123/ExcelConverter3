package excel.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CellDescriptions {
    private HashMap<CellType1C, CellDescription> cellDescriptions;

    public CellDescriptions() {
        cellDescriptions = new HashMap<CellType1C, CellDescription>();
        cellDescriptions.put(CellType1C.materialGroupGuid, new CellDescription(0, "", "Group guid", null));
        cellDescriptions.put(CellType1C.materialGroupName, new CellDescription(3, "", "group name", null));
        cellDescriptions.put(CellType1C.materialGuid, new CellDescription(1, "", "material guid", null));
        cellDescriptions.put(CellType1C.materialName, new CellDescription(4, "", "material name", null));
        cellDescriptions.put(CellType1C.materialVendorCode, new CellDescription(5, "", "material vendor code", null));
        cellDescriptions.put(CellType1C.materialAttibuteGuid, new CellDescription(2, "", "attribute guid", null));
    }

    public CellDescription getCellDescription(CellType1C cellType) {
        return cellDescriptions.get(cellType);
    }

    public Iterator<Entry<CellType1C, CellDescription>> getIterator() {
        return this.cellDescriptions.entrySet().iterator();
    }
}
