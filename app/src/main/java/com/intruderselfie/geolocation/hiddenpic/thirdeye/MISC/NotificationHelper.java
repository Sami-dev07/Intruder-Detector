package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

public class NotificationHelper {

    public enum Priority {

        LOW(2, -1);
        private final int higher24;
        private final int lower24;

        Priority(int i, int i2) {
            this.higher24 = i;
            this.lower24 = i2;
        }

        public int getBelow24() {
            return this.lower24;
        }

        public int getAboveAnd24() {
            return this.higher24;
        }
    }
}
