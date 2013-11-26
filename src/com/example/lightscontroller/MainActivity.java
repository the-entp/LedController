package com.example.lightscontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button lightController = (Button) findViewById(R.id.light_controller);
		Log.d("finished", "finished creating button");
		lightController.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		Log.d("Starting", "Starting clicker");
		new Thread(new Runnable() {
			public void run() {
				Socket socket = null;
				PrintWriter writer = null;
				BufferedReader inStream = null;
				try {
					
					socket = new Socket("192.168.0.6", 13);
					writer = new PrintWriter(socket.getOutputStream(), true);
					inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				    writer.println("turn lights on");
				    writer.flush();
				}
				catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						inStream.close();
						writer.close();
						socket.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		Button lightController = (Button) findViewById(R.id.light_controller);
		String text = (String) lightController.getText();
		if (text.equals("off")) {
			lightController.setText("on");
		} else {
			lightController.setText("off");
		}
	}

}
