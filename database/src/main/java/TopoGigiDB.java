import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class TopoGigiDB {
    ConcurrentHashMap<String, String> dbenv;
    private ReadWriteLock lock;

    public TopoGigiDB(){
        this.dbenv = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
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
