package project.contcheck.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import project.contcheck.domain.Checklist;
import project.contcheck.exceptions.ChecklistNotFoundException;
import project.contcheck.repositorys.ChecklistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;

    public List<Checklist> listAll(){
        return checklistRepository.findAll();
    }

    public Checklist findByIdOrThrowNotFoundException(long id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new ChecklistNotFoundException("Checklist not found."));
    }
}
