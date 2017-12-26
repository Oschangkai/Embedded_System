package tw.kaiyeee.triptao.app;

/**
 * Created by Home on 2017/10/31.
 */
public class Point {
    private String id;
    private String name;
    private String tel;
    private class latlng{
        private String lat;
        public void setlat(String lat)
        {
            this.lat = lat;
        }
        public String getlat()
        {
            return lat;
        }
        private String lng;
        public void setlng(String lng)
        {
            this.lng = lng;
        }
        public String getlng()
        {
            return lng;
        }
    };
    private String imgurl;
    private String ARurl;
    private String available_time;
    private String description;
//P.S. 以上變數名稱與串接欄位必需相符

    public Point()
    {

    }

    public void setid(String id)
    {
        this.id = id;
    }

    public String getid()
    {
        return id;
    }

    public void setname(String name)
    {
        this.name = name;
    }

    public String getname()
    {
        return name;
    }

    public void settel(String tel)
    {
        this.tel = tel;
    }

    public String gettel()
    {
        return tel;
    }

    public void setimgurl(String imgurl)
    {
        this.imgurl = imgurl;
    }

    public String getimgurl()
    {
        return imgurl;
    }

    public void setARurl(String ARurl)
    {
        this.ARurl = ARurl;
    }

    public String getARurl()
    {
        return ARurl;
    }

    public void setavailable_time(String available_time)
    {
        this.available_time = available_time;
    }

    public String getavailable_time()
    {
        return available_time;
    }

    public void setdescription(String description)
    {
        this.description = description;
    }

    public String getdescription()
    {
        return description;
    }
}
