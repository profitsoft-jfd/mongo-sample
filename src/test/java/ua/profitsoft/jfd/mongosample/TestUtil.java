package ua.profitsoft.jfd.mongosample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

  private TestUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static <T>T parseJson(String json, Class<T> c) {
    try {
      return OBJECT_MAPPER.readValue(json, c);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error parsing json", e);
    }
  }

  public static <T>T parseJson(String json, TypeReference<T> valueTypeRef) {
    try {
      return OBJECT_MAPPER.readValue(json, valueTypeRef);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error parsing json", e);
    }
  }

}
