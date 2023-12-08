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

public class ProdutosController {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	@FXML
	private TextField nomeid;
	@FXML
	private TextField precoid;
	@FXML
	private TextField descricaoid;
	@FXML
	private TextField idid;
	@FXML
	private TableView tableid;
	@FXML
	private Button criarcontaid;
	
	public void initialize() {
		Connect();
        table_load();
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
		
		String nome,preco,descricao;
		nome = nomeid.getText();
		preco = precoid.getText();
		descricao = descricaoid.getText();
		
		try {
				 pst = con.prepareStatement("insert into produtos(nome,preco,descricao)values(?,?,?)");
				 pst.setString(1, nome);
				 pst.setString(2, preco);
				 pst.setString(3, descricao);
				 pst.executeUpdate();
				 
				 nomeid.setText("");
				 precoid.setText("");
				 descricaoid.setText("");
				 nomeid.requestFocus();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void table_load() {
	    try {
	        pst = con.prepareStatement("select * from produtos");
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
			
			Scene currentScene = precoid.getScene();
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
		nomeid.setText("");
        precoid.setText("");
        descricaoid.setText("");
        nomeid.requestFocus();
	}
	
	@FXML
	private void atualizar() {
		String cname,preco,descricao,cid;
        
        
		cname = nomeid.getText();
		preco = precoid.getText();
		descricao = descricaoid.getText();
		cid = idid.getText();
        try {
            pst = con.prepareStatement("update produtos set nome= ?,preco=?,descricao=? where id =?");
            pst.setString(1, cname);
            pst.setString(2, preco);
            pst.setString(3, descricao);
            pst.setString(4, cid);
            pst.executeUpdate();
            table_load();
           
            nomeid.setText("");
            precoid.setText("");
            descricaoid.setText("");
            nomeid.requestFocus();
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
                    pst = con.prepareStatement("delete from produtos where id =?");
            
                    pst.setString(1, cid);
                    pst.executeUpdate();
                    table_load();
                   
                    nomeid.setText("");
                    precoid.setText("");
                    descricaoid.setText("");
                    nomeid.requestFocus();
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
    	
    	               pst = con.prepareStatement("select nome,preco,descricao from produtos where id = ?");
    	               pst.setString(1, id);
    	               ResultSet rs = pst.executeQuery();
    	
    	           if(rs.next()==true)
    	           {
    	             
    	               String nome = rs.getString(1);
    	               String preco = rs.getString(2);
    	               String descricao = rs.getString(3);
    	               
    	               
    	               nomeid.setText(nome);
    	               precoid.setText(preco);
    	               descricaoid.setText(descricao);
    	               
    	
    	           }   
    	           else
    	           {
    	        	   nomeid.setText("");
    	        	   precoid.setText("");
    	        	   descricaoid.setText("");
    	        	   
    	                
    	           }
    	       } 
    	   
    	    catch (SQLException ex) {
    	          
    	       }
    	}
	
}
