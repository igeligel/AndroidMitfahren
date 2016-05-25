package com.ostfalia.presentation.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.TextView;

import com.ostfalia.mitfahren.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Presenter class for the Autocomplete Text
 */
public class AutoCompletePresenter {

  /**
   * Sets a city for every AutocompleteText
   * @param autoCompleteTextViews
   */
  public void setCitiesToAutoCompleteList(ArrayList<AutoCompleteTextView> autoCompleteTextViews) {
    for (AutoCompleteTextView autoCompleteTextView: autoCompleteTextViews) {
      setCitiesToAutoComplete(autoCompleteTextView);
    }
  }

  //TODO set the Adapter not to null
  public void setCitiesToAutoComplete(AutoCompleteTextView autoCompleteTextView) {
    autoCompleteTextView.setAdapter(null);

  }
}
/* Versuch 2 Filter kann man nicht implementieren
class customAdapter extends ArrayAdapter<String> {


  List<String> citiesList;
  Context context;
  private ArrayList<String> itemsAll;
  private ArrayList<String> suggestions;
  public MyCustomBrandAdapter(Context context, int textViewResourceId, List<String> citiesList)
  {

    super(context,  textViewResourceId, citiesList);
    this.citiesList =  citiesList;
    this.itemsAll = (ArrayList<String>) ((ArrayList<String>) citiesList).clone();
    this.suggestions = new ArrayList<String>();
    this.context = context;
  }


  public View getView(int position, View convertView, ViewGroup parent)
  {
    View v = convertView;
    if (v == null) {
      LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      v = vi.inflate(R.layout.listview, null);

    }
    String city= citiesList.get(position);
    if (city != null) {
      TextView tt = (TextView) v.findViewById(R.id.txtName);

      if (tt != null) {
        tt.setText(citiesList.get(position).toString());
      } else {
        System.out.println("Not getting textview..");
      }

    }
    return v;
  }
  @Override
  public LayoutInflater.Filter getFilter() {
    return nameFilter;
  }

  LayoutInflater.Filter nameFilter = new LayoutInflater.Filter() {
    public String convertResultToString(Object resultValue) {
      String str = resultValue.toString();
      return str;
    }
    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
      if(constraint != null) {
        suggestions.clear();
        for (String city: itemsAll) {
          if(city.toLowerCase().startsWith(constraint.toString().toLowerCase())){
            suggestions.add(city);
          }
        }
        Filter.FilterResults filterResults = new Filter.FilterResults();
        filterResults.values = suggestions;
        filterResults.count = suggestions.size();
        return filterResults;
      } else {
        return new Filter.FilterResults();
      }
    }
    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
      ArrayList<String> filteredList = (ArrayList<String>) results.values;
      List<String> getResult=new ArrayList<String>();
      if(results != null && results.count > 0) {
        clear();
        for (String c : filteredList) {
          getResult.add(c);
        }
        Iterator<String> cityIterator=getResult.iterator();
        while(cityIterator.hasNext()){
          String city=cityIterator.next();
          add(city);
        }
        notifyDataSetChanged();
      }
    }
  };

}
*/
