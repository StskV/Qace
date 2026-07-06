package api.models.testCase;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCaseRq {

    @Expose
    private String code;
    @Expose
    private String description;
    @Expose
    private String preconditions;
    @Expose
    private String postconditions;
    @Expose
    private String title;
    @Expose
    private int severity;
    @Expose
    private int priority;
    @Expose
    private int isToBeAutomated;

}
