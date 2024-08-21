package parserTest

import node.PrimType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode
import parser.elements.Parser
import org.example.token.Token
import org.example.token.TokenImpl
import org.example.token.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.elements.Parser2
import type.LiteralType
import type.LiteralValue

class ParserTests {

    private val parser: Parser = Parser2();

    @Test
    fun testDeclaration(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.COLON, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ENDING, ";", 1)
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0];
        Assertions.assertTrue(headNode is DeclarationType)
        val node: DeclarationType = headNode as DeclarationType
        Assertions.assertEquals("let" ,node.modifier.value)
        Assertions.assertEquals(true ,node.modifier.canModify)
        Assertions.assertEquals("name" ,node.name)
        Assertions.assertEquals(PrimType.STRING ,node.type.type)
    }

    @Test
    fun testAssignation(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.COLON, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ASSIGNATION, "=", 1),
            TokenImpl(TokenType.NUMBER_LITERAL, "1", 1),
            TokenImpl(TokenType.OPERAND, "+", 1),
            TokenImpl(TokenType.NUMBER_LITERAL, "2", 1),
            TokenImpl(TokenType.ENDING, ";", 1),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0];
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let" ,leftNode.modifier.value)
        Assertions.assertEquals(true ,leftNode.modifier.canModify)
        Assertions.assertEquals("name" ,leftNode.name)
        Assertions.assertEquals(PrimType.STRING ,leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (sumNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun testComplexAssignation(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.COLON, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ASSIGNATION, "=", 1),
            TokenImpl(TokenType.NUMBER_LITERAL, "1", 1),
            TokenImpl(TokenType.OPERAND, "+", 1),
            TokenImpl(TokenType.NUMBER_LITERAL, "2", 1),
            TokenImpl(TokenType.OPERAND, "-", 1),
            TokenImpl(TokenType.NUMBER_LITERAL, "3", 1),
            TokenImpl(TokenType.ENDING, ";", 1),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0];
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let" ,leftNode.modifier.value)
        Assertions.assertEquals(true ,leftNode.modifier.canModify)
        Assertions.assertEquals("name" ,leftNode.name)
        Assertions.assertEquals(PrimType.STRING ,leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SubtractType)
        val subNode: SubtractType = headNode.value as SubtractType
        Assertions.assertTrue(subNode.left is SumType)
        val sumNode: SumType = subNode.left as SumType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (sumNode.right.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(3.0, (subNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun multipleSentence(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.COLON, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ENDING, ";", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.ASSIGNATION, "=", 1),
            TokenImpl(TokenType.STRING_LITERAL, "Tomi", 1),
            TokenImpl(TokenType.ENDING, ";", 1),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
        Assertions.assertEquals(2, parseList.size)
        val declarationNode: StaticNode = parseList[0];
        Assertions.assertTrue(declarationNode is DeclarationType)
        val node: DeclarationType = declarationNode as DeclarationType
        Assertions.assertEquals("let" ,node.modifier.value)
        Assertions.assertEquals(true ,node.modifier.canModify)
        Assertions.assertEquals("name" ,node.name)
        Assertions.assertEquals(PrimType.STRING ,node.type.type)
        val expressionNode: StaticNode = parseList[1]
        Assertions.assertTrue(expressionNode is ExpressionType)
        val node2: ExpressionType = expressionNode as ExpressionType
        Assertions.assertEquals("name", node2.variable.name)
        Assertions.assertTrue(node2.value is LiteralType)
        val stringNode: LiteralType = node2.value as LiteralType
        Assertions.assertEquals("Tomi", (stringNode.result as LiteralValue.StringValue).string)
    }
}