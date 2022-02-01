package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;

public class MultiThreadHelper {
    public interface MultiThreadHelperLambda {
        void exec();
    }

    public static class MultiThreadHelperLambdaWaiter {
        private volatile boolean complete = false;

        protected void on_complete() {
            complete = true;
        }

        public void complete() {
            while (!complete) {
                Thread.onSpinWait();
            }
        }
    }

    public static class MultiThreadHelperClassWaiter {
        private volatile boolean complete = false;
        public Object instance;

        protected void on_complete() {
            complete = true;
        }

        public MultiThreadHelperClassWaiter complete() {
            while (!complete) {
                Thread.onSpinWait();
            }

            return this;
        }
    }


    public static MultiThreadHelperLambdaWaiter run(MultiThreadHelperLambda lambda) {
        MultiThreadHelperLambdaWaiter waiter = new MultiThreadHelperLambdaWaiter();

        new Thread(() -> {
            Log.log("Thread start (" + lambda.toString() + ")");

            lambda.exec();
            waiter.on_complete();

            Log.log("Thread exit (" + lambda.toString() + ")");
        }).start();

        return waiter;
    }

    public static MultiThreadHelperClassWaiter run(Class _class) {
        MultiThreadHelperClassWaiter waiter = new MultiThreadHelperClassWaiter();

        new Thread(() -> {
            Log.log("Thread start (" + _class.getSimpleName() + ")");

            try {
                waiter.instance = _class.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            waiter.on_complete();

            Log.log("Thread exit (" + _class.getSimpleName() + ")");
        }).start();

        return waiter;
    }
}
