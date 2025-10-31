package mx.gapsi.commons.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

import mx.gapsi.commons.model.Base;
import mx.gapsi.commons.model.SortPaginator;

public class DbQueries {

    public static <T extends Base> String buildQuery(T toObject, String baseQuery) {
         StringBuilder SQLQuery = new StringBuilder(baseQuery + " ");
         HashMap<String, String> search = Objects.isNull(toObject.getSearch()) ? null : toObject.getSearch().getParameters();

         if (!Objects.isNull(search) && !search.isEmpty()){
            if (search.size() > 0) {
                search = StringUtils.escapeSpecialChars(search);
                if (!baseQuery.toUpperCase().contains("WHERE")) {
                    SQLQuery.append(" WHERE ");
                }
                if (search.containsKey("searchType")) {
                    String type = (search.get("searchType").equals("basic")) ? "OR" : "AND";
                    search.remove("searchType");
                    int cnt = 1;
                    for (String column : search.keySet()) {
                        String searchValue = Arrays.stream(search.get(column).split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.joining("|"));
                        search.put(column, searchValue);
                        SQLQuery.append(StringUtils.camelCaseToSnakeCase(column)).append("::text ~* ? ");
                        if (cnt < search.size()) {
                            SQLQuery.append(" ").append(type).append(" ");
                        }
                        cnt++;
                    }
                } else {
                    return SQLQuery.toString();
                }
            }
         } else {
            return SQLQuery.toString();
         }

         return SQLQuery.toString();
    }

    public static <T extends Base> String buildOrderBy(T toObject) {
        StringBuilder SQLPagination = new StringBuilder(" ");
        if(!Objects.isNull(toObject.getSortPaginator())) {
            SortPaginator sortPaginator = toObject.getSortPaginator();
            if (sortPaginator.getColumnOrder() != null && !sortPaginator.getColumnOrder().isEmpty()) {
                SQLPagination.append("ORDER BY " + sortPaginator.getColumnOrder());
            }
            if (sortPaginator.getOrder() != null && !sortPaginator.getOrder().isEmpty()) {
                SQLPagination.append(" " + sortPaginator.getOrder().toUpperCase());
            }
        }

        return SQLPagination.toString();
    }

    public static <T extends Base> String buildPagination(T toObject) {
        StringBuilder SQLPagination = new StringBuilder(" ");
        if(!Objects.isNull(toObject.getSortPaginator())) {
            SortPaginator sortPaginator = toObject.getSortPaginator();
            if (sortPaginator.getRows() > 0 && sortPaginator.getPage() > 0){
                SQLPagination.append("LIMIT " + sortPaginator.getRows());
                SQLPagination.append(" OFFSET " + ((sortPaginator.getPage() - 1) * sortPaginator.getRows()));
            }
        }

        return SQLPagination.toString();
    }

    public static PreparedStatement buildStatement(HashMap<String, String> params, PreparedStatement ps) throws SQLException {
        if (!Objects.isNull(params) && !params.isEmpty()) {
            params.remove("searchType");
            int i = 1;
            for (String column : Objects.requireNonNull(params).keySet()) {
                ps.setObject(i, params.get(column));
                i++;
            }
        }

        return ps;
    }

    public static int getTotalRows(String baseQuery, HashMap<String, String> params, JdbcTemplate jdbcTemplate) {
        if (!Objects.isNull(params) && !params.isEmpty()) {
            params.remove("searchType");
            int index = baseQuery.toUpperCase().indexOf("FROM");
            String query = "SELECT COUNT(*) " + baseQuery.substring(index);
            Collection<String> valuesCollection = params.values();
            Object[] values = valuesCollection.toArray(new Object[0]);

            return jdbcTemplate.queryForObject(query, Integer.class, values);
        } else {
            return 0;
        }
    }

    public static String buildQueryIn(String baseQuery, List<String> items, String fieldId) {
       StringBuilder query = new StringBuilder(" ");
        if (!items.isEmpty()){
            query.append(baseQuery + " WHERE " + fieldId + " IN (");
            items.forEach(item -> query.append(item + ", "));
            query.setLength(query.length() - 2);
            query.append(")");
        }

        return query.toString();
    }
    
}
