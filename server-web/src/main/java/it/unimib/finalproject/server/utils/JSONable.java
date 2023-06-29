package it.unimib.finalproject.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JSONable {
    public String toJSON() throws JsonProcessingException;
}
