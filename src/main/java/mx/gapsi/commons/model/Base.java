package mx.gapsi.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gapsi.commons.dto.CustomDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Base {
    private CustomDto customDto;
    private SortPaginator sortPaginator;
    private Object data;
    private Search search;
    private Boolean successfully;
}
