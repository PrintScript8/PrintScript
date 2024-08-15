package type

import node.staticpkg.StaticNode
import node.dynamic.SubtractType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SubtractTypeTest {

    @Test
    fun testSumTypeCreation() {
        val subtractType = SubtractType()
        assertNotNull(subtractType)
    }

    @Test
    fun testNumberResult() {
        val subtractType = SubtractType()
        val children = listOf<StaticNode>(AST(NumberType(5), listOf()), AST(NumberType(5), listOf())) // Assuming Node is a valid class
        assert(subtractType.apply(children) is NumberType)
        assert((subtractType.apply(children) as NumberType).print(listOf()) == "0")
    }

    @Test
    fun testSumTypePrint() {
        val subtractType = SubtractType()
        val children = listOf<StaticNode>(AST(StringType("5"), listOf()), AST(StringType("5"), listOf())) // Assuming Node is a valid class
        val expectedOutput = "5 - 5"
        assertEquals(expectedOutput, subtractType.print(children))
    }
}