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
import com.unesc.mastersysapp.List.ModalityList;
import com.unesc.mastersysapp.List.PlanList;
import com.unesc.mastersysapp.Models.Graduation;
import com.unesc.mastersysapp.Models.Matriculation;
import com.unesc.mastersysapp.Models.Plan;
import com.unesc.mastersysapp.Services.GraduationService;
import com.unesc.mastersysapp.Services.MatriculationService;
import com.unesc.mastersysapp.Services.ModalityService;
import com.unesc.mastersysapp.Services.PlanService;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.util.Mask;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatriculationFormActivity extends AppCompatActivity {
    private TextInputEditText cod_student, date_matriculation, day_pay, date_closing;
    private MaterialAutoCompleteTextView plans_cbx;
    private String[] plans;
    private Map<String,Integer> map_plans = new HashMap<String, Integer>();
    private MaterialButton register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriculation);

        cod_student = findViewById(R.id.cod_student);
        plans_cbx = findViewById(R.id.plans_cbx);
        findPlans();
        date_matriculation = findViewById(R.id.date_matriculation);
        date_matriculation.addTextChangedListener(Mask.insert("##/##/####", date_matriculation));
        day_pay = findViewById(R.id.day_pay);
        day_pay.addTextChangedListener(Mask.insert("##", day_pay));
        date_closing = findViewById(R.id.date_closing);
        date_closing.addTextChangedListener(Mask.insert("##/##/####", date_closing));

        register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    String dataM = date_matriculation.getText().toString();
                    String[] d = dataM.split("/");
                    String data_matriculation_formatted = d[2] + "-" + d[1] + "-" + d[0];

                    String data = date_closing.getText().toString();
                    String[] dd = data.split("/");
                    String date_closing_formatted = d[2] + "-" + d[1] + "-" + d[0];

                    if(date_closing.getText().toString().isEmpty()) {
                        date_closing_formatted = "";
                    }

                    Matriculation matriculation = new Matriculation();
                    matriculation.student_id = 0;
                    matriculation.plan_id = 0;
                    matriculation.due_date = 0;
                    matriculation.start_date = "";
                    matriculation.end_date = "";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Service.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MatriculationService matriculationService = retrofit.create(MatriculationService.class);
                    final String json  =  "{" +
                            "\"student_id\":\""+cod_student.getText().toString()+ "\"," +
                            "\"plan_id\":\""+ getIdPlan(plans_cbx.getText().toString()) + "\"," +
                            "\"due_date\":\""+ Integer.valueOf(day_pay.getText().toString()) + "\"," +
                            "\"start_date\":\""+ data_matriculation_formatted + "\"," +
                            "\"end_date\":\""+ date_closing_formatted +
                            "\"}";
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                    Call<Matriculation> request = matriculationService.create(body);

                    request.enqueue(new Callback<Matriculation>() {
                        @Override
                        public void onResponse(Call<Matriculation> call, Response<Matriculation> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(MatriculationFormActivity.this, "Matricula salva com sucesso", Toast.LENGTH_LONG).show();
                                clearFields();
                            }else{
                                Toast.makeText(MatriculationFormActivity.this, "Ocorreu um problema ao salvar Matricula!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Matriculation> call, Throwable t) {
                            Toast.makeText(MatriculationFormActivity.this, "Erro ao conectar na API", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Busca todos os planos cadastrados
     */
    private void findPlans(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlanService planService = retrofit.create(PlanService.class);
        Call<PlanList> request = planService.listPlans();

        request.enqueue(new Callback<PlanList>() {
            @Override
            public void onResponse(Call<PlanList> call, Response<PlanList> response) {
                if(response.isSuccessful()) {
                    PlanList planList = response.body();

                    if(planList.plans.size() > 0) {
                        plans = new String[planList.plans.size()];

                        for(int i=0; i<planList.plans.size(); i++) {
                            plans[i] = planList.plans.get(i).name;
                            map_plans.put(planList.plans.get(i).name,
                                    planList.plans.get(i).id);
                        }
                        ArrayAdapter<String> adapter_plans = new ArrayAdapter<String>(MatriculationFormActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, plans);
                        plans_cbx.setAdapter(adapter_plans);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlanList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    /**
     * Busca o ID do plano
     * @param name
     * @return id
     */
    private int getIdPlan(final String name) {
        return map_plans.get(name);
    }

    /**
     * Verifica os campos, se estão preenchidos corretamente
     */
    private boolean checkRequiredFields(){
        if(cod_student.getText().toString().isEmpty()){
            cod_student.setError("Informe o código do aluno");
            return false;
        }else if(date_matriculation.getText().toString().isEmpty() || date_matriculation.getText().toString().length() < 10){
            date_matriculation.setError("Informe uma data válida!");
            return false;
        }else if(day_pay.getText().toString().isEmpty() |
                (Integer.valueOf(day_pay.getText().toString()) < 1 || Integer.valueOf(day_pay.getText().toString()) > 31)
                ) {
            day_pay.setError("Informe um dia de vencimento válido!");
            return false;
        }

        return true;
    }

    /**
     * Limpa os campos preenchidos
     */
    private void clearFields() {
        cod_student.setText("");
        date_matriculation.setText("");
        day_pay.setText("");
        date_closing.setText("");
    }
}
