package com.codebrewers.onlinebookstore.dto;

public class UploadFileResponse {
    public String fileName;
    public String fileDownloadUri;

    public UploadFileResponse(String fileName, String fileDownloadUri) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }
}