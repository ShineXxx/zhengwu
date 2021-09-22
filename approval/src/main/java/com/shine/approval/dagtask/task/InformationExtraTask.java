package com.shine.approval.dagtask.task;

import com.shine.approval.dagtask.AbstractTask;
import com.shine.approval.dagtask.DataContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class InformationExtraTask extends AbstractTask {

    private final List<String> dependencies;

    private final String name;

    public InformationExtraTask(String name, String taskId, List<String> dependencies) {
        super(taskId);
        this.name = "info_extra";
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }


    @Override
    public void doAction(DataContext dataContext) {
        long start = System.currentTimeMillis();
        // do something amazing
        dataContext.getData().put(getId(), getName());
        long end = System.currentTimeMillis();
        log.info("take time: {} ms", (end - start));
    }

    @Override
    public List<String> getDependencies() {
        return dependencies;
    }

}
