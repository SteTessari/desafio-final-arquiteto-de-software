package com.desafio.api.controller;

import com.desafio.api.model.Cliente;
import com.desafio.api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
    })
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodosOrdenadosPorNome();
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar clientes por nome", description = "Retorna clientes cujo nome contém a string fornecida (busca case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
    })
    public ResponseEntity<List<Cliente>> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do cliente", required = true)
            @PathVariable String nome) {
        List<Cliente> clientes = clienteService.buscarPorNomeParcial(nome);
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Retorna um cliente específico baseado no email fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorEmail(
            @Parameter(description = "Email do cliente", required = true)
            @PathVariable String email) {
        Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/contar")
    @Operation(summary = "Contar clientes", description = "Retorna o número total de clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contarClientes() {
        long total = clienteService.contarClientes();
        Map<String, Long> response = new HashMap<>();
        response.put("total", total);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recentes")
    @Operation(summary = "Buscar clientes recentes", description = "Retorna os clientes mais recentemente cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes recentes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
    })
    public ResponseEntity<Page<Cliente>> buscarClientesRecentes(
            @Parameter(description = "Número máximo de clientes a retornar (padrão: 10)")
            @PageableDefault Pageable pageable) {
        Page<Cliente> clientes = clienteService.buscarClientesRecentes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Email já está em uso")
    })
    public ResponseEntity<?> criarCliente(
            @Parameter(description = "Dados do cliente a ser criado", required = true)
            @Valid @RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("erro", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email já está em uso")
    })
    public ResponseEntity<?> atualizarCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do cliente", required = true)
            @Valid @RequestBody Cliente cliente) {
        try {
            Optional<Cliente> clienteAtualizado = clienteService.atualizar(id, cliente);
            return clienteAtualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("erro", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletarCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        boolean deletado = clienteService.deletar(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/existe")
    @Operation(summary = "Verificar se cliente existe", description = "Verifica se um cliente existe baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    })
    public ResponseEntity<Map<String, Boolean>> verificarExistencia(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        boolean existe = clienteService.existePorId(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        return ResponseEntity.ok(response);
    }
}

