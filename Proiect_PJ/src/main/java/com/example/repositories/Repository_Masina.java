package com.example.repositories;

import com.example.entities.Masina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

//Repository = legatura cu my sql
public interface Repository_Masina extends JpaRepository<Masina, String> {
    @Query("SELECT c FROM Masina c WHERE " +
            "(:nrInmatriculare IS NULL OR c.nrInmatriculare = :nrInmatriculare) AND " +
            "(:tipCombustibil IS NULL OR c.tipCombustibil = :tipCombustibil) AND " +
            "(:anFabricatie IS NULL OR c.anFabricatie = :anFabricatie)")
    //fuctie din JPQL - nu trebuie sa se aplice pentru toate filtrele (doar care nu-s NULL)
    List<Masina> findCarsByCriteria(@Param("nrInmatriculare") String nrInmatriculare,
                                    @Param("tipCombustibil") String tipCombustibil,
                                    @Param("anFabricatie") Integer anFabricatie);
}
//ex: :nrInmatriculare - va primi valoare la rularea interogarii
