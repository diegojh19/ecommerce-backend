package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.SliderService;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.Slider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/admin/sliders")
@CrossOrigin(origins = "http://localhost:4200")
public class SliderController {
    private final SliderService sliderService;

    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }
    @PostMapping
    public ResponseEntity<Slider> save(@RequestParam("id") Integer id,
                                        @RequestParam("title") String title,
                                        @RequestParam("subtitle") String subtitle,
                                        @RequestParam("image") String image,
                                        @RequestParam(value = "images", required = false) MultipartFile multipartFile
    ) throws IOException {

        Slider slider = new Slider();
        slider.setId(id);
        slider.setTitle(title);
        slider.setSubtitle(subtitle);
        slider.setImage(image);


        return new ResponseEntity<>(sliderService.save(slider, multipartFile), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Iterable<Slider>> findAll(){
        return ResponseEntity.ok(sliderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slider> findById(@PathVariable Integer id){
        return ResponseEntity.ok(sliderService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        sliderService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
