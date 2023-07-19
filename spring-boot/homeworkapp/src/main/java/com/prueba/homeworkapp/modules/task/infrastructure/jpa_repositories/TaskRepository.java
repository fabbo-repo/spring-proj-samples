package com.prueba.homeworkapp.modules.task.infrastructure.jpa_repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findAllByTaskStatus(TaskStatus taskStatus);
	
	/**
	 * Execute native query in spring 
	 * (it is executed according to the used database engine)
	 * @param id
	 */
	@Modifying // Transactional query
	@Query(value = "UPDATE TASK SET FINISHED=true WHERE ID=:id", nativeQuery = true)
	void markTaskAsFinished(@Param("id") Long id);
}
