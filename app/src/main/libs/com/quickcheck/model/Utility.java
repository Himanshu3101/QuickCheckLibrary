package com.quickcheck.model;

import java.io.Serializable;

public class Utility implements Serializable{

    String utid;
    String utname;

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getUtname() {
        return utname;
    }

    public void setUtname(String utname) {
        this.utname = utname;
    }

}
