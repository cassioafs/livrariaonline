package br.com.livraria.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.livraria.loja.daos.ProdutoDAO;
import br.com.livraria.loja.models.Produto;
import br.com.livraria.loja.models.TipoPreco;

@Controller
public class ProdutosController {

	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	
	@RequestMapping("/produtos/form")
	public ModelAndView form(){
		ModelAndView modelAndView = new ModelAndView("produtos/form"); 
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping("produtos")
	public String grava(Produto produto){
		
		System.out.println(produto);
		produtoDAO.gravar(produto);
		
		return "produtos/ok";
		
	}
}

