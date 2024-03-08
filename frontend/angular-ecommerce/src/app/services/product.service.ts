import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Product } from '../common/product';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl='http://localhost:8080/api/products'

  private categoryUrl='http://localhost:8080/api/products'


  constructor(private httpClient: HttpClient) { }

  getProductList(theCategoryId: number): Observable<Product[]>{

    const searchUrl=`${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`
    
    return this.httpClient.get<GetResponseProducts>(searchUrl).pipe(map(response=>response._embedded.products))
    
  }
  getProductCaetgories(): Observable<ProductCategory[]> {
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(map(response=>response._embedded.ProductCategory))
  }
  
}
interface GetResponseProducts{
    _embedded:{
      products:Product[];
    }
}

interface GetResponseProductCategory{
  _embedded:{
    ProductCategory:ProductCategory[];
  }
}