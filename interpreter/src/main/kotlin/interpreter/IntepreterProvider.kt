package interpreter

import node.staticpkg.StaticNode

class IntepreterProvider(private val iterator: Iterator<StaticNode>) {
    fun provideInterpreter(version: String): Interpreter {
        return when (version) {
            "1.0" -> InterpreterV1(iterator)
            "1.1" -> InterpreterV2(iterator)
            else -> throw IllegalArgumentException("Version not found")
        }
    }
}
