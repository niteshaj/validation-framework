package org.nj.validator.bo;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class User implements Serializable {
    private static final long serialVersionUID = -1701811200496987543L;

    @NotEmpty
    private String firstName;

}
