package com.gymapplication.pass_service.service;

import com.gymapplication.pass_service.entityDbClubsAndPasses.Pass;
import com.gymapplication.pass_service.repositoryDbClubsAndPasses.PassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassServiceImpl implements PassService {
    private final PassRepository passRepository;

    public PassServiceImpl(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Override
    public List<Pass> findAll() {
        return passRepository.findAll();
    }

    @Override
    public void loadImgUrls(List<Pass> passes) {
        passes.forEach(pass -> pass.setImgUrl("/ps/img/pass" + pass.getId() + ".jpg"));
    }

}
