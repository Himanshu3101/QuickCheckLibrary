package com.quickcheck.model;

import java.io.Serializable;

public class sponserbank implements Serializable{
    String spname;
    String spid;

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }
}
