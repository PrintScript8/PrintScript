package parser.strategies

import elements.RightSideParser
import node.Node
import node.dynamic.DynamicNode
import node.staticpkg.PrintLnType
import token.CloseParenthesis
import token.OpenParenthesis
import token.TokenInterface
import token.TokenType

class MethodStrategy(private val allowedTypes: Set<TokenType>) : ParseStrategy {

    private val rightSideParser: RightSideParser = RightSideParser(allowedTypes)

    override fun parse(tokenInterfaces: List<TokenInterface>, currentIndex: Int, statementNodes: MutableList<Node>):
        Int {
        require(statementNodes.isEmpty()) {
            "Can't call nativeMethod '${tokenInterfaces[currentIndex].text}' " +
                "with other arguments before it at ${tokenInterfaces[currentIndex].position}"
        }
        require(tokenInterfaces[currentIndex + 1].type == OpenParenthesis) {
            "Missing opening parenthesis at ${tokenInterfaces[currentIndex].position}"
        }
        require(
            !(
                currentIndex + 1 >= tokenInterfaces.size ||
                    tokenInterfaces[currentIndex+1].type == CloseParenthesis
                )
        ) {
            "Expected arguments for method at ${tokenInterfaces[currentIndex].position}"
        }
        val tuple: Pair<DynamicNode, Int> = rightSideParser.parseRightHandSide(
            tokenInterfaces, currentIndex + 2, CloseParenthesis
        )
        val argument: DynamicNode = tuple.first
        val resultIndex: Int = tuple.second
        statementNodes.add(PrintLnType(argument))
        return resultIndex
    }
}
