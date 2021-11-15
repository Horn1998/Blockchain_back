package com.horn.blockchain.chaincode.block;

import cn.hutool.crypto.digest.DigestUtil;

public class Block {
    //区块头
    private BlockHeader blockHeader;
    //区块体
    private BlockBody blockBody;
    //区块哈希
    private String blockHash;

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }

    public String getBlockHash(){
        return DigestUtil.sha256Hex(blockHeader.toString() + blockBody.toString());
    }

    public void setBlockHash(String blockHash){
        this.blockHash = blockHash;
    }
}
