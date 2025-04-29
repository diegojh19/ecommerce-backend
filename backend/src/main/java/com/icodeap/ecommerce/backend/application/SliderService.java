package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.Slider;
import com.icodeap.ecommerce.backend.domain.port.ICategoryRepository;
import com.icodeap.ecommerce.backend.domain.port.ISliderRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class SliderService {
    private final ISliderRepository iSliderRepository;

    private final UploadFile uploadFile;

    public SliderService(ISliderRepository iSliderRepository, UploadFile uploadFile) {
        this.iSliderRepository = iSliderRepository;
        this.uploadFile = uploadFile;
    }

    public Slider save(Slider slider,  MultipartFile multipartFile) throws IOException {
        if(slider.getId()!=0){
            if(multipartFile==null) {
                slider.setImage(slider.getImage());
            }else{
                String nameFile = slider.getImage().substring(29);
                if (!nameFile.equals("default.jpg")){
                    uploadFile.delete(nameFile);

                }
                slider.setImage(uploadFile.upload(multipartFile));
            }
        }else{
            slider.setImage(uploadFile.upload(multipartFile));
        }
        return iSliderRepository.save(slider);
    }

    public Slider update(Slider slider){
        return iSliderRepository.save(slider);
    }

    public Iterable<Slider> findAll(){
        return iSliderRepository.findAll();
    }

    public Slider findById(Integer id){
        return iSliderRepository.findById(id);
    }

    public void deleteById(Integer id) {

        Slider slider = findById(id);
        String nameFile = slider.getImage().substring(29);
        if (!nameFile.equals("default.jpg")) {
            uploadFile.delete(nameFile);

        }

        iSliderRepository.deleteById(id);
    }
}

