package client;

import com.beust.jcommander.Parameter;

public class ClientInput {
    @Parameter(
            names = "-t",
            description = "The type of task requested by the client",
            required = true
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

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
