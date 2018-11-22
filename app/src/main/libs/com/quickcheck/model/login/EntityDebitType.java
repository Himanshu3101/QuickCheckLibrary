package com.quickcheck.model.login;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityDebitType {

@SerializedName("debittype")
@Expose
private String debittype;
@SerializedName("isenable")
@Expose
private String isenable;

public String getDebittype() {
return debittype;
}

public void setDebittype(String debittype) {
this.debittype = debittype;
}

public String getIsenable() {
return isenable;
}

public void setIsenable(String isenable) {
this.isenable = isenable;
}

}


