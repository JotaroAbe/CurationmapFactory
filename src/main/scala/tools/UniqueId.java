package tools;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 重複しないユニークなIDを作成する
 * ただしインスタンスが異なるなら重複する
 */
public class UniqueId {
    //  最後に作成したID
    private long lastId = getNow();

    private UniqueId(){}

    public static UniqueId getInstance(){
        return UniqueIdInstanceHolder.INSTANCE;
    }

    public static class UniqueIdInstanceHolder{
        private static final UniqueId INSTANCE = new UniqueId();
    }


    //  絶対に重複しないIDの生成
    //  最大で１秒間に最大で１０００のIDが作成できる
    //  返される値は時間だけどミリ秒すなわち下３桁はミリ秒ではない
    //  より多くのIDを生成できるようにその秒でのリクエストで連番を返す
    //  仮に、１０００を超えてIDが作成できない時はブロックする
    public synchronized long createId() {
        while (true) {
            long newId = getNow();

            //  前回と同じ秒での取得リクエスト
            //  ミリ秒は連番を返す
            if (newId/1000 == lastId/1000) {
                if (lastId%1000 < 999) {
                    return ++lastId;
                }

                //  連番オーバーフロー
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else if (newId < lastId) {
                //  サーバーの時間が変わってしまった場合の対処
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            return lastId = newId;
        }
    }

    private static long getNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        long date = calendar.get(Calendar.YEAR)         * 10000000000000L;
        date += (calendar.get(Calendar.MONTH)+1)        * 100000000000L;
        date += calendar.get(Calendar.DAY_OF_MONTH)     * 1000000000L;
        date += calendar.get(Calendar.HOUR_OF_DAY)      * 10000000L;
        date += calendar.get(Calendar.MINUTE)           * 100000;
        date += calendar.get(Calendar.SECOND)           * 1000;
        return date;
    }
}
