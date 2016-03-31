package com.github.csongradyp.badger.parser.json;

import com.github.csongradyp.badger.domain.achievement.CompositeAchievementBean;
import com.github.csongradyp.badger.domain.achievement.relation.Relation;
import com.github.csongradyp.badger.domain.achievement.trigger.ITrigger;
import com.github.csongradyp.badger.exception.MalformedAchievementDefinition;
import com.github.csongradyp.badger.parser.json.domain.AchievementsJson;
import com.github.csongradyp.badger.parser.json.domain.CompositeAchievementJson;
import com.github.csongradyp.badger.parser.json.domain.ISimpleTriggerAchievementJson;
import com.github.csongradyp.badger.parser.trigger.TriggerParser;
import com.github.csongradyp.badger.provider.date.DateProvider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import com.github.csongradyp.badger.AchievementDefinition;
import com.github.csongradyp.badger.domain.AchievementType;
import com.github.csongradyp.badger.domain.IAchievementBean;
import com.github.csongradyp.badger.domain.ITriggerableAchievementBean;
import com.github.csongradyp.badger.domain.achievement.IAchievement;
import com.github.csongradyp.badger.domain.achievement.ScoreRangeAchievementBean;
import com.github.csongradyp.badger.domain.achievement.SingleAchievementBean;
import com.github.csongradyp.badger.domain.achievement.TimeRangeAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.ScoreTriggerPair;
import com.github.csongradyp.badger.domain.achievement.trigger.TimeTriggerPair;
import com.github.csongradyp.badger.parser.AchievementFactory;
import com.github.csongradyp.badger.parser.RelationParser;
import com.github.csongradyp.badger.parser.json.domain.AchievementDefinitionJson;
import com.github.csongradyp.badger.parser.json.domain.AchievementJson;
import com.github.csongradyp.badger.parser.json.domain.IAchievementJson;
import com.github.csongradyp.badger.parser.json.domain.RangeTrigger;
import com.github.csongradyp.badger.parser.trigger.ITriggerParser;
import org.codehaus.jackson.map.ObjectMapper;

public class AchievementJsonParser {

    public static final String FILE_ERROR = "Achievement JSon file read error.";
    private TriggerParser triggerParsers;
    private final ObjectMapper mapper;
    private RelationParser relationParser;

    public AchievementJsonParser() {
        triggerParsers = new TriggerParser();
        relationParser = new RelationParser();
        mapper = new ObjectMapper();
    }

    public AchievementDefinition parse(final File achievementFile) {
        final AchievementDefinition achievementDefinition = new AchievementDefinition();
        try {
            final AchievementDefinitionJson achievementDefinitionJson = mapper.readValue(achievementFile, AchievementDefinitionJson.class);
            setProperties(achievementDefinition, achievementDefinitionJson);
        } catch (IOException e) {
            throw new MalformedAchievementDefinition(FILE_ERROR, e);
        }
        return achievementDefinition;
    }

    public AchievementDefinition parse(final String achievementFileLocation) {
        final AchievementDefinition achievementDefinition = new AchievementDefinition();
        try {
            final AchievementDefinitionJson achievementDefinitionJson = mapper.readValue(new File(achievementFileLocation), AchievementDefinitionJson.class);
            setProperties(achievementDefinition, achievementDefinitionJson);
        } catch (IOException e) {
            throw new MalformedAchievementDefinition(FILE_ERROR, e);
        }
        return achievementDefinition;
    }

    public AchievementDefinition parse(final URL achievementFile) {
        final AchievementDefinition achievementDefinition = new AchievementDefinition();
        try {
            final AchievementDefinitionJson achievementDefinitionJson = mapper.readValue(achievementFile, AchievementDefinitionJson.class);
            setProperties(achievementDefinition, achievementDefinitionJson);
        } catch (IOException e) {
            throw new MalformedAchievementDefinition(FILE_ERROR, e);
        }
        return achievementDefinition;
    }

    private void setProperties(AchievementDefinition achievementDefinition, AchievementDefinitionJson achievementDefinitionJson) {
        achievementDefinition.setEvents(achievementDefinitionJson.getEvents());
        final AchievementsJson achievements = achievementDefinitionJson.getAchievements();
        achievementDefinition.setAchievements(parseAchievements(achievements));
    }

    private Collection<IAchievement> parseAchievements(final AchievementsJson achievements) {
        final Collection<IAchievement> achievementBeans = new ArrayList<>();
        for (AchievementType type : AchievementType.values()) {
            if (type == AchievementType.SCORE || type == AchievementType.TIME || type == AchievementType.DATE) {
                final List<IAchievementBean> beans = mapTriggerAchievements(type, (List<ISimpleTriggerAchievementJson>) achievements.get(type));
                achievementBeans.addAll(beans);
            } else if (type == AchievementType.SINGLE) {
                achievementBeans.addAll(mapSingleAchievementBeans(achievements.get(type)));
            }
        }
        if (achievements.getScoreRange() != null) {
            achievementBeans.addAll(mapScoreRangeAchievements(achievements.getScoreRange()));
        }
        if (achievements.getTimeRange() != null) {
            achievementBeans.addAll(mapTimeRangeAchievements(achievements.getTimeRange()));
        }
        if (achievements.getComposite() != null) {
            achievementBeans.addAll(mapCompositeAchievementBeans(achievements.getComposite()));
        }
        return achievementBeans;
    }

