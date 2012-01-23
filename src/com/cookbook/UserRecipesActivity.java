package com.cookbook;

import java.io.InputStream;

import com.cookbook.RecipeList;
import com.cookbook.readFile;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserRecipesActivity extends ListActivity {

	
	Resources myResources;
	readFile rd;
	CookbookDBAdapter mDbHelper;
	
	/** List of Recipes */
	protected RecipeList list = new RecipeList();

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        //Initialise the DB
        mDbHelper = new CookbookDBAdapter(this);
        mDbHelper.open();
        //createRecipe();
        //createIngredient();
        //createRecipeIngredients();
        
        myResources = getResources();
        /*
         * Add the database entries to the list
         */
        InputStream fos = myResources.openRawResource(R.raw.userrecipes);
        rd = new readFile();
        list.fetchFromIDs(rd.readIDs(fos),mDbHelper);
        
       
        
        /**
  	   * adding the list to the recipeArray used to display it
  	   */
        
      if (list.size()>0)
      {  
  	  RECIPES = new String[list.size()];
  	  for (int i =0; i<list.size();i++){
  		  RECIPES[i] = list.getRecipe(i).getName()+"\nType: "+list.getRecipe(i).getType();
  		  System.out.println(list.getRecipe(i).getName());
  	  }
      }
      else
      {
    	  RECIPES = new String[1];
    	  RECIPES[0] = "No Recipes added yet";
      }
        
     // list_item is in /res/layout/ should be created
  	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, RECIPES));



  	  final ListView lv = getListView();
  	  lv.setTextFilterEnabled(true);

        
        

  	/**
	   * Onclik show the info about the Recipe on a popup message
	   */
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Toast.makeText(getApplicationContext(), 
	    "Ingredients: "+list.getRecipe(position).getIngredients()+"\nPreparation: "+list.getRecipe(position).getPreparation()
	    +"\nType: "+list.getRecipe(position).getType()+"\nRegion: "+list.getRecipe(position).getRegion(),
	          Toast.LENGTH_SHORT).show();
	      }
        });
    }
	
	/** need it*/
	 String[] RECIPES = new String[]{"lol"};
}
