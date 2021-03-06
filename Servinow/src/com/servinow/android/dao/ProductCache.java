package com.servinow.android.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.ProductInCategory;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;

public class ProductCache extends ServinowDAOBase<Producto, Integer> {

	public ProductCache(Context context) {
		super(context, Producto.class);
	}
	
	void setProductCache(Restaurant restaurant, Collection<Producto> productsList){
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		
		for(Producto product: productsList){
			product.restaurant = restaurant;
			productDAO.createOrUpdate(product);
		}
	}
	
	public void setProductCacheByCategory(Categoria category, List<Integer> productIDsList){
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		
		RuntimeExceptionDao<ProductInCategory, Integer> picDAO = servinowDatabase.getRuntimeExceptionDao(ProductInCategory.class);
		
		for(Integer productID: productIDsList){
			Producto product = productDAO.queryForId(productID);

			ProductInCategory pic = new ProductInCategory(product, category);
			picDAO.createOrUpdate(pic);
			
			productDAO.createOrUpdate(product);
		}
	}

	
	public List<Producto> getProducts(){
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		List<Producto> product = productDAO.queryForAll();
		return product;
	}

	public Producto getProducto(int productoID) {
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		Producto producto = productDAO.queryForId(productoID);
		
		return producto;

	}
}
