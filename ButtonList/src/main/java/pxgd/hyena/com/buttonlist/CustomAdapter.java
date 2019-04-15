package pxgd.hyena.com.buttonlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by juan.brusco on 22-May-17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataSet;
    Context mContext;
    MediaPlayer mediaPlayer = null;
    ImageView iw;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        TextView txtFIle;
        ImageView play;
        ImageView stop;
        ImageView share;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel) object;

        switch (v.getId()) {
            case R.id.item_play:
                String fileName = dataModel.getFile().toString().toLowerCase();
//                int resID = mContext.getResources().getIdentifier(fileName, "raw", mContext.getPackageName());
//                if (mediaPlayer == null) {
//                    mediaPlayer = MediaPlayer.create(mContext, R.raw.batata_boa2);
//                    mediaPlayer.start();
//                }

                MediaPlayer p = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = mContext.getAssets().openFd("batata_boa2.opus");
                    p.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    p.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.start();
                break;
            case R.id.item_stop:
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                break;
            case R.id.item_share:
                final Intent sendIntent  = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("audio/*");
                sendIntent.putExtra(Intent.EXTRA_STREAM, R.raw.batata_boa2);
                mContext.startActivity(Intent.createChooser(sendIntent, ""));
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtFIle = (TextView) convertView.findViewById(R.id.file);
            viewHolder.play = (ImageView) convertView.findViewById(R.id.item_play);
            viewHolder.stop = (ImageView) convertView.findViewById(R.id.item_stop);
            viewHolder.share = (ImageView) convertView.findViewById(R.id.item_share);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        viewHolder.txtFIle.setText(dataModel.getFile());
        viewHolder.play.setOnClickListener(this);
        viewHolder.play.setTag(position);
        viewHolder.stop.setOnClickListener(this);
        viewHolder.stop.setTag(position);
        viewHolder.share.setOnClickListener(this);
        viewHolder.share.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
