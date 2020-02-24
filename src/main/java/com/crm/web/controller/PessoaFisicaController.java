package com.crm.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crm.config.CrmConfig;
import com.crm.web.page.PageWrapper;
import com.crm.model.PessoaFisica;
import com.crm.model.enumerate.Sexo;
import com.crm.repository.filtros.PessoaFisicaFiltro;
import com.crm.service.PessoaFisicaService;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
@RequestMapping(value="/pessoa_fisica")
public class PessoaFisicaController {
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	

	@RequestMapping(value="/lista_pessoa_fisica", method=RequestMethod.GET)
	public ModelAndView listPessoaFisica(PessoaFisicaFiltro pessoaFisicaFiltro,
										 HttpServletRequest httpServletRequest,
										 @RequestParam(value="size", required=false) Optional<Integer> size,
										 @RequestParam(value="page",required=false) Optional<Integer> page) {

		Pageable pageable = PageRequest.of(page.orElse(CrmConfig.INITIAL_PAGE), 
										   size.orElse(CrmConfig.INITIAL_PAGE_SIZE));
	
		PageWrapper<PessoaFisica> pagina = new PageWrapper<>(pessoaFisicaService.listaPessoaComPaginacao(pessoaFisicaFiltro, pageable), 
				                                             size.orElse(CrmConfig.INITIAL_PAGE_SIZE), 
				                                             httpServletRequest);
		
		ModelAndView mv = new ModelAndView("/pessoa_fisica/lista_pessoa_fisica");
		
		mv.addObject("pageSize", CrmConfig.PAGE_SIZES);
		mv.addObject("size", size.orElse(CrmConfig.INITIAL_PAGE_SIZE));
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@RequestMapping(value="/nova_pessoa_fisica", method=RequestMethod.GET)
	public ModelAndView newForm(PessoaFisica pessoaFisica) {
		ModelAndView mv = new ModelAndView("/pessoa_fisica/pessoa_fisica");
		mv.addObject("pessoa_fisica", pessoaFisica);
		return mv;
	}
	
	
	@RequestMapping(value="/buscar_pessoa_fisica/{id}", method=RequestMethod.GET)
	public ModelAndView searchPessoaFisica(@PathVariable("id") Long id) {
		PessoaFisica pessoaFisica = pessoaFisicaService.findPessoaFisicaById(id);
		return newForm(pessoaFisica);
	}
	
	@RequestMapping(value="/salvar_pessoa_fisica",method=RequestMethod.POST)
	public ModelAndView saveNewPessoaFisica(@Valid PessoaFisica pessoaFisica, BindingResult result, RedirectAttributes attr ) {
	    if (result.hasErrors()) {
	    	return newForm(pessoaFisica);
	    }
	    System.out.println("codigo da pessoa "+pessoaFisica.getPessoa().getId());
		pessoaFisicaService.save(pessoaFisica);
		
		attr.addFlashAttribute("success","Registro gravado com sucesso");
		return newForm(pessoaFisica);
	}
	
	@RequestMapping(value="/edit_pessoa_fisica",method=RequestMethod.POST)
	public ModelAndView editPessoaFisica(@Valid PessoaFisica pessoaFisica, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
	    	return newForm(pessoaFisica);
	    }
		pessoaFisicaService.save(pessoaFisica);
		attr.addFlashAttribute("success","Registro gravado com sucesso");
		return new ModelAndView("redirect:/pessoa_fisica/nova_pessoa_fisica");
	}
	
	@RequestMapping(value="/excluir_pessoa_fisica/{id}",method=RequestMethod.GET)
	public ModelAndView removePessoaFisicaById(@PathVariable("id") Long id) {
		PessoaFisica pessoaFisica = pessoaFisicaService.findPessoaFisicaById(id);
		ModelAndView mv = new ModelAndView("/pessoa_fisica/excluir_pessoa_fisica");
		mv.addObject("pessoaFisica", pessoaFisica);
		return mv;
	}
	
	
	@RequestMapping(value="/excluir_pessoa_fisica",method=RequestMethod.POST)
	public ModelAndView removePessoaFisica(PessoaFisica pessoaFisica, BindingResult result, RedirectAttributes attr) {
		pessoaFisicaService.remove(pessoaFisica);
		ModelAndView mv = new ModelAndView("/pessoa_fisica/excluir_pessoa_fisica");
		attr.addFlashAttribute("success","Registro excluido com sucesso!");
		return mv;
	}
	
    
	@RequestMapping(value={"/edit_pessoa_fisca",
			               "/salvar_pessoa_fisica",
			               "/excluir_pessoa_fisica"},method=RequestMethod.POST, params="action=cancelar")
	public String cancelar() {
		return "redirect:/pessoa_fisica/lista_pessoa_fisica";
	}
	
	@ModelAttribute("sexos")
	public Sexo[] getSexo() {
		return Sexo.values();
	}
	
	
	
	
	
}
