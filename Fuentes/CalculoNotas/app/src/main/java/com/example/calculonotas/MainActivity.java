package com.example.calculonotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.calculonotas.dto.Nota;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Nota> notas = new ArrayList<>();
    private EditText notaTxt;
    private EditText porcentajeTxt;
    private ListView notasLv;
    private Button agregarBtn;
    private Button limpiarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.notaTxt = findViewById(R.id.notaTxt);
        this.porcentajeTxt  = findViewById(R.id.porcentajeTxt);
        this.notasLv = findViewById(R.id.notasLv);
        this.agregarBtn = findViewById(R.id.agregarBtn);
        this.limpiarBtn = findViewById(R.id.limpiarBtn);
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List <String> errores = new ArrayList<>();
                double nota = 0;
                int porcentaje = 0;
                try {
                    nota = Double.parseDouble(notaTxt.getText().toString());
                    if(nota < 1.0 || nota > 7.0){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    errores.add("La nota debe tener un valor entre 1.0 y 7.0");
                }
                try {
                    porcentaje = Integer.parseInt(porcentajeTxt.getText().toString());
                    if(porcentaje < 1 || porcentaje > 100){
                        throw new NumberFormatException();
                    }
                }catch(NumberFormatException ex){
                    errores.add("El número debe ser entre 1 y 100");
                }
                if(errores.isEmpty()){
                    Nota n = new Nota();
                    n.setValor(nota);
                    n.setPorcentaje(porcentaje);
                    notas.add(n);
                }else{
                    mostrarErrores(errores);
                 }
            }
        });
    }
    private void mostrarErrores(List <String> errores){
        String mensaje ="";
        for(String e:errores){
            mensaje+= "-" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validación")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .create()
                .show();
    }
}