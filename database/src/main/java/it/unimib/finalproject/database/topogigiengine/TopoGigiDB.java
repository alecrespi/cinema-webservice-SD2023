package it.unimib.finalproject.database.topogigiengine;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Singleton
public class TopoGigiDB {
    private final ConcurrentMap<String, String> dbenv;
    private final ConcurrentMap<String, ReentrantReadWriteLock> locks;
    private static TopoGigiDB gigi = null;

    private TopoGigiDB() {
        this.dbenv = new ConcurrentHashMap<>();
        this.locks = new ConcurrentHashMap<>();
    }

    public static TopoGigiDB getInstance(){
        if(gigi == null)
            gigi = new TopoGigiDB();
        return gigi;
    }

    // true if overwrote a value, false otherwise
    public boolean set(@NotNull String id, @NotNull String value) {
        lockWrite(id);
        try {
            return this.dbenv.put(id, value) != null;
        } finally {
            unlockWrite(id);
        }
    }

    public String get(String id) {
        lockRead(id);
        try {
            return this.dbenv.get(id);
        } finally {
            unlockRead(id);
        }
    }

    public String remove(String id) {
        lockWrite(id);
        try {
            return this.dbenv.remove(id);
        } finally {
            unlockWrite(id);
        }
    }

    public boolean bind(String ...keys) {
        synchronized (this.locks){
            for (String key : keys)
                if(getLock(key).isWriteLocked())
                    return false;
            for (String key : keys)
                lockWrite(key);
            return true;
        }
    }

    public boolean release(String ...keys) {
        boolean failure = false;
        for (String key : keys){
            try{
                unlockWrite(key);
            }catch(IllegalMonitorStateException e){
                failure = true;
            }
        }
        return failure;
    }

    private void lockRead(String key) {
        getLock(key).readLock().lock();
    }

    private void unlockRead(String key) {
        getLock(key).readLock().unlock();
    }

    private void lockWrite(String key) {
        getLock(key).writeLock().lock();
    }

    private void unlockWrite(String key) {
        getLock(key).writeLock().unlock();
    }

    private ReentrantReadWriteLock getLock(String key) {
        synchronized (this.locks){
            return this.locks.computeIfAbsent(key, k -> new ReentrantReadWriteLock());
        }
    }
}
