package acedemy.learningprogramming.newsforu;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by win on 10-05-2018.
 */

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResourse;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;
    private int rank =1;
    public FeedAdapter(@NonNull Context context, int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResourse = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = layoutInflater.inflate(layoutResourse,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }


        if (position % 2 == 1) {
            convertView.setBackgroundColor((Color.rgb(255, 218, 185)));
        } else {

            convertView.setBackgroundColor((Color.rgb(255,255, 255)));
        }
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvArtist = (TextView)convertView.findViewById(R.id.tvArtist);
//        TextView tvSummary = (TextView)convertView.findViewById(R.id.tvSummary);
        FeedEntry currentApp = applications.get(position);
        //viewHolder.tvname.setText("Rank "+ currentApp.getRank() + ":- ");
        viewHolder.title.setText(currentApp.getname());
        viewHolder.pubDate.setText(currentApp.getReleaseDate());
        viewHolder.description.setText(currentApp.getSummary());

        return convertView;
    }

    private class ViewHolder{
        final TextView title;
        final TextView pubDate;
        final TextView description;

        ViewHolder(View v){
            this.title =(TextView)v.findViewById(R.id.title);
            this.pubDate=(TextView)v.findViewById(R.id.pubDate);
            this.description=(TextView)v.findViewById(R.id.description);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPubDate() {
            return pubDate;
        }

        public TextView getDescription() {
            return description;
        }
    }
}










