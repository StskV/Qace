package dict;

import lombok.Getter;

@Getter
public enum MemberAccess {
    ALL_MEMBERS("Add all members to this project"),
    GROUP_ACCESS("Group access"),
    DONT_ADD_MEMBERS("Don't add members");

    private final String label;

    MemberAccess(String label) {
        this.label = label;
    }
}