package tw.kaiyeee.triptao.app;


public class Site {
    private String name;
    //景點名稱
    private String tel;
    //景點電話
    private Category category;
    //景點分類
    private String address;
    //景點地址
    private String login;
    private Latlng latlng;
    //景點座標
    private String weather;


    private String available_time;//營業時間
    private String img;//景點預覽圖
    private String description;//景點簡介
    private Point[] point;//景點任務點
    private Coupon[] coupon;//景點酷碰
    private Broadcast[] broadcast;//景點快訊
    private Mission[] mission;//景點任務資訊
    private Event[] event;
    public Site()
    {

    }
    public Latlng   getLatlng(){return latlng;}
    public Category getCategory(){return category;}
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public void setWeather(String weather)
    {
        this.weather = weather;
    }

    public String getWeather()
    {
        return weather;
    }


    public void settel(String tel)
    {
        this.tel = tel;
    }

    public String gettel()
    {
        return tel;
    }

    public void setaddress(String address)
    {
        this.address = address;
    }

    public String getaddress()
    {
        return address;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return login;
    }

    public void setavailable_time(String available_time)
    {
        this.available_time = available_time;
    }

    public String getavailable_time()
    {
        return available_time;
    }

    public void setimg(String img)
    {
        this.img = img;
    }

    public String getimg()
    {
        return img;
    }


    public void setdescription(String description)
    {
        this.description = description;
    }

    public String getdescription()
    {
        return description;
    }



    public void setPoint(Point[] point)
    {
        this.point = point;
    }

    public  Point[] getPoint()
    {
        return point;
    }
    public void setEvent(Event[] event)
    {
        this.event = event;
    }

    public  Event[] getEvent()
    {
        return event;
    }


    public void setCoupon(Coupon[] coupon)
    {
        this.coupon = coupon;
    }

    public  Coupon[] getCoupon()
    {
        return coupon;
    }
    public void setBroadcast(Broadcast[] broadcast)
    {
        this.broadcast = broadcast;
    }

    public  Broadcast[] getBroadcast()
    {
        return broadcast;
    }
    public void setMission(Mission[] mission)
    {
        this.mission = mission;
    }

    public  Mission[] getMission()
    {
        return mission;
    }

}
