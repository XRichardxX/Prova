package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	

	@FXML
	private TextField emailid;
	@FXML
	private PasswordField shid;
	@FXML
	private Button logarid;
	
	public void initialize() {
		Connect();
		
	}
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/login","root","");
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void logar() {
		String email,senha;
		email = emailid.getText();
		senha = shid.getText();
		
		try {
			pst = con.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND senha = ?");
			// TODO Auto-generated catch block
			pst.setString(1, email);
			pst.setString(2, senha);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				paginaAtual("Menu.fxml");
			}
		}catch(SQLException e) {
			// TODO Auto.generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void sair() {
		System.exit(0);
	}
	private void novaPagina(String Pagina) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(Pagina));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage appStage = new Stage();
			appStage.setScene(scene);
			appStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void paginaAtual(String Pagina) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Pagina));
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(Pagina));
			
			Scene currentScene = emailid.getScene();
			currentScene.setRoot(root);
			
			currentScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
		
}
