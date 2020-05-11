package com.example.segundo_parcial_jorge_torres;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.segundo_parcial_jorge_torres.BaseDeDatos.DBControlador;
import com.example.segundo_parcial_jorge_torres.Clases.Adaptador;
import com.example.segundo_parcial_jorge_torres.Clases.Persona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LifecycleObserver, View.OnClickListener{

    private ArrayList<Persona> lista = new ArrayList<Persona>();
    private ListView listView;
    private FloatingActionButton btnAdd;
    private Adaptador adaptador;
    private DBControlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        controlador = new DBControlador(getApplicationContext());

        lista = controlador.optenerRegistros();
        listView.setAdapter(adaptador = new Adaptador(this, lista));
        registerForContextMenu(listView);
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Persona> listaaux = controlador.optenerRegistros();
                Adaptador adap = new Adaptador(this, listaaux);
                listView.setAdapter(adap);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Intent i = new Intent(this, AgregarActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idBuscar:
                Intent i = new Intent(this, BuscarActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_listado, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.idEditar:
                Intent intent = new Intent(this, EditarActivity.class);
                intent.putExtra("indice", menuInfo.position);
                startActivityForResult(intent, 2);
                return true;
            case R.id.idEliminar:
                int retorno = controlador.borrarRegistro(lista.get(menuInfo.position));
                if (retorno == 1) {
                    Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
                    lista = controlador.optenerRegistros();
                    adaptador = new Adaptador(this, lista);
                    listView.setAdapter(adaptador);
                } else {
                    Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
