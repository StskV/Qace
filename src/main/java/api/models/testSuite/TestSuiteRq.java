package api.models.testSuite;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestSuiteRq {

    @Expose
    private String code;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String preconditions;
}
