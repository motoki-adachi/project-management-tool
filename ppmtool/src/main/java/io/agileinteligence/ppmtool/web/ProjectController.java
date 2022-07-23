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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileinteligence.ppmtool.domain.Project;
import io.agileinteligence.ppmtool.services.MapValidationErrorService;
import io.agileinteligence.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult,
	    Principal principal) {

	// エラーハンドリング処理
	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
	if (errorMap != null) {
	    return errorMap;
	}
	// 送信されてきたデータを登録する
	Project targetProject = projectService.saveOrUpdateProject(project, principal.getName());
	// 登録したデータをJSONレスポンスとして返却する
	return new ResponseEntity<Project>(targetProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {

	// projectIdentiferフィールドをパラメーターとしてレコードを抽出する
	Project resultProject = projectService.findProjectByIdentifier(projectId, principal.getName());
	// 抽出したデータをJSONレスポンスとして返却する
	return new ResponseEntity<Project>(resultProject, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
	// 全検索処理
	return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {

	projectService.deleteProjectByIdentifier(projectId, principal.getName());
	return new ResponseEntity<String>("ProjectId<" + projectId + ">を削除しました", HttpStatus.CREATED);

    }

}
