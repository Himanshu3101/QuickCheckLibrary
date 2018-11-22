package com.quickcheck;

import java.text.DecimalFormat;

public class EnglishNumberToWords {

private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty",
        " fifty", " sixty", " seventy", " eighty", " ninety" };

private static final String[] numNames = { "", " one", " two", " three", " four", " five",
        " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen",
        " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen" };

private static String convertLessThanOneThousand(int number)
{
    String soFar;

    if (number % 100 < 20)
    {
        soFar = numNames[number % 100];
        number /= 100;
    } else
    {
        soFar = numNames[number % 10];
        number /= 10;

        soFar = tensNames[number % 10] + soFar;
        number /= 10;
    }
    if (number == 0)
        return soFar;
    return numNames[number] + " hundred" + soFar;
}

public static String convert(long number)
{
    // 0 to 999 999 999 999
    if (number == 0)
    {
        return "zero";
    }

    String snumber = Long.toString(number);

    // pad with "0"
    String mask = "00000000";
    DecimalFormat df = new DecimalFormat(mask);
    snumber = df.format(number);

    // XXXnnnnnnnnn
    int billions = Integer.parseInt(snumber.substring(0, 1));
    // nnnXXXnnnnnn
    int millions = Integer.parseInt(snumber.substring(1, 3));
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(3, 5));
    // nnnnnnnnnXXX
    int thousands = Integer.parseInt(snumber.substring(5, 8));

    String tradBillions;
    switch (billions)
    {
        case 0:
            tradBillions = "";
            break;
        case 1:
            tradBillions = convertLessThanOneThousand(billions) + " Crore ";
            break;
        default:
            tradBillions = convertLessThanOneThousand(billions) + " Crore ";
    }
    String result = tradBillions;

    String tradMillions;
    switch (millions)
    {
        case 0:
            tradMillions = "";
            break;
        case 1:
            tradMillions = convertLessThanOneThousand(millions) + " Lakh ";
            break;
        default:
            tradMillions = convertLessThanOneThousand(millions) + " Lakh ";
    }
    result = result + tradMillions;

    String tradHundredThousands;
    switch (hundredThousands)
    {
        case 0:
            tradHundredThousands = "";
            break;
        case 1:
            tradHundredThousands = "one thousand ";
            break;
        default:
            tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
    }
    result = result + tradHundredThousands;

    String tradThousand;
    tradThousand = convertLessThanOneThousand(thousands);
    result = result + tradThousand;

    // remove extra spaces!
    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
}

}