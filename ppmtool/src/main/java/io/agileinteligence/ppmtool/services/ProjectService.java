package io.agileinteligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileinteligence.ppmtool.domain.Backlog;
import io.agileinteligence.ppmtool.domain.Project;
import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.exceptions.ProjectIdException;
import io.agileinteligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileinteligence.ppmtool.repositories.BacklogRepository;
import io.agileinteligence.ppmtool.repositories.ProjectRepository;
import io.agileinteligence.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;

    /*
     * エンティティの登録処理
     *
     * @param io.agileinteligence.ppmtool.domain.Project
     */
    public Project saveOrUpdateProject(Project project, String username) {

	if (project.getId() != null) {
	    Project existingProject = projectRepository.findByProjectIdentifer(project.getProjectIdentifer());
	    if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
		throw new ProjectNotFoundException("ログイン中のアカウントでプロジェクトを見つけることができませんでした");
	    } else if (existingProject == null) {
		throw new ProjectNotFoundException(
			"プロジェクトID: " + project.getProjectIdentifer() + "は存在しないため更新することができません");
	    }
	}

	try {
	    User user = userRepository.findByUsername(username);
	    project.setUser(user);
	    project.setProjectLeader(user.getUsername());
	    // projectIdentiferの文字列を全て大文字に変換する
	    project.setProjectIdentifer(project.getProjectIdentifer().toUpperCase());

	    if (project.getId() == null) {
		// 新規作成の場合はリレーション先のエンティティを生成して紐付けを行う
		Backlog backlog = new Backlog();
		project.setBacklog(backlog);
		backlog.setProject(project);
		backlog.setProjectIdentifier(project.getProjectIdentifer().toUpperCase());
	    } else {
		// 編集の際は既存のプロジェクトIDから検索を行い結果をセットする
		project.setBacklog(
			backlogRepository.findByProjectIdentifier(project.getProjectIdentifer().toUpperCase()));
	    }

	    // 保存処理
	    return projectRepository.save(project);
	} catch (Exception e) {
	    throw new ProjectIdException("ProjectId<" + project.getProjectIdentifer().toUpperCase() + ">は既に使用されています。");
	}
    }

    /*
     * 単一検索処理
     *
     * @param projectIdentifer(io.agileinteligence.ppmtool.domain.Project)
     */
    public Project findProjectByIdentifier(String projectId, String username) {

	// projectIdentifer(大文字)をパラメーターとしてレコードを抽出する
	Project project = projectRepository.findByProjectIdentifer(projectId.toUpperCase());
	// 抽出結果が得られなかった時の例外処理
	if (project == null) {
	    throw new ProjectIdException("このProjectIdは登録されていません");
	}

	if (!project.getProjectLeader().equals(username)) {
	    throw new ProjectNotFoundException("ログイン中のアカウントでプロジェクトを見つけることができませんでした");
	}

	return project;
    }

    /*
     * 全検索処理
     */
    public Iterable<Project> findAllProjects(String username) {
	return projectRepository.findAllByProjectLeader(username);
    }

    /*
     * プロジェクト削除処理
     */
    public void deleteProjectByIdentifier(String projectId, String username) {
	projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}
