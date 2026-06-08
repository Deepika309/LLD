//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import model.Category;
import model.Project;
import service.ProjectService;
import service.RequestService;
import service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ProjectService projectService = new ProjectService();
        RequestService requestService = new RequestService();

        userService.registerLead("L1", "Lead1");
        userService.registerDeveloper("D1", "Dev1");
        userService.registerDeveloper("D2", "Dev2");

        Project project = projectService
                        .createProject("P1", "Project1", Category.BACKEND, "L1");
        Project project1 = projectService
                .createProject("P2", "Project2", Category.BACKEND, "L1");

        List<Project> projects = projectService.getOpenProjects();
        System.out.println(projects.size());

        requestService.requestProject("D1", "P1");
        requestService.requestProject("D1", "P2");
        requestService.requestProject("D2", "P1");

        String reqId = project.getRequests().get(0).getRequestId();

        requestService.approveRequest("P1", reqId);
        projectService.startProject("P1");
        projectService.completeProject("P1");


        System.out.println(project.getProjectStatus());
    }
}