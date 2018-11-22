package com.nisa.bismillah_dokter.Model;

import com.google.gson.annotations.SerializedName;


public class KomunitasItem{

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_komunitas")
	private String idKomunitas;

	@SerializedName("lon_komunitas")
	private String lonKomunitas;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("lat_komunitas")
	private String latKomunitas;

	@SerializedName("nm_komunitas")
	private String nmKomunitas;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setIdKomunitas(String idKomunitas){
		this.idKomunitas = idKomunitas;
	}

	public String getIdKomunitas(){
		return idKomunitas;
	}

	public void setLonKomunitas(String lonKomunitas){
		this.lonKomunitas = lonKomunitas;
	}

	public String getLonKomunitas(){
		return lonKomunitas;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setLatKomunitas(String latKomunitas){
		this.latKomunitas = latKomunitas;
	}

	public String getLatKomunitas(){
		return latKomunitas;
	}

	public void setNmKomunitas(String nmKomunitas){
		this.nmKomunitas = nmKomunitas;
	}

	public String getNmKomunitas(){
		return nmKomunitas;
	}
}