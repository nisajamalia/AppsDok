package com.nisa.bismillah_dokter.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelKomunitas{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("komunitas")
	private List<KomunitasItem> komunitas;

	@SerializedName("sukses")
	private String sukses;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setKomunitas(List<KomunitasItem> komunitas){
		this.komunitas = komunitas;
	}

	public List<KomunitasItem> getKomunitas(){
		return komunitas;
	}

	public void setSukses(String sukses){
		this.sukses = sukses;
	}

	public String getSukses(){
		return sukses;
	}
}