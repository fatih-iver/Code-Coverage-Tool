package fun.fiver.nodes;

public class CCTInstructionNode {

    private int opcode;

    public CCTInstructionNode(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    @Override
    public String toString() {
        return "CCTInstructionNode{" +
                "opcode=" + opcode +
                '}';
    }
}
