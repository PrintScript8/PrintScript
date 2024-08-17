package type

import interpreter.InterpreterImpl
import node.staticpkg.StaticNode
import node.dynamic.SubtractType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class SubtractTypeTest {

    @Test
    fun testSumTypeCreation() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        assertNotNull(subtractType)
    }

    @Test
    fun testNumberResult() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        subtractType.visit(dynamicVisitor)
        val result: LiteralValue = subtractType.result!!
        assertEquals("0", result.toString())
    }
}