package cn.edu.cugb.information_program.go_test;

public class submenu {
    private String name;
    private int imageId;
    public submenu(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}