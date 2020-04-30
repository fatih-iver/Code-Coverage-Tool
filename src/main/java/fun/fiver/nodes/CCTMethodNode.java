package fun.fiver.nodes;

import java.util.ArrayList;
import java.util.List;

public class CCTMethodNode {

    private String methodName;
    private List<CCTLabelNode> cctLabelNodeList;

    public CCTMethodNode(String methodName) {
        this.methodName = methodName;
        this.cctLabelNodeList = new ArrayList<>();
    }

    public String getMethodName() {
        return methodName;
    }

    public List<CCTLabelNode> getCCTLabelNodeList() {
        return cctLabelNodeList;
    }

    public void addCCTLabelNode(CCTLabelNode cctLabelNode) {
        this.cctLabelNodeList.add(cctLabelNode);
    }

    public CCTLabelNode getActiveCCTLabelNode() {
        return cctLabelNodeList.get(cctLabelNodeList.size() - 1);
    }

    public void removeLastAddedLabel() {
        cctLabelNodeList.remove(cctLabelNodeList.size() - 1);
    }

    public void findMissingLineNumbers() {
        for (int i = 0; i < cctLabelNodeList.size(); i++) {
            CCTLabelNode currentCCTLabelNode = cctLabelNodeList.get(i);
            if (currentCCTLabelNode.getLineNumber() == -1) {
                CCTLabelNode previousCCTLabelNode = cctLabelNodeList.get(i - 1);
                currentCCTLabelNode.setLineNumber(previousCCTLabelNode.getLineNumber());
            }
        }
    }

    @Override
    public String toString() {
        return "CCTMethodNode{" +
                "methodName='" + methodName + '\'' +
                ", cctLabelNodeList=" + cctLabelNodeList +
                '}';
    }
}
