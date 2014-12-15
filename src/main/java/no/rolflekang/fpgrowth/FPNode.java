package no.rolflekang.fpgrowth;

import java.io.Serializable;
import java.util.*;

public class FPNode implements Serializable{
    private int item;
    private int count;
    private FPNode parent;
    private Map<Integer, FPNode> children;
    private FPNode neighbor;
    private boolean isARootNode;

    public FPNode() {
        this.children = new HashMap<Integer, FPNode>();
        this.isARootNode = true;
    }
    public FPNode(int item, int count) {
        this.item = item;
        this.count = count;
        this.children = new HashMap<Integer, FPNode>();
        this.isARootNode = false;
    }


    public int getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public int incrementCount(){
        this.count += 1;
        return this.count;
    }
    public int incrementCount(Integer count) {
        this.count += count;
        return this.count;
    }

    public FPNode getParent() {
        return parent;
    }

    public void setParent(FPNode parent) {
        this.parent = parent;
    }

    public Collection<FPNode> getChildren() {
        return this.children.values();
    }

    public void addChild(FPNode child) {
        if(!this.children.containsKey(child.getItem())) {
            this.children.put(child.getItem(), child);
            child.setParent(this);
        }
    }
    public void removeChild(FPNode node) {
        this.children.remove(node.getItem());
        node.parent = null;
    }
    public FPNode getChild(int item){
        return this.children.get(item);
    }

    public FPNode getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(FPNode neighbor) {
        this.neighbor = neighbor;
    }

    public boolean isRoot() {
        return isARootNode;
    }

    public boolean hasSingleChild() {
        return this.getChildren().size() == 1;
    }

    public FPNode getFirstChild() {
        return (FPNode) this.getChildren().toArray()[0];
    }

    @Override
    public String toString() {
       return this.toString(0);
    }
    public String toString(int indent) {
        String pre = "";
        for(int i = 0; i < indent; i++){
            pre += " | ";
        }
        String str = pre + "(" + item +  ":" + count + ")\n";
        for(FPNode child:children.values()){
            str += child.toString(indent + 1);
        }
        return str;
    }

    public boolean hasNeighbor() {
        return this.neighbor != null;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

}
