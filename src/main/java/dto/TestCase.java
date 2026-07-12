package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TestCase {
    private String title;
    private String description;
    private String preconditions;
    private String postconditions;
    private Integer severity;
    private Integer priority;
    private Integer isToBeAutomated;
}
