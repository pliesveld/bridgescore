package pliesveld.bridge.model;



public interface ScoreMark {
    enum MADE implements ScoreMark {
        BID_AND_MADE,
        OVER_TRICKS;
    }
    enum SET implements ScoreMark {
        UNDER_TRICKS;
    }
    enum RUBBER implements ScoreMark {
        RUBBER_WIN;
    }
    enum SLAM implements ScoreMark {
        SLAM_SMALL,
        SLAM_GRAND;
    }
    enum BONUS implements ScoreMark {
        INSULT

    }
}
