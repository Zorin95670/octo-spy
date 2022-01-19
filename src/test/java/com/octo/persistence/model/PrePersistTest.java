package com.octo.persistence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PrePersistTest {

    @Test
    void testPrePersist() {
        Deployment deployment = new Deployment();
        DeploymentProgress deploymentProgress = new DeploymentProgress();
        Project project = new Project();
        Group group = new Group();
        ProjectGroup projectGroup = new ProjectGroup();
        User user = new User();
        UserToken userToken = new UserToken();

        assertNull(deployment.getInsertDate());
        assertNull(deploymentProgress.getInsertDate());
        assertNull(project.getInsertDate());
        assertNull(group.getInsertDate());
        assertNull(projectGroup.getInsertDate());
        assertNull(user.getInsertDate());
        assertNull(userToken.getInsertDate());

        deployment.prePersist();
        deploymentProgress.prePersist();
        project.prePersist();
        group.prePersist();
        projectGroup.prePersist();
        user.prePersist();
        userToken.prePersist();

        assertNotNull(deployment.getInsertDate());
        assertNotNull(deploymentProgress.getInsertDate());
        assertNotNull(project.getInsertDate());
        assertNotNull(group.getInsertDate());
        assertNotNull(projectGroup.getInsertDate());
        assertNotNull(user.getInsertDate());
        assertNotNull(userToken.getInsertDate());
    }
}
