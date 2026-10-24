/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectlaundry;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LIKMI
 */
public class DBLayanan {
    private LayananModel dt=new LayananModel();    
    public LayananModel getLayananModel(){ return(dt);}
    public void setLayananModel(LayananModel s){ dt=s;}
    
    public ObservableList<LayananModel>  Load() {
        try {
            ObservableList<LayananModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery
            ("Select kodelayanan, namalayanan, satuan, tarif, estimasi, gambar from layanan order by kodelayanan");
            int i = 1;
            while (rs.next()) {
                LayananModel d=new LayananModel();
                d.setKodelayanan(rs.getString("kodelayanan"));                
                d.setNamalayanan(rs.getString("namalayanan"));
                d.setSatuan(rs.getString("satuan"));
                d.setTarif(rs.getDouble("tarif"));                
                d.setEstimasi(rs.getInt("estimasi"));
                d.setGambar(rs.getString("gambar")); 
                tableData.add(d);                
                i++;            
            }
            con.tutupKoneksi();            
            return tableData;
        } catch (Exception e) {            
            e.printStackTrace();            
            return null;        
        }
    }

    // Search data layanan berdasarkan kode atau nama
    public ObservableList<LayananModel> CariLayanan(String kode, String nama) {
        try {
            ObservableList<LayananModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
            ("select kodelayanan, namalayanan, satuan, tarif, estimasi, gambar from layanan WHERE kodelayanan LIKE ? OR namalayanan LIKE ? order by kodelayanan");
            con.preparedStatement.setString(1, kode + "%");
            con.preparedStatement.setString(2, nama + "%");
            ResultSet rs = con.preparedStatement.executeQuery();
            int i = 1;
            while (rs.next()) {
                LayananModel d=new LayananModel();
                d.setKodelayanan(rs.getString("kodelayanan"));
                d.setNamalayanan(rs.getString("namalayanan"));
                d.setSatuan(rs.getString("satuan"));
                d.setTarif(rs.getDouble("tarif"));
                d.setEstimasi(rs.getInt("estimasi"));
                d.setGambar(rs.getString("gambar"));
                tableData.add(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int validasi(String nomor) {
        int val = 0;
        try {         
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
            ("select count(*) as jml from layanan where kodelayanan = ?");
            con.preparedStatement.setString(1, nomor);
            ResultSet rs = con.preparedStatement.executeQuery();
            while (rs.next()) {                
                val = rs.getInt("jml");            
            }            
            con.tutupKoneksi();
        } catch (SQLException e) {            
            e.printStackTrace();        
        }
        return val;
    }   
        
    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
            ("insert into layanan (kodelayanan, namalayanan, satuan, tarif, estimasi, gambar) values (?,?,?,?,?,?)");
            con.preparedStatement.setString(1, getLayananModel().getKodelayanan());           
            con.preparedStatement.setString(2, getLayananModel().getNamalayanan());
            con.preparedStatement.setString(3, getLayananModel().getSatuan());
            con.preparedStatement.setDouble(4, getLayananModel().getTarif());           
            con.preparedStatement.setInt(5, getLayananModel().getEstimasi());
            con.preparedStatement.setString(6, getLayananModel().getGambar()); 
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
     }    
    
    public boolean update() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
           ("update layanan set namalayanan = ?, satuan = ?, tarif = ?, estimasi = ?, gambar = ?  where  kodelayanan = ? ");
            con.preparedStatement.setString(1, getLayananModel().getNamalayanan());
            con.preparedStatement.setString(2, getLayananModel().getSatuan());
            con.preparedStatement.setDouble(3, getLayananModel().getTarif());
            con.preparedStatement.setInt(4, getLayananModel().getEstimasi());
            con.preparedStatement.setString(5, getLayananModel().getGambar());
            con.preparedStatement.setString(6, getLayananModel().getKodelayanan());
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        } 
    }
    
    
    public boolean delete(String nomor) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
           ("delete from layanan where kodelayanan  = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }
    }
}
