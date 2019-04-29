package dto;

import com.google.gson.annotations.SerializedName;
import common.BaseResp;

public class DataResp extends BaseResp {
    @SerializedName("data")
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
