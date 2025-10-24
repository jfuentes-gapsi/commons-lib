package mx.gapsi.commons.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import mx.gapsi.commons.model.Paginator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomDto {
    private int code;
    private String description;
    @JsonIgnore
    private Object object;
	private List<Object> objects;
    private Paginator paginator;
}
