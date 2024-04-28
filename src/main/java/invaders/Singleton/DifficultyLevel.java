/**
 * This class uses the singleton design pattern to create one instance of the difficulty level.
 * includes the levels easy, medium and hard
 * based on the instance selected in the app class, the relevent config file will be used to run the game
 * feature and design pattern work as required.
 */


package invaders.Singleton;

public class DifficultyLevel {
    private static DifficultyLevel easy;
    private static DifficultyLevel medium;
    private static DifficultyLevel hard;
    private String pathName;
    private String name;

    private DifficultyLevel(String name, String pathName){
        this.name = name;
        this.pathName = pathName;
    }

    // method to create Easy difficulty level
    public static DifficultyLevel Easy(){
        if(easy == null) {
            easy = new DifficultyLevel("EASY", "src/main/resources/config_easy.json");
        }
        return easy;
    }

    // method to create medium difficulty level
    public static DifficultyLevel Medium(){
        if(medium == null){
            medium = new DifficultyLevel("MEDIUM", "src/main/resources/config_medium.json");
        }
        return medium;
    }

    // method to create hard difficulty level
    public static DifficultyLevel Hard(){
        if(hard == null){
            hard = new DifficultyLevel("HARD", "src/main/resources/config_hard.json");
        }
        return hard;
    }

    public String getConfigFileName(){
        return this.pathName;
    }

}
