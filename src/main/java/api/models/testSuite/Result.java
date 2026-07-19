package api.models.testSuite;

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
}
