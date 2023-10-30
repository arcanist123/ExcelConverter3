package excel.adapters;

import java.util.HashMap;

public class CellDescriptions {
    private HashMap<CellType1C, CellDescription> cellDescriptions;

    public CellDescriptions() {
        cellDescriptions.put(CellType1C.groupGuid, new CellDescription(1, "", "", null));
        cellDescriptions.put(CellType1C.groupName, new CellDescription(1, "", "", null));
        cellDescriptions.put(CellType1C.materialGuid, new CellDescription(1, "", "", null));
        cellDescriptions.put(CellType1C.materialName, new CellDescription(1, "", "", null));
        cellDescriptions.put(CellType1C.attibuteName, new CellDescription(1, "", "", null));
    }

    public CellDescription getCellDescription(CellType1C cellType) {
        return cellDescriptions.get(cellType);
    }
}
