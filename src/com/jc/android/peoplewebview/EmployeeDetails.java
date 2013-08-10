package com.jc.android.peoplewebview;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jc.android.peoplewebview.R;




import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

 public class EmployeeDetails extends Activity{

	
	protected TextView employeeNameText;
	protected TextView titleText;
	protected TextView bioText;
    protected int employeeId;
    protected int managerId;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details);
           
        String name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("title");
        String imge = getIntent().getStringExtra("imge");
        String bio = getIntent().getStringExtra("biography");
        
	        employeeNameText = (TextView) findViewById(R.id.employeeName);
	        ImageView  imgeIV =(ImageView)findViewById(R.id.imgeIV);
	        titleText = (TextView) findViewById(R.id.titles);
	        bioText = (TextView) findViewById(R.id.decc);
	        
	        employeeNameText.setText("Name:	"	+ name);
	        titleText.setText("Title:		"+ title);
	        bioText.setText("Biography:  "+"\n\n"+bio);        
	
	        Bitmap  bitmap;
	        InputStream in = null;
	        try {
	        	in = new java.net.URL(imge).openStream();
	        } catch (MalformedURLException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        } catch (IOException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	        bitmap = BitmapFactory.decodeStream(new SanInputStream(in));

	      //clean existing image for Image control
	        imgeIV.setImageDrawable(null);

	        getRoundedShape(bitmap);
	        imgeIV.setImageBitmap(getRoundedShape(bitmap));

	       
    }
    public class SanInputStream extends FilterInputStream {
        public SanInputStream(InputStream in) {
          super(in);
        }
        public long skip(long n) throws IOException {
          long m = 0L;
          while (m < n) {
            long _m = in.skip(n-m);
            if (_m == 0L) break;
            m += _m;
          }

          return m;
        }
  }
    /*
     * Making image in circular shape
     */
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
     // TODO Auto-generated method stub
     int targetWidth = 150;
     int targetHeight = 150;
     Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
                               targetHeight,Bitmap.Config.ARGB_8888);
     
                   Canvas canvas = new Canvas(targetBitmap);
     Path path = new Path();
     path.addCircle(((float) targetWidth - 1) / 2,
     ((float) targetHeight - 1) / 2,
     (Math.min(((float) targetWidth), 
                   ((float) targetHeight)) / 2),
             Path.Direction.CCW);
     
                   canvas.clipPath(path);
     Bitmap sourceBitmap = scaleBitmapImage;
     canvas.drawBitmap(sourceBitmap, 
                                   new Rect(0, 0, sourceBitmap.getWidth(),
       sourceBitmap.getHeight()), 
                                   new Rect(0, 0, targetWidth,
       targetHeight), null);
     
     
     return targetBitmap;
    }

   
}