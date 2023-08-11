package com.junesworld.pharmacyrecommendation.pharmacy.repository;

import com.junesworld.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> { // <Entity, Pk type>

}
