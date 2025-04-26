package com.example.appxemphim.Model;

public class ActorModel {
    private String name;
    private String nation;
    private String actor_Director_id;

    public ActorModel() {
    }

    public ActorModel(String name, String nation, String actor_Director_id) {
        this.name = name;
        this.nation = nation;
        this.actor_Director_id = actor_Director_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getActor_Director_id() {
        return actor_Director_id;
    }

    public void setActor_Director_id(String actor_Director_id) {
        this.actor_Director_id = actor_Director_id;
    }

    @Override
    public String toString() {
        return "ActorModel{" +
                "name='" + name + '\'' +
                ", nation='" + nation + '\'' +
                ", actor_Director_id='" + actor_Director_id + '\'' +
                '}';
    }
}
