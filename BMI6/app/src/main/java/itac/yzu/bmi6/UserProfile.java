package itac.yzu.bmi6;

public class UserProfile {
    // MEMBER ATTRIBUTES
    private String Name;
    private String Gender;
    private Double Height;
    private Double Weight;

    // CONSTRUCTOR
    public UserProfile() {    }

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

    // SETS
    public void setName(String name) { this.Name = name; }
    public void setGender(String gender) { this.Gender = gender; }
    public void setHeight(Double height) { this.Height = height; }
    public void setWeight(Double weight) { this.Weight = weight; }


}
