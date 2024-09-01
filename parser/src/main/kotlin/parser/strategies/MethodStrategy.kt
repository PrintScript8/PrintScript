package parser.strategies

import elements.RightSideParser
import node.Node
import node.dynamic.DynamicNode
import node.staticpkg.PrintLnType
import token.CloseParenthesis
import token.OpenParenthesis
import token.Token
import token.TokenType

class MethodStrategy(private val allowedTypes: Set<TokenType>) : ParseStrategy {

    private val rightSideParser: RightSideParser = RightSideParser(allowedTypes)

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        require(statementNodes.isEmpty()) {
            "Can't call nativeMethod '${tokens[currentIndex].text}' " +
                "with other arguments before it at ${tokens[currentIndex].position}"
        }
        require(tokens[currentIndex + 1].type == OpenParenthesis) {
            "Missing opening parenthesis at ${tokens[currentIndex].position}"
        }
        require(!(currentIndex + 1 >= tokens.size || tokens[currentIndex+1].type == CloseParenthesis)) {
            "Expected arguments for method at ${tokens[currentIndex].position}"
        }
        val tuple: Pair<DynamicNode, Int> = rightSideParser.parseRightHandSide(
            tokens, currentIndex + 2, CloseParenthesis
        )
        val argument: DynamicNode = tuple.first
        val resultIndex: Int = tuple.second
        statementNodes.add(PrintLnType(argument))
        return resultIndex
    }
}
