package com.jkwy.commui.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzejin on 2016/9/22.
 * 功能说明：疫苗的实体类
 */

public class VaccineList implements Parcelable {
    private String time="";
    private List<Vaccine> list = new ArrayList<>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Vaccine> getList() {
        return list;
    }

    public void setList(List<Vaccine> list) {
        this.list = list;
    }

    public static class Vaccine implements Parcelable {
        //显示在listView中的时间标题
        private String title;
        private String name="";//名字
        private String briefing="";//简介
        private String effect="";//预防疾病
        private String considerations="";//注意事项
        private String nursing="";//接种后的护理
        private String reminder="";//温馨提示
        private String replace="";//另一种选择
        private String count="";//第几次

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBriefing() {
            return briefing;
        }

        public void setBriefing(String briefing) {
            this.briefing = briefing;
        }

        public String getEffect() {
            return effect;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public String getConsiderations() {
            return considerations;
        }

        public void setConsiderations(String considerations) {
            this.considerations = considerations;
        }

        public String getNursing() {
            return nursing;
        }

        public void setNursing(String nursing) {
            this.nursing = nursing;
        }

        public String getReminder() {
            return reminder;
        }

        public void setReminder(String reminder) {
            this.reminder = reminder;
        }

        public String getReplace() {
            return replace;
        }

        public void setReplace(String replace) {
            this.replace = replace;
        }

        public String getCount() {
            return "第"+count+"次";
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.briefing);
            dest.writeString(this.effect);
            dest.writeString(this.considerations);
            dest.writeString(this.nursing);
            dest.writeString(this.reminder);
            dest.writeString(this.replace);
            dest.writeString(this.count);
        }

        public Vaccine() {
        }

        protected Vaccine(Parcel in) {
            this.name = in.readString();
            this.briefing = in.readString();
            this.effect = in.readString();
            this.considerations = in.readString();
            this.nursing = in.readString();
            this.reminder = in.readString();
            this.replace = in.readString();
            this.count = in.readString();
        }

        public static final Parcelable.Creator<Vaccine> CREATOR = new Parcelable.Creator<Vaccine>() {
            @Override
            public Vaccine createFromParcel(Parcel source) {
                return new Vaccine(source);
            }

            @Override
            public Vaccine[] newArray(int size) {
                return new Vaccine[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeTypedList(this.list);
    }

    public VaccineList() {
    }

    protected VaccineList(Parcel in) {
        this.time = in.readString();
        this.list = in.createTypedArrayList(Vaccine.CREATOR);
    }

    public static final Parcelable.Creator<VaccineList> CREATOR = new Parcelable.Creator<VaccineList>() {
        @Override
        public VaccineList createFromParcel(Parcel source) {
            return new VaccineList(source);
        }

        @Override
        public VaccineList[] newArray(int size) {
            return new VaccineList[size];
        }
    };
}
