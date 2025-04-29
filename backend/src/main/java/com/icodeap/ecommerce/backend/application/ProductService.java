package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ProductService {

    private final IProductRepository iProductRepository;
    private final UploadFile uploadFile;

    public ProductService(IProductRepository iProductRepository, UploadFile uploadFile) {
        this.iProductRepository = iProductRepository;
        this.uploadFile = uploadFile;
    }


    public Product save(Product product, MultipartFile multipartFile) throws IOException {
        if(product.getId()!=0){
            if(multipartFile==null) {
                product.setUrlImage(product.getUrlImage());
            }else{
                String nameFile = product.getUrlImage().substring(29);
                if (!nameFile.equals("default.jpg")){
                    uploadFile.delete(nameFile);

                }
                product.setUrlImage(uploadFile.upload(multipartFile));
            }
        }else{
            product.setUrlImage(uploadFile.upload(multipartFile));
        }
        return iProductRepository.save(product);
    }

    public Iterable<Product> findAll(){
        return iProductRepository.findAll();
    }

    public Product findById(Integer id){
        return iProductRepository.findById(id);
    }

    // Método que obtiene los productos de una categoría específica
    public List<Product> getProductsByCategory(Integer categoryEntityId) {
        return iProductRepository.findByCategoryEntityId(categoryEntityId);
    }

    public void deleteById(Integer id){
        Product product = findById(id);
        String nameFile = product.getUrlImage().substring(29);
        if (!nameFile.equals("default.jpg")){
            uploadFile.delete(nameFile);

        }
        iProductRepository.deleteById(id);
    }

}
