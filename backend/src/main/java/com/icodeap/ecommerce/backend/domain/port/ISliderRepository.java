package com.icodeap.ecommerce.backend.domain.port;

import com.icodeap.ecommerce.backend.domain.model.Slider;

public interface ISliderRepository {
    Slider save(Slider slider);
    Slider update(Slider slider);
    Iterable<Slider> findAll();
    Slider findById(Integer id);
    void deleteById(Integer  id);
}
