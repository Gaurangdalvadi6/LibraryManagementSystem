package com.manage.library.repository;

import com.manage.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Long> {

    Borrower findByUsername(String currentUsername);
}
