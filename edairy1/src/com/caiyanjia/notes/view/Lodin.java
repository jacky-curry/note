package com.caiyanjia.notes.view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Lodin extends Application {


    public static void main(String[] args) throws Exception {

	Application.launch(args);
	
}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../view/lodin.fxml"));
		stage.setTitle("meau view");
		stage.setScene(new Scene(root));
		stage.show();

	}






}


