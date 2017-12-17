package itac.yzu.bmi6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfileAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<UserProfile> UserProfiles;
    private DBHelper db;

    public UserProfileAdapter(Context context, ArrayList<UserProfile> UserProfile) {
        inflater = LayoutInflater.from(context);
        this.UserProfiles = UserProfile;
        db = new DBHelper(context);
    }

    @Override
    public int getCount() {
        return UserProfiles.size();
    }

    @Override
    public Object getItem(int arg0) {
        return UserProfiles.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return UserProfiles.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.user_profile_list, null);
            vHolder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.nameTV),
                    (TextView) convertView.findViewById(R.id.genderTV),
                    (TextView) convertView.findViewById(R.id.heightTV),
                    (TextView) convertView.findViewById(R.id.weightTV)
                    );
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        final UserProfile uProfile = (UserProfile)getItem(position);
        vHolder.txtName.setText(uProfile.getName());
        vHolder.txtGender.setText(uProfile.getGender());
        vHolder.txtHeight.setText(String.valueOf(uProfile.getHeight()));
        vHolder.txtWeight.setText(String.valueOf(uProfile.getWeight()));

        return convertView;
    }


    public void updateView() {
        UserProfiles = db.getAllUserProfile();
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtGender;
        TextView txtHeight;
        TextView txtWeight;

        public ViewHolder(TextView txtName, TextView txtGender, TextView txtHeight, TextView txtWeight) {
            this.txtName = txtName;
            this.txtGender = txtGender;
            this.txtHeight = txtHeight;
            this.txtWeight = txtWeight;
        }
    }

}
