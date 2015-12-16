package tc.easygo.models;

/**
 * Created by ikromaulia on 12/14/2015.
 */
import java.io.Serializable;
public class PopularModel implements Serializable {
    private int id;
    private String nama;
    private String jenisWisata;
    private int jumlahReview;
    private float rataRating;
    private String gambar;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisWisata() {
        return jenisWisata;
    }

    public void setJenisWisata(String jenisWisata) {
        this.jenisWisata = jenisWisata;
    }

    public int getJumlahReview() {
        return jumlahReview;
    }

    public void setJumlahReview(int jumlahReview) {
        this.jumlahReview = jumlahReview;
    }

    public float getRataRating() {
        return rataRating;
    }

    public void setRataRating(float rataRating) {
        this.rataRating = rataRating;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
