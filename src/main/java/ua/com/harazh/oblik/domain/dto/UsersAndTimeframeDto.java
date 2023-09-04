package ua.com.harazh.oblik.domain.dto;

import java.util.List;

public class UsersAndTimeframeDto {

    private List<Long> users;

    private String fromDate;

    private String toDate;

    public UsersAndTimeframeDto() {
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
