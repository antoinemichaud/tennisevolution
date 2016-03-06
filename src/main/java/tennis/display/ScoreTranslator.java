package tennis.display;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ScoreTranslator {

    public String translate(ScoreLabelBase scoreLabelBase, String language) {
        try {
            String languageRelativeScoresLine = Resources.toString(Resources.getResource(language + ".csv"), StandardCharsets.UTF_8);
            ImmutableMap.Builder<ScoreLabelBase, String> builder = ImmutableMap.<ScoreLabelBase, String>builder();
            String[] scoresLabels = languageRelativeScoresLine.split("\\|");
            for (int i = 0; i < ScoreLabelBase.values().length; i++) {
                ScoreLabelBase currentScoreLabelBase = ScoreLabelBase.values()[i];
                builder.put(currentScoreLabelBase, scoresLabels[i]);
            }
            ImmutableMap<ScoreLabelBase, String> scoresToLabelMap = builder.build();

            return scoresToLabelMap.get(scoreLabelBase);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
