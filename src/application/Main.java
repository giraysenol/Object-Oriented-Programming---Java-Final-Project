package application;
	
import java.io.IOException;
import java.io.RandomAccessFile;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



public class Main extends Application {
	
	int index = 0;
	Person[] abArray;
	int index_array = 0;
	
	final static int ID_SIZE = 4;
	final static int NAME_SIZE = 32;
	final static int STREET_SIZE = 32;
	final static int CITY_SIZE = 20;
	final static int GENDER_SIZE = 1;
	final static int ZIP_SIZE = 5;
	final static int RECORD_SIZE =(ID_SIZE + NAME_SIZE + STREET_SIZE + CITY_SIZE + GENDER_SIZE + ZIP_SIZE); //94 char 188byte
	
	

	TextField tfID = new TextField();
	TextField tfSU = new TextField();
	TextField tfName = new TextField();
	TextField tfStreet = new TextField();
	TextField tfCity = new TextField();
	TextField tfGender = new TextField();
	TextField tfZip = new TextField();
	
	

	Button btAdd = new Button("Add");
	Button btFirst = new Button("First");
	Button btNext = new Button("Next");
	Button btPrevious = new Button("Previous");
	Button btLast = new Button("Last");
	Button btUpdate = new Button("UpdateByID");
	Button btSearch = new Button("SearchByID");
	Button btClean = new Button("Clean textFields");
	

