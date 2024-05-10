package yalong.site.bo;

import lombok.Data;
import yalong.site.json.entity.match.ParticipantIdentities;
import yalong.site.json.entity.match.Participants;

import java.util.Date;
import java.util.List;

/**
 * 带有每个玩家的比赛历史记录
 *
 * @author WuYi
 */
@Data
public class GameMatchHistoryBO {
    private String endOfGameResult;
    private String gameCreation;
    private Date gameCreationDate;
    private String gameDuration;
    private Long gameId;
    private String gameVersion;
    private Integer mapId;
    private String gameMode;
    private String gameType;
    private List<ParticipantIdentities> participantIdentities;
    private List<Participants> participants;

}