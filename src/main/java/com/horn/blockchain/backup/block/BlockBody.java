package com.horn.blockchain.backup;

import java.util.List;

public class BlockBody {
    private List<ContentInfo> contentInfos;

    public List<ContentInfo> getContentInfos(){
        return contentInfos;
    }

    public void setContentInfos(List<ContentInfo> contentInfos){
        this.contentInfos = contentInfos;
    }
}
