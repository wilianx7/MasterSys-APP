package com.unesc.mastersysapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.unesc.mastersysapp.Models.Modality;
import com.unesc.mastersysapp.Models.User;
import com.unesc.mastersysapp.Services.ModalityService;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.Services.UserService;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModalityFormActivity extends AppCompatActivity {
    MaterialButton register_button;
    TextInputEditText modality_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modality_form);

        modality_input = findViewById(R.id.modality_input);
        register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    User user = new User();
                    user.password = "";
                    user.login = "";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Service.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ModalityService modalityService = retrofit.create(ModalityService.class);
                    final String json  =  "{\"name\": \""+modality_input.getText().toString()+"\"}";
                    System.out.println(json);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    Call<Modality> request = modalityService.create(body);

                    request.enqueue(new Callback<Modality>() {
                        @Override
                        public void onResponse(Call<Modality> call, Response<Modality> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(ModalityFormActivity.this, "Modalidade criada com sucesso", Toast.LENGTH_LONG).show();
                                modality_input.setText("");
                            }else{
                                Toast.makeText(ModalityFormActivity.this, "Erro ao criar modalidade. Verifique um outro nome", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Modality> call, Throwable t) {
                            Toast.makeText(ModalityFormActivity.this, "Erro ao criar modalidade. Verifique um outro nome" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Verifica os campos, se estão preenchidos corretos
     * @return
     */
    private boolean checkRequiredFields(){
        if(modality_input.getText().toString().isEmpty()){
            modality_input.setError("Informe um nome para a modalidade");
            return false;
        }

        return true;
    }
}
