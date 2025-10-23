package mx.gapsi.commons.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import mx.gapsi.commons.model.Base;

public class DbQueries {

    public static <T extends Base> String buidlQuery(T toObject, String baseQuery) {
         StringBuilder SQLQuery = new StringBuilder(baseQuery);
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
    
}
