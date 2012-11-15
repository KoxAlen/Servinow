package com.servinow.android;


import android.content.Intent;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
import com.servinow.android.dao.ProductCache;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Producto;
import com.servinow.android.pedidosSystem.PedidosHandler;

public class DetalleProductoActivity extends SherlockActivity {

	private int restaurantID;
	private int placeID;
	private int categoriaID;
	private int productoID;
	private Producto producto;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			restaurantID = extras.getInt(Param.RESTAURANT.toString());
			placeID = extras.getInt(Param.PLACE.toString());
			categoriaID = extras.getInt(Param.CATEGORIA.toString());
			productoID = extras.getInt(Param.PRODUCTO.toString());
			
			this.producto = new ProductCache(this).getProducto(productoID);
	        Resources res = getResources();

	        ImageView imageView = (ImageView) findViewById(R.id.imageView);
			imageView.setImageDrawable(producto.getImagen());
			
			ImageAsyncHelper imageAsyncHelper = new ImageAsyncHelper();
			
			Bitmap img = imageAsyncHelper.getBitmap(producto.getImageName(),
					new ImageAsyncHelperCallBack() {
				
				@Override
				public void onImageSyn(Bitmap img) {
					ImageView imageView = (ImageView) findViewById(R.id.imageView);
					imageView.setImageBitmap(img);
				}
			}, null);
			
			if (img != null)
				imageView.setImageBitmap(img);
			
			TextView textViewPrecio = (TextView) findViewById(R.id.textViewPrecio);
			textViewPrecio.setText(String.valueOf(producto.getPrecio()) + " €");
			
			TextView textViewDisponible = (TextView) findViewById(R.id.textViewDisponibilidad);
			Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
			if (producto.isDisponible()) {
				textViewDisponible.setText("Disponible");
				buttonAdd.setEnabled(true);
			} else {
				textViewDisponible.setText("No disponible");
				buttonAdd.setEnabled(false);
			}
			
			TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcion);
			textViewDescripcion.setText(producto.getDescripcion());
			
			setTitle(producto.getNombre());
		}

		final Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				Bundle b = new Bundle();
				b.putInt(Param.RESTAURANT.toString(), restaurantID);
				b.putInt(Param.PLACE.toString(), placeID);
				b.putInt(Param.CATEGORIA.toString(), categoriaID);
				b.putInt(Param.PRODUCTO.toString(), productoID);
				// TODO se envía cantidad=1 para probar. Implementar diálogo con NumberPicker
				b.putInt(Param.CANTIDAD.toString(), 1);
				
				// TODO conectar con la parte de Carlos.
				
				PedidosHandler pedidosHandler = new PedidosHandler(DetalleProductoActivity.this);
				pedidosHandler.insertarProducto(producto, 1, placeID, restaurantID);
				/*Intent i = new Intent(DetalleProductoActivity.this, ?.class);
				i.putExtras(b);
				startActivity(i);*/
				// TODO para depuración, eliminar
            	printToast("Se ha hecho click en el botón de añadir");            	
            }
        });
    }
	
    // TODO para depuración, eliminar
	private void printToast(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	}
}
