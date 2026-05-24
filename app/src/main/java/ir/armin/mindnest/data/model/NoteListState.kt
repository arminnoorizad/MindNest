package ir.armin.mindnest.data.model

import ir.armin.mindnest.data.local.database.entity.NoteEntity

data class NoteListState(
    val notes: List<NoteEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDeletionInProgress: Boolean = false,
    val deletionSuccessMessage: String? = null,
    val noteToDeleteId: Int? = null
)
