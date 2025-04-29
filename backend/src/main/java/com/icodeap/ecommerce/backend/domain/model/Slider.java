package com.icodeap.ecommerce.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slider {
    private Integer id;
    private String title;
    private String subtitle;
    private String image;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
