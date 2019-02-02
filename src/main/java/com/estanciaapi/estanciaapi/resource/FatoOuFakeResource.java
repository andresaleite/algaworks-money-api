package com.estanciaapi.estanciaapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estanciaapi.estanciaapi.evento.RecursoCriadoEvent;
import com.estanciaapi.estanciaapi.exceptionhandler.EstanciaApiExceptionHandler.Erro;
import com.estanciaapi.estanciaapi.model.FatoOuFake;
import com.estanciaapi.estanciaapi.repository.FatoOuFakeRepository;
import com.estanciaapi.estanciaapi.repository.filter.FatoOuFakeFilter;
import com.estanciaapi.estanciaapi.repository.projection.ResumoFatoOuFake;
import com.estanciaapi.estanciaapi.service.FatoOuFakeService;
import com.estanciaapi.estanciaapi.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/fatoOuFakes")
public class FatoOuFakeResource {

	@Autowired
	private FatoOuFakeRepository fatoOuFakeRepository;
	
	@Autowired
	private FatoOuFakeService fatoOuFakeService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<FatoOuFake> pesquisar(FatoOuFakeFilter fatoOuFakeFilter, Pageable pageable) {
		return fatoOuFakeRepository.filtrar(fatoOuFakeFilter, pageable);
	}
	
	@GetMapping(params="resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoFatoOuFake> resumir(FatoOuFakeFilter fatoOuFakeFilter, Pageable pageable) {
		return fatoOuFakeRepository.resumir(fatoOuFakeFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<FatoOuFake> buscarPeloCodigo(@PathVariable Long codigo) {
		FatoOuFake fatoOuFake = fatoOuFakeRepository.findOne(codigo);
		return fatoOuFake != null ? ResponseEntity.ok(fatoOuFake) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<FatoOuFake> criar(@Valid @RequestBody FatoOuFake fatoOuFake, HttpServletResponse response) {
		FatoOuFake fatoOuFakeSalvo = fatoOuFakeService.salvar(fatoOuFake);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fatoOuFakeSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fatoOuFakeSalvo);
	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		fatoOuFakeRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<FatoOuFake> alterar(@PathVariable Long codigo, @Valid @RequestBody FatoOuFake fatoOuFake) {
		FatoOuFake fatoOuFakeSalvo = fatoOuFakeService.alterar(codigo, fatoOuFake);
		return ResponseEntity.ok(fatoOuFakeSalvo);
	}
	
}
