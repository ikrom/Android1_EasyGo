package tc.easygo.models;

import java.io.Serializable;

/**
 * Created by ikrom on 12/30/2015.
 */
public class RencanaModel implements Serializable {
    private String id;
    private String id_user;
    private String jenis_rencana;
    private String id_wisata;
    private String id_event;
    private String nama_wisata;
    private String nama_event;
    private String gambar_wisata;
    private String gambar_event;
    private String catatan;
    private String tanggal;
    private String waktu;
    private boolean ingatkan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJenis_rencana() {
        return jenis_rencana;
    }

    public void setJenis_rencana(String jenis_rencana) {
        this.jenis_rencana = jenis_rencana;
    }

    public String getId_wisata() {
        return id_wisata;
    }

    public void setId_wisata(String id_wisata) {
        this.id_wisata = id_wisata;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getNama_event() {
        return nama_event;
    }

    public void setNama_event(String nama_event) {
        this.nama_event = nama_event;
    }

    public String getGambar_wisata() {
        return gambar_wisata;
    }

    public void setGambar_wisata(String gambar_wisata) {
        this.gambar_wisata = gambar_wisata;
    }

    public String getGambar_event() {
        return gambar_event;
    }

    public void setGambar_event(String gambar_event) {
        this.gambar_event = gambar_event;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public boolean isIngatkan() {
        return ingatkan;
    }

    public void setIngatkan(boolean ingatkan) {
        this.ingatkan = ingatkan;
    }
}
