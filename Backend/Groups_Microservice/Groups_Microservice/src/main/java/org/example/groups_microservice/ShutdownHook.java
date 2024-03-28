package org.example.groups_microservice;

import org.springframework.stereotype.Component;
import org.example.groups_microservice.Service.GroupPubSub;
import javax.annotation.PreDestroy;

@Component
public class ShutdownHook {
    private final GroupPubSub groupPubSub;

    public ShutdownHook(GroupPubSub groupPubSub) {
        this.groupPubSub = groupPubSub;
    }
    @PreDestroy
    public void onShutdown() {
        try {
            groupPubSub.shutdownPublisher();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}