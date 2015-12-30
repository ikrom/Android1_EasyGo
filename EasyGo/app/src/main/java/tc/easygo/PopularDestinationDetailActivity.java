package tc.easygo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tc.easygo.adapter.FasilitasAdapter;
import tc.easygo.connection.PostRequest;
import tc.easygo.models.FasilitasModel;
import tc.easygo.models.PopularModel;

public class PopularDestinationDetailActivity extends AppCompatActivity implements AsyncResponse {

    String[][] data = new String[][]{
            {"50 Barang yang harus dibawa saat traveling"},
            {"Tips Melipat dan mengorganisir pakaian serta barang lain"},
            {"Tips mengatasi Jetlag"},
            {"Panduan sebelum melakukan Hiking"},
            {"Tips agar tidak gosong saat berlibur ke pantai"},
            {"Tips mengepak pakaian yang sederhana"},
            {"Daftar barang bawaan untuk anak-anak dan balita"},
            {"Daftar barang bawaan untuk anak-anak dan balita"}
    };

    public static String KEY_ITEM = "item";
    private PopularModel item;
    private ProgressDialog progress;

    private ListView lvFasilitas;
    private ArrayList<FasilitasModel> fasilitasItem;

    /*slide show*/
    ArrayList<String> gambarList;
    ViewFlipper viewFlipper;

