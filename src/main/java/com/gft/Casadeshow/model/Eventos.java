package com.gft.Casadeshow.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Eventos {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome do evento é obrigatório!")
	@Size(max = 60,message = "O nome do evento não pode conter mais de 60 caracteres")
	private String nomeEvento;
	
	@NotNull(message = "Capacidade é obrigatória!")
	private Integer capacidade;
	
	@NotNull(message = "Data do evento é obrigatória!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataEvento;
	
	
	@NotNull(message = "Valor é obrigatório!")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999999.99", message = "Valor não pode ser maior que 9.999.999.999.99")
	@NumberFormat(pattern = "#,##0.00")
	private Double valor;
	
	
	@ManyToOne
	@NotNull(message = "Casa de Show é obrigatória!")
	private CadastrarCasadeShow casadeShow1;
	
	@Enumerated(EnumType.STRING)
	private StatusGenero status;
	
	
	private String nomeImagem;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public CadastrarCasadeShow getCasadeShow1() {
		return casadeShow1;
	}

	public void setCasadeShow1(CadastrarCasadeShow casadeShow) {
		this.casadeShow1 = casadeShow;
	}
	public StatusGenero getStatus() {
		return status;
	}
	
	public void setStatus(StatusGenero status) {
		this.status = status;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	





	
	
	
}
