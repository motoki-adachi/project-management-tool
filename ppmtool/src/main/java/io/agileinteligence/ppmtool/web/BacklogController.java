package io.agileinteligence.ppmtool.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileinteligence.ppmtool.domain.ProjectTask;
import io.agileinteligence.ppmtool.services.MapValidationErrorService;
import io.agileinteligence.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
	    BindingResult bindingResult, @PathVariable String backlogId, Principal principal) {

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
	if (errorMap != null) {
	    return errorMap;
	}
	ProjectTask projectTask1 = projectTaskService.addProjectTask(backlogId, projectTask, principal.getName());
	return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlogId, Principal principal) {

	return projectTaskService.findBacklogById(backlogId, principal.getName());
    }

    @GetMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId,
	    Principal principal) {

	ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlogId, projectTaskId,
		principal.getName());
	return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
	    @PathVariable String backlogId, @PathVariable String projectTaskId, Principal principal) {

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
	if (errorMap != null) {
	    return errorMap;
	}
	ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlogId, projectTaskId,
		principal.getName());
	return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId,
	    Principal principal) {

	projectTaskService.deleteProjectTaskByProjectSequence(backlogId, projectTaskId, principal.getName());
	return new ResponseEntity<String>("ProjectTask:" + projectTaskId + " の削除が成功しました。", HttpStatus.OK);
    }

}
