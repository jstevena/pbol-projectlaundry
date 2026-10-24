/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectlaundry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LIKMI
 */
public class FXMLLayananController implements Initializable {

    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnlast;
    @FXML
    private Button btnfirst;
    @FXML
    private Button btnnext;
    @FXML
    private Button btnprev;
    @FXML
    private Button btnsearch;
    @FXML
    private TableView<LayananModel> tbvlayanan;
    @FXML
    private ImageView imglayanan;
    @FXML
    private TextField txtsearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
        tbvlayanan.getSelectionModel().selectFirst();
        // gambar ikut berganti saat baris dipilih lewat mouse, tombol, maupun keyboard
        tbvlayanan.getSelectionModel().selectedItemProperty().addListener((obs, lama, baru) -> showgambar());
        showgambar();
    }    
    
    public void showdata(){
        ObservableList<LayananModel> data=FXMLDocumentController.dtlayanan.Load();
        tampilkanData(data);
    }

    // dipakai bareng oleh showdata() dan cariData() supaya kolom tabel tidak ditulis dua kali
    private void tampilkanData(ObservableList<LayananModel> data){
        if(data!=null){            
            tbvlayanan.getColumns().clear();            
            tbvlayanan.getItems().clear();
            
            TableColumn col=new TableColumn("kode");
            col.setPrefWidth(55);
            col.setCellValueFactory(new PropertyValueFactory<LayananModel, String>("kodelayanan"));
            tbvlayanan.getColumns().addAll(col);
            col=new TableColumn("layanan");
            col.setPrefWidth(150);
            col.setCellValueFactory(new PropertyValueFactory<LayananModel, String>("namalayanan"));
            tbvlayanan.getColumns().addAll(col);
            col=new TableColumn("satuan");
            col.setPrefWidth(60);
            col.setCellValueFactory(new PropertyValueFactory<LayananModel, String>("satuan"));
            tbvlayanan.getColumns().addAll(col);
            col=new TableColumn("tarif");
            col.setPrefWidth(75);
            col.setCellValueFactory(new PropertyValueFactory<LayananModel, String>("tarif"));
            tbvlayanan.getColumns().addAll(col);
            col=new TableColumn("estimasi (jam)");
            col.setPrefWidth(125);
            col.setCellValueFactory(new PropertyValueFactory<LayananModel, String>("estimasi"));
            tbvlayanan.getColumns().addAll(col);
            tbvlayanan.setItems(data);
    }else {  Alert a=new Alert(Alert.AlertType.ERROR,"Data kosong",ButtonType.OK);
            a.showAndWait();
            tbvlayanan.getScene().getWindow().hide();
        }                
    }

    // Search Data: cari berdasarkan kodelayanan atau namalayanan
    private void cari(){
        String key = txtsearch.getText();
        if(!key.isEmpty()){
            ObservableList<LayananModel> data = FXMLDocumentController.dtlayanan.CariLayanan(key, key);
            tampilkanData(data);
        } else {
            showdata();
        }
        tbvlayanan.getSelectionModel().selectFirst();
        showgambar();
    }

    @FXML
    private void cariData(KeyEvent event) {
        cari();
    }

    @FXML
    private void btnsearchclick(ActionEvent event) {
        cari();
    }
     
    @FXML
    public void showgambar(){
        Image gambar = null;
        LayananModel s = tbvlayanan.getSelectionModel().getSelectedItem();
        if(s!=null && s.getGambar()!=null && !s.getGambar().isEmpty()){
            try {
                gambar = new Image(new FileInputStream(s.getGambar()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLLayananController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        imglayanan.setImage(gambar);
    } 

    @FXML
    private void btndeleteclick(ActionEvent event) {
        LayananModel s= new LayananModel();       
        s=tbvlayanan.getSelectionModel().getSelectedItem();
        if(s==null){
            new Alert(Alert.AlertType.WARNING,"Pilih dulu data yang ingin dihapus",ButtonType.OK).showAndWait();
            return;
        }
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Mau dihapus?",ButtonType.YES,ButtonType.NO);
        a.showAndWait();
        if(a.getResult()==ButtonType.YES){
           if(FXMLDocumentController.dtlayanan.delete(s.getKodelayanan())){
               Alert b=new Alert(Alert.AlertType.INFORMATION,"Data berhasil dihapus", ButtonType.OK);
               b.showAndWait();
           } else {
               Alert b=new Alert(Alert.AlertType.ERROR,"Data gagal dihapus", ButtonType.OK);
               b.showAndWait();               
           }    
           showdata();           
           btnfirstclick(event);       
        }
    }

    @FXML
    private void btnupdateclick(ActionEvent event) {
        LayananModel s= new LayananModel();
        s=tbvlayanan.getSelectionModel().getSelectedItem();
        if(s==null){
            new Alert(Alert.AlertType.WARNING,"Pilih dulu data yang ingin diubah",ButtonType.OK).showAndWait();
            return;
        }
        try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputLayanan.fxml"));
        Parent root = (Parent)loader.load();
        FXMLInputLayananController isidt=(FXMLInputLayananController)loader.getController();
        isidt.execute(s);
        Scene scene = new Scene(root);
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);
        stg.setIconified(false);
        stg.setScene(scene);
        stg.showAndWait();
        } catch (IOException e){   e.printStackTrace();   }
        showdata();
        btnfirstclick(event);
    }

    @FXML
    private void btninsertclick(ActionEvent event) {
        try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputLayanan.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);
        stg.setIconified(false);
        stg.setScene(scene);
        stg.showAndWait();
        } catch (IOException e){
            e.printStackTrace();   }
        showdata();
        btnfirstclick(event);
    }

    @FXML
    private void btnprevclick(ActionEvent event) {
        tbvlayanan.getSelectionModel().selectAboveCell();
        showgambar();
        tbvlayanan.requestFocus();
    }

    @FXML
    private void btnnextclick(ActionEvent event) {
        tbvlayanan.getSelectionModel().selectBelowCell();
        showgambar();
        tbvlayanan.requestFocus();
    }

    @FXML
    private void btnfirstclick(ActionEvent event) {
        tbvlayanan.getSelectionModel().selectFirst();
        showgambar();
        tbvlayanan.requestFocus();
    }

    @FXML
    private void btnlastclick(ActionEvent event) {
        tbvlayanan.getSelectionModel().selectLast();
        showgambar();
        tbvlayanan.requestFocus();
    }
    
}
