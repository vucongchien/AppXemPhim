package com.example.appxemphim.object_data;

public class Actor_Director {
    private String Actor_Director_id;
    private  String name;
    private String nation;

    public Actor_Director(String nation, String name) {
        this.nation = nation;
        this.name = name;
    }

    public Actor_Director(String actor_Director_id, String name, String nation) {
        Actor_Director_id = actor_Director_id;
        this.name = name;
        this.nation = nation;
    }

    public String getActor_Director_id() {
        return Actor_Director_id;
    }

    public void setActor_Director_id(String actor_Director_id) {
        Actor_Director_id = actor_Director_id;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
