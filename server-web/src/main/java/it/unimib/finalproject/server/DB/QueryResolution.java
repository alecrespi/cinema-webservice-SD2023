package it.unimib.finalproject.server.DB;

import java.security.InvalidParameterException;

public class QueryResolution {
    private final String query;
    private final String message;
    private final boolean isError;

    public QueryResolution(String query, String message, boolean isError) {
        this.query = query;
        this.message = message;
        this.isError = isError;
    }

    public QueryResolution(String query, String message){
        this.query = query;
        if(message == null || message.isEmpty())
            throw new InvalidParameterException();
        if(message.charAt(0) == '#'){
            this.message = message.substring(1);
            this.isError = false;
        }else{
            this.message = message;
            this.isError = true;
        }
    }

    public String query() {
        return query;
    }

    public String message() {
        return message;
    }

    public boolean isError() {
        return isError;
    }

    public int error(){
        return this.isError()? Integer.parseInt(this.message()) : -1;
    }
}
