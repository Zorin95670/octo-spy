package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.octo.model.project.ProjectRecord;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Project service.
 */
public interface ProjectService extends ServiceHelper {

    /**
     * Count projects.
     *
     * @param filters Filter options.
     * @param field   Field to count.
     * @param value   Default value for count.
     * @return Count object.
     */
    JsonNode count(Map<String, String> filters, String field, String value);

    /**
     * Load project by id.
     *
     * @param id Primary key.
     * @return Project.
     */
    ProjectView load(Long id);

    /**
     * Save project in database.
     *
     * @param newProject Project to save.
     * @return Project.
     */
    Project save(ProjectRecord newProject);

    /**
     * Save master project.
     *
     * @param newProject Project to save.
     * @return Created project.
     */
    Project saveMasterProject(ProjectRecord newProject);

    /**
     * Save subproject.
     *
     * @param newProject Subproject to create.
     * @return Created subproject.
     */
    Project saveSubProject(ProjectRecord newProject);

    /**
     * Delete a project.
     *
     * @param id Id of project to update.
     */
    void delete(Long id);

    /**
     * Get all projects.
     *
     * @param filters  Project filter.
     * @param pageable Object to manage page and sort.
     * @return Projects
     */
    Page<ProjectView> findAll(Map<String, String> filters, Pageable pageable);

    /**
     * Update project information.
     *
     * @param id         Project's id.
     * @param newProject Project's information.
     */
    void update(Long id, Project newProject) throws InvocationTargetException, IllegalAccessException;
}
