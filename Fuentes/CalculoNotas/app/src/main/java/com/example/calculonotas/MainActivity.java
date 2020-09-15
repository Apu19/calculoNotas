package com.example.calculonotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculonotas.dto.Nota;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int porcentajeActual = 0;
    private List<Nota> notas = new ArrayList<>();
    private EditText notaTxt;
    private EditText porcentajeTxt;
    private ListView notasLv;
    private ArrayAdapter<Nota> notasAdapter;
    private Button agregarBtn;
    private Button limpiarBtn;
    private LinearLayout promedioLl;
    private TextView promedioTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.promedioLl = findViewById(R.id.promedioLl);
        this.promedioTxt = findViewById(R.id.promedioTxt);
        this.notaTxt = findViewById(R.id.notaTxt);
        this.porcentajeTxt = findViewById(R.id.porcentajeTxt);
        this.notasLv = findViewById(R.id.notasLv);
        this.notasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notas);
        this.notasLv.setAdapter(notasAdapter);
        this.agregarBtn = findViewById(R.id.agregarBtn);
        this.limpiarBtn = findViewById(R.id.limpiarBtn);
        this.limpiarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notaTxt.setText("");
                promedioTxt.setText("");
                porcentajeTxt.setText("");
                promedioLl.setVisibility(View.INVISIBLE);
                notas.clear();
                notasAdapter.notifyDataSetChanged();
                porcentajeActual = 0;
            }
        });
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();
                double nota = 0;
                int porcentaje = 0;
                try {
                    nota = Double.parseDouble(notaTxt.getText().toString());
                    if (nota < 1.0 || nota > 7.0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    errores.add("La nota debe tener un valor entre 1.0 y 7.0");
                }
                try {
                    porcentaje = Integer.parseInt(porcentajeTxt.getText().toString());
                    if (porcentaje < 1 || porcentaje > 100) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    errores.add("El número debe ser entre 1 y 100");
                }
                if (errores.isEmpty()) {
                    if (porcentajeActual + porcentaje > 100) {
                        Toast.makeText(MainActivity.this, "Está exediendo el limite de porcentaje, el maximo posible es 100"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Nota n = new Nota();
                        n.setValor(nota);
                        n.setPorcentaje(porcentaje);
                        notas.add(n);
                        notasAdapter.notifyDataSetChanged();
                        porcentajeActual += porcentaje;
                        mostrarPromedio();
                    }
                } else {
                    mostrarErrores(errores);
                }
            }
        });
    }
        private void mostrarPromedio () {
            double promedio = 0;
            for (Nota n : notas) {
                promedio += n.getValor() * n.getPorcentaje() / 100;
            }
            this.promedioTxt.setText(String.format("%.1f", promedio));
            if (promedio < 4.0) {
                this.promedioTxt.setTextColor(ContextCompat.getColor(this, R.color.red));
            } else {
                this.promedioTxt.setTextColor(ContextCompat.getColor(this, R.color.green));
            }
            this.promedioLl.setVisibility(View.VISIBLE);
        }

        private void mostrarErrores (List < String > errores) {
            String mensaje = "";
            for (String e : errores) {
                mensaje += "-" + e + "\n";
            }
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setTitle("Error de validación")
                    .setMessage(mensaje)
                    .setPositiveButton("Aceptar", null)
                    .create()
                    .show();
        }
    }
