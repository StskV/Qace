package api.models.defect;

import api.models.common.BaseResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Result extends BaseResult {

    @SerializedName("actual_result")
    @Expose
    public String actualResult;
    @Expose
    public String severity;
}
