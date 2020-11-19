package com.unesc.mastersysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.unesc.mastersysapp.ModelList.UserList;
import com.unesc.mastersysapp.Models.User;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.Services.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private MaterialButton sign_in_button;
    private TextInputEditText username_input, password_input;
    private MaterialTextView createAccount;
    private Map<String,String> users = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        sign_in_button = findViewById(R.id.sign_in_button);
        createAccount = findViewById(R.id.createAccount);

        //Busca todos os usuarios
        findUsers();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserFormActivity.class));
                //Busca todos os usuarios
                findUsers();
            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    if(isUserAuthenticated(username_input.getText().toString(), password_input.getText().toString())) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Usuário ou senha inválido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * Faz as validações necessárias para validar o usuário
     * @param name
     * @param password
     * @return
     */
    private boolean isUserAuthenticated(final String name, final String password){
        return users.containsKey(name) && (users.get(name).equals(password));
    }

    /**
     * Busca todos os usuarios cadastrados
     */
    private void findUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<UserList> request = userService.listUsers();

        request.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(response.isSuccessful()) {
                    UserList usersList = response.body();

                    //Adiciona todos os usuario no hashMap pra comparar depois
                    for(User user: usersList.users){
                        users.put(user.login,user.password);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    /**
     * Verifica os campos, se estão preenchidos corretos
     * @return
     */
    private boolean checkRequiredFields(){
        if(username_input.getText().toString().isEmpty() || username_input.getText().toString().contains(" ")){
            username_input.setError("Campo não foi informado ou contem espaço");
            return false;
        }else if(password_input.getText().toString().isEmpty() || password_input.getText().toString().contains(" ")){
            password_input.setError("Campo não foi informado ou contem espaço");
            return false;
        }
        return true;
    }
}