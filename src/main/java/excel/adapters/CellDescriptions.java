package excel.adapters;

import java.util.HashMap;

public class CellDescriptions {
    private HashMap<CellType1C, CellDescription> cellDescriptions;

    public CellDescriptions() {
        cellDescriptions = new HashMap<CellType1C, CellDescription>();
        cellDescriptions.put(CellType1C.materialGroupGuid, new CellDescription(1, "", "Group guid", null));
        cellDescriptions.put(CellType1C.materialGroupName, new CellDescription(4, "", "group name", null));
        cellDescriptions.put(CellType1C.materialGuid, new CellDescription(2, "", "material guid", null));
        cellDescriptions.put(CellType1C.materialName, new CellDescription(5, "", "material name", null));
        cellDescriptions.put(CellType1C.materialVendorCode, new CellDescription(6, "", "material vendor code", null));
        cellDescriptions.put(CellType1C.materialAttibuteGuid, new CellDescription(3, "", "attribute guid", null));
    }

    public CellDescription getCellDescription(CellType1C cellType) {
        return cellDescriptions.get(cellType);
    }
}
