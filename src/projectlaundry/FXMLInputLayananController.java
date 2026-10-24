/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectlaundry;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LIKMI
 */
public class FXMLInputLayananController implements Initializable {

    boolean editdata = false;

    @FXML
    private Button btncancel;
    @FXML
    private Button btnsave;
    @FXML
    private Button btnpilih;
    @FXML
    private TextField txtkodelayanan;
    @FXML
    private TextField txtnamalayanan;
    @FXML
    private TextField txtsatuan;
    @FXML
    private TextField txttarif;
    @FXML
    private TextField txtestimasi;
    @FXML
    private TextField txtgambar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    // Dipanggil dari FXMLLayananController saat tombol Ubah diklik,
    // untuk mengisi form dengan data layanan yang dipilih di tabel.
    public void execute(LayananModel d){
        if(!d.getKodelayanan().isEmpty()){
            editdata=true;
            txtkodelayanan.setText(d.getKodelayanan());
            txtnamalayanan.setText(d.getNamalayanan());
            txtsatuan.setText(d.getSatuan());
            txttarif.setText(String.valueOf(d.getTarif()));
            txtestimasi.setText(String.valueOf(d.getEstimasi()));
            txtgambar.setText(d.getGambar());
            txtkodelayanan.setEditable(false);
            txtnamalayanan.requestFocus();
        }
    }

    // Tombol "..." untuk pilih file gambar layanan
    @FXML
    private void btnpilihclick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih File Gambar");

        Stage stage = (Stage) txtgambar.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // simpan sebagai images\namafile, jadi filenya harus ada di folder images project
            txtgambar.setText("images\\" + selectedFile.getName());
        }
    }

    @FXML
    private void btnsaveclick(ActionEvent event) {
        LayananModel n=new LayananModel();
        n.setKodelayanan(txtkodelayanan.getText().trim());
        n.setNamalayanan(txtnamalayanan.getText().trim());
        n.setSatuan(txtsatuan.getText().trim());
        n.setGambar(txtgambar.getText().trim());
        if(n.getKodelayanan().isEmpty() || n.getNamalayanan().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Kode dan nama layanan wajib diisi",ButtonType.OK).showAndWait();
            return;
        }
        try {
            n.setTarif(Double.parseDouble(txttarif.getText()));
            n.setEstimasi(Integer.parseInt(txtestimasi.getText()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,"Tarif dan estimasi harus berupa angka",ButtonType.OK).showAndWait();
            return;
        }

        FXMLDocumentController.dtlayanan.setLayananModel(n);
        if(editdata){
            if(FXMLDocumentController.dtlayanan.update()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil diubah",ButtonType.OK);
               a.showAndWait();   
               btncancelclick(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal diubah",ButtonType.OK);
                a.showAndWait(); 
            }

        }else if(FXMLDocumentController.dtlayanan.validasi(n.getKodelayanan())<=0){
            if(FXMLDocumentController.dtlayanan.insert()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil disimpan",ButtonType.OK);
               a.showAndWait();            
               btncancelclick(event);
            } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal disimpan",ButtonType.OK);
               a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data sudah ada",ButtonType.OK);
            a.showAndWait();
            txtkodelayanan.requestFocus();
        }
    }

    @FXML
    private void btncancelclick(ActionEvent event) {
        // setelah simpan/ubah selesai, form kembali ke mode tambah
        editdata=false;
        txtkodelayanan.setEditable(true);
        txtkodelayanan.setText("");        
        txtnamalayanan.setText("");
        txtsatuan.setText("");
        txttarif.setText("");       
        txtestimasi.setText("");  
        txtgambar.setText("");
        txtkodelayanan.requestFocus();
    }
    
}
