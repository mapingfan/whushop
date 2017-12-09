package com.whu.vo;

import java.util.Date;

public class History implements Comparable {

    private String pid;
    private Date date;

    public History(String pid, Date date) {
        this.pid = pid;
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        return pid.equals(history.pid);
    }

    @Override
    public int hashCode() {
        return pid.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        if (((History) o).date.after(this.date)) {
            return -1;
        } else {
            return 1;
        }
    }
}
