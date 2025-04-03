package com.example.appxemphim.object_data;

public class Movie_Actor_Director {
    private String movive_Id;
    private String Actor_Director_id;

    public Movie_Actor_Director(String movive_Id, String actor_Director_id) {
        this.movive_Id = movive_Id;
        Actor_Director_id = actor_Director_id;
    }

    public Movie_Actor_Director() {
    }

    public String getMovive_Id() {
        return movive_Id;
    }

    public void setMovive_Id(String movive_Id) {
        this.movive_Id = movive_Id;
    }

    public String getActor_Director_id() {
        return Actor_Director_id;
    }

    public void setActor_Director_id(String actor_Director_id) {
        Actor_Director_id = actor_Director_id;
    }
}
