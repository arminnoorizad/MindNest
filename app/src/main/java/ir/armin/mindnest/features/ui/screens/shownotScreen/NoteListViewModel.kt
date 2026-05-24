package ir.armin.mindnest.features.ui.screens.shownotScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.armin.mindnest.core.common.AppResult
import ir.armin.mindnest.data.analytics.AnalyticsEvents
import ir.armin.mindnest.data.analytics.AnalyticsScreens
import ir.armin.mindnest.data.model.NoteListState
import ir.armin.mindnest.data.repository.AnalyticsRepository
import ir.armin.mindnest.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state: StateFlow<NoteListState> = _state.asStateFlow()

    private val searchFlow = MutableStateFlow("")

    init {
        analyticsRepository.logScreen(AnalyticsScreens.NOTE_LIST)
        collectNotes()
    }

    fun setSearchQuery(query: String) {
        val trimmedQuery = query.trim()
        searchFlow.value = trimmedQuery
        _state.update { it.copy(searchQuery = trimmedQuery) }

        if (trimmedQuery.isNotEmpty()) {
            analyticsRepository.logEvent(
                AnalyticsEvents.NOTE_SEARCH,
                mapOf(AnalyticsEvents.PARAM_QUERY_LENGTH to trimmedQuery.length)
            )
        }
    }

    fun onSwipeToDelete(noteId: Int) {
        _state.update { it.copy(noteToDeleteId = noteId) }
    }

    fun onDismissDeleteDialog() {
        _state.update { it.copy(noteToDeleteId = null) }
    }

    fun deleteNote(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            it.copy(
                isDeletionInProgress = true,
                error = null,
                deletionSuccessMessage = null,
                noteToDeleteId = null
            )
        }

        when (val result = repository.deleteNoteById(noteId)) {
            is AppResult.Success -> {
                analyticsRepository.logEvent(
                    AnalyticsEvents.NOTE_DELETED,
                    mapOf(AnalyticsEvents.PARAM_NOTE_ID to noteId)
                )
                _state.update {
                    it.copy(
                        isDeletionInProgress = false,
                        deletionSuccessMessage = "Note deleted successfully.",
                        error = null
                    )
                }
            }

            is AppResult.Error -> {
                _state.update {
                    it.copy(
                        isDeletionInProgress = false,
                        error = result.message,
                        deletionSuccessMessage = null
                    )
                }
            }
        }
    }

    fun errorHandled() {
        _state.update { it.copy(error = null) }
    }

    fun deletionMessageShown() {
        _state.update { it.copy(deletionSuccessMessage = null) }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun collectNotes() {
        viewModelScope.launch {
            searchFlow
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    _state.update { it.copy(isLoading = true, error = null) }
                    repository.observeNotes(query)
                }
                .collectLatest { notes ->
                    _state.update {
                        it.copy(
                            notes = notes,
                            isLoading = false
                        )
                    }
                }
        }
    }
}
