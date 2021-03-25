package br.gov.sp.etecsebrae.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.etecsebrae.dto.LoginDto;
import br.gov.sp.etecsebrae.entity.LoginEntity;
import br.gov.sp.etecsebrae.repository.LoginRepository;

@RestController
@RequestMapping({ "/login" })
public class LoginController {

	@Autowired
	LoginRepository repository;

	@GetMapping(path = { "", "/get_all", "/all" })
	public ResponseEntity<?> findAll() {
		List<LoginEntity> list = repository.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(path = { "/{id}", "/get_id/{id}", "/id/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(repository.findById(id).orElse(null));
	}

	@GetMapping(path = { "/get/{login}", "/get_login/{login}", "/login/{login}" })
	public ResponseEntity<?> findByLogin(@PathVariable String login) {
		return ResponseEntity.ok(repository.findByLogin(login).orElse(null));
	}

	@PostMapping(path = { "", "/add", "/create" }, consumes = "application/json", produces = "application/json")
	public LoginEntity create(@RequestBody LoginDto dto) {
		LoginEntity entity = new LoginEntity(dto.getLogin(), dto.getPassword(), dto.getTipo(), dto.getNome());
		return repository.save(entity);
	}

	@PutMapping(path = { "", "/edit", "/update" }, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody LoginDto dto) {
		return repository.findByLogin(dto.getLogin()).map(record -> {
			record.setPassword(dto.getPassword());
			record.setTipo(dto.getTipo());
			record.setNome(dto.getNome());
			LoginEntity updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "", "/remove", "/delete" }, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> delete(@RequestBody LoginDto dto) {
		return repository.findByLogin(dto.getLogin()).map(record -> {
			repository.deleteById(record.getId());
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
