package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CriarContaController {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	@FXML
	private TextField emailid;
	@FXML
	private PasswordField senhaid;
	@FXML
	private TextField nmid;
	@FXML
	private TextField cpfid;
	@FXML
	private TextField idid;
	@FXML
	private TableView tableid;
	@FXML
	private Button criarcontaid;
	
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
	private void salvar() {
		Connect();
		
		String nome,email,senha,cpf;
		nome = nmid.getText();
		email = emailid.getText();
		senha = senhaid.getText();
		cpf = cpfid.getText();
		
		try {
				 pst = con.prepareStatement("insert into usuarios(nome,email,senha,cpf)values(?,?,?,?)");
				 pst.setString(1, nome);
				 pst.setString(2, email);
				 pst.setString(3, senha);
				 pst.setString(4, cpf);
				 pst.executeUpdate();
		            table_load();
				 
				 nmid.setText("");
				 emailid.setText("");
				 senhaid.setText("");
				 cpfid.setText("");
				 nmid.requestFocus();
		            table_load();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void table_load() {
	    try {
	        pst = con.prepareStatement("select * from usuarios");
	        rs = pst.executeQuery();

	        // Create ObservableList to store data
	        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

	        // Assuming 'table' is an instance of TableView in your class
	        tableid.getColumns().clear();

	        // Retrieve metadata (column names) from the ResultSet
	        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
	            final int j = i;
	            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
	            col.setCellValueFactory(param -> {
	                return new ReadOnlyObjectWrapper<>(param.getValue().get(j));
	            });
	            tableid.getColumns().addAll(col);
	        }

	        // Retrieve data from the ResultSet
	        while (rs.next()) {
	            ObservableList<String> row = FXCollections.observableArrayList();
	            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	                row.add(rs.getString(i));
	            }
	            data.add(row);
	        }

	        // Set the items in the TableView
	        tableid.setItems(data);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	@FXML
	private void voltar() {
		paginaAtual("Menu.fxml");
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
	
	@FXML
	private void sair() {
		System.exit(0);
	}
	
	@FXML
	private void limpar() {
		nmid.setText("");
        emailid.setText("");
        senhaid.setText("");
        cpfid.setText("");
        nmid.requestFocus();
	}
	
	@FXML
	private void atualizar() {
		String cname,email,senha,cid,cpf;
        
        
		cname = nmid.getText();
		email = emailid.getText();
		senha = senhaid.getText();
		cpf = cpfid.getText();
		cid = idid.getText();
        try {
            pst = con.prepareStatement("update usuarios set nome= ?,email=?,senha=?,cpf=? where id =?");
            pst.setString(1, cname);
            pst.setString(2, email);
            pst.setString(3, senha);
            pst.setString(4, cpf);
            pst.setString(5, cid);
            pst.executeUpdate();
            table_load();
           
            nmid.setText("");
            emailid.setText("");
            senhaid.setText("");
            cpfid.setText("");
            nmid.requestFocus();
            table_load();
        }

        catch (SQLException e1) {
            
            e1.printStackTrace();
        }
	}   
        @FXML
    	private void deletar() {
    		String cid;
    		cid  = idid.getText();
            
             try {
                    pst = con.prepareStatement("delete from usuarios where id =?");
            
                    pst.setString(1, cid);
                    pst.executeUpdate();
                    table_load();
                   
                    nmid.setText("");
                    emailid.setText("");
                    senhaid.setText("");
                    cpfid.setText("");
                    nmid.requestFocus();
                    table_load();
                }

                catch (SQLException e1) {
                    
                    e1.printStackTrace();
                }
    	}
        
        @FXML
    	private void search() {
    		try {
    	         
    	           String id = idid.getText();
    	
    	               pst = con.prepareStatement("select nome,email,senha,cpf from usuarios where id = ?");
    	               pst.setString(1, id);
    	               ResultSet rs = pst.executeQuery();
    	
    	           if(rs.next()==true)
    	           {
    	             
    	               String nome = rs.getString(1);
    	               String email = rs.getString(2);
    	               String senha = rs.getString(3);
    	               String cpf = rs.getString(4);
    	               
    	               nmid.setText(nome);
    	               emailid.setText(email);
    	               senhaid.setText(senha);
    	               cpfid.setText(cpf);
    	
    	           }   
    	           else
    	           {
    	        	   nmid.setText("");
    	        	   emailid.setText("");
    	        	   senhaid.setText("");
    	        	   cpfid.setText("");
    	                
    	           }
    	       } 
    	   
    	    catch (SQLException ex) {
    	          
    	       }
    	}
	
}
