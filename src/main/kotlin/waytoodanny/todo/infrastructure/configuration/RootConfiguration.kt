package waytoodanny.todo.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import waytoodanny.todo.domain.Task
import waytoodanny.todo.infrastructure.jpa.JpaTaskRepository
import waytoodanny.todo.infrastructure.jpa.JpaTaskRepositoryAdapter
import waytoodanny.todo.infrastructure.jpa.entity.TaskEntity
import waytoodanny.todo.service.AllTasksQueryImpl
import waytoodanny.todo.service.CreateTaskCommandImpl
import waytoodanny.todo.service.TaskByIdQueryImpl
import waytoodanny.todo.service.persistence.TaskRepository
import waytoodanny.todo.usecase.AllTasksQuery
import waytoodanny.todo.usecase.CreateTaskCommand
import waytoodanny.todo.usecase.TaskByIdQuery
import java.util.*

@Configuration
class RootConfiguration {

    @Bean
    fun createTaskCommand(taskRepository: TaskRepository): CreateTaskCommand = CreateTaskCommandImpl(taskRepository)

    @Bean
    fun allTasksQuery(taskRepository: TaskRepository): AllTasksQuery = AllTasksQueryImpl(taskRepository)

    @Bean
    fun taskByIdQuery(taskRepository: TaskRepository): TaskByIdQuery = TaskByIdQueryImpl(taskRepository)

    @Bean
    fun taskRepository(jpaTaskRepository: JpaTaskRepository): TaskRepository =
            JpaTaskRepositoryAdapter(
                    jpaTaskRepository,
                    jpaDomainTaskMapper()
            )

    //TODO replace with mapStruct mapper
    @Bean
    fun jpaDomainTaskMapper(): JpaTaskRepositoryAdapter.TaskMapper =
            object : JpaTaskRepositoryAdapter.TaskMapper {

                override fun entityToDomain(entity: TaskEntity): Task =
                        Task(
                                UUID.fromString(entity.id),
                                entity.description
                        )

                override fun domainToEntity(domain: Task): TaskEntity =
                        TaskEntity(
                                domain.id.toString(),
                                domain.description
                        )
            }

}