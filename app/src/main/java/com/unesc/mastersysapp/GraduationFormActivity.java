package com.unesc.mastersysapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.unesc.mastersysapp.ModelList.ModalityList;
import com.unesc.mastersysapp.Models.Graduation;
import com.unesc.mastersysapp.Services.GraduationService;
import com.unesc.mastersysapp.Services.ModalityService;
import com.unesc.mastersysapp.Services.Service;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraduationFormActivity extends AppCompatActivity {
    private MaterialAutoCompleteTextView modality_cbx;
    private TextInputEditText graduation_input;
    private MaterialButton register_button;

    private String[] modalities;
    private Map<String,Integer> map_modalities = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduation);

        modality_cbx = findViewById(R.id.modality_cbx);
        findModalities();
        graduation_input = findViewById(R.id.graduation_input);

        register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    Graduation graduation = new Graduation();
                    graduation.modality_id = 0;
                    graduation.name = "";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Service.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    GraduationService studentService = retrofit.create(GraduationService.class);
                    final String json  =  "{\"modality_id\":\""+getIdModality(modality_cbx.getText().toString())+ "\"," +
                            "\"name\":\""+graduation_input.getText().toString()+
                            "\"}";
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    Call<Graduation> request = studentService.create(body);

                    request.enqueue(new Callback<Graduation>() {
                        @Override
                        public void onResponse(Call<Graduation> call, Response<Graduation> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(GraduationFormActivity.this, "Graduação salva com sucesso", Toast.LENGTH_LONG).show();
                                clearFields();
                            }else{
                                Toast.makeText(GraduationFormActivity.this, "Ocorreu um problema ao salvar graduação!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Graduation> call, Throwable t) {
                            Toast.makeText(GraduationFormActivity.this, "Erro ao conectar na API", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Busca todas as modalidades cadastrados
     */
    private void findModalities(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModalityService modalityService = retrofit.create(ModalityService.class);
        Call<ModalityList> request = modalityService.listModalities();

        request.enqueue(new Callback<ModalityList>() {
            @Override
            public void onResponse(Call<ModalityList> call, Response<ModalityList> response) {
                if(response.isSuccessful()) {
                    ModalityList modalityList = response.body();

                    if(modalityList.modalities.size() > 0) {
                        modalities = new String[modalityList.modalities.size()];

                        for(int i=0; i<modalityList.modalities.size(); i++) {
                            modalities[i] = modalityList.modalities.get(i).name;
                            map_modalities.put(modalityList.modalities.get(i).name,
                                    modalityList.modalities.get(i).id);
                        }
                        ArrayAdapter<String> adapter_modalities = new ArrayAdapter<String>(GraduationFormActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, modalities);
                        modality_cbx.setAdapter(adapter_modalities);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModalityList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    /**
     * Busca o ID da modalidae
     * @param name
     * @return id
     */
    private int getIdModality(final String name) {
        return map_modalities.get(name);
    }

    /**
     * Verifica os campos, se estão preenchidos corretamente
     */
    private boolean checkRequiredFields(){
        if(modality_cbx.getText().toString().isEmpty()){
            modality_cbx.setError("Seleciona uma modalidade!");
            return false;
        }else if(graduation_input.getText().toString().isEmpty()){
            graduation_input.setError("Informe a graduação!");
            return false;
        }

        return true;
    }

    /**
     * Limpa os campos preenchidos
     */
    private void clearFields() {
        modality_cbx.setText("Modalidade"); //avaliar remover seleção da combo.
        graduation_input.setText("");
    }
}
