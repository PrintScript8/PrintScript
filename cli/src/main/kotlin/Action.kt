package cli

enum class Action {
    VALIDATE,
    FORMAT,
    EXECUTE,
    ANALYZE;

    fun requiresJson(): Boolean {
        return this == FORMAT || this == ANALYZE
    }
}
