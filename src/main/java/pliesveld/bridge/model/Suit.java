package pliesveld.bridge.model;

/**
 * Biddable suits during the bridge auction
 */
public enum Suit {
    CLUBS {
        boolean isMinor() {
            return true;
        }

        boolean isMajor() {
            return false;
        }
    },
    DIAMONDS {
        boolean isMinor() {
            return true;
        }

        boolean isMajor() {
            return false;
        }
    },
    HEARTS {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return true;
        }
    },
    SPADES {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return true;
        }
    },
    NOTRUMP {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return false;
        }
    };

    abstract boolean isMinor();
    abstract boolean isMajor();
}