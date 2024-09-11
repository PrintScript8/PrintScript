package interpreter

interface Interpreter {
    fun execute(): Iterator<String>
}
