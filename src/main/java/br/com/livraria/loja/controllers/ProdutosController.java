package br.com.livraria.loja.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.livraria.loja.daos.ProdutoDAO;
import br.com.livraria.loja.infra.FileSaver;
import br.com.livraria.loja.models.Produto;
import br.com.livraria.loja.models.TipoPreco;
import br.com.livraria.loja.validacao.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new ProdutoValidation());
		
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto){
		ModelAndView modelAndView = new ModelAndView("produtos/form"); 
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView grava(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes){
	
		
		System.out.println(sumario.getOriginalFilename());
		if(result.hasErrors()){
			return form(produto);
		}
		String path = fileSaver.wirite("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		produtoDAO.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto Cadastrado com sucesso!");
		
		return new ModelAndView("redirect:produtos");
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
}

