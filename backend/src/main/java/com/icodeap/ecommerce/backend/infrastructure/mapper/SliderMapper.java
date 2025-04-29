package com.icodeap.ecommerce.backend.infrastructure.mapper;

import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.Slider;
import com.icodeap.ecommerce.backend.infrastructure.Entity.CategoryEntity;
import com.icodeap.ecommerce.backend.infrastructure.Entity.SliderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface SliderMapper {

    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "title", target = "title"),
                    @Mapping(source = "subtitle", target = "subtitle"),
                    @Mapping(source = "image", target = "image"),
                    @Mapping(source = "dateCreated", target = "dateCreated"),
                    @Mapping(source = "dateUpdated", target = "dateUpdated")
            }
    )
    Slider toSlider(SliderEntity sliderEntity);
    Iterable<Slider> toSliderList(Iterable<SliderEntity> sliderEntities);

    @InheritInverseConfiguration
    SliderEntity toSliderEntity(Slider slider);
}
