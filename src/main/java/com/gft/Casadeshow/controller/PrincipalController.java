package com.gft.Casadeshow.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.gft.Casadeshow.model.CadastrarCasadeShow;
import com.gft.Casadeshow.repository.CadastrarCasadeShowRepository;

@Controller
@RequestMapping("/casadeshow")
public class PrincipalController {

	private static final String CADASTRO_VIEW = "Casadeshow";

	@Autowired
	private CadastrarCasadeShowRepository cadastrarCasadeShowRepository;


	@RequestMapping()
	public ModelAndView casadeshow(CadastrarCasadeShow cadastrarCasadeShow) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		List<CadastrarCasadeShow> listCasadeShow = cadastrarCasadeShowRepository.findAll();
		mv.addObject("cadastrarCasadeShowRepository", listCasadeShow);
		mv.addObject(cadastrarCasadeShow);
		mv.addObject(new CadastrarCasadeShow());
		return mv;
	}

	@RequestMapping( method = RequestMethod.POST)
	public ModelAndView salvar(@Validated CadastrarCasadeShow cadastrarCasadeShow, Errors errors) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if (errors.hasErrors()) {
			List<CadastrarCasadeShow> listCasadeShow = cadastrarCasadeShowRepository.findAll();	
			mv.addObject("cadastrarCasadeShowRepository", listCasadeShow);
			return mv;
		}

		cadastrarCasadeShowRepository.save(cadastrarCasadeShow);

		mv.addObject("mensagem", "Casa de show salva com sucesso!");
		List<CadastrarCasadeShow> listCasadeShow = cadastrarCasadeShowRepository.findAll();
		mv.addObject("cadastrarCasadeShowRepository", listCasadeShow);
		return mv;

	}
	
	@RequestMapping("/editar/{id}")
	public ModelAndView edicao(@PathVariable Long id) {
		CadastrarCasadeShow cadastrarCasadeShow = cadastrarCasadeShowRepository.findById(id).get();
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		List<CadastrarCasadeShow> listCasadeShow = cadastrarCasadeShowRepository.findAll();
		mv.addObject("cadastrarCasadeShowRepository", listCasadeShow);
		mv.addObject(cadastrarCasadeShow);
		return mv;
		
	}
	
	@RequestMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		cadastrarCasadeShowRepository.deleteById(id);;
		return "redirect:/casadeshow";
		
	
	}
	
}
