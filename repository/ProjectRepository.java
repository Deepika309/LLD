package repository;

import model.Developer;
import model.Lead;
import model.Project;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectRepository {
    public static final Map<String, Lead> leads = new ConcurrentHashMap<>();
    public static final Map<String, Developer> developers = new ConcurrentHashMap<>();
    public static final Map<String, Project> projects = new ConcurrentHashMap<>();
}
