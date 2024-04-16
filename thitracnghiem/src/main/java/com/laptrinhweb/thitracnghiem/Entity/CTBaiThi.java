package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CTBAITHI")
@IdClass(IDCTBaiThi.class)
public class CTBaiThi {
	@Id
	@Column(name = "IDCH")
	private int idch;
	@Column(name = "DAPANSV")
	private int dapAnSv;
	@Column(name = "THUTUCHON")
	private int thuTuChon;
	@Column(name = "TRANGTHAIXOA")
	private boolean trangThaiXoa;
	@ManyToOne
	@JoinColumn(name = "IDTHI")
	private Thi thi;

	public CTBaiThi(int idch, int dapAnSv, int thuTuChon, boolean trangThaiXoa) {
		this.idch = idch;
		this.dapAnSv = dapAnSv;
		this.thuTuChon = thuTuChon;
		this.trangThaiXoa = trangThaiXoa;
	}

	public CTBaiThi() {
	}

	public int getIdch() {
		return idch;
	}

	public void setIdch(int idch) {
		this.idch = idch;
	}

	public int getDapAnSv() {
		return dapAnSv;
	}

	public void setDapAnSv(int dapAnSv) {
		this.dapAnSv = dapAnSv;
	}

	public int getThuTuChon() {
		return thuTuChon;
	}

	public void setThuTuChon(int thuTuChon) {
		this.thuTuChon = thuTuChon;
	}

	public boolean isTrangThaiXoa() {
		return trangThaiXoa;
	}

	public void setTrangThaiXoa(boolean trangThaiXoa) {
		this.trangThaiXoa = trangThaiXoa;
	}
}
