package com.horn.blockchain.chaincode.block;

import java.util.List;

public class BlockHeader {
    //版本号
    private int version;
    //上一区块的哈希
    private String hashPreviousBlock;
    //Merkle树根节点哈希值
    private String hashMerkleRoot;
    //生成该区块的公钥
    private String publicKey;
    //区块的序号
    private int number;
    //时间戳
    private long timeStrmp;
    //32位随机数
    private long nonce;
    //区块内每条交易信息的哈希集合，按顺序来的，通过该哈希集合能算出根哈希节点哈希值
    private List<String> hashList;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public void setHashPreviousBlock(String hashPreviousBlock) {
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public String getHashMerkleRoot() {
        return hashMerkleRoot;
    }

    public void setHashMerkleRoot(String hashMerkleRoot) {
        this.hashMerkleRoot = hashMerkleRoot;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTimeStrmp() {
        return timeStrmp;
    }

    public void setTimeStrmp(long timeStrmp) {
        this.timeStrmp = timeStrmp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public List<String> getHashList() {
        return hashList;
    }

    public void setHashList(List<String> hashList) {
        this.hashList = hashList;
    }
}
