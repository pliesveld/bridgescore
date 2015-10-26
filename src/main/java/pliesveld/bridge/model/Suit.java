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

        @Override
        public String toString()
        {
            return "\u2663";
        }
    },
    DIAMONDS {
        boolean isMinor() {
            return true;
        }

        boolean isMajor() {
            return false;
        }

        @Override
        public String toString()
        {
            return "\u2666";
        }
    },
    HEARTS {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return true;
        }

        @Override
        public String toString()
        {
            return "\u2665";
        }
    },
    SPADES {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return true;
        }

        @Override
        public String toString()
        {
            return "\u2660";
        }
    },
    NOTRUMP {
        boolean isMinor() {
            return false;
        }

        boolean isMajor() {
            return false;
        }

        @Override
        public String toString()
        {
            return "NT";
        }
    };

    abstract boolean isMinor();
    abstract boolean isMajor();
}