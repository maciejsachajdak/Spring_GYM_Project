package com.gymapplication.pass_service.service;

import com.gymapplication.pass_service.entityDbClubsAndPasses.Pass;

import java.util.List;

public interface PassService {
    List<Pass> findAll();

    void loadImgUrls(List<Pass> passes);
}
