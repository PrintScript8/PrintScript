package node.dynamic

import operations.DynamicVisitor

sealed class LiteralValue {
    data class NumberValue(val number: Number) : LiteralValue() {
        override fun toString(): String {
            return if (number.toDouble() % 1 == 0.0) {
                number.toInt().toString()
            } else {
                number.toString()
            }
        }
    }
    data class StringValue(val string: String) : LiteralValue() {
        override fun toString(): String {
            return string.replace("\"", "")
        }
    }

    data class BooleanValue(val boolean: Boolean) : LiteralValue() {
        override fun toString(): String {
            return boolean.toString()
        }
    }

    operator fun plus(other: LiteralValue): LiteralValue {
        require(this !is BooleanValue && other !is BooleanValue) { "Cannot add boolean" }
        return when (this) {
            is StringValue -> when (other) {
                is NumberValue -> StringValue(string = this.string + other.toString())
                is StringValue -> StringValue(string = this.string + other.string)
                else -> error("Unsupported type")
            }
            is NumberValue -> when (other) {
                is StringValue -> StringValue(string = this.toString() + other.string)
                is NumberValue -> NumberValue(number = this.number.toDouble() + other.number.toDouble())
                else -> error("Unsupported type")
            }
            else -> error("Unsupported type")
        }
    }

    operator fun minus(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Subtraction is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() - other.number.toDouble())
    }

    operator fun div(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Division is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() / other.number.toDouble())
    }

    operator fun times(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Multiplication is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() * other.number.toDouble())
    }
}

class LiteralType(override var result: LiteralValue?) : DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptLiteral(this)
    }
}
