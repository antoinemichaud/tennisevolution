package tennis.game.classic;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ScoreTranslator {

    public String translate(Score score, String language) {
        try {
            String languageRelativeScoresLine = Resources.toString(Resources.getResource(language + ".csv"), StandardCharsets.UTF_8);
            ImmutableMap.Builder<Score, String> builder = ImmutableMap.<Score, String>builder();
            String[] scoresLabels = languageRelativeScoresLine.split("\\|");
            for (int i = 0; i < Score.values().length; i++) {
                Score currentScore = Score.values()[i];
                builder.put(currentScore, scoresLabels[i]);
            }
            ImmutableMap<Score, String> scoresToLabelMap = builder.build();

            return scoresToLabelMap.get(score);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
