package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private final String id;
    private final Category category;
    private final String name;
    private final Lead lead;
    private final LocalDateTime createdAt;
    private ProjectStatus projectStatus;
    private Developer assignedDeveloper;
    private final List<ProjectRequest> requests;

    public Project(String id, String name, Category category, Lead lead) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.lead = lead;

        this.projectStatus = ProjectStatus.OPEN;
        this.requests = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public synchronized ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public synchronized Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public synchronized List<ProjectRequest> getRequests() {
        return requests;
    }

    public synchronized void addRequest(ProjectRequest request) {
        requests.add(request);
    }

    public synchronized void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public synchronized void setAssignedDeveloper(Developer developer) {
        assignedDeveloper = developer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Lead getLead() {
        return lead;
    }
}
