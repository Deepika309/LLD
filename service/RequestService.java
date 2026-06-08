package service;

import model.*;
import repository.ProjectRepository;

import java.util.UUID;

public class RequestService {
    ProjectService projectService = new ProjectService();
    NotificationService notificationService = new NotificationService();

    public void requestProject(String devId, String projectId) {
        Developer dev = ProjectRepository.developers.get(devId);
        Project project = ProjectRepository.projects.get(projectId);
        if(project == null) {
            throw new RuntimeException("Project does not exist");
        }

        synchronized (dev) {
            projectService.checkAndCancel(project);
            if(!dev.isAvailable()) {
                throw new RuntimeException("Developer already have project");
            }

            ProjectRequest projectRequest = new ProjectRequest(UUID.randomUUID().toString(), dev);

            synchronized (project) {
                if(project.getProjectStatus() != ProjectStatus.OPEN) {
                    throw new RuntimeException("Project not open");
                }

                project.addRequest(projectRequest);
            }
        }


    }

    public void approveRequest(String projectId, String requestId) {
        Project project = ProjectRepository.projects.get(projectId);
        if(project == null) {
            throw new RuntimeException("Project does not exist");
        }

        synchronized (project) {
            projectService.checkAndCancel(project);
            if (project.getProjectStatus() != ProjectStatus.OPEN) {
                throw new RuntimeException("Project not open");
            }

            ProjectRequest approveReq = null;

            for (ProjectRequest request : project.getRequests()) {
                if (request.getRequestId().equals(requestId)) {
                    approveReq = request;
                    break;
                }
            }

            if (approveReq == null) {
                throw new RuntimeException("request not found");
            }

            Developer dev = approveReq.getDeveloper();

            synchronized (dev) {
                if (!dev.isAvailable()) {
                    throw new RuntimeException("dev busy");
                }

                dev.assign();
                approveReq.setRequestStatus(RequestStatus.APPROVED);

                project.setAssignedDeveloper(dev);

                project.setProjectStatus(ProjectStatus.ASSIGNED);
                notificationService.notify(project.getLead(),
                        project.getAssignedDeveloper(), "Project moved to ASSIGNED");

                for (ProjectRequest request : project.getRequests()) {
                    if (!request.getRequestId().equals(requestId)) {
                        request.setRequestStatus(RequestStatus.REJECTED);
                    }
                }
            }

        }

    }
}
