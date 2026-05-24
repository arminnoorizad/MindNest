package ir.armin.mindnest.core.common

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : AppResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data

    fun errorMessageOrNull(): String? = (this as? Error)?.message
}

inline fun <T> appResultOf(
    errorMessage: (Throwable) -> String = { it.message ?: "Unknown error" },
    block: () -> T
): AppResult<T> = try {
    AppResult.Success(block())
} catch (e: Exception) {
    AppResult.Error(errorMessage(e), e)
}
