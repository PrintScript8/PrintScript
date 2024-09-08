package type

import interpreter.InterpreterProvider
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.IfElseType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IfElseTypeTest {
    @Test
    fun `run should return ifBranch result when boolean is true`() {
        // Arrange
        val ifBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testLeft"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val elseBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testRight"
            ),
            LiteralType(LiteralValue.NumberValue(3))
        )

        val boolean = LiteralValue.BooleanValue(true)
        val ifElseType = IfElseType(ifBranch, elseBranch, boolean)
        val version = "1.1"
        val printLnTypeLeft = PrintLnType(VariableType("testLeft", null, true))
        val printLnTypeRight = PrintLnType(VariableType("testRight", null, true))

        // Act
        val validInput = listOf(ifElseType, printLnTypeLeft)
        val validInterpreter = InterpreterProvider(validInput.iterator()).provideInterpreter(version)

        val invalidInput = listOf(ifElseType, printLnTypeRight)
        val invalidInterpreter = InterpreterProvider(invalidInput.iterator()).provideInterpreter(version)

        // Assert
        assertEquals(listOf("1"), validInterpreter.execute())
        assertThrows<IllegalArgumentException> {
            invalidInterpreter.execute()
        }
    }

    @Test
    fun `run should return elseBranch result when boolean is false`() {
        // Arrange
        val ifBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testLeft"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val elseBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testRight"
            ),
            LiteralType(LiteralValue.NumberValue(3))
        )

        val boolean = LiteralValue.BooleanValue(false)
        val ifElseType = IfElseType(ifBranch, elseBranch, boolean)
        val version = "1.1"
        val printLnTypeLeft = PrintLnType(VariableType("testLeft", null, true))
        val printLnTypeRight = PrintLnType(VariableType("testRight", null, true))

        // Act
        val validInput = listOf(ifElseType, printLnTypeRight)
        val validInterpreter = InterpreterProvider(validInput.iterator()).provideInterpreter(version)

        val invalidInput = listOf(ifElseType, printLnTypeLeft)
        val invalidInterpreter = InterpreterProvider(invalidInput.iterator()).provideInterpreter(version)

        // Assert
        assertEquals(listOf("3"), validInterpreter.execute())
        assertThrows<IllegalArgumentException> {
            invalidInterpreter.execute()
        }
    }

    @Test
    fun `run should fail ifBranch result when variable not declared`() {
        // Arrange
        val ifBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testLeft"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val elseBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testRight"
            ),
            LiteralType(LiteralValue.NumberValue(3))
        )

        val boolean = LiteralValue.BooleanValue(true)
        val ifElseType = IfElseType(ifBranch, elseBranch, boolean)
        val version = "1.1"
        val printLnTypeLeft = PrintLnType(VariableType("testLeft", null, true))
        val printLnTypeRight = PrintLnType(VariableType("testRight", null, true))

        // Act
        val validInput = listOf(ifElseType, printLnTypeLeft)
        val validInterpreter = InterpreterProvider(validInput.iterator()).provideInterpreter(version)

        val invalidInput = listOf(ifElseType, printLnTypeRight)
        val invalidInterpreter = InterpreterProvider(invalidInput.iterator()).provideInterpreter(version)

        // Assert
        assertEquals(listOf("1"), validInterpreter.execute())
        assertThrows<IllegalArgumentException> {
            invalidInterpreter.execute()
        }
    }
}
