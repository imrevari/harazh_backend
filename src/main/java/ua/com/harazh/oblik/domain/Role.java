package ua.com.harazh.oblik.domain;

public enum Role {
	
	ROLE_ADMIN("адмiн"),

    ROLE_USER("майстер");

    private String displayedname;

    Role(String displayedname) {
        this.displayedname = displayedname;
    }

    public String getDisplayedname() {
        return displayedname;
    }

}
