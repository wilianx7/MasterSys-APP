package com.unesc.mastersysapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.unesc.mastersysapp.ModelList.ModalityList;
import com.unesc.mastersysapp.ModelList.PlanList;
import com.unesc.mastersysapp.ModelList.RegistrationList;
import com.unesc.mastersysapp.ModelList.StudentList;
import com.unesc.mastersysapp.Models.Matriculation;
import com.unesc.mastersysapp.Services.MatriculationService;
import com.unesc.mastersysapp.Services.ModalityService;
import com.unesc.mastersysapp.Services.PlanService;
import com.unesc.mastersysapp.Services.Service;
import com.unesc.mastersysapp.Services.StudentService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView student_value, modality_value, plan_value, registration_value, price_value;
    private CardView plan_card, registration_card, student_card, modality_card;

    ModalityList modalityList;
    StudentList studentList;
    PlanList planList;
    RegistrationList registrationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student_value = findViewById(R.id.student_value);
        findStudent();
        modality_value = findViewById(R.id.modality_value);
        findModality();
        plan_value = findViewById(R.id.plan_value);
        findPlans();
        registration_value = findViewById(R.id.registration_value);
        price_value = findViewById(R.id.price_value);
        findRegistrations();

        plan_card = findViewById(R.id.plan_card);
        registration_card = findViewById(R.id.registration_card);
        student_card = findViewById(R.id.student_card);
        modality_card = findViewById(R.id.modality_card);

        plan_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, PlanListActivity.class);
                it.putExtra("plans", planList);
                startActivity(it);
            }
        });

        registration_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, RegistrationListActivity.class);
                it.putExtra("registrations", registrationList);
                startActivity(it);
            }
        });

        student_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, StudentListActivity.class);
                it.putExtra("students", studentList);
                startActivity(it);
            }
        });

        modality_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, PlansFormActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle(getString(R.string.app_name));
        }

        setSupportActionBar(toolbar);

        createDrawer();
    }

    private void createDrawer(){
        //Itens do Drawer
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.item1_menu)
                .withIcon(R.drawable.ic_baseline_person_24_black);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item2_menu)
                .withIcon(R.drawable.ic_baseline_person_24_black);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item3_menu)
                .withIcon(R.drawable.ic_baseline_school_24_black);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item4_menu)
                .withIcon(R.drawable.ic_baseline_monetization_on_24_black);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item5_menu)
                .withIcon(R.drawable.ic_baseline_person_24_black);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item6_menu)
                .withIcon(R.drawable.ic_baseline_info_24_black);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.item7_menu)
                .withIcon(R.drawable.ic_baseline_exit_to_app_24_black);

        //Definição do Drawer
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Cadastros"),//Seção
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        new DividerDrawerItem(), //Divisor
                        item6,
                        item7
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                startActivity(new Intent(MainActivity.this, StudentFormActivity.class));
                                return false;
                            case 2:
                                startActivity(new Intent(MainActivity.this, ModalityFormActivity.class));
                                return false;
                            case 3:
                                startActivity(new Intent(MainActivity.this, GraduationFormActivity.class));
                                return false;
                            case 4:
                                startActivity(new Intent(MainActivity.this, PlansFormActivity.class));
                                return false;
                            case 5:
                                startActivity(new Intent(MainActivity.this, MatriculationFormActivity.class));
                                return false;
                            case 8:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                return false;
                        }

                        return false;
                    }
                })
                .withSelectedItemByPosition(0)
                .build();
    }

    private void findModality()  {
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
                    modalityList = response.body();
                    modality_value.setText(String.valueOf(modalityList.modalities.size()));
                }
            }

            @Override
            public void onFailure(Call<ModalityList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    private void findStudent()  {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentService studentService = retrofit.create(StudentService.class);
        Call<StudentList> request = studentService.listStudents();


        request.enqueue(new Callback<StudentList>() {
            @Override
            public void onResponse(Call<StudentList> call, Response<StudentList> response) {
                if(response.isSuccessful()) {
                    studentList = response.body();
                    student_value.setText(String.valueOf(studentList.students.size()));
                }
            }

            @Override
            public void onFailure(Call<StudentList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        findRegistrations();
        findPlans();
        findStudent();
        findModality();
    }

    private void findPlans()  {
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
                    planList = response.body();
                    plan_value.setText(String.valueOf(planList.plans.size()));
                }
            }

            @Override
            public void onFailure(Call<PlanList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

    private void findRegistrations()  {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatriculationService matriculationService = retrofit.create(MatriculationService.class);
        Call<RegistrationList> request = matriculationService.listRegistrations();

        request.enqueue(new Callback<RegistrationList>() {
            @Override
            public void onResponse(Call<RegistrationList> call, Response<RegistrationList> response) {
                float price = 0;
                if(response.isSuccessful()) {
                    registrationList = response.body();
                    registration_value.setText(String.valueOf(registrationList.registrations.size()));

                    Calendar c = Calendar.getInstance();
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    date = c.getTime();

                    //seta primeiro dia do mês
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
                    date = c.getTime();

                    for(Matriculation registration : registrationList.registrations){
                        //Verifica se termina no ultimo dia do mês
                        if(registration.end_date.compareTo(date) >= 0){
                            price += registration.plan.price;
                        }
                    }
                    price_value.setText("R$ " + String.valueOf(price));
                }
            }

            @Override
            public void onFailure(Call<RegistrationList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }
        });
    }

}