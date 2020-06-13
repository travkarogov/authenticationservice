package twitsec.authenticationservice.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class ListConverter<T> implements AttributeConverter<List<T>, String> {
    private ObjectMapper jacksonObjectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(final List<T> list) {
        return jacksonObjectMapper.writeValueAsString(list);
    }

    @Override
    @SneakyThrows
    public List<T> convertToEntityAttribute(final String json) {
        return jacksonObjectMapper.readValue(json, new TypeReference<List<T>>(){});
    }
}
