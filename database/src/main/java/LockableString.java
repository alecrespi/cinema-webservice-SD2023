import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockableString{
    // such as Strings, they're immutable. So there is no this.set()
    private final String str;
    private final ReadWriteLock lock;

    public LockableString(String str) {
        this.str = str;
        this.lock = new ReentrantReadWriteLock();
    }

    public String get() {
        return str;
    }

    public java.util.concurrent.locks.Lock readLock(){
        return this.lock.readLock();
    }

    public java.util.concurrent.locks.Lock writeLock(){
        return this.lock.writeLock();
    }
}
