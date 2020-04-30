package fun.fiver.nodes;

import java.util.ArrayList;
import java.util.List;

public class CCTLabelNode {

    private String label;
    private int lineNumber;
    private List<CCTInstructionNode> cctInstructionNodeList;

    public CCTLabelNode(String label) {
        this.label = label;
        this.lineNumber = -1;
        this.cctInstructionNodeList = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public List<CCTInstructionNode> getCctInstructionNodeList() {
        return cctInstructionNodeList;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addCCTInstructionNode(CCTInstructionNode cctInstructionNode) {
        this.cctInstructionNodeList.add(cctInstructionNode);
    }

    @Override
    public String toString() {
        return "CCTLabelNode{" +
                "label='" + label + '\'' +
                ", lineNumber=" + lineNumber +
                ", cctInstructionNodeList=" + cctInstructionNodeList +
                '}';
    }
}
