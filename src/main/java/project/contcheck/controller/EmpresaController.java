package project.contcheck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.contcheck.domain.Empresa;
import project.contcheck.dto.EmpresaNewCnpjRequestBody;
import project.contcheck.dto.EmpresaPostRequestBody;
import project.contcheck.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("empresa")
@Log4j2
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping(path = "/all")
    @Operation(summary = "List all empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Empresa>> listAll() {
        return new ResponseEntity<>(empresaService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/find") // /empresa/find?name=empresaName
    @Operation(summary = "Find an empresa by name", description = "/empresa/find?name=empresa+name+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Empresa>> findByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(empresaService.findByName(name));
    }

    @GetMapping(path = "/{cnpj}")
    @Operation(summary = "Find an empresa by cnpj", description = "/empresa/cnpj+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Empresa>> findByCnpj(@RequestParam(required = false) String cnpj) {
        return ResponseEntity.ok(empresaService.findEmpresaByCnpj(cnpj));
    }

    @PostMapping
    @Operation(summary = "Create a new empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation, created", content = @Content),
            @ApiResponse(responseCode = "500", description = "The empresa must have a name", content = @Content)
    })
    public ResponseEntity<Empresa> save(@RequestBody EmpresaPostRequestBody empresaPostRequestBody) {
        return new ResponseEntity<>(empresaService.save(empresaPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{cnpj}")
    @Operation(summary = "Delete empresa by cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<Void> deleteByCnpj(@RequestParam(required = false) String cnpj) {
        empresaService.deleteByCnpj(cnpj);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update empresa nome by cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation, updated"),
    })
    public ResponseEntity<Void> updateNomeByCnpj(@RequestBody EmpresaPostRequestBody empresaPostRequestBody) {
        empresaService.updateNomePorCnpj(empresaPostRequestBody.cnpj(), empresaPostRequestBody.nome());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update empresa cnpj by cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation, updated"),
    })
    public ResponseEntity<Void> updateCnpjByCnpj(@RequestBody EmpresaNewCnpjRequestBody empresaNewCnpjRequestBody) {
        empresaService.updateCnpj(empresaNewCnpjRequestBody.cnpjNovo(), empresaNewCnpjRequestBody.cnpjAntigo());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
