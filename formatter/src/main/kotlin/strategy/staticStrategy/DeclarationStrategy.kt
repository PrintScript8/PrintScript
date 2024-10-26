package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.DeclarationType
import strategy.FormatStrategy
import java.io.Writer

class DeclarationStrategy : FormatStrategy<DeclarationType> {

    private val modifierStrategy = ModifierStrategy()
    private val identifierStrategy = IdentifierStrategy()

    override fun apply(node: DeclarationType, rules: FormattingRules, writer: Writer) {
        apply(node, rules, writer, true)
    }

    fun apply(node: DeclarationType, rules: FormattingRules, writer: Writer, addSemicolon: Boolean) {
        modifierStrategy.apply(node.modifier, rules, writer)
        writer.write(" ")
        writer.write(node.name)

        if (rules.spaceBeforeColon) {
            writer.write(" ")
        }
        writer.write(":")
        if (rules.spaceAfterColon) {
            writer.write(" ")
        }
        identifierStrategy.apply(node.type, rules, writer)
        if (addSemicolon) {
            writer.write(";")
        }
    }
}

