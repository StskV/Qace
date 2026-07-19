package api.models.testCase;

import api.models.common.BaseResult;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Result extends BaseResult {

    @Expose
    public String description;
    @Expose
    public String preconditions;
    @Expose
    public String postconditions;
    @Expose
    public int severity;
    @Expose
    public int priority;
    @Expose
    public int automation;
}
