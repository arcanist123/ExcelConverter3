package excel.domains;

import java.util.ArrayList;

public record MaterialGroup(String groupName, String groupGUID, ArrayList<Material> materials) {
}