package com.crm.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crm.config.CrmConfig;
import com.crm.web.page.PageWrapper;
import com.crm.model.Pessoa;
import com.crm.repository.filtros.PessoaFiltro;
import com.crm.service.PessoaService;
import com.crm.service.exceptions.EmailExistente;

@Controller
@RequestMapping(value="/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping(value="/lista_pessoa", method=RequestMethod.GET)
	public ModelAndView listPessoa(PessoaFiltro pessoaFiltro,
								   HttpServletRequest httpServletRequest,
								   @RequestParam(value="size", required=false) Optional<Integer> size,
			                       @RequestParam(value="page", required=false) Optional<Integer> page ) {
		
		
		Pageable pageable = PageRequest.of(page.orElse(CrmConfig.INITIAL_PAGE), 
				                           size.orElse(CrmConfig.INITIAL_PAGE_SIZE));
		
		PageWrapper<Pessoa> pagina = new PageWrapper<>(
				pessoaService.listaPessoaComPaginacao(pessoaFiltro, pageable),
				        size.orElse(CrmConfig.INITIAL_PAGE_SIZE), 
				        httpServletRequest);
		
		ModelAndView mv = new ModelAndView("/pessoa/lista_pessoa");
		
		mv.addObject("pageSizes",CrmConfig.PAGE_SIZES);
		mv.addObject("size", size.orElse(CrmConfig.INITIAL_PAGE_SIZE));
		mv.addObject("pagina", pagina);
	
		return mv;

	}
	
	/*
	 * Rotina para inclusão e alteração de um registro
	 */
	
	@RequestMapping(value="/nova_pessoa", method=RequestMethod.GET)
	public ModelAndView newForm(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView("/pessoa/pessoa");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	@RequestMapping(value="/buscar_pessoa/{id}", method=RequestMethod.GET)
	public ModelAndView searchPessoa(@PathVariable("id") Long id) {
		Pessoa pessoa = new Pessoa();
		pessoa = pessoaService.findPessoaById(id);
		return newForm(pessoa);
	}
	
	@RequestMapping(value="/salvar_pessoa", method=RequestMethod.POST)
	public ModelAndView saveNewPessoa(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return newForm(pessoa);
		}
		try {
			pessoaService.save(pessoa);
		} catch (EmailExistente e) {
	       result.rejectValue("email", e.getMessage());
	       return newForm(pessoa);
		}
		attr.addFlashAttribute("success", "Registro cadastrado com sucesso.");
		return new ModelAndView("redirect:/pessoa/nova_pessoa");
	}
	
	
	@RequestMapping(value="/edit_pessoa", method=RequestMethod.POST)
	public ModelAndView editPessoa(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return newForm(pessoa);
		}
		pessoaService.update(pessoa);
		attr.addFlashAttribute("success", "Registro cadastrado com sucesso.");
		return new ModelAndView("redirect:/pessoa/nova_pessoa");
	}
	
	
	/*
	 * Rotinas para exclusão do registro cadastro
	 */

	@RequestMapping(value="/excluir_pessoa/{id}", method=RequestMethod.GET)
	public ModelAndView removePessoaById(@PathVariable("id") Long id) {
    	Pessoa pessoa = new Pessoa();
		pessoa = pessoaService.findPessoaById(id);
		ModelAndView mv = new ModelAndView("/pessoa/excluir_pessoa");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
		
	@RequestMapping(value="/excluir_pessoa", method=RequestMethod.POST)
	public ModelAndView removePessoa(Pessoa pessoa) {
		pessoaService.remove(pessoa);
		return new ModelAndView("redirect:/pessoa/nova_pessoa");
	}
	
	
	@RequestMapping(value= {"/salvar_pessoa","/edit_pessoa","/excluir_pessoa"}, method=RequestMethod.POST, params="action=cancelar")
	public String cancelar() {
		return "redirect:/pessoa/lista_pessoa";
	}
	
	
	

}
