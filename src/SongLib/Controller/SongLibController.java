/****************************************************
 * @author -> Usama Sajid, Tausif Alam              *
 *                                                  *
 * ************* Controller Class *******************
 *                                                  *
 * Main Controller class                            *
 * adds to listView implements songPlayer functions *
 *                                                  *
 ****************************************************/
package SongLib.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SongLibController implements Initializable{
	
	@FXML private TextField addSongName;
	@FXML private TextField addSongAuthor;
	@FXML private TextField addSongAlbum;
	@FXML private TextField addSongYear;
	@FXML private ListView<Song> listView;
	@FXML private SplitPane splitPane;
	@FXML private TextField SplitName;
	@FXML private TextField SplitAuthor;
	@FXML private TextField SplitYear;
	@FXML private TextField SplitAlbum;
	static ObservableList<Song> oblist = FXCollections.observableArrayList();
	SongPlayer player = new SongPlayer();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		listView.setItems(oblist);
		listView.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null || item.getName().equals("") || item.getAuthor().equals("")) {
					setText("");
				} else {
					if(item.getName().length() > 4)
						setText(item.getName().trim() + '\t' + item.getAuthor());
					else
					{
						setText(item.getName().trim() + '\t' + item.getAuthor());
					}
				}
			}
		});
		
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				clearFields();
				if(oblist.size() == 0) return;
				Song change = listView.getSelectionModel().getSelectedItem();
				SplitName.setText(change.getName());
				SplitAlbum.setText(change.getAlbum());
				if(change.getYear()!=0)
				{
					SplitYear.setText(Integer.toString(change.getYear()));
				}
				else
				{
					SplitYear.clear();
					SplitYear.setPromptText("unknown");
				}
				SplitAuthor.setText(change.getAuthor());
				return;
			}
			
			

			
		});
		
		
		
		SplitName.setEditable(false);
		SplitAuthor.setEditable(false);
		SplitYear.setEditable(false);
		SplitAlbum.setEditable(false);
		
		
	}
	
	public void start(Stage stage)
	{   
		
		File file = new File("src/songdata.txt");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			fillList();
			if(oblist.size() > 0)
			{
				
				Song change = listView.getItems().get(0);
				listView.getSelectionModel().select(0);
				SplitName.setText(change.getName());
				SplitAlbum.setText(change.getAlbum());
				if(change.getYear()!=0)
				{
					SplitYear.setText(Integer.toString(change.getYear()));
				}
				else
				{
					SplitYear.setPromptText("unknown");
				}
				SplitAuthor.setText(change.getAuthor());
			}
			stage.setOnCloseRequest(e -> 
			{
				
				try
				{
					PrintWriter dataWriter = new PrintWriter(file);
					file.createNewFile();
					for(int i=0; i<oblist.size(); i++)
					{
						dataWriter.write(oblist.get(i).getName() + ",");
						dataWriter.write(oblist.get(i).getAuthor() + ",");
						dataWriter.write(oblist.get(i).getAlbum() + ",");
						dataWriter.write(oblist.get(i).getYear() + "," );
						dataWriter.println();
					}
					dataWriter.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				
				
			});		
			
		}
		
		
		

		private void fillList() {
			try {
				
				File file = new File("src/songdata.txt");

				if(file != null){
					BufferedReader in = new BufferedReader(new FileReader(file));
					String str = in.readLine();
					while (str != null) {
						String[] ar=str.split(",");
						Song renew = new Song(ar[0], ar[1], Integer.parseInt(ar[3]),ar[2]);
						player.addSong(renew);
						oblist.add(renew);
						str = in.readLine();
					}
					in.close();	
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		public void addToPlayer()
		{
			
			if(addSongName.getText() == null || addSongName.getText().equals("")  || addSongAuthor.getText() == null || addSongAuthor.getText().equals("") )
			{
				Alert warning = new Alert(Alert.AlertType.WARNING, "Plaese input Song name/Author", ButtonType.OK);
				warning.showAndWait();
				clearFields();
				return;
			}
			
			int year = convertToInt(addSongYear.getText().trim());
			if(year==-1) return;
			Song addMe = new Song(addSongName.getText().trim().toUpperCase(), addSongAuthor.getText().trim().toUpperCase(), year, addSongAlbum.getText().trim().toUpperCase());
			if(player.addSong(addMe))
			{
				Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add this song?");
				Optional<ButtonType> result = confirm.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					oblist.add(addMe);
					Alert confirm_new = new Alert(Alert.AlertType.CONFIRMATION, "SONG ADDED SUCCESSFULLY", ButtonType.OK);
					confirm_new.showAndWait();
					FXCollections.sort(oblist, new songComparator());
					clearFields();
					listView.getSelectionModel().select(findSongLocation(addMe));
					SplitName.setText(addMe.getName());
					SplitAlbum.setText(addMe.getAlbum());
					SplitYear.clear();
					if(addMe.getYear()!=0)
					{
						SplitYear.setText(Integer.toString(addMe.getYear()));
					}
					else
					{
						SplitYear.setPromptText("unknown");
					}
					SplitAuthor.setText(addMe.getAuthor());
					return;
				}
				else 
				{
					clearFields();
					return;
				}
				
				
			}
			else
			{
				Alert warning = new Alert(Alert.AlertType.WARNING, "Song already exists, can't add!", ButtonType.OK);
				warning.showAndWait();
				clearFields();
				return;
			}
			
		}
		
		public void deleteFromPlayer()
		{
			
			Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this song?");
			int songRow;
			songRow = listView.getSelectionModel().getSelectedIndex();
			Optional<ButtonType> result = confirm.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				Song deleteMe = listView.getSelectionModel().getSelectedItem();
				if(deleteMe!=null)
				{
					int i = findSongLocation(deleteMe);
					if(player.deleteSong(deleteMe))
					{
						Alert confirmed = new Alert(Alert.AlertType.CONFIRMATION, "Song DELETED SUCCESSFULLY", ButtonType.OK);
						confirmed.showAndWait();
						listView.getItems().remove(songRow);
						FXCollections.sort(oblist, new songComparator());
						clearFields();
						listView.getSelectionModel().select(i);
						System.out.println(oblist.size());
						Song change = null;
						if(oblist.size() == 0 )
						{
							SplitName.clear();
							SplitAlbum.clear();
							SplitAuthor.clear();
							SplitYear.clear();
							return;                        	
						}                                              
						if(oblist.size() == 1)
						{
							change = listView.getItems().get(0);
						}
						else if(oblist.size() >= 2)
						{
							if(oblist.size() == i)
								change = listView.getItems().get(i-1);
							else change = listView.getItems().get(i);
						}
						SplitName.setText(change.getName());
						SplitAlbum.setText(change.getAlbum());
						if(change.getYear()!=0)
						{
							SplitYear.setText(Integer.toString(change.getYear()));
						}
						else
						{
							SplitYear.setPromptText("unknown");
						}
						SplitAuthor.setText(change.getAuthor());
						return;
						
					}
					else
					{
						Alert warning = new Alert(Alert.AlertType.WARNING, "Song doesn't exists, can't delete!", ButtonType.OK);
						warning.showAndWait();
						clearFields();
						return;        		
					}
					
				}
				else
				{
					Alert warning = new Alert(Alert.AlertType.WARNING, "No Song Selected!", ButtonType.OK);
					warning.showAndWait();
					clearFields();
					return;  
					
				}
				
				
			}
			else return;         
			
		}
		
		
		
		@FXML
		private void updatePlayer()
		{
			Song updateMe = listView.getSelectionModel().getSelectedItem();
			if(updateMe == null)
			{
				Alert warning = new Alert(Alert.AlertType.WARNING, "No Song Selected!", ButtonType.OK);
				warning.showAndWait();
				clearFields();
				return;  
			}
			else
			{
				int year = convertToInt(addSongYear.getText().trim());
				if(year==-1) return;
				Song updatedSong = player.update(updateMe,addSongName.getText(),addSongAuthor.getText(), year, addSongAlbum.getText());
				boolean allNull = addSongName.getText() == null && addSongAuthor.getText() == null && addSongYear.getText() == null && addSongAlbum.getText() == null;
				boolean allEmpty = addSongName.getText().equals("") && addSongAuthor.getText().equals("") && addSongYear.getText().equals("") && addSongAlbum.getText().equals(""); 
				if(updatedSong!=null)
				{
					if(allNull || allEmpty )
					{
						Alert warning = new Alert(Alert.AlertType.WARNING, "Please enter Fields to edits", ButtonType.OK);
						warning.showAndWait();
						clearFields();
						return;				
					}
					
					else
					{
						Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update song information?");
						Optional<ButtonType> result = confirm.showAndWait();
						if(result.get() == ButtonType.OK)
						{
							Alert confirmed = new Alert(Alert.AlertType.CONFIRMATION, "Song UPDATED SUCCESSFULLY", ButtonType.OK);
							confirmed.showAndWait();
							listView.getItems().remove(player.updateTemp);
							oblist.add(updatedSong);
							FXCollections.sort(oblist, new songComparator());
							clearFields();
							listView.getSelectionModel().select(findSongLocation(updatedSong));
							listView.getSelectionModel().select(findSongLocation(updatedSong));
							SplitName.setText(updatedSong.getName());
							SplitAlbum.setText(updatedSong.getAlbum());
							if(updatedSong.getYear()!=0)
							{
								SplitYear.setText(Integer.toString(updatedSong.getYear()));
							}
							else
							{
								SplitYear.setPromptText("unknown");
							}
							SplitAuthor.setText(updatedSong.getAuthor());
							return;
							
							
						}
						else return;
					}
					
				}
				else
				{
					Alert warning = new Alert(Alert.AlertType.WARNING, "Song already exists, can't add!", ButtonType.OK);
					warning.showAndWait();
					clearFields();
					return;		    	
				}
			}
			
		}
		
		private int convertToInt(String year)
		{ 
			if(year.equals("")) return 0;
			int i;
			try
			{
				i = Integer.parseInt(year);
				return i;
			}catch(Exception e)
			{
				Alert warning = new Alert(Alert.AlertType.WARNING, "Please Enter a valid Year:", ButtonType.OK);
				warning.showAndWait();
				return -1;
			}
			
		}
		
		private void clearFields()
		{
			addSongName.clear();
			addSongAlbum.clear();
			addSongYear.clear();
			addSongAuthor.clear();
			return;
		}
		
		private int findSongLocation(Song song)
		{
			if(oblist.size() == 0)
			{
				return 0;
			}
			for(int i=0; i<oblist.size(); i++)
			{
				if(song.getName().equals(oblist.get(i).getName()) && song.getAuthor().equals(oblist.get(i).getAuthor()))
				{
					return i;
				}
			}
			return 0;
		}

	}
