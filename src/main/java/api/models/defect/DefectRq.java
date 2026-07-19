package api.models.defect;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefectRq {
    @Expose
    private String code;
    @Expose
    private String title;
    @SerializedName("actual_result")
    @Expose
    private String actualResult;
    @Expose
    private Integer severity;
}
