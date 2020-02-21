package com.gft.Casadeshow.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "cadastrarCasadeShow")
public class CadastrarCasadeShow{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Nome da casa de show é obrigatória!")
	@Size (max=60, message = "O nome da sua casa de show não pode conter mais de 60 caracteres!")
	private String nomeCasa;
	
	@NotEmpty(message="Endereço é obrigatório!")
	private String endereco;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "casadeShow1")
	private List<Eventos> eventos;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeCasa() {
		return nomeCasa;
	}
	public void setNomeCasa(String nomeCasa) {
		this.nomeCasa = nomeCasa;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public List<Eventos> getEventos() {
		return eventos;
	}
	public void setEventos(List<Eventos> eventos) {
		this.eventos = eventos;
	}
	

	
	
}
