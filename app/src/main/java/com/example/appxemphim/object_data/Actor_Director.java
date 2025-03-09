package com.example.appxemphim.object_data;

public class Actor_Director {
    private int Actor_Director_id;
    private  String name;
    private String nation;

    public Actor_Director(int actor_Director_id, String nation, String name) {
        Actor_Director_id = actor_Director_id;
        this.nation = nation;
        this.name = name;
    }

    public int getActor_Director_id() {
        return Actor_Director_id;
    }

    public void setActor_Director_id(int actor_Director_id) {
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
