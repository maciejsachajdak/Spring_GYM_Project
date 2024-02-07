package com.gymapplication.pass_service.repositoryDbClubsAndPasses;

import com.gymapplication.pass_service.entityDbClubsAndPasses.Pass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassRepository extends JpaRepository<Pass,Long> {
}
