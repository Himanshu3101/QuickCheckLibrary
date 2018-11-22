package com.quickcheck.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityPeriondEnableOn {

@SerializedName("periodenableon")
@Expose
private String periodenableon;
@SerializedName("isenable")
@Expose
private String isenable;

public String getPeriodenableon() {
return periodenableon;
}

public void setPeriodenableon(String periodenableon) {
this.periodenableon = periodenableon;
}

public String getIsenable() {
return isenable;
}

public void setIsenable(String isenable) {
this.isenable = isenable;
}

}
