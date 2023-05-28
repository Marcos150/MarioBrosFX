package com.example.mariobrosfx;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingRegisterTest
{

    @Test
    void saveLoadData()
    {
        StringBuilder r1 = new StringBuilder();
        StringBuilder r2 = new StringBuilder();
        List<RankingRegister> ranking = new ArrayList<>();

        ranking.add(new RankingRegister(69, "Pikachu"));
        ranking.add(new RankingRegister(420, "Spock"));
        ranking.add(new RankingRegister(4, "Yoda"));
        for (RankingRegister r : ranking)
        {
            r1.append(r.toString());
        }

        RankingRegister.saveData(ranking);
        List<RankingRegister> ranking2 = RankingRegister.loadRanking();
        for (RankingRegister r : ranking2)
        {
            r2.append(r.toString());
        }
        assertEquals(r1.toString(), r2.toString());
    }

    @Test
    void getTime()
    {
        RankingRegister register = new RankingRegister(19, "Spiderman");
        assertEquals(register.getTime(), 19);
    }

    @Test
    void testToString()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String strDate = format.format(LocalDateTime.now());
        RankingRegister register = new RankingRegister(317, "Waluigi");
        assertEquals("317  Waluigi  " + strDate, register.toString());
    }
}