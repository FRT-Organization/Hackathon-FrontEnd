package solutions.frt.trashman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class basic_menu extends AppCompatActivity {

    public void mapsActivity(View view){
        Intent maps =new Intent(this,MapsActivity.class);
        startActivity(maps);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_menu);
    }
}
