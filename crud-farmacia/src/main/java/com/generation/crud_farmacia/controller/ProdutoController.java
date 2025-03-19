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

import com.generation.crud_farmacia.model.Produto;
import com.generation.crud_farmacia.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> findAll() {
		return ResponseEntity.ok(produtoService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		if (!produtoService.findById(id).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");

		return ResponseEntity.ok(produtoService.findById(id).get());
	}

	@GetMapping("/nome/{nome}")

	public ResponseEntity<?> findByName(@PathVariable String nome) {
		List<Produto> produtos = produtoService.findByName(nome);

		if (produtos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");

		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<Produto> create(@Valid @RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.create(produto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Produto produto) {
		if (!produtoService.findById(id).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
		
	    produto.setId(id);
		return ResponseEntity.ok(produtoService.update(produto));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		produtoService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
