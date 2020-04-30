package fun.fiver.nodes;

import java.util.ArrayList;
import java.util.List;

public class CCTClassNode {

    private String className;
    private List<CCTMethodNode> cctMethodNodeList;

    public CCTClassNode(String className) {
        this.className = className;
        this.cctMethodNodeList = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public List<CCTMethodNode> getCCTMethodNodeList() {
        return cctMethodNodeList;
    }

    public void addCCTMethodNode(CCTMethodNode cctMethodNode) {
        cctMethodNodeList.add(cctMethodNode);
    }

    public CCTMethodNode getActiveCCTMethodNode() {
        return cctMethodNodeList.get(cctMethodNodeList.size() - 1);
    }

    @Override
    public String toString() {
        return "CCTClassNode{" +
                "className='" + className + '\'' +
                ", cctMethodNodeList=" + cctMethodNodeList +
                '}';
    }
}
