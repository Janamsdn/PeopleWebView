package com.jc.android.peoplewebview;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jc.android.peoplewebview.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ViewSourceActivity extends Activity  {
	
	private static ArrayList<People> peopleArrayList;
	protected ListAdapter adapter;
	ListView lv;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        // from values
        String[] from = new String[] {"Name", "title",};
        // to values
        int[] to = new int[] { R.id.empname, R.id.jobtitle,  };
        // extract WebViewsource through Jsoup lib
        extractWebViewsource();
        // Get a handle to the list view
         lv = (ListView) findViewById(R.id.list);
         // get name and title from people Array List
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < peopleArrayList.size(); i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Name","Name	: 	"+ peopleArrayList.get(i).getName());
            map.put("title", "Title  	:   "+ peopleArrayList.get(i).getTitle());
            fillMaps.add(map);
        }
        
        // fill in  layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.employee_list_item, from, to);
        lv.setAdapter(adapter);
        // setOnItemClickListener
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                // get values from selected name
            	String name=peopleArrayList.get(myItemInt).getName().toString();
        		String title=peopleArrayList.get(myItemInt).getTitle().toString();
        		String imge=peopleArrayList.get(myItemInt).getPhoto().toString();
       			String biography=peopleArrayList.get(myItemInt).getBiography().toString();
       			// call EmployeeDetails intent
            	Intent intent = new Intent(myView.getContext(),EmployeeDetails.class);
       			intent.putExtra("name",name);
                intent.putExtra("title",title);
                intent.putExtra("imge",imge);
                intent.putExtra("biography",biography);
                myView.getContext().startActivity(intent);
            }                 
      });
    }
        

   //extractWebViewsource
	private void extractWebViewsource() {
		peopleArrayList= new ArrayList<People>();
		ArrayList<String> header=new ArrayList<String>();
		
		// Connect to the website and parse it into a document
        Document doc = null;
         try {
			doc = Jsoup.connect("http://theappbusiness.net/people/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
         // select image source
		for( Element element : doc.select("img") )
		{
			People p=new People();
			// set absUrl for image
			p.setPhoto(element.absUrl("src"));
			// select parent element
			Element divGuarantee = element.parent();
			//get header values and set to object
			 for( Element pelement : divGuarantee.select("h3") )
			 { 
				  header.add(pelement.text());
				 
			    }
			    String[] stockArr = new String[header.size()];
			    stockArr = header.toArray(stockArr);
			    p.setName(stockArr[0].toString());
			    p.setTitle(stockArr[1].toString());
			    header.clear();
			    //set Biography
			 p.setBiography(divGuarantee.select("p").first().text());
			 //fill array
			 peopleArrayList.add(p);
		}
	
	}
	
	
}
    
        
    
   

