package com.gft.Casadeshow.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gft.Casadeshow.model.CadastrarCasadeShow;
import com.gft.Casadeshow.model.Eventos;
import com.gft.Casadeshow.repository.CadastrarCasadeShowRepository;
import com.gft.Casadeshow.repository.EventosRepository;

@Controller
public class HomeController {

	//Para funcionar com a imagem aparecendo , trocar as 4 letras dentro de users.
	private static String caminhoImagens = "/Users/HEMA/Documents/";
	
	@Autowired
	private EventosRepository eventosRepository;
	
	@Autowired
	private CadastrarCasadeShowRepository casadeShow;
	
	@RequestMapping("/home")
	public ModelAndView acesso() {
		ModelAndView mv = new ModelAndView("home");
		List<CadastrarCasadeShow> listCasadeShow = casadeShow.findAll();	
		mv.addObject("casadeShow", listCasadeShow);
		List<Eventos> todoseventos = eventosRepository.findAll();
		mv.addObject("todoseventos", todoseventos);
		return mv;
	}
	
	@GetMapping("eventos/imagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException{
		File imagemArquivo = new File(caminhoImagens + imagem);
		if(imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		
		return null;
	}
	
}
