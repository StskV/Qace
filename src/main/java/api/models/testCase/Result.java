package api.models.testCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Result {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
}
