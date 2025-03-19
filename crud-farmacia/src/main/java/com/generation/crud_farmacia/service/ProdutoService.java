package com.generation.crud_farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.crud_farmacia.model.Produto;
import com.generation.crud_farmacia.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
    @Autowired
    private CategoriaService categoriaService;


	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> findById(Long id) {
		return produtoRepository.findById(id);
	}

	public List<Produto> findByName(String nome) {
		return produtoRepository.findAllByNomeContainingIgnoreCase(nome);
	}

	public Produto create(Produto produto) {
		categoriaService.findById(produto.getCategoria().getId())
			.orElseThrow(
					() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe."));
		
		return produtoRepository.save(produto);
	}

	public Produto update(Produto produto) {
		
		 if (!produtoRepository.existsById(produto.getId())) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");
		    }

        categoriaService.findById(produto.getCategoria().getId())
        	.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe."));

        return produtoRepository.save(produto);
	}

	public void delete(Long id) {

		if (!produtoRepository.existsById(id))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");

		produtoRepository.deleteById(id);
	}
}
