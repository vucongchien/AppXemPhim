package com.example.appxemphim.object_data;

public class Movie_Actor_Director {
    private int movive_Id;
    private int Actor_Director_id;

    public Movie_Actor_Director(int movive_Id, int actor_Director_id) {
        this.movive_Id = movive_Id;
        Actor_Director_id = actor_Director_id;
    }

    public int getMovive_Id() {
        return movive_Id;
    }

    public void setMovive_Id(int movive_Id) {
        this.movive_Id = movive_Id;
    }

    public int getActor_Director_id() {
        return Actor_Director_id;
    }

    public void setActor_Director_id(int actor_Director_id) {
        Actor_Director_id = actor_Director_id;
    }
}
