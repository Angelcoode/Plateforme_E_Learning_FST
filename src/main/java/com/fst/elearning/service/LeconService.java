package com.fst.elearning.service;

import com.fst.elearning.entity.Lecon;
import com.fst.elearning.repository.LeconRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeconService {

    private final LeconRepository leconRepository;

    public LeconService(LeconRepository leconRepository) {
        this.leconRepository = leconRepository;
    }

    public List<Lecon> findAll() {
        return leconRepository.findAll();
    }

    public Optional<Lecon> findById(Long id) {
        return leconRepository.findById(id);
    }

    public Lecon save(Lecon lecon) {
        return leconRepository.save(lecon);
    }

    public void deleteById(Long id) {
        leconRepository.deleteById(id);
    }
}
