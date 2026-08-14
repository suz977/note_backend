package com.example.demo.controller

import com.example.demo.database.model.Note
import com.example.demo.database.repository.NotesRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
//import org.springframework.data.mongodb.core.aggregation.MergeOperation.UniqueMergeId.id
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.time.Clock
import kotlin.time.Instant

@RestController
@RequestMapping("/notes")
class NotesController(val repository: NotesRepository) {
    data class NoteRequest(
        val id : String?,
        @field:NotBlank(message = "Title can not be blanked")
        val title : String,
        val content : String,
        val color : Long
    )
    data class NoteResponse(
        val id : String,
        val title : String,
        val content : String,
        val color : Long,
        val createdAt : Instant,
    )

    @PostMapping
    fun saveNote(@Valid @RequestBody request: NoteRequest): NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication!!.principal as String
        val note = repository.save(
            com.example.demo.database.model.Note(
                title = request.title,
                content = request.content,
                color = request.color,
                createdAt = Clock.System.now(),
                ownerId = ObjectId(ownerId),
                id = request.id?.let { ObjectId(it) } ?: ObjectId.get()
            )
        )
        return note.toResponse()
    }

    @GetMapping
    fun findNotesByOwnerId(): List<NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication!!.principal as String
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse();
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        val note = repository.findById(ObjectId(id)).orElseThrow { Exception("Note not found") }
        val ownerId = SecurityContextHolder.getContext().authentication!!.principal as String
        if (note.ownerId.toHexString() == ownerId) {
            repository.deleteById(ObjectId(id))
        }
    }

    private fun Note.toResponse(): NotesController.NoteResponse {
        return NoteResponse(
            id = id.toHexString(),
            title = title,
            content = content,
            color = color,
            createdAt = createdAt
        )
    }
}