package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.adapter.ImageAndTextAdapter;


public class PokeGridFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = (GridView) view.findViewById(R.id.poke_grid);
        gridView.setAdapter(new ImageAndTextAdapter(this.getActivity()));
    }
}
