package waytoodanny.todo.infrastructure.out.jpa

import org.mapstruct.Mapper
import waytoodanny.todo.domain.Task
import waytoodanny.todo.infrastructure.out.jpa.entity.TaskEntity
import waytoodanny.todo.service.persistence.TaskRepository

class JpaTaskRepositoryAdapter(
        private val jpaRepository: JpaTaskRepository,
        private val taskMapper: TaskMapper
) : TaskRepository {

    override fun save(task: Task): Task =
            taskMapper.entityToDomain(
                    jpaRepository.save(
                            taskMapper.domainToEntity(task)
                    )
            )

    @Mapper
    interface TaskMapper {
        fun entityToDomain(entity: TaskEntity): Task
        fun domainToEntity(domain: Task): TaskEntity
    }
}