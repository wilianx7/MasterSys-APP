package com.unesc.mastersysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.unesc.mastersysapp.Models.User;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.Services.UserService;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFormActivity extends AppCompatActivity {
    MaterialButton register_button;
    TextInputEditText username_input, password_input, password_confirmation_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        register_button = findViewById(R.id.register_button);
        password_confirmation_input = findViewById(R.id.password_confirmation_input);

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

                    UserService userService = retrofit.create(UserService.class);
                    final String json  =  "{\"login\": \""+username_input.getText().toString()+"\",\"password\": \""+password_input.getText().toString()+"\"}";
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    Call<User> request = userService.create(body);

                    request.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(UserFormActivity.this, "Usuário criado com sucesso", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UserFormActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(UserFormActivity.this, "Erro ao criar usuário. Verifique um outro nome", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(UserFormActivity.this, "Erro ao conectar na API", Toast.LENGTH_LONG).show();
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
        if(username_input.getText().toString().isEmpty() || username_input.getText().toString().contains(" ")){
            username_input.setError("Usuário não informado ou contêm espaço");
            return false;
        }else if(password_input.getText().toString().isEmpty() || password_input.getText().toString().contains(" ")){
            password_input.setError("Senha não informada ou contêm espaço");
            return false;
        }else if(!password_confirmation_input.getText().toString().equals(password_input.getText().toString())){
            password_confirmation_input.setError("As senhas não são iguais");
            return false;
        }
        return true;
    }
}