package api.models.defect;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefectRq {
    @Expose
    private String code;
    @Expose
    private String title;
    @Expose
    private String actual_result;
    @Expose
    private Integer severity;
}
