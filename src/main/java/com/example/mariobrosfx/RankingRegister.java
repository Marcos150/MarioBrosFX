package com.example.mariobrosfx;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RankingRegister implements Serializable
{
    private int time;
    private String name;
    private LocalDateTime date;

    public RankingRegister(int time, String name)
    {
        this.time = time;
        this.name = name;
        this.date = LocalDateTime.now();
    }

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

    public int getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String strDate = format.format(date);
        return time + "  " + name + "  " + strDate;
    }
}
