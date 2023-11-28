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
import project.contcheck.domain.Checklist;
import project.contcheck.dto.ChecklistPostRequestBody;
import project.contcheck.service.ChecklistService;

import java.util.List;

@RestController
@RequestMapping("checklist")
@Log4j2
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @GetMapping(path = "/all")
    @Operation(summary = "List all checklists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Checklist>> listAll() {
        return new ResponseEntity<>(checklistService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find a checklist by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<Checklist> findById(@PathVariable long id) {
        return ResponseEntity.ok(checklistService.findByIdOrThrowNotFoundException(id));
    }

    @GetMapping(path = "/tipo/{tipo}")
    @Operation(summary = "Find a checklist by tipo", description = "/tipo/tipo+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Checklist>> findByTipo(@RequestParam(required = false) String tipo) {
        return ResponseEntity.ok(checklistService.findByTipo(tipo));
    }

    @GetMapping(path = "/mes_ano/{mesAno}")
    @Operation(summary = "Find a checklist by mesAno", description = "/mes_ano/mesAno+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Checklist>> findByMesAno(@RequestParam(required = false) String mesAno) {
        return ResponseEntity.ok(checklistService.findByMesAno(mesAno));
    }

    @GetMapping(path = "/status/{status}")
    @Operation(summary = "Find a checklist by status", description = "/status/status+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Checklist>> findByStatus(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(checklistService.findByStatus(status));
    }

    @GetMapping(path = "/cnpj/{cnpj}")
    @Operation(summary = "Find a checklist by cnpj", description = "/cnpj/cnpj+here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<List<Checklist>> findByCnpj(@RequestParam(required = false) String cnpj) {
        return ResponseEntity.ok(checklistService.findChecklistByCnpj(cnpj));
    }

    @PostMapping
    @Operation(summary = "Create a new checklist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation, created", content = @Content)
    })
    public ResponseEntity<Checklist> save(@RequestBody ChecklistPostRequestBody checklistPostRequestBody) {
        return new ResponseEntity<>(checklistService.save(checklistPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deleteByCnpj/{cnpj}")
    @Operation(summary = "Delete all checklist by cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content)
    })
    public ResponseEntity<Void> deleteAllByCnpj(@RequestParam(required = false) String cnpj) {
        checklistService.deleteAllChecklistByCnpj(cnpj);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/deleteById/{id}")
    @Operation(summary = "Delete a checklist by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation, deleted"),
            @ApiResponse(responseCode = "400", description = "Checklist not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        checklistService.deleteChecklistById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
