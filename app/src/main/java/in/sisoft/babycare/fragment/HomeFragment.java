package in.sisoft.babycare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import in.sisoft.babycare.ActivityBabyAU;
import in.sisoft.babycare.ActivityBabyManage;
import in.sisoft.babycare.ActivityFeeding;
import in.sisoft.babycare.ActivityInformation;
import in.sisoft.babycare.ActivityVaccineMainPage;
import in.sisoft.babycare.DatabaseHelper;
import in.sisoft.babycare.R;
import in.sisoft.babycare.model.AppConstant;

public class HomeFragment extends Fragment implements Animation.AnimationListener {

    LinearLayout layout_baby, layout_vaccine, layout_feeding, layout_info ;
    Animation anim ;
    private AdView mAdView;
    String clickObject ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        layout_baby = root.findViewById(R.id.ll_baby);
        layout_vaccine = root.findViewById(R.id.ll_vaccine);
        layout_feeding = root.findViewById(R.id.ll_feeding);
        layout_info = root.findViewById(R.id.ll_info);

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        anim.setAnimationListener(this);

        layout_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_baby.startAnimation(anim);
                clickObject="B" ;
            }
        });

        layout_vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_vaccine.startAnimation(anim);
                clickObject="V" ;

            }
        });

        layout_feeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_feeding.startAnimation(anim);
                clickObject="F" ;

            }
        });

        layout_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_info.startAnimation(anim);
                clickObject = "I";
            }
        });

        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return root;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // No action
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (clickObject.equals("B")){
            DatabaseHelper dbh = new DatabaseHelper(getActivity());
            int cnt = dbh.countBaby();
            Intent i1 ;
            if (cnt==0) {
                i1 = new Intent(getActivity(), ActivityBabyAU.class);
                i1.putExtra(AppConstant.keyBabyOption,"add");
            }
            else {

                i1 = new Intent(getActivity(), ActivityBabyManage.class);
            }
            startActivity(i1);
        }
        if (clickObject.equals("V")){
            Intent vaccine_main = new Intent(getActivity(), ActivityVaccineMainPage.class) ;
            startActivity(vaccine_main);

        }
        if (clickObject.equals("F")) {
            Intent vaccine_main = new Intent(getActivity(), ActivityFeeding.class) ;
            startActivity(vaccine_main);

        }
        if (clickObject.equals("I")) {
            Intent vaccine_main = new Intent(getActivity(), ActivityInformation.class) ;
            startActivity(vaccine_main);
        }
        }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // No action
    }
}