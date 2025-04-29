package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.Slider;
import com.icodeap.ecommerce.backend.domain.port.ISliderRepository;
import com.icodeap.ecommerce.backend.infrastructure.mapper.CategoryMapper;
import com.icodeap.ecommerce.backend.infrastructure.mapper.SliderMapper;
import org.springframework.stereotype.Repository;

@Repository

public class SliderRepositoryImpl  implements ISliderRepository {

    private final ISliderCrudRepository iSliderCrudRepository;
    private final SliderMapper sliderMapper;

    public SliderRepositoryImpl(ISliderCrudRepository iSliderCrudRepository, SliderMapper sliderMapper) {
        this.iSliderCrudRepository = iSliderCrudRepository;
        this.sliderMapper = sliderMapper;
    }

    @Override
    public Slider save(Slider slider) {
        return sliderMapper.toSlider(iSliderCrudRepository.save(sliderMapper.toSliderEntity(slider)));
    }

    @Override
    public Slider update(Slider slider) {
        return sliderMapper.toSlider(iSliderCrudRepository.save(sliderMapper.toSliderEntity(slider)));
    }

    @Override
    public Iterable<Slider> findAll() {
        return sliderMapper.toSliderList(iSliderCrudRepository.findAll());
    }

    @Override
    public Slider findById(Integer id) {
        return sliderMapper.toSlider(iSliderCrudRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Slider con id "+id+ " no existe")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        iSliderCrudRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Slider con id"+id+ " no existe")
        );
        iSliderCrudRepository.deleteById(id);

    }
}
