package com.quickcheck.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityTodebit {

@SerializedName("todebit")
@Expose
private String todebit;
@SerializedName("isenable")
@Expose
private String isenable;

public String getTodebit() {
return todebit;
}

public void setTodebit(String todebit) {
this.todebit = todebit;
}

public String getIsenable() {
return isenable;
}

public void setIsenable(String isenable) {
this.isenable = isenable;
}

}
