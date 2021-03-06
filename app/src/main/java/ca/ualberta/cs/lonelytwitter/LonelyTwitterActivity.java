/**
 *Copyright (C) 2016 Team 20, CMPUT301, University of Alberta - All Rights Reserved.
 *You may use, copy, or distribute this code under terms and conditions of University of Alberta
 *and Code of Student Behavior.
 *Please contact abc@abc.ca for more details or questions.
 */

package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
* This class is the main view class in lonelyTwitter class.
* It deals with user inputs, saves/loads them in/from the file FILE_NAME(file.sav)
* <p> You can access this file from Android Device Monitor.</p>
* @author All
* @since 1.4
* @see NormalTweet
 * @see ImportantTweet
* @see java.io.BufferedReader
* @see TweetList
 */

public class LonelyTwitterActivity extends Activity {

	/**
	 * This is the name of the file that is saved in your virtual device.
	 * You can access it through Android Device monitor by selecting your app.
	 * then data -> data -> file -> file.sav
	 * @see NormalTweet
	 * @author A
	 */
	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> adapter;


	/** Called when the activity is first created.
	 *  Get ready to get input from user.
	 *  Buttons are also set here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				Tweet newTweet = new NormalTweet(text);
				tweetList.add(newTweet);
				adapter.notifyDataSetChanged();
				saveInFile();
			}
		});
	}

	/**
	 *
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweetList);
		oldTweetsList.setAdapter(adapter);
	}

	/**
	 * This method loads the json file, generates the tweets from its contents.
	 * @throws RuntimeException
	 * @exception FileNotFoundException
	 */
	private void loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			//Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
			Type listType = new TypeToken<ArrayList<NormalTweet>>(){}.getType();
			tweetList = gson.fromJson(in, listType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			tweetList = new ArrayList<Tweet>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	/**
	 * This method save the user's input as a json file.
	 * @throws RuntimeException
	 * @exception FileNotFoundException
	 */
	private void saveInFile() {
		try {

			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(tweetList, writer);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
}