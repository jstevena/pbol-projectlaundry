/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectlaundry;

/**
 *
 * @author LIKMI
 */
public class LayananModel {
    private String kodelayanan, namalayanan, satuan, gambar;
    private double tarif;
    private int estimasi;

    public String getKodelayanan() {
        return kodelayanan;
    }

    public void setKodelayanan(String kodelayanan) {
        this.kodelayanan = kodelayanan;
    }

    public String getNamalayanan() {
        return namalayanan;
    }

    public void setNamalayanan(String namalayanan) {
        this.namalayanan = namalayanan;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    // estimasi pengerjaan dalam jam
    public int getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(int estimasi) {
        this.estimasi = estimasi;
    }
    
}
