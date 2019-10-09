package rafsanches.com.br.dmic_aula05_OO_reciclagemView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import rafsanches.com.br.dmic_aula05_OO_reciclagemView.R;

public class ListaLocaisActivity extends AppCompatActivity {

    private ListView locaisListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_locais);
        Intent origemIntent = getIntent();
        final List<String> locais = origemIntent.getStringArrayListExtra("historyPos");
        locaisListView = findViewById(R.id.locaisListView);
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, locais);
        locaisListView.setAdapter(adapter);
        locaisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locaisList = locaisListView.getItemAtPosition(position).toString();
                String lat = locaisList.split(",")[0].split(":")[1];
                String lon = locaisList.split(",")[1].split(":")[1];

                Uri gmmIntentUri = Uri.parse(String.format("geo:%s,%s", lat, lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

//                Intent intent = new Intent(ListaLocaisActivity.this, DetalhesLocalActivity.class);
//                intent.putExtra("local_escolhido", locais.get(position));
//                startActivity(intent);
            }
        });
        Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show();
    }

}
