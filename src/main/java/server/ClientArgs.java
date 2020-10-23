package server;

import com.beust.jcommander.Parameter;

public class ClientArgs {
    @Parameter(
            names = "-t",
            description = "The task requested by the client",
            required = true
    )
    private String task;

    @Parameter(
            names = "-i",
            description = "The location where the data is stored in the DB"
    )
    private int index;

    @Parameter(
            names = "-m",
            description = "new entry to be set"
    )
    private String entry;

    public String getTask() {
        return task;
    }

    public int getIndex() {
        return index;
    }

    public String getEntry() {
        return entry;
    }
}
