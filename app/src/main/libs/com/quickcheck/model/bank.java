package com.quickcheck.model;

import java.io.Serializable;

public class bank implements Serializable {

    String bank_code;
    String nbank_name;

    public String getBank_code() {
        return bank_code;
    }
    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getNbank_name() {
        return nbank_name;
    }

    public void setNbank_name(String nbank_name) {
        this.nbank_name = nbank_name;
    }

}
