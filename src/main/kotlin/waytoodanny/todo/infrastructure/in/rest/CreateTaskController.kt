package waytoodanny.todo.infrastructure.`in`.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import waytoodanny.todo.domain.Task
import waytoodanny.todo.usecase.CreateTaskCommand
import java.net.URI

@RestController
class CreateTaskController(val createTaskCommand: CreateTaskCommand) {

    @PostMapping("/task/create")
    fun createTask(@RequestBody task: CreateTaskDto): ResponseEntity<String> {
        val persistedTask = createTaskCommand.apply(task.toDomainEntity())
        val taskUri = URI.create("/task/").resolve(persistedTask.id.toString())
        return ResponseEntity.created(taskUri).build()
    }

    data class CreateTaskDto(val description: String) {

        fun toDomainEntity(): Task = Task(null, this.description)
    }
}