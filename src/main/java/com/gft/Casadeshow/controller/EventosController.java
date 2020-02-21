package com.gft.Casadeshow.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gft.Casadeshow.model.CadastrarCasadeShow;
import com.gft.Casadeshow.model.Eventos;
import com.gft.Casadeshow.model.StatusGenero;
import com.gft.Casadeshow.repository.CadastrarCasadeShowRepository;
import com.gft.Casadeshow.repository.EventosRepository;

@Controller
@RequestMapping("/eventos")
public class EventosController {

	private static final String EVENTO_VIEW = "Eventos";
	
	//Para funcionar com a imagem aparecendo , trocar as 4 letras dentro de users.
	private static String caminhoImagens = "/Users/GIJF/Documents/";
	
	@Autowired
	private EventosRepository eventosRepository;
	
	@Autowired
	private CadastrarCasadeShowRepository casadeShow;
	
	@RequestMapping
	public ModelAndView eventos() {
		ModelAndView mv = new ModelAndView(EVENTO_VIEW);
		List<CadastrarCasadeShow> listnomeCasadeShow = casadeShow.findAll();
		mv.addObject("casadeShow", listnomeCasadeShow);
		List<Eventos> todoseventos = eventosRepository.findAll();
		mv.addObject("todoseventos", todoseventos);
		mv.addObject(new Eventos());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
		public ModelAndView salvar(@Validated Eventos eventos, Errors errors 
			,@RequestParam("arquivo") MultipartFile arquivo ) {
		ModelAndView mv = new ModelAndView(EVENTO_VIEW);
		if (errors.hasErrors()) {
			List<CadastrarCasadeShow> listnomeCasadeShow = casadeShow.findAll();
			mv.addObject("casadeShow", listnomeCasadeShow);	
			List<Eventos> todoseventos = eventosRepository.findAll();
			mv.addObject("todoseventos", todoseventos);
			return mv;
		}
		
		eventosRepository.save(eventos);
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens+String.valueOf(eventos.getId())+arquivo.getOriginalFilename());
				Files.write(caminho,bytes);
				
				eventos.setNomeImagem(String.valueOf(eventos.getId())+arquivo.getOriginalFilename());
				eventosRepository.save(eventos);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		mv.addObject("mensagem", "Casa de show salva com sucesso!");
		List<CadastrarCasadeShow> listnomeCasadeShow = casadeShow.findAll();
		mv.addObject("casadeShow", listnomeCasadeShow);
		List<Eventos> todoseventos = eventosRepository.findAll();
		mv.addObject("todoseventos", todoseventos);
		return mv;
	}
	@RequestMapping("/editar/{id}")
	public ModelAndView edicao(@PathVariable Long id ) {
		Eventos eventos = eventosRepository.findById(id).get();
		ModelAndView mv = new ModelAndView(EVENTO_VIEW);
		List<CadastrarCasadeShow> listnomeCasadeShow = casadeShow.findAll();
		mv.addObject("casadeShow", listnomeCasadeShow);	
		List<Eventos> todoseventos = eventosRepository.findAll();
		mv.addObject("todoseventos", todoseventos);
		mv.addObject(eventos);
		return mv;
		
	}
	
	@RequestMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		eventosRepository.deleteById(id);
		return "redirect:/eventos";
	}
	
	@ModelAttribute
	public List<StatusGenero> todosStatusGenero(){
		return Arrays.asList(StatusGenero.values());
		
	}
	
	
}

