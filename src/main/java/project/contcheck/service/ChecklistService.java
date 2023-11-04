package project.contcheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.contcheck.domain.Checklist;
import project.contcheck.repositorys.ChecklistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;

    public List<Checklist> listAll(){
        return checklistRepository.findAll();
    }
}
