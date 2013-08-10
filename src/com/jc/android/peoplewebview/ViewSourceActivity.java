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

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ViewSourceActivity extends Activity  {
	private WebView web;
	private static ArrayList<People> peopleArrayList;
	
	protected ListAdapter adapter;
	private ArrayAdapter<String> mAdapter = null;
	private ArrayAdapter arrayAdapter;
	ListView lv;
	private Context context;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        
        extract();
     // Get a handle to the list view
         lv = (ListView) findViewById(R.id.list);
     // fill in the grid_item layout 
      // create the grid item mapping
         String[] from = new String[] {"firstName", "title",};
         int[] to = new int[] { R.id.firstName, R.id.lastName,  };
         
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < peopleArrayList.size(); i++){
            HashMap<String, String> map = new HashMap<String, String>();
            //map.put("rowid", "" + i);
            map.put("firstName","Name	: 	"+ peopleArrayList.get(i).getName());
            map.put("title", "Title  	:   "+ peopleArrayList.get(i).getManagement());
            fillMaps.add(map);
        }
        
        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.employee_list_item, from, to);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
            	
            	String name=peopleArrayList.get(myItemInt).getName().toString();
        		String title=peopleArrayList.get(myItemInt).getManagement().toString();
        		String imge=peopleArrayList.get(myItemInt).getPhoto().toString();
       			String biography=peopleArrayList.get(myItemInt).getBiography().toString();
       			
            	//ViewSourceActivity d = (ViewSourceActivity)context;
            	Intent intent = new Intent(myView.getContext(),EmployeeDetails.class);
            	
       			intent.putExtra("name",name);
                intent.putExtra("title",title);
                intent.putExtra("imge",imge);
                intent.putExtra("biography",biography);
                myView.getContext().startActivity(intent);
            }                 
      });
    }
        

   
	private void extract() {
		// Connect to the website and parse it into a document
        Document doc = null;
         try {
			doc = Jsoup.connect("http://theappbusiness.net/people/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		peopleArrayList= new ArrayList<People>();
		ArrayList<String> header=new ArrayList<String>();
		
		for( Element element : doc.select("img") )
		{
			People p=new People();
			p.setPhoto(element.absUrl("src"));
			
			Element divGuarantee = element.parent();
			 for( Element pelement : divGuarantee.select("h3") )
			 { 
				  header.add(pelement.text());
				 
			    }
			    String[] stockArr = new String[header.size()];
			    stockArr = header.toArray(stockArr);
			    p.setName(stockArr[0].toString());
			    p.setManagement(stockArr[1].toString());
			    header.clear();
			 p.setBiography(divGuarantee.select("p").first().text());
			 
			 peopleArrayList.add(p);
		}
	
	}
	
	
}
    
        
    
   

