package utils

enum class Action {
    VALIDATE,
    FORMAT,
    EXECUTE,
    ANALYZE,
    EXIT;

    fun requiresJson(): Boolean {
        return this == FORMAT || this == ANALYZE
    }
}