	Label lbID = new Label("ID");
	Label lbSU = new Label("Search/Update ID");
	Label lbName = new Label("Name");
	Label lbStreet = new Label("Street");
	Label lbCity = new Label("City");
	Label lbGender = new Label("Gender");
	Label lbZip = new Label("Zip");
	//Label deneme = new Label("deneme");
	
	
	Alert alert = new Alert(AlertType.INFORMATION);
	Alert alert_warning = new Alert(AlertType.WARNING);
	GridPane p1 = new GridPane();	
	HBox p2 = new HBox(5);
	HBox p3 = new HBox(5);
	HBox p4 = new HBox(5);
	BorderPane borderPane = new BorderPane();
	GridPane p5 = new GridPane();
	
	
	public RandomAccessFile raf;
	public Main() {	
		try {
			raf = new RandomAccessFile("address.dat", "rw");
			abArray = new Person[100];
		}
		catch(IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
					
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Look, an Information Dialog");
			alert_warning.setTitle("Warning");
			
			tfID.setPrefColumnCount(4);
			tfID.setDisable(true);
			tfGender.setPrefColumnCount(1);
			tfZip.setPrefColumnCount(4);
			tfCity.setPrefColumnCount(12);
			
			p1.setAlignment(Pos.CENTER);
			p1.setHgap(5);
			p1.setVgap(5);
			
			
			p1.add(lbID, 0, 0);
			p2.setAlignment(Pos.CENTER);
			tfID.setText(String.valueOf(index+1));
			p2.getChildren().addAll(tfID, lbSU, tfSU);
			p1.add(p2, 1, 0);

			
			p1.add(lbName, 0, 1);
			p1.add(tfName, 1, 1);
			
			p1.add(lbStreet, 0, 2);
			p1.add(tfStreet, 1, 2);
			
			p1.add(lbCity, 0, 3);
			p3.setAlignment(Pos.CENTER);
			p3.getChildren().addAll(tfCity, lbGender, tfGender, lbZip, tfZip);
			p1.add(p3, 1, 3);
			
			p4.setAlignment(Pos.CENTER);
			p4.getChildren().addAll(btAdd, btFirst, btPrevious, btNext, btLast, btUpdate, btSearch, btClean);
			
			
	
			
	
			
			
			borderPane.setCenter(p1);
			borderPane.setBottom(p4);
			borderPane.setTop(p5);
			Scene scene = new Scene(borderPane, 550, 180);
			primaryStage.setTitle("Aress Book Project");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			try {
				if (raf.length() >0) {
					
					long currentPos = raf.getFilePointer();
					
					while(currentPos < raf.length()) {
						
						readFileFillArray(abArray, currentPos, index);
						index++;
						currentPos = raf.getFilePointer();
					}
					readFileByPos(0);
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		
			btAdd.setOnAction(e->{
				
				try {
					if(tfName.getText().length() <= NAME_SIZE && tfName.getText().length() > 0  
							&& tfStreet.getText().length() <=  STREET_SIZE && tfStreet.getText().length() > 0
							&& tfCity.getText().length() <= CITY_SIZE && tfCity.getText().length() > 0 
							&& tfGender.getText().length() <= GENDER_SIZE && tfGender.getText().length() > 0 
							&& tfZip.getText().length() <= ZIP_SIZE && tfZip.getText().length() > 0) {
						
						writeAddressToFile(raf.length());
						readFileFillArray(abArray, RECORD_SIZE*2*(index), index);
						index++;
						alert.setContentText("Record is added successfully");
						alert.showAndWait();
						cleanTextFields();
					}
					else {
						alert_warning.setContentText("Fazla karakter girdiniz");
						alert_warning.showAndWait();
					}

				}
				catch (Exception ex) {
					
				}
			});
			
			btFirst.setOnAction(e->{
				try {
					index_array = 0;
					strollinArray(abArray, index_array);
				}
				catch(Exception ex){
					
				}
			});
			
			btNext.setOnAction(e->{
				try {
					if (index_array < index-1) {
						index_array += 1;
					}
					else {
						alert_warning.setContentText("Son kayida ulastýnýz daha ileri gidemezsiniz");
						alert_warning.showAndWait();
					}
					strollinArray(abArray, index_array);
				}
				catch(Exception ex){
					
				}
			});
			
			btPrevious.setOnAction(e->{
				try {
					if (index_array > 0) {
						index_array -= 1;
					}
					else {
						alert_warning.setContentText("Ilk kayida ulastiniz daha geri gidemezsiniz");
						alert_warning.showAndWait();
					}
						
					strollinArray(abArray, index_array);
				}
				catch (Exception ex) {
					
				}
			});
			
			btUpdate.setOnAction(e->{
				try {
					if (tfSU.getText().length() == 0) {
						alert_warning.setContentText("Arama kutusuna deger giriniz");
						alert_warning.showAndWait();						
					}
					if(Integer.parseInt(tfSU.getText()) > index || Integer.parseInt(tfSU.getText()) <= 0 ) {
						alert_warning.setContentText("Kayit bulanamadi");
						alert_warning.showAndWait();
					}
					else {
						if(tfName.getText().length() <= NAME_SIZE && tfName.getText().length() > 0  
								&& tfStreet.getText().length() <=  STREET_SIZE && tfStreet.getText().length() > 0
								&& tfCity.getText().length() <= CITY_SIZE && tfCity.getText().length() > 0 
								&& tfGender.getText().length() <= GENDER_SIZE && tfGender.getText().length() > 0 
								&& tfZip.getText().length() <= ZIP_SIZE && tfZip.getText().length() > 0) {
													
							int search = Integer.parseInt(tfSU.getText()) - 1;
							index_array = search;
							upgradeFile(search*RECORD_SIZE*2);
							readFileFillArray(abArray, RECORD_SIZE*2*(search), search);
							tfSU.clear();
							alert.setContentText("Record is upgrade successfully");
							alert.showAndWait();
						}
						else {
							alert_warning.setContentText("Fazla karakter girdiniz");
							alert_warning.showAndWait();
						}
					}
					
					
				}
				catch (Exception ex) {
				}
			});
			
			btSearch.setOnAction(e->{
				try {
					if (tfSU.getText().length() == 0) {
						alert_warning.setContentText("Arama kutusuna deger giriniz");
						alert_warning.showAndWait();						
					}
					if(Integer.parseInt(tfSU.getText()) > index || Integer.parseInt(tfSU.getText()) <= 0 ) {
						alert_warning.setContentText("Kayit bulanamadi");
						alert_warning.showAndWait();
					}
					else {
						int search = Integer.parseInt(tfSU.getText()) - 1;
						index_array = search;
						strollinArray(abArray, search);
						tfSU.clear();
						}

				}
				catch (Exception ex) {
					
				}
			});
			btLast.setOnAction(e->{
				try {
					index_array = index - 1;
					strollinArray(abArray, index_array);
				}
				catch (Exception ex) {
					
				}
			});
			
			btClean.setOnAction(e->{
				try {
					cleanTextFields();
				}
				catch (Exception ex) {
					
				}
			});
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void upgradeFile (long position) { 		
		try {
			raf.seek(position);
			FileOperations.writeFixedLengthString(String.valueOf(index_array+1), ID_SIZE, raf);
			FileOperations.writeFixedLengthString(tfName.getText(), NAME_SIZE, raf);
			FileOperations.writeFixedLengthString(tfStreet.getText(), STREET_SIZE, raf);
			FileOperations.writeFixedLengthString(tfCity.getText(), CITY_SIZE, raf);
			FileOperations.writeFixedLengthString(tfGender.getText(), GENDER_SIZE, raf);
			FileOperations.writeFixedLengthString(tfZip.getText(), ZIP_SIZE, raf);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void strollinArray(Person[] people,int index2){
	
		tfID.setText(String.valueOf(people[index2].getId()));
		tfName.setText(people[index2].getName());
		tfStreet.setText(people[index2].getStreet());
		tfCity.setText(people[index2].getCity());
		tfGender.setText(people[index2].getGender());
		tfZip.setText(people[index2].getZip());
		//deneme.setText(String.valueOf(index));
	}
	
	public void writeAddressToFile(long position) {
		try {
			raf.seek(position);
			FileOperations.writeFixedLengthString(String.valueOf(index+1), ID_SIZE, raf);
			FileOperations.writeFixedLengthString(tfName.getText(), NAME_SIZE, raf);
			FileOperations.writeFixedLengthString(tfStreet.getText(), STREET_SIZE, raf);
			FileOperations.writeFixedLengthString(tfCity.getText(), CITY_SIZE, raf);
			FileOperations.writeFixedLengthString(tfGender.getText(), GENDER_SIZE, raf);
			FileOperations.writeFixedLengthString(tfZip.getText(), ZIP_SIZE, raf);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void cleanTextFields() throws IOException{
		index_array = index;
		tfID.setText(String.valueOf(index + 1));
		tfName.clear();
		tfStreet.clear();
		tfCity.clear();
		tfGender.clear();
		tfZip.clear();
		tfSU.clear();
	}
	

	public void readFileFillArray(Person[] people, long position, int index) throws IOException{
		
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf).trim();
		int intID = Integer.parseInt(id.trim().toString());
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
		Person p = new Person(intID, name, gender, street, city, zip);
		people[index] = p;
		
		
				
	}
	
	public void readFileByPos(long position) throws IOException{
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf).trim();
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
		tfID.setText(id);
		tfName.setText(name);
		tfStreet.setText(street);
		tfCity.setText(city);
		tfGender.setText(gender);
		tfZip.setText(zip);


	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
