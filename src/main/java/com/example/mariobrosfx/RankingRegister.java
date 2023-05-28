package com.example.mariobrosfx;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class that defines a Ranking register and manages the ranking data
 */
public class RankingRegister implements Serializable
{
    /**
     * Quantity of time the player spent to beat the game
     */
    private int time;
    /**
     * Name of the player
     */
    private String name;
    /**
     * Date and time of the game
     */
    private LocalDateTime date;

    /**
     * Constructor with parameters
     * @param time Final time made in the game
     * @param name name of the player of the game
     */
    public RankingRegister(int time, String name)
    {
        this.time = time;
        this.name = name;
        this.date = LocalDateTime.now();
    }

    /**
     * Method that saves the ranking into a serialized file
     * @param ranking list containing the ranking
     */
    public static void saveData(List<RankingRegister> ranking)
    {
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream("ranking.dat")))
        {
            oos.writeObject(ranking);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads the raking from the serialized file
     * @return A list with the deserialized ranking
     */
    public static List<RankingRegister> loadRanking()
    {
        List<RankingRegister> ranking = null;
        try(ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream("ranking.dat")))
        {
            ranking = (List<RankingRegister>) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ranking;
    }

    /**
     * Returns the register time
     * @return Register time
     */
    public int getTime()
    {
        return time;
    }

    /**
     * Returns formated ranking register
     * @return Ranking register
     */
    @Override
    public String toString()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String strDate = format.format(date);
        return time + "  " + name + "  " + strDate;
    }
}
