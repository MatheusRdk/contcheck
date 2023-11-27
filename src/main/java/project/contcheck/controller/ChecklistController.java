package project.contcheck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.contcheck.domain.Checklist;
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
    public ResponseEntity<List<Checklist>> listAll(){
        return new ResponseEntity<>(checklistService.listAll(), HttpStatus.OK);
    }
}
