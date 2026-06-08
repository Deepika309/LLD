package service;

import model.Developer;
import model.Lead;

public class NotificationService {
    public void notify(Lead lead, Developer dev, String msg) {
        System.out.println("Lead: " + lead.getId() + ", Developer: " +
                dev.getId() + ", Notification: " + msg);
    }

}
