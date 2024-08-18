package type

import interpreter.InterpreterImpl
import node.dynamic.MultiplyType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class StringTypeTest {

    @Test
    fun testStringTypeCreation() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        assertNotNull(stringType)
    }

    @Test
    fun testStringTypeResultType() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        stringType.visit(dynamicVisitor)
        val result: LiteralValue = stringType.result!!
        assertEquals("Hello", result.toString())
    }

    @Test
    fun testPrint() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello!"))
        val stringPrintLnType = PrintLnType(stringType)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        stringType.visit(dynamicVisitor)
        stringPrintLnType.visit(dynamicVisitor)
    }
}