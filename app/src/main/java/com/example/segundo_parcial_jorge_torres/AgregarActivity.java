package com.example.segundo_parcial_jorge_torres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segundo_parcial_jorge_torres.BaseDeDatos.DBControlador;
import com.example.segundo_parcial_jorge_torres.Clases.Persona;

public class AgregarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titulo;
    private EditText etCedula, etNombre, etSalario;
    private Spinner spEstrato, spNivel;
    private Button btGuardar, btCancelar;
    private DBControlador controlador;

    int estrato, nivelEducativo, indice, cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        titulo = findViewById(R.id.tvTitulo);
        etCedula = findViewById(R.id.edCedula);
        etNombre = findViewById(R.id.edNombre);
        etSalario = findViewById(R.id.edSalario);
        spEstrato = findViewById(R.id.spEstrato);
        spNivel = findViewById(R.id.spNivelEducativo);
        btGuardar = findViewById(R.id.btGuardar);
        btCancelar = findViewById(R.id.btCancelar);
        titulo.setText("Agregar");
        controlador = new DBControlador(getApplicationContext());

        String s1 [] = { "1","2","3","4","5","6"};
        String s2  [] = { "Bachillerato","Pregrado","Maestria","Doctorado"};
        
        ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,s1);
        spEstrato.setAdapter(a1);
        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,s2);
        spNivel.setAdapter(a2);
        
        spEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btGuardar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGuardar:
                try {
                    int cedula = etCedula.getText().toString().isEmpty() ? 0 : Integer.parseInt(etCedula.getText().toString());
                    int salario = etSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(etSalario.getText().toString());
                    Persona persona = new Persona(cedula, etNombre.getText().toString(), estrato, salario, nivelEducativo);
                    long retorno = controlador.agregarRegistro(persona);
                    if (retorno != -1) {
                        Toast.makeText(v.getContext(), "registro guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                    }
                    limpiarCampo();
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btCancelar:
                limpiarCampo();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idListado:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        etCedula.setText("");
        etNombre.setText("");
        etSalario.setText("");
    }
}
