package type

import interpreter.InterpreterImpl
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import visitor.DynamicInterpreterVisitor
import visitor.StaticInterpreterVisitor

class StringTypeTest {

    @Test
    fun testStringTypeCreation() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        assertNotNull(stringType)
    }

    @Test
    fun testStringTypeResultType() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        stringType.visit(dynamicVisitor)
        val result: LiteralValue = stringType.result!!
        assertEquals("Hello", result.toString())
    }

    @Test
    fun testPrint() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello!"))
        val stringPrintLnType = PrintLnType(stringType)
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        val staticVisitor = StaticInterpreterVisitor(InterpreterImpl())
        stringType.visit(dynamicVisitor)
        stringPrintLnType.visit(staticVisitor)
    }
}
