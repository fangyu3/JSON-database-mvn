package utility;

import com.google.gson.JsonObject;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class FileIOUtil {

    private String fileName;

    public FileIOUtil(String fullPathFileName) {
        fileName = fullPathFileName;
    }

    public void serialize(HashMap<String, String> obj) {
        try (
            FileOutputStream fos = new FileOutputStream(fileName,false);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(obj);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize() {
        File file = new File(fileName);
        try {
            if (file.exists() && file.length() != 0) {
                FileInputStream fis = new FileInputStream(fileName);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();
                ois.close();
                return obj;
            }
            // Create new file if does not exist
            else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<String,String>();
    }
}
