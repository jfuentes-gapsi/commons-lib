package mx.gapsi.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortPaginator {
    private int page;
    private int rows;
    private String columnOrder;
    private String order;
}
