package it.unimib.finalproject.server.DB;

import java.util.ArrayList;

public class ScriptResolution extends ArrayList<QueryResolution>{
    private boolean containsError;

    public ScriptResolution(){
        super();
        this.containsError = false;
    }

    public ScriptResolution(QueryResolution ql){
        this();
        super.add(ql);
        this.containsError = ql.isError();
    }

    @Override
    public boolean add(QueryResolution query){
        if(this.containsError())    return false;
        else {
            this.containsError = query.isError();
            return super.add(query);
        }
    }

    public String error(){
        if(this.containsError())
            return super.get(super.size() - 1).message();
        else return null;
    }

    public QueryResolution last(){
        return super.get(super.size() - 1);
    }

    public boolean containsError() {
        return containsError;
    }
}
