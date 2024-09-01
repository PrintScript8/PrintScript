import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.elements.Parser
import parser.elements.ParserProvider
import token.Assignment
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.Minus
import token.Modifier
import token.NativeMethod
import token.NumberLiteral
import token.Plus
import token.Position
import token.StringLiteral
import token.Token
import token.TokenInterface
import token.TypeId

class ParserTests {

    private val parserProvider: ParserProvider = ParserProvider()
    private val parser: Parser = parserProvider.getParser("1.0")

    @Test
    fun testDeclaration() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parseList: List<StaticNode> = parser.parse(tokenInterfaceList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is DeclarationType)
        val node: DeclarationType = headNode as DeclarationType
        Assertions.assertEquals("let", node.modifier.value)
        Assertions.assertEquals(true, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.STRING, node.type.type)
    }

    @Test
    fun testAssignation() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(NumberLiteral, "2", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenInterfaceList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.STRING, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (sumNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun testComplexAssignation() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(NumberLiteral, "2", Position(1, 1, 1)),
            Token(Minus, "-", Position(1, 1, 1)),
            Token(NumberLiteral, "3", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenInterfaceList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.STRING, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertTrue(sumNode.right is SubtractType)
        val subNode: SubtractType = sumNode.right as SubtractType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (subNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(3.0, (subNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun multipleSentence() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(StringLiteral, "Tomi", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenInterfaceList)
        Assertions.assertEquals(2, parseList.size)
        val declarationNode: StaticNode = parseList[0]
        Assertions.assertTrue(declarationNode is DeclarationType)
        val node: DeclarationType = declarationNode as DeclarationType
        Assertions.assertEquals("let", node.modifier.value)
        Assertions.assertEquals(true, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.STRING, node.type.type)
        val expressionNode: StaticNode = parseList[1]
        Assertions.assertTrue(expressionNode is ExpressionType)
        val node2: ExpressionType = expressionNode as ExpressionType
        Assertions.assertEquals("name", node2.variable.name)
        Assertions.assertTrue(node2.value is LiteralType)
        val stringNode: LiteralType = node2.value as LiteralType
        Assertions.assertEquals("Tomi", (stringNode.result as LiteralValue.StringValue).string)
    }

    @Test
    fun printLnTest() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(NativeMethod, "printLn", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenInterfaceList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: PrintLnType = parseList[0] as PrintLnType
        Assertions.assertTrue(headNode.argument is LiteralType)
        val stringNode: LiteralType = headNode.argument as LiteralType
        Assertions.assertEquals("Hello World!", (stringNode.result as LiteralValue.StringValue).string)
    }

    @Test
    fun missingEndingSemicolon() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingTypeInDeclaration() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun invalidAssignationWithoutRightHandSide() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun invalidTokenSequence() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingClosingParenthesisInNativeMethod() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(NativeMethod, "printLn", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingExpressionTest() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Ending, ";", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun expressionBeforePrintTest() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(NativeMethod, "printLn", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingArgumentsInMethod() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(NativeMethod, "printLn", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingParenthesisAndArgumentInMethod() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(NativeMethod, "printLn", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun testMissingArgumentsInAssignation() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingPreviousArgsInAssignation() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Assignment, "1", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun missingPreviousArgsInDeclaration() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }

    @Test
    fun incorrectDeclarationTest() {
        val tokenInterfaceList: List<TokenInterface> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenInterfaceList)
        }
    }
}
