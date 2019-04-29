package dto;

import com.google.gson.annotations.SerializedName;

public class PageDataResp extends DataResp {
    @SerializedName("total_num")
    private Long totalNum;

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }
}
