package com.doks.mypanel.repository;

import com.doks.mypanel.model.Permissoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissoesRepository extends JpaRepository<Permissoes, Long> {

}
