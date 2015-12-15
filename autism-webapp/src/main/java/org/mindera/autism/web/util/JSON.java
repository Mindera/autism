package org.mindera.autism.web.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class JSON {

    private static final Logger LOG = Logger.getLogger(JSON.class.getName());

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String encode(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOG.log(Level.SEVERE, "Error parsing json: " + o.toString(), e);
            return "{}";
        }
    }

    public static <T> T decode(String o, TypeReference<T> typeReference) {
        try {
            return new ObjectMapper().readValue(o, typeReference);
        } catch (IOException e) {
            return null;
        }
    }
}
