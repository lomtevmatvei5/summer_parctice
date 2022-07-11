package ru.ac.uniyar.mf.lomtev;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Node {
    public String id;
    public String name;
    public List<Node> children;

    public Node(){;}

    public Node(String node_name) {
        this.id = UUID.randomUUID().toString();
        this.name = node_name;
        this.children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void add(Node _child) {
        children.add(_child);
    }

    public void deleteNode(String ident){
        int idx = -1;
        for (int i = 0; i<children.size(); i++)
            if (children.get(i).id == ident)
                idx = i;
        if (idx != -1)
            children.remove(idx);
    }

    private String toString(Node node, int space){
        if (node == null)
            return "";
        StringBuilder result = new StringBuilder();
        for (int i=0;i<space;i++){
            result.append("\t");
        }
        result.append(node.name).append("\n");
        if (node.children == null){
            return result.toString();
        }
        for (Node child : node.children){
            result.append(toString(child, space + 1));
        }
        return result.toString();
    }

    @Override
    public String toString(){
        return toString(this,0);
    }

    public void printTree(Node root, List<String> arr_1){
        List<Node> arr = root.getChildren();
        if (arr.size() == 0) {
            arr_1.add((String)root.getName());
            return;
        }
        for (int i=0;i<arr.size();i++){
            printTree((Node) arr.get(i), arr_1);
        }
    }
}