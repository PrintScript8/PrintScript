package strategy

import json.FormattingRules
import node.staticpkg.IdentifierType
import java.io.Writer

class IdentifierStrategy: FormatStrategy<IdentifierType> {

    override fun apply(node: IdentifierType, rules: FormattingRules, writer: Writer) {

        writer.write(node.type.toString().lowercase())
    }
}