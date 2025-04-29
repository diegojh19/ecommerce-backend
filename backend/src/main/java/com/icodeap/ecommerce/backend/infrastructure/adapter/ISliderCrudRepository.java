package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.infrastructure.Entity.SliderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ISliderCrudRepository extends CrudRepository<SliderEntity,Integer> {
}
