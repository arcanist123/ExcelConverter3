package excel.domains;

import java.util.ArrayList;

public record Material(String materialName, String materialGUID, String materialVendorCode,
        ArrayList<MaterialAttribute> materialAttributes, String materialPicture) {
}