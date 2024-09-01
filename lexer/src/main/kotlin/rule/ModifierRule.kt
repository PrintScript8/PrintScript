package rule

import token.Modifier
import token.Position
import token.Token
import token.TokenInterface

class ModifierRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val modifierKeywords = listOf("let", "const")
        for (keyword in modifierKeywords) {
            if (input.startsWith(keyword)) {
                return Token(
                    Modifier, keyword,
                    Position(
                        position.row,
                        position.startColumn,
                        position.startColumn + keyword.length - 1
                    )
                )
            }
        }
        return null
    }
}
