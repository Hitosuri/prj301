package helpers;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {

    private class RequestParameter {

        public String name;
        public boolean require;
        public String rawData = "";
        public Map<Predicate<Object>, String> checkFunc = new HashMap<>();
        public boolean isValid = false;
        public Class<?> type;
        public Object result = null;

        public RequestParameter(String name, boolean require, Class<?> type) {
            this.name = name;
            this.require = require;
            this.type = type;
        }

        @Override
        public String toString() {
            return "RequestParameter{" + "name=" + name + ", rawData=" + rawData + ", result=" + result + '}';
        }

    }

    private HttpServletRequest request;
    private final Map<String, RequestParameter> parameters = new HashMap<>();
    private final static Map<Class<?>, Function<String, Object>> parsers = new HashMap<>();
    private final static Map<Class<?>, Object> defaultValue = new HashMap<>();
    private final Pattern checker = Pattern.compile("(?<!\\\\%)(?<=%)[^%]+(?=%)");

    static {
        parsers.put(Integer.class, Integer::parseInt);
        parsers.put(Double.class, Double::parseDouble);
        parsers.put(Boolean.class, Boolean::parseBoolean);
        parsers.put(String.class, String::valueOf);
        parsers.put(Date.class, Date::valueOf);
        parsers.put(Timestamp.class, Timestamp::valueOf);

        defaultValue.put(Integer.class, 0);
        defaultValue.put(Double.class, 0.0);
        defaultValue.put(Boolean.class, false);
        defaultValue.put(String.class, "");
        defaultValue.put(Date.class, new Date(System.currentTimeMillis()));
        defaultValue.put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
    }

    public FormValidator(HttpServletRequest request) {
        this.request = request;
    }

    public Object get(String paramName) {
        return parameters.get(paramName).result;
    }

    public Object getRaw(String paramName) {
        return parameters.get(paramName).rawData;
    }

    public Set<String> getParamNameSet() {
        return parameters.keySet();
    }

    public boolean isValid() {
        boolean valid = true;

        for (Map.Entry<String, RequestParameter> entry : parameters.entrySet()) {
            String paramName = entry.getKey();
            RequestParameter paramObject = entry.getValue();
            paramObject.rawData = request.getParameter(paramName) == null ? "" : request.getParameter(paramName).trim();

            if (paramObject.require && paramObject.rawData.isEmpty() && paramObject.type != Boolean.class) {
                System.out.println(paramName + " is required");
                request.setAttribute(paramName + "Err", paramName + " is required");
                valid = false;
            }
        }

        if (valid) {
            for (Map.Entry<String, RequestParameter> entry : parameters.entrySet()) {
                String paramName = entry.getKey();
                RequestParameter paramObject = entry.getValue();

                if (paramObject.type == Boolean.class) {
                    paramObject.result = "on".equalsIgnoreCase(paramObject.rawData) || "true".equalsIgnoreCase(paramObject.rawData) || "yes".equalsIgnoreCase(paramObject.rawData);
                    continue;
                } else if (paramObject.require) {
                    try {
                        paramObject.result = parsers.get(paramObject.type).apply(paramObject.rawData);
                    } catch (Exception e) {
                        System.out.println(paramName + " does not meet " + paramObject.type.getSimpleName() + " format");
                        request.setAttribute(paramName + "Err", paramName + " does not meet " + paramObject.type.getSimpleName() + " format");
                        valid = false;
                    }
                } else {
                    if (paramObject.rawData.isEmpty()) {
                        paramObject.result = defaultValue.get(paramObject.type);
                        continue;
                    }
                    try {
                        paramObject.result = parsers.get(paramObject.type).apply(paramObject.rawData);
                    } catch (Exception e) {
                        System.out.println(paramName + " does not meet " + paramObject.type.getSimpleName() + " format");
                        request.setAttribute(paramName + "Err", paramName + " does not meet " + paramObject.type.getSimpleName() + " format");
                        valid = false;
                    }
                }
            }
        }

        if (valid) {
            for (Map.Entry<String, RequestParameter> entry : parameters.entrySet()) {
                String paramName = entry.getKey();
                RequestParameter paramObject = entry.getValue();

                if (!paramObject.require && paramObject.rawData.isEmpty()) {
                    continue;
                }

                for (Map.Entry<Predicate<Object>, String> funcEntry : paramObject.checkFunc.entrySet()) {
                    Predicate<Object> func = funcEntry.getKey();
                    String errMsg = replaceString(funcEntry.getValue());
                    if (!func.test(paramObject.result)) {
                        System.out.println(errMsg);
                        request.setAttribute(paramName + "Err", errMsg);
                        valid = false;
                        break;
                    }
                }
            }
        }
        
        for (Map.Entry<String, RequestParameter> entry : parameters.entrySet()) {
//            System.out.println(entry.getValue());
        }

        return valid;
    }

    public void addCheckFunction(String paramName, Predicate<Object> predicate, String errMsg) {
        RequestParameter rp = parameters.get(paramName);
        rp.checkFunc.put(predicate, errMsg);
    }

    public void setCheckParam(String paramName, boolean require, Class<?> clazz) {
        RequestParameter rp = new RequestParameter(paramName, require, clazz);
        parameters.put(paramName, rp);
    }

    public void setCheckParam(String paramName, boolean require, Class<?> clazz, Predicate<Object> predicate, String errMsg) {
        RequestParameter rp = new RequestParameter(paramName, require, clazz);
        rp.checkFunc.put(predicate, errMsg);
        parameters.put(paramName, rp);
    }
    
    private String replaceString(String str) {
        Matcher matcher = checker.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        int matchIndex = 0;
        while (matcher.find()) {
            if (matchIndex++ % 2 == 0) {
                builder.replace(matcher.start() - 1, matcher.end() + 1, parameters.get(matcher.group()).result.toString());
            }
        }
        return builder.toString();
    }
}
