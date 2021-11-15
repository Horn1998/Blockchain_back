package com.horn.blockchain.chaincode.util.merkle;

import com.horn.blockchain.chaincode.util.hash.SHAUtil;

import java.util.ArrayList;
import java.util.List;

public class SimpleMerkleTree {
    /**
     * 简化的Merkle树根节点哈希值计算
     * */
    public static String getTreeNodeHash(List<String> hashList){
        if(hashList == null || hashList.size() == 0) return null;
        while(hashList.size() != 1) hashList = getMerKleNodeList(hashList);
        return hashList.get(0);
    }

    //按照Merkle树思想计算根节点哈希值
    public static List<String> getMerKleNodeList(List<String> contentList){
        List<String> merkleNodeList = new ArrayList<String>();
        if(contentList == null || contentList.size() == 0) return merkleNodeList;
        int index = 0, length = contentList.size();
        while(index < length){
            //获取左孩子节点数据
            String left = contentList.get(index++);
            //获取右孩子节点数据
            String right = "";
            if(index < length) right = contentList.get(index++);
            //计算左右孩子节点的父节点哈希值
            String parentHash = SHAUtil.sha256BasedHutool(left + right);
            merkleNodeList.add(parentHash);
        }
        return merkleNodeList;
    };
}
