package server;

import server.exception.EmptyCellException;
import utility.FileIOUtil;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Database {
    private HashMap<String,String> db;
    private final String DBfilePath = "./src/main/java/server/data/db.json";
    private final FileIOUtil util = new FileIOUtil(DBfilePath);
    private ReadWriteLock lock;

    public Database() {
        lock = new ReentrantReadWriteLock();
        db = (HashMap<String,String>)util.deserialize();
    }

    public void updateRecord(String key, String value) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            db.put(key, value);
            util.serialize(db);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void deleteRecord(String key) throws EmptyCellException {
        Lock writeLock = lock.writeLock();
        writeLock.lock();

        try {
            if (db.get(key) == null)
                throw new EmptyCellException("Error: trying to delete empty cell!");

            db.remove(key);
            util.serialize(db);
        }
        finally {
            writeLock.unlock();
        }
    }

    public String getRecord(String key) throws EmptyCellException {
        Lock readLock = lock.readLock();
        readLock.lock();

        try {
            if (db.get(key) == null)
                throw new EmptyCellException("Error: trying to read empty cell!");

            return db.get(key);
        }
        finally {
            readLock.unlock();
        }
    }
}
