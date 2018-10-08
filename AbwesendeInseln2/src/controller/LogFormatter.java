package controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Custom formatter wich brings a log message into the correct syntax and adds missing information:
 * <p>	-appends [Thread : x] if missing
 * <br>	-appends [Time : hh:mm:ss.SSS] if missing
 * <p>  if at the time 13:05:49.207, thread 34 calls
 * <br>	log("[Exc]Something went horribly wrong!");
 * <br>	will log -->
 * <br>	[Exc]Something went horribly wrong! [Thread : 34] [Time : 13:05:49.207]
 * @author georgschwab
 *
 */
class LogFormatter extends Formatter {
	@Override
    public String format(final LogRecord r) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatMessage(r));
        if(!sb.toString().contains("[Thread :")){
			sb.append(" [Thread : "+ Thread.currentThread().getId() + "]");
		}
		if(!sb.toString().contains("[Time:")){
			String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
			sb.append(" [Time : "+ timeStamp + "]");
		}
		sb.append(System.getProperty("line.separator"));
        if (null != r.getThrown()) {
            sb.append("Throwable occurred: "); //$NON-NLS-1$
            Throwable t = r.getThrown();
            PrintWriter pw = null;
            try {
                StringWriter sw = new StringWriter();
                pw = new PrintWriter(sw);
                t.printStackTrace(pw);
                sb.append(sw.toString());
            } finally {
                if (pw != null) {
                    try {
                        pw.close();
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return sb.toString();
    }
}