    @SuppressWarnings("unchecked")
    private List<CompositeAchievementBean> mapCompositeAchievementBeans(final List<CompositeAchievementJson> achievements) {
        List<CompositeAchievementBean> collection = new ArrayList<>();
        for (CompositeAchievementJson json : achievements) {
            final CompositeAchievementBean bean = new CompositeAchievementBean();
            mapBasicAttributes(json, bean);

            final List<ITrigger> triggers = new ArrayList<>();
            if (json.getScoreTrigger() != null) {
                triggers.addAll(triggerParsers.score().parse(json.getScoreTrigger()));
            }
            if (json.getDateTrigger() != null) {
                triggers.addAll(triggerParsers.date().parse(json.getDateTrigger()));
            }
            if (json.getTimeTrigger() != null) {
                triggers.addAll(triggerParsers.time().parse(json.getTimeTrigger()));
            }
            if (json.getScoreRangeTrigger() != null) {
                triggers.addAll(getScoreTriggerPairs(json.getScoreRangeTrigger()));
            }

            if (json.getTimeRangeTrigger() != null) {
                triggers.addAll(getTimeTriggerPairs(json.getTimeRangeTrigger()));
            }

            if (json.getRelation() == null) {
                throw new MalformedAchievementDefinition("Missing relation definition for: " + json.getId());
            }
            bean.setTrigger(triggers);
            final Relation relation = relationParser.parse(json.getRelation(), triggers);
            bean.setRelation(relation);
            collection.add(bean);
        }
        return collection;
    }

    private List<SingleAchievementBean> mapSingleAchievementBeans(final List<? extends IAchievementJson> achievements) {
        List<SingleAchievementBean> singleAchievements = new LinkedList<>();
        for (IAchievementJson json : achievements) {
            final SingleAchievementBean bean = new SingleAchievementBean();
            mapBasicAttributes(json, bean);
            singleAchievements.add(bean);
        }
        return singleAchievements;
    }

    @SuppressWarnings("unchecked")
    private List<IAchievementBean> mapTriggerAchievements(final AchievementType type, final List<? extends ISimpleTriggerAchievementJson> achievementJsons) {
        List<IAchievementBean> triggerAchievements = new LinkedList<>();
        for (ISimpleTriggerAchievementJson json : achievementJsons) {
            final ITriggerableAchievementBean bean = (ITriggerableAchievementBean) AchievementFactory.create(type);
            mapBasicAttributes(json, bean);
            final ITriggerParser<ITrigger> triggerParser = triggerParsers.get(type);
            bean.setTrigger(triggerParser.parse(json.getTrigger()));
            triggerAchievements.add(bean);
        }
        return triggerAchievements;
    }

    private Collection<IAchievement> mapScoreRangeAchievements(final List<AchievementJson<RangeTrigger<Long>>> scoreRange) {
        Collection<IAchievement> scoreRangeAchievements = new LinkedList<>();
        for (AchievementJson<RangeTrigger<Long>> json : scoreRange) {
            final ScoreRangeAchievementBean bean = new ScoreRangeAchievementBean();
            mapBasicAttributes(json, bean);
            final List<ScoreTriggerPair> triggers = getScoreTriggerPairs(json.getTrigger());
            bean.setTrigger(triggers);
            scoreRangeAchievements.add(bean);
        }
        return scoreRangeAchievements;
    }

    private List<ScoreTriggerPair> getScoreTriggerPairs(List<RangeTrigger<Long>> trigger) {
        List<ScoreTriggerPair> scoreRangeTriggers = new LinkedList<>();
        for (RangeTrigger<Long> t : trigger) {
            final ScoreTriggerPair scoreTriggerPair = new ScoreTriggerPair(t.getStart(), t.getEnd());
            scoreRangeTriggers.add(scoreTriggerPair);
        }
        return scoreRangeTriggers;
    }

    private Collection<IAchievement> mapTimeRangeAchievements(final List<AchievementJson<RangeTrigger<String>>> timeRange) {
        Collection<IAchievement> timeRangeAchievements = new LinkedList<>();
        for (AchievementJson<RangeTrigger<String>> json : timeRange) {
            final TimeRangeAchievementBean bean = new TimeRangeAchievementBean();
            mapBasicAttributes(json, bean);
            final List<TimeTriggerPair> triggers = getTimeTriggerPairs(json.getTrigger());
            bean.setTrigger(triggers);
            timeRangeAchievements.add(bean);
        }
        return timeRangeAchievements;
    }

    private List<TimeTriggerPair> getTimeTriggerPairs(List<RangeTrigger<String>> trigger) {
        List<TimeTriggerPair> timeTriggers = new LinkedList<>();
        for (RangeTrigger<String> t : trigger) {
            final TimeTriggerPair timeTriggerPair = new TimeTriggerPair(DateProvider.parseTime(t.getStart()), DateProvider.parseTime(t.getEnd()));
            timeTriggers.add(timeTriggerPair);
        }
        return timeTriggers;
    }

    private void mapBasicAttributes(final IAchievementJson json, final IAchievementBean bean) {
        bean.setId(json.getId());
        bean.setCategory(json.getCategory());
        bean.setSubscription(json.getSubscription());
    }
}
