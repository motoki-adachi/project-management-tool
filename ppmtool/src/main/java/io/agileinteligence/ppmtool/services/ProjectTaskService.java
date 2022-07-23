package io.agileinteligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileinteligence.ppmtool.domain.Backlog;
import io.agileinteligence.ppmtool.domain.Project;
import io.agileinteligence.ppmtool.domain.ProjectTask;
import io.agileinteligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileinteligence.ppmtool.repositories.BacklogRepository;
import io.agileinteligence.ppmtool.repositories.ProjectRepository;
import io.agileinteligence.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    /*
     * プロジェクトタスクの登録処理
     */
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

	try {
	    Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
	    projectTask.setBacklog(backlog);
	    Integer backlogSequence = backlog.getPTSequence();
	    backlogSequence++;
	    backlog.setPTSequence(backlogSequence);
	    projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
	    projectTask.setProjectIdentifier(projectIdentifier);
	    return projectTaskRepository.save(projectTask);
	} catch (Exception e) {
	    throw new ProjectNotFoundException("このプロジェクトは見つかりませんでした。");
	}

    }

    /*
     * BacklogIDからプロジェクトタスクを検索する
     */
    public Iterable<ProjectTask> findBacklogById(String id, String username) {

	Project project = projectService.findProjectByIdentifier(id, username);
	if (project == null) {
	    throw new ProjectNotFoundException("Project: " + id + " は存在しません。");
	}
	return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    /*
     * ProjectSequenceからタスクを単一検索する
     */
    public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectTaskId, String username) {

	Backlog backlog = projectService.findProjectByIdentifier(backlogId, username).getBacklog();

	if (backlog == null) {
	    throw new ProjectNotFoundException("Project: " + backlogId + " は存在しません。");
	}

	ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTaskId);
	if (projectTask == null) {
	    throw new ProjectNotFoundException("ProjectTask: " + projectTaskId + " は存在しません。");
	}

	if (!projectTask.getProjectIdentifier().equals(backlogId)) {
	    throw new ProjectNotFoundException(
		    "ProjectTask: " + projectTaskId + " はProject: " + backlogId + " のタスクとして存在しません。");
	}

	return projectTask;
    }

    /*
     * プロジェクトタスクの更新処理
     */
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId, String projectTaskId,
	    String username) {

	ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectTaskId, username);
	projectTask = updatedTask;
	return projectTaskRepository.save(projectTask);
    }

    /*
     * プロジェクトタスクの削除
     */
    public void deleteProjectTaskByProjectSequence(String backlogId, String projectTaskId, String username) {

	ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectTaskId, username);
	projectTaskRepository.delete(projectTask);
    }

}
