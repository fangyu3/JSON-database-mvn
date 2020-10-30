package client;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ClientArgs {
    @Parameter(
            names = "-t",
            description = "The type of task requested by the client"
    )
    private String type;

    @Parameter(
            names = "-k",
            description = "The location where the data is stored in the DB"
    )
    private String key;

    @Parameter(
            names = "-v",
            description = "new value to be set"
    )
    private String value;

    @Parameter(
            names = "-in",
            description = "input file"
    )
    private String fileName;



    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFileName() { return fileName; }
}
