
package com.demolistiview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageList implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private List<String> message = null;
    public final static Creator<ImageList> CREATOR = new Creator<ImageList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ImageList createFromParcel(Parcel in) {
            return new ImageList(in);
        }

        public ImageList[] newArray(int size) {
            return (new ImageList[size]);
        }

    };

    protected ImageList(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.message, (String.class.getClassLoader()));
    }

    public ImageList() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(message);
    }

    public int describeContents() {
        return 0;
    }

}
