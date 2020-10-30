package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ServerResponse {
    private String response;
    private JsonElement value;
    private String reason;

    public ServerResponse() {
        this.response = null;
        this.value = null;
        this.reason = null;
    }


    public void setResponse(String status) {
        this.response = status;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public void setReason(String errMsg) {
        this.reason = errMsg;
    }
}
