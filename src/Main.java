import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // начальные средства польззователя
        double capitalRUB = 1000000,
                capitalUSD = 0,
                capitalEUR = 0,
                capitalUSDT = 0,
                capitalBTC = 0;

        // начальные средства терминала
        double startRUB = 10000,
                startUSD = 1000,
                startEUR = 1000,
                startUSDT = 1000,
                startBTC = 1.5;

        // курс валютных пар
        double courseRubUsd = 90,
                courseRubEur = 95,
                courseUsdEur = 1.1,
                courseUsdUsdt = 1,
                courseUsdBtc = 45000;

        String rub_usd = "RUB/USD", rub_eur = "RUB/EUR", usd_eur = "USD/EUR", usd_usdt = "USD/USDT", usd_btc = "USD/BTC";
        String rub = "RUB", usd = "USD", eur = "EUR", usdt = "USDT", btc = "BTC";
        System.out.println("Актуальные обменные курсы:\n" +
                "1. " + rub_usd + " = " + courseRubUsd + "\n" +
                "2. " + rub_eur + " = " + courseRubEur + "\n" +
                "3. " + usd_eur + " = " + courseUsdEur + "\n" +
                "4. " + usd_usdt + " = " + courseUsdUsdt + "\n" +
                "5. " + usd_btc + " = " + courseUsdBtc);
        System.out.println("Чтобы покинуть терминал напишите '-10' или 'выйти'\nЧтобы вернуться к предыдущему шагу напишите '-1' или 'назад'");

        boolean fl = true;
        String ans = "";
        boolean trade = false;
        while (fl || trade) {
            if (trade) {
                trade = false;
                fl = true;
            }
            if (ans.equals("-10") || ans.equals("выйти") || ans.equals("-1") || ans.equals("назад")) {
                System.out.println("До свидания!\nСессия завершена. На вашем счету:\n" +
                        "1. " + rub + " = " + capitalRUB + "\n" +
                        "2. " + usd + " = " + capitalUSD + "\n" +
                        "3. " + eur + " = " + capitalEUR + "\n" +
                        "4. " + usdt + " = " + capitalUSDT + "\n" +
                        "5. " + btc + " = " + capitalBTC);
                fl = false;
                continue;
            }
            if (ans.equals(""))
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            else
                System.out.println("некорректный ввод. Повторите попытку.");
            ans = in.nextLine();

            Integer num_course;
            try {
                num_course = Integer.valueOf(ans);
                if (num_course < 1 || num_course > 5)
                    continue;
            } catch (Exception e) {
                continue;
            }
            ans = "";

            String y_n = "";
            while (fl) {
                if (y_n.equals("-10") || y_n.equals("выйти") || !fl) {
                    System.out.println("До свидания!\n" +
                            "Сессия завершена. На вашем счету:\n" +
                            "1. " + rub + " = " + capitalRUB + "\n" +
                            "2. " + usd + " = " + capitalUSD + "\n" +
                            "3. " + eur + " = " + capitalEUR + "\n" +
                            "4. " + usdt + " = " + capitalUSDT + "\n" +
                            "5. " + btc + " = " + capitalBTC);
                    fl = false;
                    break;
                } else if (y_n.equals("-1") || y_n.equals("назад")) {
                    break;
                }
                if (y_n.equals(""))
                    System.out.println("Продать, введите '1' или 'продать'\n" +
                            "Купить, введите '2' или 'купить'");
                else
                    System.out.println("Некорректный ввод. Повторите попытку.");
                y_n = in.nextLine();

                Integer exchange;
                if (y_n.equals("1") || y_n.equals("продать"))
                    exchange = 1;
                else if (y_n.equals("2") || y_n.equals("купить"))
                    exchange = 2;
                else
                    continue;
                y_n = "";

                String cnt = "";
                while (fl) {
                    if (cnt.equals("-10") || cnt.equals("выйти")) {
                        System.out.println("До свидания!\n" +
                                "Сессия завершена. На вашем счету:\n" +
                            "1. " + rub + " = " + capitalRUB + "\n" +
                            "2. " + usd + " = " + capitalUSD + "\n" +
                            "3. " + eur + " = " + capitalEUR + "\n" +
                            "4. " + usdt + " = " + capitalUSDT + "\n" +
                            "5. " + btc + " = " + capitalBTC);
                        fl = false;
                        break;
                    } else if (cnt.equals("-1") || cnt.equals("назад")) {
                        break;
                    }
                    if (cnt.equals(""))
                        if (exchange == 1) {
                            System.out.println("Введите жалаемый объем продажи:");
                        } else {
                            System.out.println("Введите желаемый объем покупки:");
                        }
                    else
                        System.out.println("У вас или терминала недостаточно средств для совершения сделки\n" +
                                "Попробуйте ввести новую сумму.");
//                        System.out.println("Некорректный ввод. Повторите попытку.");
                    cnt = in.nextLine();

                    Double count;
                    try {
                        count = Double.valueOf(cnt);
                    } catch (Exception e) {
                        continue;
                    }
                    cnt = "";

                    Random rn = new Random();
                    int minimum = 0, maximum = 50;
                    double randNum = (double) (rn.nextInt(maximum - minimum + 1) + minimum)/10;
                    boolean finish = false;
                    if (exchange == 1) {
                        if (num_course == 1) {
                            if (capitalUSD >= count && startRUB >= count*courseRubUsd) {
                                capitalUSD -= count;
                                startUSD += count;
                                startRUB -= count*courseRubUsd;
                                capitalRUB += count*courseRubUsd;
                                finish = true;
                            }
                        } else if (num_course == 2) {
                            if (capitalEUR >= count && startRUB >= count*courseRubEur) {
                                capitalEUR -= count;
                                startEUR += count;
                                startRUB -= count*courseRubEur;
                                capitalRUB += count*courseRubEur;
                                finish = true;
                            }
                        } else if (num_course == 3) {
                            if (capitalEUR >= count && startUSD >= count*courseUsdEur) {
                                capitalEUR -= count;
                                startEUR += count;
                                startUSD -= count*courseUsdEur;
                                capitalUSD += count*courseUsdEur;
                                finish = true;
                            }
                        } else if (num_course == 4) {
                            if (capitalUSDT >= count && startUSD >= count*courseUsdUsdt) {
                                capitalUSDT -= count;
                                startUSDT += count;
                                startUSD -= count*courseUsdUsdt;
                                capitalUSD += count*courseUsdUsdt;
                                finish = true;
                            }
                        } else {
                            if (capitalBTC >= count && startUSD >= count*courseUsdBtc) {
                                capitalBTC -= count;
                                startBTC += count;
                                startUSD -= count*courseUsdBtc;
                                capitalUSD += count*courseUsdBtc;
                                finish = true;
                            }
                        }
                    } else {
                        if (num_course == 1) {
                            if (startUSD >= count && capitalRUB >= count*courseRubUsd) {
                                startUSD -= count;
                                capitalUSD += count;
                                capitalRUB -= count*courseRubUsd;
                                startRUB += count*courseRubUsd;
                                finish = true;
                            }
                        } else if (num_course == 2) {
                            if (startEUR >= count && capitalRUB >= count*courseRubEur) {
                                startEUR -= count;
                                capitalEUR += count;
                                capitalRUB -= count*courseRubEur;
                                startRUB += count*courseRubEur;
                                finish = true;
                            }
                        } else if (num_course == 3) {
                            if (startEUR >= count && capitalUSD >= count*courseUsdEur) {
                                startEUR -= count;
                                capitalEUR += count;
                                capitalUSD -= count*courseUsdEur;
                                startUSD += count*courseUsdEur;
                                finish = true;
                            }
                        } else if (num_course == 4) {
                            if (startUSDT >= count && capitalUSD >= count*courseUsdUsdt) {
                                startUSDT -= count;
                                capitalUSDT += count;
                                capitalUSD -= count*courseUsdUsdt;
                                startUSD += count*courseUsdUsdt;
                                finish = true;
                            }
                        } else {
                            if (startBTC >= count && capitalUSD >= count*courseUsdBtc) {
                                startBTC -= count;
                                capitalBTC += count;
                                capitalUSD -= count*courseUsdBtc;
                                startUSD += count*courseUsdBtc;
                                finish = true;
                            }
                        }
                    }
                    int pORm = rn.nextInt(1) + 1;
                    if (finish) {
                        if (pORm == 1) {
                            courseRubUsd += courseRubUsd * randNum / 100;
                            courseRubEur += courseUsdEur * randNum / 100;
                            courseUsdEur += courseUsdEur * randNum / 100;
                            courseUsdUsdt += courseUsdUsdt * randNum / 100;
                            courseUsdBtc += courseUsdBtc * randNum / 100;
                        } else if (pORm == 2) {
                            courseRubUsd -= courseRubUsd * randNum / 100;
                            courseRubEur -= courseUsdEur * randNum / 100;
                            courseUsdEur -= courseUsdEur * randNum / 100;
                            courseUsdUsdt -= courseUsdUsdt * randNum / 100;
                            courseUsdBtc -= courseUsdBtc * randNum / 100;
                        }
                        System.out.println("Обмен произведен успешно");
                        System.out.println("Актуальные обменные курсы:\n" +
                            "1. " + rub_usd + " = " + courseRubUsd + "\n" +
                            "2. " + rub_eur + " = " + courseRubEur + "\n" +
                            "3. " + usd_eur + " = " + courseUsdEur + "\n" +
                            "4. " + usd_usdt + " = " + courseUsdUsdt + "\n" +
                            "5. " + usd_btc + " = " + courseUsdBtc);
                        fl = false;
                        trade = true;
                    } else {
                        cnt = "fail";
                    }
                }
            }
            if (!fl) continue;

            ans = "";
        }


    }
}