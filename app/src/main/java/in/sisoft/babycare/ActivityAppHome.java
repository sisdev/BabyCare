package in.sisoft.babycare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.sisoft.babycare.R;
import in.sisoft.babycare.model.AppConstant;

public class ActivityAppHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
     //   NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_baby) {
            // All promotions
            DatabaseHelper dbh = new DatabaseHelper(this);
            int cnt = dbh.countBaby();
            Log.d("ActivityAppHome","Baby Count:"+ cnt);
            Intent i1 ;
            if (cnt==0) {
                i1 = new Intent(this, ActivityBabyAU.class);
                i1.putExtra(AppConstant.keyBabyOption,"add");
            }
            else {
                i1 = new Intent(this, ActivityBabyManage.class);
            }
            startActivity(i1);
        }
        else if (id == R.id.nav_vaccine) {
            Intent i1 = new Intent(this, ActivityVaccineMainPage.class);
            startActivity(i1);

        }
        else if (id == R.id.nav_feeding) {
            Intent i1 = new Intent(this, ActivityFeeding.class);
            startActivity(i1);

        }
        else if (id == R.id.nav_features) {
            Intent var1 = new Intent(this, ActivityWebView1.class);
            var1.putExtra("a", 11);
            startActivity(var1);
        }
        else if (id == R.id.nav_email) {
            Intent var1 = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:info@sisoft.in"));
            var1.putExtra("android.intent.extra.SUBJECT", "Baby Care App(Android App)");
            var1.putExtra("android.intent.extra.TEXT", "  ");
            this.startActivity(var1);
        }
        else if (id == R.id.nav_share) {
            Intent var1 = new Intent("android.intent.action.SEND");
            var1.setType("text/plain");
            var1.putExtra("android.intent.extra.SUBJECT", "Baby care app India");
            var1.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=in.sisoft.babycare");
            this.startActivity(Intent.createChooser(var1, "share via"));
        }

        return true;
    }
}