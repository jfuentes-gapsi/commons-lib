package mx.gapsi.commons.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import mx.gapsi.commons.dto.CustomDto;
import mx.gapsi.commons.exception.BadSortPaginator;
import mx.gapsi.commons.model.Base;
import mx.gapsi.commons.model.Paginator;
import mx.gapsi.commons.model.Search;
import mx.gapsi.commons.model.SortPaginator;

public class InitObject {

    private static <T extends Base> T setBase (T toObject, Paginator paginator, SortPaginator sortPaginator, CustomDto customDto, Search search) {
        customDto.setPaginator(paginator);
        toObject.setCustomDto(customDto);
        toObject.setSortPaginator(sortPaginator);
        toObject.setSearch(search);

        return toObject;
    }
    
    public static <T extends Base> T toBase(T toObject, String dataQuery) throws BadSortPaginator {
        Paginator paginator = new Paginator();
        SortPaginator sortPaginator = new SortPaginator();
        CustomDto customDto = new CustomDto();
        Search search = new Search();
        if (!StringUtils.isEmpty(dataQuery)) {
            LinkedHashMap<String, Object> params = Decoder.base64Unpack(dataQuery, new TypeToken<LinkedHashMap<String, Object>>() {
            }.getType());
            if (params.containsKey("page") && params.containsKey("rows") && params.containsKey("order") && params.containsKey("columnOrder")) {
                sortPaginator.setPage(Integer.parseInt(String.valueOf(params.get("page")).replaceAll("\\.\\d+$", "")));
                sortPaginator.setRows(Integer.parseInt(String.valueOf(params.get("rows")).replaceAll("\\.\\d+$", "")));
                sortPaginator.setOrder(String.valueOf(params.get("order")).replaceAll("\\.\\d+$", ""));
                sortPaginator.setColumnOrder(String.valueOf(params.get("columnOrder")).replaceAll("\\.\\d+$", ""));
                setBase(toObject, paginator, sortPaginator, customDto, search);
            } else {
                setBase(toObject, paginator, sortPaginator, customDto, search);
                throw new BadSortPaginator("Bad request: SortPaginator");
            }
            if (params.containsKey("search")) {
                @SuppressWarnings("unchecked")
                Map<String, String> map = (Map<String, String>) params.get("search");
                if (!map.isEmpty()) {
                    search.setParameters(new HashMap<String, String>(map));
                }
            }
        } else {
            setBase(toObject, paginator, sortPaginator, customDto, search);
            throw new BadSortPaginator("Bad request: SortPaginator");
        }

        return toObject;
    }

    public static <T extends Base> T toBase(T toObject) {
        Paginator paginator = new Paginator();
        SortPaginator sortPaginator = new SortPaginator();
        CustomDto customDto = new CustomDto();
        Search search = new Search();
        setBase(toObject, paginator, sortPaginator, customDto, search);        

        return toObject;
    }

    public static <T extends Base> CustomDto toCustomDto(T toObject, int totalRows) {
        int rowsPerPage = 0;
        int currentPage = 0;
        int totalPages = 0;
        if(!Objects.isNull(toObject.getSortPaginator())) {
            SortPaginator sortPaginator = toObject.getSortPaginator();
            if (sortPaginator.getRows() > 0 && sortPaginator.getPage() > 0) {
			    rowsPerPage = sortPaginator.getRows();
			    currentPage = sortPaginator.getPage();
			    totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            }
        }
        toObject.getCustomDto().getPaginator().setCurrentPage(currentPage);
        toObject.getCustomDto().getPaginator().setPages(totalPages);
        toObject.getCustomDto().getPaginator().setTotalRows(totalRows);

        return toObject.getCustomDto();
    }
}
