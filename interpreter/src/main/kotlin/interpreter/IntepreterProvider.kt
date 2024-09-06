package interpreter

class IntepreterProvider {
    fun provideInterpreter(version: String): Interpreter {
        return when (version) {
            "1.0" -> InterpreterImpl()
            else -> throw IllegalArgumentException("Version not found")
        }
    }
}
