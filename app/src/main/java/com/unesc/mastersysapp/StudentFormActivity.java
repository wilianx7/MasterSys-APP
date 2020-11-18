package com.unesc.mastersysapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.unesc.mastersysapp.Models.Student;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.Services.StudentService;
import com.unesc.mastersysapp.util.Mask;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentFormActivity extends AppCompatActivity {
    private TextInputEditText student_input, date_birth_input, telephone_input,
            email_input, observation_input, address_input, number_input, complement_input,
            district_input, city_input, country_input, cep_input;
    private MaterialAutoCompleteTextView sex_cbx, states_cbx;
    private String[] sex = {"Masculino", "Feminino"},
    states = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
            "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
    private MaterialButton register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        student_input =  findViewById(R.id.student_input);
        date_birth_input = findViewById(R.id.date_birth_input);
        date_birth_input.addTextChangedListener(Mask.insert("##/##/####", date_birth_input));
        sex_cbx = findViewById(R.id.sex_cbx);
        ArrayAdapter<String> adapter_sex = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sex);
        sex_cbx.setAdapter(adapter_sex);
        telephone_input = findViewById(R.id.telephone_input);
        telephone_input.addTextChangedListener(Mask.insert("(##)#####-####", telephone_input));
        email_input = findViewById(R.id.email_input);
        observation_input = findViewById(R.id.observation_input);
        address_input = findViewById(R.id.address_input);
        number_input = findViewById(R.id.number_input);
        complement_input = findViewById(R.id.complement_input);
        district_input = findViewById(R.id.district_input);
        city_input = findViewById(R.id.city_input);
        states_cbx = findViewById(R.id.states_cbx);
        ArrayAdapter<String> adapter_states = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);
        states_cbx.setAdapter(adapter_states);
        country_input = findViewById(R.id.country_input);
        cep_input = findViewById(R.id.cep_input);
        cep_input.addTextChangedListener(Mask.insert("#####-###", cep_input));
        register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    String data = date_birth_input.getText().toString();
                    String[] d = data.split("/");
                    String data_formatted = d[2] + "-" + d[1] + "-" + d[0];
                    Integer number = !number_input.getText().toString().isEmpty() ?
                            Integer.valueOf(number_input.getText().toString()) :
                            0;

                    Student student = new Student();
                    student.name = "";
                    student.birth_date = "";
                    student.gender = "";
                    student.phone = "";
                    student.email = "";
                    student.observation = "";
                    student.address = "";
                    student.cep = "";
                    student.city = "";
                    student.complement = "";
                    student.district = "";
                    student.number = "";
                    student.state = "";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Service.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    StudentService studentService = retrofit.create(StudentService.class);
                    final String json  =  "{\"student_data\": " +
                            "{\"name\":\""+student_input.getText().toString()+ "\"," +
                            "\"birth_date\":\""+data_formatted+ "\"," +
                            "\"gender\":\""+sex_cbx.getText().toString()+ "\"," +
                            "\"email\":\""+email_input.getText().toString()+ "\"," +
                            "\"observation\":\""+observation_input.getText().toString()+ "\"," +
                            "\"phone\":\""+telephone_input.getText().toString()+ "\"}," +
                            "\"address_data\": " +
                            "{\"description\":\""+address_input.getText().toString()+ "\"," +
                            "\"cep\":\""+cep_input.getText().toString()+ "\"," +
                            "\"city\":\""+city_input.getText().toString()+ "\"," +
                            "\"complement\":\""+complement_input.getText().toString()+ "\"," +
                            "\"district\":\""+district_input.getText().toString()+ "\"," +
                            "\"number\":\""+number+ "\"," +
                            "\"state\":\""+states_cbx.getText().toString()+
                            "\"}}";
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    Call<Student> request = studentService.create(body);

                    request.enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(StudentFormActivity.this, "Aluno criado com sucesso", Toast.LENGTH_LONG).show();
                                clearFields();
                            }else{
                                Toast.makeText(StudentFormActivity.this, "Ocorreu um problema ao criar aluno!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Toast.makeText(StudentFormActivity.this, "Erro ao conectar na API", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Verifica os campos, se estão preenchidos corretos
     */
    private boolean checkRequiredFields(){
        if(student_input.getText().toString().isEmpty()){
            student_input.setError("Informe o nome do aluno!");
            return false;
        }else if(date_birth_input.getText().toString().isEmpty() || date_birth_input.getText().toString().length() < 10){
            date_birth_input.setError("Informe uma data válida!");
            return false;
        }else if(sex_cbx.getText().toString().isEmpty()){
            sex_cbx.setError("Selecione o sexo");
            return false;
        }else if(address_input.getText().toString().isEmpty()){
            address_input.setError("Informe o endereço!");
            return false;
        }else if(district_input.getText().toString().isEmpty()){
            district_input.setError("Informe o bairro!");
            return false;
        }else if(states_cbx.getText().toString().isEmpty()){
            states_cbx.setError("Informe o estado!");
            return false;
        }else if(city_input.getText().toString().isEmpty()){
            city_input.setError("Informe a cidade!");
            return false;
        }else if(country_input.getText().toString().isEmpty()){
            country_input.setError("Informe a País!");
            return false;
        }

        return true;
    }

    /**
     * Limpa os campos preenchidos
     */
    private void clearFields() {
        student_input.setText("");
        date_birth_input.setText("");
        sex_cbx.setListSelection(0);
        telephone_input.setText("");
        email_input.setText("");
        observation_input.setText("");
        address_input.setText("");
        number_input.setText("");
        complement_input.setText("");
        district_input.setText("");
        city_input.setText("");
        states_cbx.setListSelection(0);
        country_input.setText("");
        cep_input.setText("");
        cep_input.setText("");
    }
}
