package server;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;

public class ClientInput {
    @Parameter(
            names = "-t",
            description = "The type of task requested by the client"
    )
    private String type;

    @Parameter(
            names = "-k",
            description = "The location where the data is stored in the DB"
    )
    private JsonElement key;

    @Parameter(
            names = "-v",
            description = "new value to be set"
    )
    private JsonElement value;

    @Parameter(
            names = "-in",
            description = "input file"
    )
    private String fileName;



    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    public String getFileName() { return fileName; }
}
