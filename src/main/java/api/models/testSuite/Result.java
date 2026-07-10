package api.models.testSuite;

import api.models.common.BaseResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Result extends BaseResult {

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("preconditions")
    @Expose
    public String preconditions;
}
