package itac.yzu.bmi6;


public class UserProfile {
    // MEMBER ATTRIBUTES
    private String Name;
    private String Gender;
    private Double Height;
    private Double Weight;

    // CONSTRUCTOR
    public UserProfile(String name, String gender, Double height, Double weight){
        Name = name;
        Gender = gender;
        Height = height;
        Weight = weight;
    }

    // GETS
    public String getName() {
        return Name;
    }
    public String getGender() {
        return Gender;
    }
    public Double getHeight(){
        return Height;
    }
    public Double getWeight(){
        return Weight;
    }


}
