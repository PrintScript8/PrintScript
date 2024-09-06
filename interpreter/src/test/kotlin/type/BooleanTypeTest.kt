package type

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class BooleanTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val boolType = LiteralType(LiteralValue.BooleanValue(false))
        assertNotNull(boolType)
    }
}
