package com.whu.vo;

public class Page {
    private int totalPages;
    private int totoalRecords;
    private int perPageRecords = 12;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotoalRecords() {
        return totoalRecords;
    }

    public void setTotoalRecords(int totoalRecords) {
        this.totoalRecords = totoalRecords;
    }

    public int getPerPageRecords() {
        return perPageRecords;
    }

    public void setPerPageRecords(int perPageRecords) {
        this.perPageRecords = perPageRecords;
    }
}
