package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final String REF_KEYWORD = "$ref";
    private static final String SELF_REFERENCE_KEYWORD = "#";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String readJsonSchema(String path) {
        val map = readSchemaObject(path);
        return OBJECT_MAPPER.writeValueAsString(map);
    }

    private static String concatPaths(String basePath, String relativePath) {
        val path = Path.of(basePath, relativePath).normalize().toString();

        if (path.contains("\\")) {
            return path.replaceAll("\\\\", "/");
        }

        return path;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static Map<String, Object> readSchemaObject(String path) {
        final Map<String, Object> map = OBJECT_MAPPER.readValue(JsonUtils.class.getResourceAsStream(path), Map.class);
        val basePath = path.replace(Path.of(path).getFileName().toString(), "");
        val result = new HashMap<String, Object>();
        normalizeValues(basePath, map, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static void normalizeValues(String basePath, Map<String, Object> map, Map<String, Object> result) {

        for (val entry : map.entrySet()) {
            val key = entry.getKey();
            val value = entry.getValue();

            if (REF_KEYWORD.equals(key) && !SELF_REFERENCE_KEYWORD.equals(value)) {
                val relativePath = (String) value;
                val path = concatPaths(basePath, relativePath);
                val schema = readSchemaObject(path);
                result.putAll(schema);
            } else {
                result.put(key, value);
            }

            if (value instanceof List) {
                val listValue = ((List<?>) value).get(0);

                if (listValue instanceof Map) {
                    val r = new HashMap<String, Object>();
                    result.put(key, r);
                    normalizeValues(basePath, (Map<String, Object>) listValue, r);
                }
            }
        }
    }
}
