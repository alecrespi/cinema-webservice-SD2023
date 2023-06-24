import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class TopoGigiDB {
    HashMap<String, String> dbenv;
    private ReadWriteLock lock;

    public TopoGigiDB(){
        this.dbenv = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public String set(String id, @NotNull String value){
        lock.writeLock().lock();
        try{
            if(dbenv.put(id, value) == null)
                return "OK";
            else
                return "OVERWRITTEN";
        }finally{
            lock.writeLock().unlock();
        }
    }

    public String get(String id) {
        lock.readLock().lock();
        try{
            if(this.dbenv.containsKey(id))
                return dbenv.get(id);
            else
                return null;
        }finally{
            lock.readLock().unlock();
        }
    }

    public String remove(String id) {
        lock.writeLock().lock();
        try{
            if (this.dbenv.containsKey(id))
                return dbenv.remove(id);
            else
                return null;
        }finally{
            lock.writeLock().unlock();
        }
    }

    public List<String> getMatches(String pattern){
        lock.readLock().lock();
        try{
            List<String> results = new ArrayList<>();
            Pattern regexPattern = Pattern.compile(pattern);
            for (Map.Entry<String, String> entry : this.dbenv.entrySet()) {
                System.out.println(entry.getKey());
                if (regexPattern.matcher(entry.getKey()).matches())
                    results.add(entry.getValue());
            }
            return results;
        }finally {
            lock.readLock().unlock();
        }
    }

}
