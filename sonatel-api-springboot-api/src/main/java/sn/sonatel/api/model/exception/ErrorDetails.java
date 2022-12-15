/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sn.sonatel.api.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails implements Serializable {
    private String type;
    private String title;
    private String instance;
    private String status;
    private String code;
    private String detail;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Violation> violations = new ArrayList<>();

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
