import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class TopoGigiDB {
    protected final ConcurrentHashMap<String, String> dbenv;
    private final ConcurrentSkipListSet<String> syncedKeys;

    public TopoGigiDB(){
        this.dbenv = new ConcurrentHashMap<>();
        this.syncedKeys = new ConcurrentSkipListSet<>();
    }

    public String set(String id, @NotNull String value){
        if(this.dbenv.put(id, value) == null)
            return "OK";
        else
            return "OVERWRITTEN";
    }

    public String get(String id) {
        return this.dbenv.get(id);
    }

    public String remove(String id) {
        return this.dbenv.remove(id);
    }

    public boolean bind(String ...keys){
        synchronized (this.syncedKeys){
            // checking if there is intersection between keys and this.syncedKeys
            Collection<String> intersection = new ArrayList<>(Arrays.asList(keys));
            intersection.retainAll(this.syncedKeys);
            if(!intersection.isEmpty())
                return false;
            this.syncedKeys.addAll(Arrays.asList(keys));
            return true;
        }
    }


    // return type??
    public void release(String ...keys){
        synchronized (this.syncedKeys){
            this.syncedKeys.removeAll(Arrays.asList(keys));
        }
    }

//    public List<String> getMatches(String pattern){
//        lock.readLock().lock();
//        try{
//            List<String> results = new ArrayList<>();
//            Pattern regexPattern = Pattern.compile(pattern);
//            for (Map.Entry<String, String> entry : this.dbenv.entrySet()) {
//                System.out.println(entry.getKey());
//                if (regexPattern.matcher(entry.getKey()).matches())
//                    results.add(entry.getValue());
//            }
//            return results;
//        }finally {
//            lock.readLock().unlock();
//        }
//    }

}
