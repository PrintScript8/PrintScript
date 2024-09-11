package parser.strategies

import elements.RightSideParser
import node.Node
import node.dynamic.DynamicNode
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import node.staticpkg.PrintLnType
import token.CloseParenthesis
import token.OpenParenthesis
import token.TokenInterface
import token.TokenType

class MethodStrategy(
    private val allowedTypes: Set<TokenType>,
    private val versionMethods: Set<String>
) : ParseStrategy {

    private val rightSideParser: RightSideParser = RightSideParser(allowedTypes)

    override fun parse(tokenInterfaces: List<TokenInterface>, currentIndex: Int, statementNodes: MutableList<Node>):
        Int {
        val currentToken = tokenInterfaces[currentIndex]
        require(versionMethods.contains(currentToken.text)) {
            "Unsupported method '${currentToken.text}' at: ${currentToken.position}"
        }
        require(currentIndex + 1 < tokenInterfaces.size && tokenInterfaces[currentIndex + 1].type == OpenParenthesis) {
            "Missing opening parenthesis at ${currentToken.position}"
        }

        return when (currentToken.text) {
            "println" -> parsePrintln(tokenInterfaces, currentIndex, statementNodes)
            "readEnv" -> parseReadEnv(tokenInterfaces, currentIndex, statementNodes)
            "readInput" -> parseReadInput(tokenInterfaces, currentIndex, statementNodes)
            else -> throw IllegalArgumentException(
                "Unsupported method '${currentToken.text}' at: ${currentToken.position}"
            )
        }
    }

    private fun parsePrintln(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(statementNodes.isEmpty()) {
            "Can't call nativeMethod '${tokenInterfaces[currentIndex].text}' " +
                "with other arguments before it at ${tokenInterfaces[currentIndex].position}"
        }
        require(
            !(
                currentIndex + 2 >= tokenInterfaces.size || tokenInterfaces[currentIndex+2].type == CloseParenthesis
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

    private fun parseReadEnv(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(statementNodes.isNotEmpty()) {
            "Method '${tokenInterfaces[currentIndex].text}' can't be used outside assignment at: " +
                "${tokenInterfaces[currentIndex].position}"
        }
        require(
            !(currentIndex + 2 >= tokenInterfaces.size || tokenInterfaces[currentIndex+2].type == CloseParenthesis)
        ) {
            "Expected arguments for method at ${tokenInterfaces[currentIndex].position}"
        }
        val tuple: Pair<DynamicNode, Int> = rightSideParser.parseRightHandSide(
            tokenInterfaces, currentIndex + 2, CloseParenthesis
        )
        val argument: DynamicNode = tuple.first
        val resultIndex: Int = tuple.second
        statementNodes.add(ReadEnvType(argument, null))
        return resultIndex
    }

    private fun parseReadInput(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(statementNodes.isNotEmpty()) {
            "Method '${tokenInterfaces[currentIndex].text}' can't be used outside assignment at: " +
                "${tokenInterfaces[currentIndex].position}"
        }
        require(
            !(currentIndex + 2 >= tokenInterfaces.size || tokenInterfaces[currentIndex+2].type == CloseParenthesis)
        ) {
            "Expected arguments for method at ${tokenInterfaces[currentIndex].position}"
        }
        val tuple: Pair<DynamicNode, Int> = rightSideParser.parseRightHandSide(
            tokenInterfaces, currentIndex + 2, CloseParenthesis
        )
        val argument: DynamicNode = tuple.first
        val resultIndex: Int = tuple.second
        statementNodes.add(ReadInputType(argument, null))
        return resultIndex
    }
}
