package service;

import model.*;
import repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private final NotificationService notificationService = new NotificationService();
    public Project createProject(String id, String name, Category category, String leadId) {
        Lead lead = ProjectRepository.leads.get(leadId);

        Project project = new Project(id, name, category, lead);

        ProjectRepository.projects.put(id, project);

        return project;
    }

    public List<Project> getOpenProjects () {
        List<Project> openProjects = new ArrayList<>();

        for(Project project : ProjectRepository.projects.values()) {
            checkAndCancel(project);
            if(project.getProjectStatus() == ProjectStatus.OPEN) {
                openProjects.add(project);
            }
        }
        return openProjects;
    }

    public void startProject(String projectId) {
        // developer can start working on the project
        Project project = ProjectRepository.projects.get(projectId);
        if(project == null) {
            throw new RuntimeException("Project does not exist");
        }
        synchronized (project) {
            checkAndCancel(project);
            if (project.getProjectStatus() != ProjectStatus.ASSIGNED) {
                throw new RuntimeException("INVALID STATUS");
            }

            project.setProjectStatus(ProjectStatus.IN_PROGRESS);
            notificationService.notify(project.getLead(),
                    project.getAssignedDeveloper(), "Project moved to IN_PROGRESS");
        }
    }

    public void completeProject(String projectId) {
        // developer can mark it as completed.
        Project project = ProjectRepository.projects.get(projectId);
        if(project == null) {
            throw new RuntimeException("Project does not exist");
        }
        synchronized (project) {
            checkAndCancel(project);
            if (project.getProjectStatus() != ProjectStatus.IN_PROGRESS) {
                throw new RuntimeException("INVALID STATUS");
            }

            project.setProjectStatus(ProjectStatus.COMPLETED);
            notificationService.notify(project.getLead(),
                    project.getAssignedDeveloper(), "Project moved to COMPLETED");

            Developer dev = project.getAssignedDeveloper();
            dev.release();
        }
    }

    public void cancelProject(String projectId) {
        Project project = ProjectRepository.projects.get(projectId);
        if(project == null) {
            throw new RuntimeException("Project does not exist");
        }
        synchronized (project) {
            checkAndCancel(project);
            if(project.getProjectStatus() == ProjectStatus.IN_PROGRESS) {
                // Once a developer starts a project, the Lead cannot cancel it.
                throw new RuntimeException("CAN'T CANCEL");
            }

            project.setProjectStatus(ProjectStatus.CANCELLED);
            notificationService.notify(project.getLead(),
                    project.getAssignedDeveloper(), "Project moved to CANCELLED");
            project.getRequests().clear();
        }

    }

    public void checkAndCancel(Project project) {
        if(project != null && project.getProjectStatus() == ProjectStatus.OPEN &&
            project.getRequests().isEmpty() &&
                project.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            project.setProjectStatus(ProjectStatus.CANCELLED);
            notificationService.notify(project.getLead(),
                    project.getAssignedDeveloper(), "Project moved to CANCELLED");
        }
    }
}
