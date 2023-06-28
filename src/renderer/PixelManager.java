package renderer;

/** PixelManager is a helper class. It is used for multi-threading in the
 * renderer and
 * for follow up its progress.<br/>
 * A Camera uses one pixel manager object and several Pixel objects - one in
 * each thread.
 * @author Dan Zilberstein */
class PixelManager {
    /** Immutable class for object containing allocated pixel (with its row and
     * column numbers) */
    record Pixel(int col, int row) {
    }

    /** Maximum rows of pixels */
    private int                 maxRows       = 0;
    /** Maximum columns of pixels */
    private int                 maxCols       = 0;
    /** Total amount of pixels in the generated image */
    private long                totalPixels   = 0l;

    /** Currently processed row of pixels */
    private volatile int        cRow          = 0;
    /** Currently processed column of pixels */
    private volatile int        cCol          = -1;
    /** Amount of pixels that have been processed */
    private volatile long       pixels        = 0l;
    /** Last printed progress update percentage */
    private volatile int        lastPrinted   = 0;

    /** Flag of debug printing of progress percentage */
    private boolean             print         = false;
    /** Progress percentage printing interval */
    private long                printInterval = 100l;
    /** Printing format */
    private static final String PRINT_FORMAT  = "%5.1f%%\r";
    /** Mutual exclusion object for synchronizing next pixel allocation between
     * threads */
    private Object              mutexNext     = new Object();
    /** Mutual exclusion object for printing progress percentage in console window
     * by different threads */
    private Object              mutexPixels   = new Object();

    /** Initialize pixel manager data for multi-threading
     * @param maxRows  the amount of pixel rows
     * @param maxCols  the amount of pixel columns
     * @param interval print time interval in seconds, 0 if printing is not
     *                 required */
    PixelManager(int maxRows, int maxCols, double interval) {
        this.maxRows  = maxRows;
        this.maxCols  = maxCols;
        totalPixels   = (long) maxRows * maxCols;
        printInterval = (int) (interval * 10);
        if (print = printInterval != 0) System.out.printf(PRINT_FORMAT, 0d);
    }

    /** Function for thread-safe manipulating of main follow up Pixel object - this
     * function is critical section for all the threads, and the pixel manager data
     * is the shared data of this critical section.<br/>
     * The function provides next available pixel number each call.
     * @return true if next pixel is allocated, false if there are no more pixels */
    Pixel nextPixel() {
        synchronized (mutexNext) {
            // אומר שכל הפיקסלים עובדו אין יותר פיקסליים לעיבוד
            if (cRow == maxRows) return null;
// כדי לעבור לעמודה הבאה
            ++cCol;
            // זה אומר שיש יותר פיקסליים בשורה הנוכחית לעיבוד
            if (cCol < maxCols)
            // יוצר אוביקט חדש עם הערכיים הנוכחים ומחזיר אותו
                return new Pixel(cRow, cCol);
//כל הפיקסליים בשורה הנוכחית עובדו
            cCol = 0;
            // כדי לעבור לשורה הבאה
            ++cRow;
            ///יד עוד שורות לעיבוד
            if (cRow < maxRows)
                return new Pixel(cRow, cCol);
            // אם אף אחד מהתנאים לא מתקיים זה אומר שכל השורות והעמודות עובדו
        }
        return null;
    }

    /** Finish pixel processing by updating and printing of progress percentage */
    void pixelDone() {
        boolean flag       = false;
        int     percentage = 0;
        //בלוק מסונכרן הוא מבטיח שרק שרשןור אחד יכול לגשת ולשנות משתנים משופים כמו פיקסלים
        synchronized (mutexPixels) {
            // ציון שהפיקסל עבר עיבוד
            ++pixels;
            // דגל ההדפבה דלוק מציין שהדפסת התקדמות מופעלת
            if (print) {
                //מחשב את אחוז ההתתקדמות הנוכחי על ידי חלוקת הפיקסלים בכמות הפיקסלים סהכ ןהכפלתם ב10000
                percentage = (int) (1000l * pixels / totalPixels);
                // בודק אם האחוז שחושב פחות אחוז  תנאי זה קובע אם יש להדפיס את ההתקדמות או לא
                if (percentage - lastPrinted >= printInterval) {
                   // מעדכן לאוחז המוכחי
                    lastPrinted = percentage;
                    flag        = true;
                }
            }
            // מדפיס את אחוז ההתקדמות
            if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
        }
    }
}