package excel.domains;

public record Material(String materialName, String materialGUID, String materialVendorCode,
                MaterialAttribute[] materialAttributes, String materialPicture) {
}