package model;

import lombok.Data;

@Data
public class Joke {

    private String type;
    private String setup;
    private String punchline;
    private int id;
}
