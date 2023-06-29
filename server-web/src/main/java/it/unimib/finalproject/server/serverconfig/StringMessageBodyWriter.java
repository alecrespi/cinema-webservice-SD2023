package it.unimib.finalproject.server.serverconfig;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Genera un JSON valido con una sola stringa.
 *
 * È alternativo a quello predefinito di Jackson. Un JSON che contiene una sola
 * stringa con le virgolette è valido dall'RFC 7159. Jackson a quanto pare non
 * supporta questa cosa.
 *
 * Riferimento e crediti:
 *
 *  - https://stackoverflow.com/a/57468899
 *  - https://memorynotfound.com/jax-rs-messagebodywriter/
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class StringMessageBodyWriter implements MessageBodyWriter<String> {

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return type == String.class && MediaType.APPLICATION_JSON_TYPE.equals(mediaType);
    }

    // Il seguente script è stato modificato al fine di eliminare la serializzazione implicita di
    // Jersey che agiva sulle stringhe quando venivano inviate delle stringhe già JSON, allora venivano
    // aggiunti dei caratteri di escape. Il risultato è che se
    // invio : (String) {"name":"foo","lastname":"bar"}
    // ricevo: "{\"name\":\"foo\",\"lastname\":\"bar\"}"
    @Override
    public void writeTo(final String t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream out) throws IOException, WebApplicationException {
        out.write(t.getBytes());
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(out, t);
    }
}
