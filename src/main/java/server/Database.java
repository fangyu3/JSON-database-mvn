package server;

import com.google.gson.*;
import server.exception.EmptyCellException;
import utility.FileIOUtil;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Database {
    private HashMap<String, String> db;
    private final String DBfilePath = "./src/main/java/server/data/db.json";
    private final FileIOUtil util = new FileIOUtil(DBfilePath);
    private ReadWriteLock lock;

    public Database() {
        lock = new ReentrantReadWriteLock();
        db = (HashMap<String,String>)util.deserialize();
    }

    public void updateRecord(JsonElement key, JsonElement value) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();

        try {
            if (!key.isJsonArray()) {
                db.put(key.getAsString(),value.toString());
            }
            else {
                JsonArray keys = key.getAsJsonArray();

                if (keys.size() != 1) {
                    JsonObject obj = db.get(keys.get(0).getAsString()) == null
                            ? obj = new JsonObject()
                            : new JsonParser().parse(db.get(keys.get(0).getAsString())).getAsJsonObject();

                    JsonObject iterator = obj;

                    // Since the 1st element in keys array is dictionary key, we start from 2nd element
                    for (int i = 1; i < keys.size(); i++) {
                        String curKey = keys.get(i).getAsString();

                        // Add the value when reached the desired nested location in JSON
                        if (i == keys.size() - 1) {
                            iterator.add(curKey, value);
                        }
                        else {
                            if (!iterator.has(curKey))
                                iterator.addProperty(curKey, keys.get(i + 1).getAsString());

                            iterator = iterator.get(curKey).getAsJsonObject();
                        }
                    }

                    db.put(keys.get(0).getAsString(),obj.toString());
                }
                else
                    db.put(keys.get(0).getAsString(),value.toString());
            }
            util.serialize(db);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void deleteRecord(JsonElement key) throws EmptyCellException {
        Lock writeLock = lock.writeLock();
        writeLock.lock();

        try {
            if (!key.isJsonArray()) {
                if (db.get(key.getAsString()) == null)
                    throw new EmptyCellException("Error: trying to delete empty cell!");

                db.remove(key.getAsString());
            }
            else {
                JsonArray keys = key.getAsJsonArray();

                if (db.get(keys.get(0).getAsString()) == null)
                    throw new EmptyCellException("Error: trying to delete empty cell!");

                if (keys.size() != 1) {

                    JsonObject obj = new JsonParser().parse(db.get(keys.get(0).getAsString())).getAsJsonObject();
                    JsonObject iterator = obj;

                    // Traverse JSON until desired location to remove
                    for (int i = 1; i < keys.size(); i++) {
                        String curkey = keys.get(i).getAsString();

                        if (!iterator.has(curkey))
                            throw new EmptyCellException("Error: trying to delete empty cell!");

                        if (i == keys.size() - 1) {
                            iterator.remove(curkey);
                            break;
                        }

                        iterator = iterator.get(curkey).getAsJsonObject();
                    }

                    db.put(keys.get(0).getAsString(),obj.toString());
                }
                else
                    db.remove(keys.get(0).getAsString());
            }

            util.serialize(db);
        }
        finally {
            writeLock.unlock();
        }
    }

    public JsonElement getRecord(JsonElement key) throws EmptyCellException {
        Lock readLock = lock.readLock();
        readLock.lock();

        try {
            // Loop through array

            if (!key.isJsonArray()) {
                if (db.get(key.getAsString()) == null)
                    throw new EmptyCellException("Error: trying to read empty cell!");

                return new JsonParser().parse(db.get(key.getAsString()));
            }
            else {
                JsonArray keys = key.getAsJsonArray();

                if (db.get(keys.get(0).getAsString()) == null)
                    throw new EmptyCellException("Error: trying to read empty cell!");

                if (keys.size() != 1) {

                    JsonObject iterator = new JsonParser().parse(db.get(keys.get(0).getAsString())).getAsJsonObject();

                    for (int i = 1; i < keys.size(); i++) {
                        String curkey = keys.get(i).getAsString();
                        if (!iterator.has(curkey))
                            throw new EmptyCellException("Error: trying to read empty cell!");

                        if (i == keys.size() - 1) {
                            return iterator.get(curkey);
                        }

                        iterator = iterator.get(curkey).getAsJsonObject();
                    }
                }
                else
                    return new JsonParser().parse(db.get(keys.get(0).getAsString()));
            }
            return null;
        }
        finally {
            readLock.unlock();
        }

    }
}
