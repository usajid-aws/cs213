/***************************************
 *                                     *
 * @author -> Usama Sajid, Tausif Alam *
 *                                     *
 * cs213 SongLib assignment            *
 * Fall 2017                           *
 *                                     *
 ***************************************/

package app;
import SongLib.Controller.SongLibController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SongLib extends Application {
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try
		{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/uinew.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);

       SongLibController controller = loader.getController();
       controller.start(stage);
		
		stage.setScene(scene);
     	stage.setTitle("Music Player");
		stage.setResizable(false);

		
		stage.show();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}