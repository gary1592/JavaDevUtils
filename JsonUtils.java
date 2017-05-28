import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonUtils{

	public static final String KEY_ID = "pid";
	public static final String KEY_NAME = "pname";
	public static final String KEY_CHILDREN = "children";
	public static final String KEY_IMPORTANCE = "importance";
	public static final String KEY_DESC = "desc";
	public static final String KEY_TAGS = "tags";

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Map<String, Object> updateEachJsonNodeWithMap(JsonNode jsonNode) {
		Map<String, Object> data = new HashMap<>();
		String pid = UUID.randomUUID().toString();
		data.put(KEY_ID, pid);
		Double importance = jsonNode.get(KEY_IMPORTANCE).asDouble();
		data.put(KEY_IMPORTANCE, importance);
		JsonNode childrenNode = jsonNode.get(KEY_CHILDREN);
		List<Map<String, Object>> children = new LinkedList<>();
		if (childrenNode != null && childrenNode.isArray()) {
			childrenNode.forEach(childNode -> {
				Map<String, Object> childData = updateEachJsonNodeWithMap(childNode);
				children.add(childData);
			});
		}
		data.put(KEY_CHILDREN, children);
		return data;
	}

	public static JsonNode stringToJsonNode(String data) throws JsonProcessingException, IOException {
		return mapper.readTree(data);
	}

	public static String jsonNodeToString(JsonNode jsonNode) throws IOException {
		return mapper.writeValueAsString(jsonNode);
	}
}
	