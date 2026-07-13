package api.models.common;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class BaseResult {

    @Expose
    public String code;
    @Expose
    public int id;
    @Expose
    public String title;
}
