package com.jc.android.peoplewebview;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import com.jc.android.peoplewebview.R;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import android.os.Bundle;

import android.widget.ImageView;

import android.widget.TextView;

 public class EmployeeDetails extends Activity{

	
	 private TextView employeeNameText;
	 private TextView titleText;
	 private TextView bioText;
	 private ImageView imgeIV;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details);
         // Get bundle data
        String name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("title");
        String imge = getIntent().getStringExtra("imge");
        String bio = getIntent().getStringExtra("biography");
        //getView And SetData 
	    getViewAndSetData(name, title, imge, bio);
	       
    }
	private void getViewAndSetData(String name, String title, String imge,
			String bio) {
		//get view by id
		employeeNameText = (TextView) findViewById(R.id.employeeName);
		  imgeIV =(ImageView)findViewById(R.id.imgeIV);
		titleText = (TextView) findViewById(R.id.titles);
		bioText = (TextView) findViewById(R.id.decc);
		// set text
		employeeNameText.setText("Name:	"	+ name);
		titleText.setText("Title:		"+ title);
		bioText.setText("Biography:  "+"\n"+bio);        
        // prepare your image to circle and set to image view
		Bitmap  bitmap;
		InputStream in = null;
		try {
			in = new java.net.URL(imge).openStream();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// decode stream
		bitmap = BitmapFactory.decodeStream(in);

       //clean existing image for Image control
		imgeIV.setImageDrawable(null);
		//get image in circular shape
		getRoundedShape(bitmap);
		//set Image 
		imgeIV.setImageBitmap(getRoundedShape(bitmap));
	}
    

    /*
     * Making image in circular shape
     */
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
     //targetWidth
     int targetWidth = 150;
     int targetHeight = 150;
     //createBitmap
     Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
                           targetHeight,Bitmap.Config.ARGB_8888);
     // create canvas
     Canvas canvas = new Canvas(targetBitmap);
     //  calculate Circle
     Path path = new Path();
     path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2,
    		 		(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
    		 		Path.Direction.CCW);  
     //clip with the specified path
     canvas.clipPath(path);
     
     Bitmap sourceBitmap = scaleBitmapImage;
     //Circle draw
     canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
    		 		   sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
    		 		  targetHeight), null);  
     return targetBitmap;
    }

   
}