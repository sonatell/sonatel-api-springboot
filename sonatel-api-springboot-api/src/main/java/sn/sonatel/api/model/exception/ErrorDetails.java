package sn.sonatel.api.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private String type;
    private String title;
    private String instance;
    private String status;
    private String code;
    private String detail;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Violation> violations;

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", instance='" + instance + '\'' +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                ", violations=" + violations +
                '}';
    }
}
