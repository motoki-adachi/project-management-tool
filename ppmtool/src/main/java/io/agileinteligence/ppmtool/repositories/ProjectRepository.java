package io.agileinteligence.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agileinteligence.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifer(String projectId);

    Iterable<Project> findAllByProjectLeader(String username);

}
