package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FILECAUHOI")
public class FileCauHoi {
    @Id
    @Column(name = "FILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int file_id;
    @Column(name = "FILENAME")
    private String fileName;
    @Column(name = "TYPE")
    private String type;
    @ManyToOne
    @JoinColumn(name = "IDCH")
    private CauHoi cauHoi;
    public FileCauHoi() {
    }
    public FileCauHoi(int file_id, String fileName, String type, CauHoi cauHoi) {
        this.file_id = file_id;
        this.fileName = fileName;
        this.type = type;
        this.cauHoi = cauHoi;
    }
    public int getFile_id() {
        return file_id;
    }
    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public CauHoi getCauHoi() {
        return cauHoi;
    }
    public void setCauHoi(CauHoi cauHoi) {
        this.cauHoi = cauHoi;
    }
}
