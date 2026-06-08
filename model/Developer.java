package model;

public class Developer {
    private final String id;
    private final String name;
    private DeveloperStatus developerStatus;

    public Developer(String id, String name) {
        this.id = id;
        this.name = name;
        this.developerStatus = DeveloperStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public synchronized boolean isAvailable() {
        return developerStatus == DeveloperStatus.AVAILABLE;
    }

    public synchronized void assign() {
        developerStatus = DeveloperStatus.ASSIGNED;
    }

    public synchronized void release() {
        developerStatus = DeveloperStatus.AVAILABLE;
    }

    public DeveloperStatus getDeveloperStatus() {
        return developerStatus;
    }
}
