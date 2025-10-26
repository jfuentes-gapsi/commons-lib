package mx.gapsi.commons.dto;

import java.util.List;

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
	private List<Object> data;
    private Paginator paginator;
}
