package com.IntegrityTool.model.EdiFile;

import java.io.File;

public class FileParam {
    private File file;
    private boolean userStatus;

    public FileParam() {
        this.file = new File("");
        userStatus = false;
    }

    public FileParam(File f, boolean userStatus) {
        this.file = f;
        this.userStatus = userStatus;
    }

    public File getFile() {
        return file;
    }

    public boolean isUserStatus() {
        return userStatus;
    }
}
