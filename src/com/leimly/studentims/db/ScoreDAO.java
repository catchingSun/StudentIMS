package com.leimly.studentims.db;

import com.leimly.studentims.entity.Score;
import javafx.collections.ObservableList;

/**
 * Created by lizm on 17-12-17.
 */
public interface ScoreDAO {
    public void addScoreStudent(Score score);

    public Boolean updateScore(Score score);

    public void searchScoreByCS(Score score);

    public Boolean searchScoreByCCS(Score score);

    public Boolean deleteScore(Score score);

    public ObservableList<Score> searchScore(Score score);
}
