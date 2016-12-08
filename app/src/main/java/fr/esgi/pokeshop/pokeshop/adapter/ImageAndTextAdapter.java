package fr.esgi.pokeshop.pokeshop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.holder.PokeHolder;


public class ImageAndTextAdapter extends BaseAdapter {

    private Activity activity;

    private Integer[] pokemons = {
        R.drawable.bulbizarre,
        R.drawable.salameche,
        R.drawable.carapuce
    };

    private String[] names = {
        "Bulbizarre",
        "Salamèche",
        "Carapuce"
    };

    private String[] prices = {
        "Gratuit",
        "Gratuit",
        "Gratuit"
    };

    public ImageAndTextAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pokemons.length;
    }

    @Override
    public Object getItem(int position) {
        return pokemons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PokeHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();

            convertView = inflater.inflate(R.layout.pokemon, null);

            holder = new PokeHolder();
            holder.button = (Button) convertView.findViewById(R.id.poke_button);
            holder.text = (TextView) convertView.findViewById(R.id.poke_name);
            holder.price = (TextView) convertView.findViewById(R.id.poke_price);

            convertView.setTag(holder);
        } else {
            holder = (PokeHolder) convertView.getTag();
        }

        holder.button.setBackgroundResource(pokemons[position]);
        holder.text.setText(names[position]);
        holder.price.setText(prices[position]);

        return convertView;
    }
}