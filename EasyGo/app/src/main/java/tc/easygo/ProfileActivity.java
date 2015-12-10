package tc.easygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button btnEditTag = (Button)findViewById(R.id.btn_edit_tag);
        TextView tvTeman = (TextView)findViewById(R.id.tv_teman);

        btnEditTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this,ProfilEditTag.class);
                startActivity(intent);
            }
        });
        tvTeman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ProfileActivity.this,ProfilOtherActivity.class);
                startActivity(intent);
            }
        });
    }
}
