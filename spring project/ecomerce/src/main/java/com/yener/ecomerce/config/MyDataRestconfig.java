package com.yener.ecomerce.config;

import com.yener.ecomerce.entity.Product;
import com.yener.ecomerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestconfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    public  MyDataRestconfig(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {


        HttpMethod[] theUnspportedActions={HttpMethod.DELETE,HttpMethod.POST,HttpMethod.PUT};

        config.getExposureConfiguration().forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) ->httpMethods.disable(theUnspportedActions) )
                .withCollectionExposure((metdata, httpMethods) ->httpMethods.disable(theUnspportedActions) );

        config.getExposureConfiguration().forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) ->httpMethods.disable(theUnspportedActions) )
                .withCollectionExposure((metdata, httpMethods) ->httpMethods.disable(theUnspportedActions) );

        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config){

        Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();

        List<Class> entityClasses=new ArrayList<>();

        for (EntityType entityType:entities) {
            entityClasses.add(entityType.getJavaType());
        }
        Class[] domainTypes= entityClasses.toArray(new Class[0]);

        config.exposeIdsFor(domainTypes);
    }

}
