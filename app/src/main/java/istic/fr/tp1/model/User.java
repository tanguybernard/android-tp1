package istic.fr.tp1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tbernard on 18/01/16.
 */
public class User implements Parcelable {


    private String name;
    private String city;
    private String lastname;
    private String birthday_date;


    public User(Parcel in) {
        name = in.readString();
        city = in.readString();
        lastname = in.readString();
        birthday_date = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String name,String lastname, String birthday_date, String city) {
        this.name = name;
        this.lastname = lastname;
        this.birthday_date = birthday_date;
        this.city = city;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(birthday_date);
        dest.writeString(city);
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCity(){
        return city;
    }

    public String getBirthday_date(){
        return birthday_date;
    }


}
