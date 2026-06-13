package service;

import model.Developer;
import model.Lead;
import repository.ProjectRepository;

public class UserService {
    public void registerLead(String id, String name) {
        ProjectRepository.leads.put(id, new Lead(id, name));
    }

    public void registerDeveloper(String id, String name) {
        ProjectRepository.developers.put(id, new Developer(id, name));
    }
}
