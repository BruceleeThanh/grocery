package com.grocery.common;

import com.grocery.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ThanhBeo on 01/08/2017.
 */

public class DateTimeFormat {
    public static SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat inTime = new SimpleDateFormat("hh:mm:ss");
    public static SimpleDateFormat inTime3 = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat inTime2 = new SimpleDateFormat("hh:mm");
    public static SimpleDateFormat inTime4 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat inMonth = new SimpleDateFormat("MMMM");
    public static SimpleDateFormat inDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static SimpleDateFormat inDateTime2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    public static SimpleDateFormat outDateTime1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    public static SimpleDateFormat outDateTime2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:a", Locale.ENGLISH);
    public static SimpleDateFormat outTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    public static SimpleDateFormat outdate = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat outSDF = new SimpleDateFormat(DateUtils.MMM_DD);
    public static SimpleDateFormat outDateTime = new SimpleDateFormat(DateUtils.MMM_DD + " hh:mm a");
    public static SimpleDateFormat outMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
    public static SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat outMonth1 = new SimpleDateFormat("MM");

    public static String formatDate(String inDate) {
        String outDateTxt = "";
        if (inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDateTxt = outDate.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return outDateTxt;
    }

    public static String formatDate3(String inDate) {
        String outDateTxt = "";
        if (inDate != null) {
            try {
                Date date = outMonth.parse(inDate);
                outDateTxt = outMonth1.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return outDateTxt;
    }

    public static String formatDate2(String inDate) {
        String outDateTxt = "";
        if (inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDateTxt = outdate.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return outDateTxt;
    }

    public static String formatDate4(String inDate) {
        String outDateTxt = "";
        if (inDate != null) {
            try {
                Date date = outdate.parse(inDate);
                outDateTxt = outDateTime1.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return outDateTxt;
    }

    public static String formatMonth(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                Date date = inMonth.parse(inDate);
                outDate = outMonth.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return outDate;
    }

    public static String formatTime(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inTime2.parse(in);
                out = outTime.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }
    public static String formatTime7(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inTime4.parse(in);
                out = outTime.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime5(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = outTime.parse(in);
                out = inTime3.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }
    public static String formatTime8(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = outTime.parse(in);
                out = inTime4.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime4(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inTime2.parse(in);
                out = inDateTime2.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime1(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inDateTime.parse(in);
                out = outTime.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime6(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inDateTime.parse(in);
                out = outDateTime2.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime2(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inDateTime.parse(in);
                out = outDateTime1.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTime3(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inSDF.parse(in);
                out = outDateTime1.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatTimeDate(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inDateTime.parse(in);
                out = outDate.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatDateTime(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = inDateTime.parse(in);
                out = inSDF.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String formatDateTime1(String in) {
        String out = "";
        if (in != null) {
            try {
                Date date = outDateTime1.parse(in);
                out = inSDF.format(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    public static String getDateYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        return dateFormat.format(yesterday).toString();
    }

    public static String coverTimeGMTtoLocal(String timeIn) throws ParseException {
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        final Date timezone = formatter.parse(timeIn);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(timezone);
    }
}
