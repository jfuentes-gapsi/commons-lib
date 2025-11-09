package mx.gapsi.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Label implements Serializable {
    private static final long serialVersionUID = 3242691350900119181L;
    private Long labelId;
    private String key;
    private String value;
    private String language;
    private Date created;
    private String userCreator;
    private String userModifier;
    private Date updated;
}