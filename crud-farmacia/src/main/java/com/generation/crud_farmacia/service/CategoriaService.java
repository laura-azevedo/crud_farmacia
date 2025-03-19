package com.generation.crud_farmacia.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findall(){
		return categoriaRepository.findAll();
	}
	
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public List<Categoria> findByName(String nome) {
		return categoriaRepository.findAllByNomeContainingIgnoreCase(nome);
	}
	
	public Categoria create(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Optional<Categoria> update(Categoria categoria) {
		if(!categoriaRepository.findById(categoria.getId()).isPresent())
			return Optional.empty();
		
		boolean categoriaHomonima = categoriaRepository.findAllByNomeContainingIgnoreCase(categoria.getNome())
				.stream()
				.anyMatch(buscaCategoria -> !buscaCategoria.getId().equals(categoria.getId()));
		
		if (categoriaHomonima)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma categoria com esse nome.", null);
		
		categoria.setNome(categoria.getNome());
		
		return Optional.ofNullable(categoriaRepository.save(categoria));
	}
	
	public void delete(Long id) {
		if (!categoriaRepository.existsById(id))
			throw new NoSuchElementException("Categoria não encontrada.");
		
		categoriaRepository.deleteById(id);
		
	}
}
