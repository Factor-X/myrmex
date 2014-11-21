package eu.factorx.myrmex.kernel.vaadin;

import eu.factorx.myrmex.kernel.model.TaskService;

import eu.factorx.utils.vaadinbridge.ApplicationFactory;

import com.vaadin.Application;
import com.vaadin.Application.SystemMessages;

public class ExampleAppFactory implements ApplicationFactory {
    
    private final TaskService taskService;
    private final String title;

    public ExampleAppFactory(TaskService taskService, String title) {
        this.taskService = taskService;
        this.title = title;
    }
    
    public String getApplicationCSSClassName() {
        return "ExampleApplication";
    }

    public SystemMessages getSystemMessages() {
        return null;
    }

    public Application newInstance() {
        return new ExampleApplication(taskService, title);
    }
}