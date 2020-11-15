package com.unesc.mastersysapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtLabel);

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
}