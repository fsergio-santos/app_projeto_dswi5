package com.crm.web.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import com.crm.domain.service.exceptions.EntidadeEmUsoException;
import com.crm.domain.service.exceptions.EntidadeNaoEncontradaException;
import com.crm.domain.service.exceptions.NegocioException;
import com.crm.domain.service.exceptions.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class CrmExceptionHandler extends ResponseEntityExceptionHandler {
    
	// para tratar erros não esperados - não tratados....
	
	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
		= "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
				+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired  //AULA 11 - 3
	private MessageSource messageSource;
	
	/*
	 * Tratando os erros nas propriedades quando estás estão 
	 * sendo atualizadas de forma individual pelo comando PATCH
	 * onde as propriedades estão marcadas no bean validation
	 */
	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}
	
	
	/*
	 * 3 aula sobre validação a partir do dos dados - propriedades
	 * com a notação do bean validation.  
	 */
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Fields> fields = bindingResult.getFieldErrors()
	    								   .stream()
	    							       .map(fieldError -> {
	    							    	   		String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			    							  	   	Fields field = new Fields();
			    							   	   	field.setNome(fieldError.getField());
			    							   	   	field.setUserMessage(message);
			    							   	   	return field;
	    							           })
	    							       .collect(Collectors.toList());
	        
	    Problem problem = createBuilder(status, problemType, detail)
	        .addUserMessage(detail)
	        .addListFields(fields)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	/*
	 * 
	 * Tratar demais exception não tratadas até aqui... no desenvolvimento. 
	 * 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/*
	 * exception para tratar quando uma url não existe, ou não é encontrada. 
	 * exemplo. http://localhost:8080/usuariooooos/buscar/1
	 * 
	 * essa url não existe..
	 * para funcionar devemos configurar no application.properties duas propriedades. 
	 * 
	 * spring.mvc.throw-exception-if-no-handler-found=true
	 * spring.resources.add-mappings=false - só que desabilita o recurso do spring 
	 * para fornecer dados estaticos de uma aplicação normal. (web)
	 * 
	 * @Override
	 * protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	 * 
	 * fazer configuração no ProjetoApplication.java - controlador inicial do sistema;
	 * sua localização é o pacote base com.crm - nesse exemplo.  
	 * 
	 */
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
				ex.getRequestURL());
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	/*
	 *  passando argumento invalido na url 
	 *  http://localhost:8080/usuario/buscar/aaaaaaa  ---> exemplo...
	 *  
	  1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException
	  2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente
	  3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
	     é uma instância de MethodArgumentTypeMismatchException
	  4. Se for, chamamos um método especialista em tratar esse tipo de exception
	  5. Poderíamos fazer tudo dentro de handleTypeMismatch, mas preferi separar em outro método
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	/*
	 * método chamadpo para tratar tipo inválido na url 
	 * a chamada é feita pela handleTypeMIsmatch..
	 * 
	 */
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	/*
	 *  1-) Método que verifica se o conteúdo passado pelo usuário está correto. 
	 *      através da excessão "InvalidFormatException" chamando a função handleInvalidFormat
	 *   
	 *  2 - Método que verifica se o usuário passaou uma propriedade que não existe na entidade,
	 *  para verificar essa possibilidade é necessário adicionar no aqruivo application.properties
	 *  o seguinte argumento "spring.jackson.deserialization.fail-on-unknown-properties=true" para 
	 *  verificar se a propriedade existe ou não.
	 *  Ainda para verificar se a propriendade está marcada com @JsonIgnore devemos colocar o argumento 
	 *  "spring.jackson.deserialization.fail-on-ignored-properties=true".
	 *  chamando handlePropertyBinding
	 *   
	 *  3-) trata também atualizações do comando PATCH
	 * 
	 */
	@Override //aula 4 e 5- erro de sintexe no envio da mensagem ---
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);  //id:"aaa"
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	/*
	 * A função é chamada para verificar se a propriedade não existe e 
	 * se ela está marcada com @JsonIgnore.
	 */
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
    
	/*
	 * Para tratar se o conteúdo da propriedade passada está correto ou não
	 */
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	
	/*
	 * começa as aulas com tratamento de erros globais no sistema
	 * 1 entidade não encontrada
	 * 2 Entidade em uso 
	 * tratando se a entidade existe no banco de dados. 
	 * 
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/*
	 * trata se a entidade está sendo usada em outro registro. 
	 * exemplo Usuario possui roles cadastradas. quando tenta
	 * apagar uma role que está atribuida a um usuário. 
	 */
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	/*
	 * Exception para tratar quando uma regra de negócio está sendo violada. 
	 * Quando querer uma mensagem genérica. 
	 */
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problem problem = createBuilder(status, problemType, detail)
				.addUserMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	//aula sobre validação de um campo através do patch
	/*
	 *  exception para tratar que há um erro no atributo
	 *  quando atualizado pelo path, através do bean validation
	 */
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    List<Fields> objects = (List<Fields>) bindingResult.getAllErrors().stream()
	    		.map(fieldError -> {
	    			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
	    			Fields field = new Fields();
	    			if (fieldError instanceof FieldError) {
	    				field.setNome(((FieldError) fieldError).getField());
	    			}
			   	   	field.setUserMessage(message);
			   	   	return field;
        		})
	    		.collect(Collectors.toList());
	    
	    Problem problem = createBuilder(status, problemType, detail)
	        .addUserMessage(detail)
	        .addListFields(objects)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	/*
	 * sobreescrevendo o método da classe pai para todoas as exceptions não tratada no sistema
	 * ResponseEntityExceptionHandler
	 * 3
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
				.addTimestamp(LocalDateTime.now())
				.addTitle(status.getReasonPhrase())
				.addStatus(status.value())
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.addTimestamp(LocalDateTime.now())
				.addTitle((String) body)
				.addStatus(status.value())
				.addUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	/*
	 * criando builder padrão conforme RFC 7807
	 */
	
	private Problem.Builder createBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
					  .addTimestamp(LocalDateTime.now())
					  .addStatus(status.value())
				      .addType(problemType.getUri())
					  .addTitle(problemType.getTitle())
					  .addDetail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
}
