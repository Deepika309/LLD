package model;

public class ProjectRequest {
    private final String requestId;
    private final Developer developer;
    private RequestStatus requestStatus;

    public ProjectRequest(String requestId, Developer developer) {
        this.requestId = requestId;
        this.developer = developer;
        this.requestStatus = RequestStatus.PENDING;
    }

    public String getRequestId() {
        return requestId;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
