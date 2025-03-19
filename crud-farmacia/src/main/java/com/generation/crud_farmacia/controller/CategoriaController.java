package com.generation.crud_farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.repository.CategoriaRepository;
import com.generation.crud_farmacia.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		if (!categoriaService.findById(id).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");

		return ResponseEntity.ok(categoriaService.findById(id).get());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<?> buscarCategoriaPorNome(@PathVariable String nome) {
		
		List<Categoria> categorias = categoriaService.findByName(nome);
		
		if (categorias.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
		}
		
		return ResponseEntity.ok(categorias);
	}

	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(categoria));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Categoria categoria) {
		if (!categoriaService.findById(id).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
		
		categoria.setId(id);

		return ResponseEntity.ok(categoriaService.update(categoria));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		if (!categoriaRepository.findById(id).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");

		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
