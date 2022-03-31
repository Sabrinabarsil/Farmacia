package com.generation.farmacia.controller;


import java.util.List;

import javax.validation.Valid;

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

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.CategoriaRepository;

@RestController // Ele informa ao spring que esta classe é um endpoint.
@RequestMapping("/categorias") // Define a rota para qual esse endpoint responde... dentro dos () vai parametro em forma de String
@CrossOrigin(origins = "*", allowedHeaders = "*") // Compartilhamento de recursos com origens diferentes
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	/*
	 * fornece controle sobre onde e como a ligação entre os beans deve ser
	 * realizada. Pode ser usado para em métodos setter, no construtor, em uma
	 * propriedade ou métodos com nomes arbitrários e / ou vários argumentos
	 */
	
	@GetMapping // uma notação composta que atua como um atalho para @RequestMapping (method = RequestMethod. GET)
	public ResponseEntity<List <Categoria>> getAll(){
		return ResponseEntity.ok(categoriaRepository.findAll());
		/* ResponseEntity: significa representar toda a resposta HTTP. Você pode
		 controlar qualquer coisa que aconteça: código de status, cabeçalhos e corpo.*/

	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return categoriaRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp)) //Lambda
			.orElse(ResponseEntity.notFound().build());	
		/* Função Lambda: permite definir uma interface funcional (novamente, um método abstrato)
		 * que o compilador identifica pela estrutura. O compilador pode determinar 
		 * a interface funcional representada a partir de sua posição. 
		 * O tipo de uma expressão lambda é o da interface funcional associada. */
		}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Categoria>> getByCategoria(@PathVariable String nome){
		return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
	}												
	
	@PostMapping
	public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));	
	}
	
	@PutMapping
	public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria) {
					
		return categoriaRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
		
		return categoriaRepository.findById(id)
				.map(resposta -> {
					categoriaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}