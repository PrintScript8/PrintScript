package strategy

import json.FormattingRules
import node.staticpkg.DeclarationType
import node.staticpkg.StaticNode
import java.io.Writer

class DeclarationStrategy : FormatStrategy<DeclarationType> {

    private val modifierStrategy = ModifierStrategy()
    private val identifierStrategy = IdentifierStrategy()

    override fun apply(node: DeclarationType, rules: FormattingRules, writer: Writer) {
        modifierStrategy.apply(node.modifier, rules, writer)
        writer.write(" ")
        writer.write(node.name)
        writer.write(": ")
        identifierStrategy.apply(node.type, rules, writer)
    }
}

