package gq.glowman554.bot.utils.math.parser;

import java.util.Objects;

public class ParserNode {
    private final ParserNodeType type;
    private final ParserNode node_a;
    private final ParserNode node_b;

    private final Object value;

    public ParserNode(ParserNodeType type, ParserNode node_a, ParserNode node_b) {
        this.type = type;

        if (type.only_1_element) {
            if (node_b != null) {
                throw new IllegalArgumentException("Node takes only 1 argument!");
            }
        }

        this.node_a = node_a;
        this.node_b = node_b;

        value = null;
    }

    public ParserNode(ParserNodeType type, Object value) {
        this.type = type;

        if (!type.only_1_element) {
            throw new IllegalArgumentException("Node does not take only 1 argument!");
        }

        this.node_a = null;
        this.node_b = null;

        this.value = value;
    }

    public ParserNode(ParserNodeType type, ParserNode node_a, Object value) {
        this.type = type;

        if (type.only_1_element) {
            throw new IllegalArgumentException("Node takes only 1 argument!");
        }

        this.node_a = node_a;
        this.node_b = null;

        this.value = value;
    }


    public Object getValue() {
        return value;
    }

    public ParserNode getNode_a() {
        return node_a;
    }

    public ParserNode getNode_b() {
        return node_b;
    }

    public ParserNodeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParserNode that = (ParserNode) o;
        return type == that.type && Objects.equals(node_a, that.node_a) && Objects.equals(node_b, that.node_b) && Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "ParserNode{" +
                "type=" + type +
                ", node_a=" + node_a +
                ", node_b=" + node_b +
                ", value=" + value +
                '}';
    }

    public enum ParserNodeType {
        number_node(true),
        add_node(false),
        substract_node(false),
        multiply_node(false),
        divide_node(false),
        modulo_node(false),
        pow_node(false),
        plus_node(true),
        fcall_node(true),
        minus_node(true);

        public final boolean only_1_element;

        ParserNodeType(boolean only_1_element) {
            this.only_1_element = only_1_element;
        }
    }
}
