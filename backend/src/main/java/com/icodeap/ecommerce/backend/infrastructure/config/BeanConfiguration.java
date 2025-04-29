package com.icodeap.ecommerce.backend.infrastructure.config;

import com.icodeap.ecommerce.backend.application.*;
import com.icodeap.ecommerce.backend.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserService userServices(IUserRepository iUserRepository){

        return new UserService(iUserRepository);
    }

    @Bean
    public CategoryService categoryServices(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);
    }

    @Bean
    public ProductService productService(IProductRepository iProductRepository, UploadFile uploadFile){
        return new ProductService(iProductRepository, uploadFile);
    }
    @Bean
    public OrderService orderService(IOrderRepository iOrderRepository){
        return new OrderService(iOrderRepository);
    }
    @Bean
    public UploadFile uploadFile(){
        return new UploadFile();
    }

    @Bean
    public  RegistrationService registrationService(IUserRepository iUserRepository){
        return new RegistrationService(iUserRepository);
    }

    @Bean
    public SliderService sliderServices(ISliderRepository iSliderRepository, UploadFile uploadFile){
        return new SliderService(iSliderRepository, uploadFile);
    }
}
