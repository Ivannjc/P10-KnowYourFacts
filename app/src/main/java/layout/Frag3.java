package layout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import sg.edu.rp.c346.p10_knowyourfacts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag3 extends Fragment {
    Button btnChange;
    boolean colorChange = true;


    public Frag3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag3, container, false);
        btnChange = (Button) view.findViewById(R.id.btnChange);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colorChange){
                    Random random = new Random();
                    int color = Color.argb(255, random.nextInt(256), random.nextInt(257), random.nextInt(258));
                    linearLayout.setBackgroundColor(color);
                }
            }
        });
        return view;
    }

}
