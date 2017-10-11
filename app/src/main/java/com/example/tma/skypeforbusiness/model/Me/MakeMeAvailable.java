package com.example.tma.skypeforbusiness.model.Me;

import java.io.Serializable;

/**
 * Created by tmvien on 2/14/17.
 */
public class MakeMeAvailable implements Serializable {
    private String href;
    private String revision;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
}
