package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CTBAITHI")
@IdClass(IDCTBaiThi.class)
public class CTBaiThi {
    @Id
    @Column(name = "IDTHI")
    private Integer idThi;
	@Id
    @Column(name = "IDCH")
    private Integer idch;

	@Column(name = "DAPANSV")
	private Integer dapAnSv;
	@Column(name = "THUTUCHON")
	private Integer thuTuChon;
	@Column(name = "TRANGTHAIXOA")
	private boolean trangThaiXoa;


	@ManyToOne
	@MapsId
	@JoinColumn(name = "IDTHI")
	private Thi thi;

	@ManyToOne
	@MapsId
	@JoinColumn(name = "IDCH")
	private CauHoi cauHoi;
	
	//============Constructor=====================//
	public CTBaiThi(Integer idThi, Integer idch, Integer dapAnSv, Integer thuTuChon, boolean trangThaiXoa, Thi thi,
			CauHoi cauHoi) {
		this.idThi = idThi;
		this.idch = idch;
		this.dapAnSv = dapAnSv;
		this.thuTuChon = thuTuChon;
		this.trangThaiXoa = trangThaiXoa;
		this.thi = thi;
		this.cauHoi = cauHoi;
	}
	// =================================getter and setter================//
	public Integer getIdThi() {
		return idThi;
	}

	public void setIdThi(Integer idThi) {
		this.idThi = idThi;
	}

	public Integer getIdch() {
		return idch;
	}

	public void setIdch(Integer idch) {
		this.idch = idch;
	}

	public Integer getDapAnSv() {
		return dapAnSv;
	}

	public void setDapAnSv(Integer dapAnSv) {
		this.dapAnSv = dapAnSv;
	}

	public Integer getThuTuChon() {
		return thuTuChon;
	}

	public void setThuTuChon(Integer thuTuChon) {
		this.thuTuChon = thuTuChon;
	}

	public boolean isTrangThaiXoa() {
		return trangThaiXoa;
	}

	public void setTrangThaiXoa(boolean trangThaiXoa) {
		this.trangThaiXoa = trangThaiXoa;
	}

	public Thi getThi() {
		return thi;
	}

	public void setThi(Thi thi) {
		this.thi = thi;
	}

	public CauHoi getCauHoi() {
		return cauHoi;
	}

	public void setCauHoi(CauHoi cauHoi) {
		this.cauHoi = cauHoi;
	}

	

}
