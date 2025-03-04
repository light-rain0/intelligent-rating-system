package org.lr0.server.core;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author Qnxy
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return it -> {

            // serializer
            it.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(ProjectConst.DEFAULT_DATE_TIME_FORMATTER));

            // deserializer
            it.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(ProjectConst.DEFAULT_DATE_TIME_FORMATTER));

        };
    }
}
