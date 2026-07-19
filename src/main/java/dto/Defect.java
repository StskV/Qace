package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Defect {
    private String title;
    private String actualResult;
    private Integer severity;
}
