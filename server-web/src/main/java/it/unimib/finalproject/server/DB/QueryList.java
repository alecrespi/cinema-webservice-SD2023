package it.unimib.finalproject.server.DB;

import java.util.ArrayList;
import java.util.List;

public class QueryList {
    private List<String> querylist;

    public QueryList(){
        this.querylist = new ArrayList<>();
    }

    public QueryList(String firstQuery){
        this();
        this.querylist.add(firstQuery);
    }

    public boolean add(String query){
        return this.querylist.add(query);
    }

    @Override
    public String toString() {
        // pay attention on trailing \n
        return String.join("\n",this.querylist);
    }
}
