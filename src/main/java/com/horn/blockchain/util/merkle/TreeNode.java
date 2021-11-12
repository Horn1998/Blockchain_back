package com.horn.blockchain.util.merkle;


import com.horn.blockchain.util.hash.SHAUtil;

/**
 * Merkle树节点定义
 * */
public class TreeNode {
    //二叉树的左孩子
    private TreeNode left;
    //二叉树的右孩子
    private TreeNode right;
    //二叉树中孩子节点的数据
    private String data;
    //二叉树中孩子节点数据对应的哈希值，这里采用SHA-256算法处理
    private String hash;
    //节点名称
    private String name;

    //构造函数1
    public TreeNode(){};

    //构造函数2
    public TreeNode(String data){
        this.data = data;
        this.hash = SHAUtil.sha256BasedHutool(data);
        this.name = "[节点：" + data + "]";
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setName(String name) {
        this.name = name;
    }
}