    private TextView tvNamaWisata,tvKetinggian,tvKecamatan,tvJenisWisata,tvJamBuka,tvHarga,tvWaktuTerbaik,tvDitempuhMenggunakan,tvDurasiRute,tvDeskripsi,tvJumlahReview;
    private RatingBar rbDetailRating, rbDetailPemandangan, rbDetailKemudahanAkses,rbDetailFasilitas, rbDetailPengelolaan, rbDetailHarga;
    private String cek,hotelJambuka,namaWisata,ketinggian,kecamatan,jenisWisata,jamBuka,harga,waktuTerbaik,ditempuhMenggunakan,durasiRute,deskripsi,jumlahReview;
    private float ratingTotal, ratingPemandangan, ratingKemudahanAkses,ratingFasilitas, ratingPengelolaan, ratingHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_destination_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*slideshow*/
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        gambarList = new ArrayList<String>();
        // handler to set duration and to upate animation
        final Handler mHandler = new Handler();
        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow();
            }
        };
        int delay = 500;
        int period = 4000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                mHandler.post(mUpdateResults);
            }
        }, delay, period);


        item = (PopularModel) getIntent().getSerializableExtra(KEY_ITEM);

        final String id = String.valueOf(item.getId());
        final String idUser = String.valueOf(1);

        //lvFasilitas = (ListView)findViewById(R.id.lv_Fasilitas);

        tvNamaWisata = (TextView) findViewById(R.id.tv_NamaWisata);
        tvKetinggian = (TextView)findViewById(R.id.tv_ketinggian);
        tvKecamatan = (TextView)findViewById(R.id.tv_kecamatan);
        tvJenisWisata = (TextView)findViewById(R.id.tv_JenisWisata);
        tvJamBuka = (TextView)findViewById(R.id.tv_JamBuka);
        tvHarga = (TextView)findViewById(R.id.tv_Harga);
        tvWaktuTerbaik = (TextView)findViewById(R.id.tv_WaktuTerbaik);
        tvDitempuhMenggunakan = (TextView)findViewById(R.id.tv_DitempuhMenggunakan);
        tvDurasiRute = (TextView)findViewById(R.id.tv_DurasiRute);
        tvDeskripsi = (TextView)findViewById(R.id.tv_Deskripsi);
        tvJumlahReview = (TextView)findViewById(R.id.tv_JumlahReview);

        rbDetailRating = (RatingBar)findViewById(R.id.rb_DetailRating);
        rbDetailPemandangan = (RatingBar)findViewById(R.id.rb_DetailRatingPemandangan);
        rbDetailKemudahanAkses = (RatingBar)findViewById(R.id.rb_DetailRatingKemudahanAkses);
        rbDetailFasilitas = (RatingBar)findViewById(R.id.rb_DetailRatingFasilitas);
        rbDetailPengelolaan = (RatingBar)findViewById(R.id.rb_DetailRatingPengelolaan);
        rbDetailHarga = (RatingBar)findViewById(R.id.rb_DetailRatingHarga);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Destinasi Populer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton ibNavPlan = (ImageButton) findViewById(R.id.ib_NavPlan);
        ImageButton ibNavTips = (ImageButton) findViewById(R.id.ib_NavTips);
        ImageButton ibNavGallery = (ImageButton) findViewById(R.id.ib_NavGallery);

        ibNavPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDestinationDetailActivity.this, PlanActivity.class);
                intent.putExtra("id_user",idUser);
                startActivity(intent);
            }
        });

        ibNavTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDestinationDetailActivity.this, TipsActivity.class);
                startActivity(intent);
            }
        });
        ibNavGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDestinationDetailActivity.this, GalleryActivity.class);
                Bundle bund = new Bundle();

                bund.putString("id",id);
                bund.putString("id_user",idUser);

                intent.putExtras(bund);
                startActivity(intent);
            }
        });

        //post data
        GetDataPost(id, idUser);

        //lvFasilitas = (ListView)findViewById(R.id.lv_Fasilitas);
        /*fasilitasItem = new ArrayList<>();

        FasilitasModel fasilitas = null;

        for (int i = 0; i < data.length; i++){
            fasilitas = new FasilitasModel();
            fasilitas.setTextFasilitas(data[i][0]);
            fasilitasItem.add(fasilitas);
        }

        FasilitasAdapter adapter = new FasilitasAdapter(PopularDestinationDetailActivity.this, fasilitasItem);
        lvFasilitas.setAdapter(adapter);*/
    }

    private void GetDataPost(String idWisata, String idUser) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id", idWisata);
        registerParams.put("id_user", idUser);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("gettourismbyid");
        return;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("data");
            JSONObject postFinalObject = new JSONObject(Objectdata);

            //variable rating
            String ObjectRatingData = postFinalObject.getString("rating");

            if(!ObjectRatingData.equals("-")){
                JSONObject postFinalObjectRatingData = new JSONObject(ObjectRatingData);

                ratingPemandangan = (float)postFinalObjectRatingData.getDouble("pemandangan");
                ratingKemudahanAkses = (float)postFinalObjectRatingData.getDouble("kemudahan_akses");
                ratingFasilitas = (float)postFinalObjectRatingData.getDouble("fasilitas");
                ratingPengelolaan = (float)postFinalObjectRatingData.getDouble("pengelolaan");
                ratingHarga = (float)postFinalObjectRatingData.getDouble("harga");

                ratingTotal = (float) postFinalObject.getDouble("rating_total");

                //rating total
                rbDetailRating.setRating(ratingTotal);
                //rating detail
                rbDetailPemandangan.setRating(ratingPemandangan);
                rbDetailKemudahanAkses.setRating(ratingKemudahanAkses);
                rbDetailFasilitas.setRating(ratingFasilitas);
                rbDetailPengelolaan.setRating(ratingPengelolaan);
                rbDetailHarga.setRating(ratingHarga);
            }
            else if (ObjectRatingData.equals("-")){
                ratingTotal=0;
                rbDetailRating.setRating(ratingTotal);
            }


            //variable review
            String objectReview = postFinalObject.getString("review");
            if(!objectReview.equals("-")){
                JSONObject reviewData = new JSONObject(objectReview);
                String objectUser = reviewData.getString("user");
                JSONObject userData = new JSONObject(objectUser);

                //user
                TextView tvReviewerNama = (TextView)findViewById(R.id.tv_ReviewerNama);
                TextView tvReviewerKota = (TextView)findViewById(R.id.tv_ReviewerKota);
                TextView tvReviewerNegara = (TextView)findViewById(R.id.tv_ReviewerNegara);
                TextView tvReviewerJumlahReviewer = (TextView)findViewById(R.id.tv_ReviewerJumlahReview);
                TextView tvReviewerJumlahReviewBermanfaat = (TextView)findViewById(R.id.tv_JumlahReviewBermanfaat);

                String reviewerNama = userData.getString("nama");
                String reviewerKota = userData.getString("kota");
                String reviewerNegara = userData.getString("negara");
                String reviewerJumlahReviewer = userData.getString("jumlah_review");
                String reviewerJumlahReviewBermanfaat = userData.getString("jumlah_review_bermanfaat");

                tvReviewerNama.setText(reviewerNama);
                tvReviewerKota.setText(reviewerKota);
                tvReviewerNegara.setText(reviewerNegara);
                tvReviewerJumlahReviewer.setText(reviewerJumlahReviewer+" ulasan");
                tvReviewerJumlahReviewBermanfaat.setText(reviewerJumlahReviewBermanfaat+" ulasan bermanfaat");

                //review
                TextView tvReviewerJudul = (TextView)findViewById(R.id.tv_ReviewerJudul);
                TextView tvReviewerTanggal = (TextView)findViewById(R.id.tv_ReviewerTanggal);
                TextView tvReviewerDeskripsi = (TextView)findViewById(R.id.tv_ReviewerDeskripsi);
                TextView tvReviewerBermanfaat = (TextView)findViewById(R.id.tv_ReviewerBermanfaat);
                RatingBar rbReviewerRating = (RatingBar)findViewById(R.id.rb_ReviewerRating);

                String reviewerJudul = reviewData.getString("judul");
                String reviewerTanggal = reviewData.getString("tanggal");
                String reviewerDeskripsi = reviewData.getString("deskripsi");
                String reviewerJumlahReview = reviewData.getString("judul");
                String reviewerBermanfaat = reviewData.getString("bermanfaat");
                Float reviewerRating = (float)reviewData.getDouble("rating");

                tvReviewerJudul.setText(reviewerJudul);
                tvReviewerTanggal.setText(reviewerTanggal);
                tvReviewerDeskripsi.setText(reviewerDeskripsi);
                tvReviewerBermanfaat.setText(reviewerBermanfaat);
                rbReviewerRating.setRating(reviewerRating);
            }

            //variable tempat wisata terdekat
            String objectTempatWisata = postFinalObject.getString("tempat_wisata");
            JSONObject tempatWisataData = new JSONObject(objectTempatWisata);

            TextView tvTempatWisataNama = (TextView)findViewById(R.id.tv_TempatWisataNama);
            TextView tvTempatWisataAlamat = (TextView)findViewById(R.id.tv_TempatWisataAlamat);
            TextView tvTempatWisataTelp = (TextView)findViewById(R.id.tv_TempatWisataTelp);
            TextView tvTempatWisataJamBuka = (TextView)findViewById(R.id.tv_TempatWisataJamBuka);
            TextView tvTempatWisataWebsite = (TextView)findViewById(R.id.tv_TempatWisataWebsite);
            RatingBar rbTempatWisataRating = (RatingBar)findViewById(R.id.rb_TempatWisataRating);

            String tempatWisataNama = tempatWisataData.getString("nama");
            String tempatWisataAlamat = tempatWisataData.getString("alamat");
            String tempatWisataTelp = tempatWisataData.getString("notelp");
            String tempatWisataJamBuka = tempatWisataData.getString("jam_buka");
            String tempatWisataWebsite = tempatWisataData.getString("website");
            Float tempatWisataRating = (float)tempatWisataData.getDouble("rating");

            tvTempatWisataNama.setText(tempatWisataNama.toUpperCase());
            tvTempatWisataAlamat.setText(tempatWisataAlamat);
            tvTempatWisataTelp.setText("Phone: "+tempatWisataTelp);
            tvTempatWisataJamBuka.setText("Jam buka: "+tempatWisataJamBuka);
            tvTempatWisataWebsite.setText(tempatWisataWebsite);
            rbTempatWisataRating.setRating(tempatWisataRating);


            //variable hotel
            String objectHotel = postFinalObject.getString("hotel");
            JSONObject hotelData = new JSONObject(objectHotel);

            TextView tvHotelNama = (TextView)findViewById(R.id.tv_HotelNama);
            TextView tvHotelAlamat = (TextView)findViewById(R.id.tv_HotelAlamat);
            TextView tvHotelTelp = (TextView)findViewById(R.id.tv_HotelTelp);
            TextView tvHotelHarga = (TextView)findViewById(R.id.tv_HotelHarga);
            TextView tvHotelJamBuka = (TextView)findViewById(R.id.tv_HotelJamBuka);
            TextView tvHotelWebsite = (TextView)findViewById(R.id.tv_HotelWebsite);
            RatingBar rbHotelRating = (RatingBar)findViewById(R.id.rb_HotelRating);

            String hotelNama = hotelData.getString("nama");
            String hotelAlamat = hotelData.getString("alamat");
            String hotelTelp = hotelData.getString("notelp");
            String hotelHarga = hotelData.getString("harga");
            String hotelJamBuka = hotelData.getString("jam_buka");
            String hotelWebsite = hotelData.getString("website");
            Float hotelRating = (float)hotelData.getDouble("rating");



            tvHotelNama.setText(hotelNama.toUpperCase());
            tvHotelAlamat.setText(hotelAlamat);
            tvHotelTelp.setText("Phone: "+hotelTelp);
            if(hotelJamBuka== "-")cekLog(hotelJambuka);

            tvHotelJamBuka.setText("Jam buka: "+hotelJamBuka);
            tvHotelHarga.setText("Harga: "+hotelHarga);
            tvHotelWebsite.setText(hotelWebsite);
            rbHotelRating.setRating(hotelRating);

            //variable restaurant
            String objectRestaurant = postFinalObject.getString("restaurant");
            JSONObject restaurantData = new JSONObject(objectRestaurant);

            TextView tvRestaurantNama = (TextView)findViewById(R.id.tv_RestaurantNama);
            TextView tvRestaurantAlamat = (TextView)findViewById(R.id.tv_RestaurantAlamat);
            TextView tvRestaurantTelp = (TextView)findViewById(R.id.tv_RestaurantTelp);
            TextView tvRestaurantHarga = (TextView)findViewById(R.id.tv_RestaurantHarga);
            TextView tvRestaurantJamBuka = (TextView)findViewById(R.id.tv_RestaurantJamBuka);
            TextView tvRestaurantWebsite = (TextView)findViewById(R.id.tv_RestaurantWebsite);
            RatingBar rbRestaurantRating = (RatingBar)findViewById(R.id.rb_RestaurantRating);

            String RestaurantNama = restaurantData.getString("nama");
            String RestaurantAlamat = restaurantData.getString("alamat");
            String RestaurantTelp = restaurantData.getString("notelp");
            String RestaurantHarga = restaurantData.getString("harga");
            String RestaurantJamBuka = restaurantData.getString("jam_buka");
            String RestaurantWebsite = restaurantData.getString("website");
            Float RestaurantRating = (float)restaurantData.getDouble("rating");

            tvRestaurantNama.setText(RestaurantNama.toUpperCase());
            tvRestaurantAlamat.setText(RestaurantAlamat);
            tvRestaurantTelp.setText("Phone: "+RestaurantTelp);
            tvRestaurantJamBuka.setText("Jam buka: "+RestaurantJamBuka);
            tvRestaurantHarga.setText("Harga: " + RestaurantHarga);
            tvRestaurantWebsite.setText(RestaurantWebsite);
            rbRestaurantRating.setRating(RestaurantRating);

            //variabel gambar
            JSONArray parentArrayGambar = postFinalObject.getJSONArray("gambar");
            for(int i=0;i<parentArrayGambar.length();i++){
                JSONObject finalObjectGambarList = parentArrayGambar.getJSONObject(i);
                String ObjectGambarList = finalObjectGambarList.getString("link");

                gambarList.add(ObjectGambarList.toString());
            }
            /*ArrayList to Array Conversion */
            String linkGambarString[] = new String[gambarList.size()];
            for(int j =0;j<gambarList.size();j++){
                linkGambarString[j] = gambarList.get(j);
            }
            //cekLog(gambarList.toString());
            setFlipperImage( gambarList);

            //variable getString
            namaWisata = postFinalObject.getString("nama");
            ketinggian = postFinalObject.getString("ketinggian");
            kecamatan = postFinalObject.getString("kecamatan");
            jenisWisata = postFinalObject.getString("jenis_wisata");
            jamBuka = postFinalObject.getString("jam_buka");
            harga = postFinalObject.getString("harga");
            waktuTerbaik = postFinalObject.getString("waktu_terbaik");
            ditempuhMenggunakan = postFinalObject.getString("ditempuh_menggunakan");
            durasiRute = postFinalObject.getString("durasi_rute");
            deskripsi = postFinalObject.getString("deskripsi");
            jumlahReview = postFinalObject.getString("jumlah_review");



            //setText
            tvNamaWisata.setText(namaWisata);
            tvKetinggian.setText(ketinggian);
            tvKecamatan.setText("Kecamatan " + kecamatan + ", Banyuwangi");
            tvJenisWisata.setText(jenisWisata);

            tvJamBuka.setText(jamBuka);
            tvHarga.setText(harga);
            tvWaktuTerbaik.setText(waktuTerbaik);
            tvDitempuhMenggunakan.setText(ditempuhMenggunakan);
            tvDurasiRute.setText(durasiRute);
            tvDeskripsi.setText(deskripsi);
            tvJumlahReview.setText("Penilaian dari "+jumlahReview+" pengguna");



        } catch (JSONException e) {
            e.printStackTrace();
            //cekLog("Gangguan koneksi internet");
            cekLog(String.valueOf(e));
        }
    }

    public void cekLog(String iniCek){
        Log.d("asd", iniCek);

        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage(iniCek)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void setFlipperImage(ArrayList<String> actorsList) {

        for(int i=0;i<actorsList.size();i++){
            Log.i("Set Filpper Called", actorsList.get(i).toString()+"");
            ImageView image = new ImageView(getApplicationContext());
            // image.setBackgroundResource(res);
            Picasso.with(PopularDestinationDetailActivity.this)
                    .load(actorsList.get(i).toString())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(image);
            viewFlipper.addView(image);
        }
    }
    private void AnimateandSlideShow() {
        viewFlipper.showNext();
    }
    //get response
        /*String postFinalJson = output.toString();
        JSONObject postParentObject = new JSONObject();
        try {
            postParentObject = new JSONObject(postFinalJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String status = "";
        String message = "";
        try {
            status = postParentObject.getString("status");
            message = postParentObject.getString("message");
            //result = postParentObject.getString("result");
        } catch (JSONException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Koneksi Terputus")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            e.printStackTrace();
            //register.setEnabled(true);
            //progress.dismiss();
            return;
        }
        try{
            JSONArray postParentArray = postParentObject.getJSONArray("data");
            JSONObject postFinalObject = postParentArray.getJSONObject(0);
            namaWisata = postFinalObject.getString("nama");

        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    //this.statusField.setText(output);
    //register.setEnabled(true);
    //progress.dismiss();
//        return;

}
