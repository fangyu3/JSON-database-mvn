package server;

public class ServerResponse {
    private String response;
    private String value;
    private String reason;

    public ServerResponse() {
        this.response = null;
        this.value = null;
        this.reason = null;
    }


    public void setResponse(String status) {
        this.response = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setReason(String errMsg) {
        this.reason = errMsg;
    }
}
