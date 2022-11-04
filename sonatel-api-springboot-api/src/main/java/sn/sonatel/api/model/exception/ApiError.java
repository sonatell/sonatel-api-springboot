package sn.sonatel.api.model.exception;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private String type;
    private String title;
    private String instance;
    private String status;
    private String code;

    private String detail;
    
    private List<Violation> violations;

}
