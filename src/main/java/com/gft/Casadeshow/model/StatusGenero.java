package com.gft.Casadeshow.model;

public enum StatusGenero {

	ELETRONICA("Eletrônica"),
	FORRO("Forró"),
	FUNK("Funk"),
	PAGODE("Pagode"),
	POP("Pop"),
	ROCK("Rock"),
	SERTANEJO("Sertanejo"),
	OUTROS("Outros...");


	private String descricao;
	
	StatusGenero(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}



}
