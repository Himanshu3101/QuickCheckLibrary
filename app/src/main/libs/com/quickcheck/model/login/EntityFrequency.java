package com.quickcheck.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityFrequency {

@SerializedName("frequency")
@Expose
private String frequency;
@SerializedName("isenable")
@Expose
private String isenable;

public String getFrequency() {
return frequency;
}

public void setFrequency(String frequency) {
this.frequency = frequency;
}

public String getIsenable() {
return isenable;
}

public void setIsenable(String isenable) {
this.isenable = isenable;
}

}
