package api.models.common;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class BaseRs<T extends BaseResult> {

    @Expose
    public Boolean status;
    @Expose
    public T result;
}
