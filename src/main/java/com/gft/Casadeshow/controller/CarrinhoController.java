package com.gft.Casadeshow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gft.Casadeshow.model.CadastrarCasadeShow;
import com.gft.Casadeshow.model.Compra;
import com.gft.Casadeshow.model.Eventos;
import com.gft.Casadeshow.model.ItensCompra;
import com.gft.Casadeshow.repository.CadastrarCasadeShowRepository;
import com.gft.Casadeshow.repository.CompraRepository;
import com.gft.Casadeshow.repository.EventosRepository;
import com.gft.Casadeshow.repository.ItensCompraRepository;

@Controller
public class CarrinhoController {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();

	private Compra compra = new Compra();

	@Autowired
	private EventosRepository eventosRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ItensCompraRepository itenscompraRepository;
	
	@Autowired
	private CadastrarCasadeShowRepository casadeShow;

	private void calcularTotal() {
		compra.setValorTotal(0.);
		for (ItensCompra it : itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	@RequestMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mv = new ModelAndView("Carrinho");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@RequestMapping("/finalizar")
	public ModelAndView finalizarCompra() {
		ModelAndView mv = new ModelAndView("Finalizar");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(String formaPagamento) {
		ModelAndView mv = new ModelAndView("mensagemFinalizou");
		compra.setFormaPagamento(formaPagamento);
		compraRepository.saveAndFlush(compra);

		for (ItensCompra c : itensCompra) {
			c.setCompra(compra);
			itenscompraRepository.saveAndFlush(c);
		}
		itensCompra = new ArrayList<>();
		compra = new Compra();
		return mv;
	}

	@RequestMapping("/adicionarCarrinho/{id}")
	public ModelAndView adicionarCarrinho(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("Carrinho");

		Optional<Eventos> prod = eventosRepository.findById(id);
		Eventos eventos = prod.get();

		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getEvento().getId().equals(eventos.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				break;
			}
		}
		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setEvento(eventos);
			item.setValorUnitario(eventos.getValor());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
			calcularTotal();
			itensCompra.add(item);
		}
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@RequestMapping("/alterarQuantidade/{id}/{acao}")
	public ModelAndView alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
		ModelAndView mv = new ModelAndView("Carrinho");

		for (ItensCompra it : itensCompra) {
			if (it.getEvento().getId().equals(id)) {
				if (acao.equals(1)) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					calcularTotal();
				} else if (acao == 0) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					calcularTotal();
				}
				break;

			}
		}
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@RequestMapping("/removerEvento/{id}")
	public ModelAndView removerEventoCarrinho(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("Carrinho");

		for (ItensCompra it : itensCompra) {
			if (it.getEvento().getId().equals(id)) {
				itensCompra.remove(it);
				calcularTotal();
				break;

			}
		}
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}
	@RequestMapping("/historico")
	public ModelAndView historico() {
		ModelAndView mv = new ModelAndView("Historico");
	List<ItensCompra> listitens = itenscompraRepository.findAll();
	mv.addObject("todositens", listitens);
	List<Compra> listcompra = compraRepository.findAll();
	mv.addObject("todoscompra", listcompra);
	return mv;
	
	}

}
