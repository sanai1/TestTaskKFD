import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    // начальные средства польззователя
    static double capitalRUB = 1000000,
            capitalUSD = 0,
            capitalEUR = 0,
            capitalUSDT = 0,
            capitalBTC = 0;

    // начальные средства терминала
    static double startRUB = 10000,
            startUSD = 1000,
            startEUR = 1000,
            startUSDT = 1000,
            startBTC = 1.5;

    // курс валютных пар
    static double courseRubUsd = 90,
            courseRubEur = 95,
            courseUsdEur = 1.1,
            courseUsdUsdt = 1,
            courseUsdBtc = 45000;
    static boolean fl = true;

    // наименования валют и валютный пар
    static String rub_usd = "RUB/USD", rub_eur = "RUB/EUR", usd_eur = "USD/EUR", usd_usdt = "USD/USDT", usd_btc = "USD/BTC";
    static String rub = "RUB", usd = "USD", eur = "EUR", usdt = "USDT", btc = "BTC";

    // печать счета пользователя
    public static void printCapital(int n) {
        if (n == 0)
            System.out.print("До свидания! Сессия завершена. ");
        System.out.println("На вашем счету:\n" +
                "1. " + rub + " = " + String.format("%.2f", capitalRUB) + "\n" +
                "2. " + usd + " = " + String.format("%.2f", capitalUSD) + "\n" +
                "3. " + eur + " = " + String.format("%.2f", capitalEUR) + "\n" +
                "4. " + usdt + " = " + String.format("%.2f", capitalUSDT) + "\n" +
                "5. " + btc + " = " + String.format("%.2f", capitalBTC));
    }

    // печать актальный обменныъ курсов
    public static void printCourse(int n, String randomNum) {
        if (n == 0) {
            printStart();
            System.out.println("--------------------");
        }
        if (!randomNum.isEmpty())
            randomNum = " (" + randomNum + "%):";
        else
            randomNum = ":";

        System.out.println("Актуальные обменные курсы" + randomNum + "\n" +
                "1. " + rub_usd + " = " + String.format("%.3f", courseRubUsd) + "\n" +
                "2. " + rub_eur + " = " + String.format("%.3f", courseRubEur) + "\n" +
                "3. " + usd_eur + " = " + String.format("%.3f", courseUsdEur) + "\n" +
                "4. " + usd_usdt + " = " + String.format("%.3f", courseUsdUsdt) + "\n" +
                "5. " + usd_btc + " = " + String.format("%.3f", courseUsdBtc));
    }

    // печать средст терминала
    public static void printStart() {
        System.out.println("Средства терминала:\n" +
                "1. " + rub + " = " + String.format("%.2f", startRUB) + "\n" +
                "2. " + usd + " = " + String.format("%.2f", startUSD) + "\n" +
                "3. " + eur + " = " + String.format("%.2f", startEUR) + "\n" +
                "4. " + usdt + " = " + String.format("%.2f", startUSDT) + "\n" +
                "5. " + btc + " = " + String.format("%.2f", startBTC));
    }

    // меню

    public static void menu(int n) {
        System.out.println("""
                МЕНЮ
                1. Для завершения сессии напишите 'выход'
                2. Для возвращения к предыдущему шагу напишите 'назад'
                3. Для просмотра баланса напишите 'баланс'
                4. Для просмотра актального курса напишите 'курс'
                5. Для просмотра баланса терминала напишите 'терминал'""");
        if (n == 0)
            System.out.println("--------------------");
    }

    //
    public static String checkInput(String str) {
        return switch (str) {
            case "баланс" -> {
                printCapital(1);
                yield "end";
            } case "курс" -> {
                printCourse(1, "");
                yield "end";
            } case "терминал" -> {
                printStart();
                yield "end";
            } default -> str;
        };
    }

    // изменение курса валютных пар
    public static String updateCourse() {
        Random rn = new Random();

        int minimum = 0, maximum = 50;
        double randNum = (double) (rn.nextInt(maximum - minimum + 1) + minimum)/1000;
        int pORm = ThreadLocalRandom.current().nextInt(1, 3);
        if (pORm == 1) {
            courseRubUsd += courseRubUsd*randNum;
            courseRubEur += courseRubEur*randNum;
            courseUsdEur += courseUsdEur*randNum;
            courseUsdUsdt += courseUsdUsdt*randNum;
            courseUsdBtc += courseUsdBtc*randNum;
            return "+" + String.format("%.2f", randNum*100);
        } else if (pORm == 2) {
            courseRubUsd -= courseRubUsd*randNum;
            courseRubEur -= courseRubEur*randNum;
            courseUsdEur -= courseUsdEur*randNum;
            courseUsdUsdt -= courseUsdUsdt*randNum;
            courseUsdBtc -= courseUsdBtc*randNum;
            return "-" + String.format("%.2f", randNum*100);
        }
        return "";
    }

    // сообщение покупка/продажа в зависимости от валютной пары
    public static void printBuy(int num_course) {
        String a = "", b = "";
        b = switch (num_course) {
            case 1 -> {
                a = rub;
                yield usd;
            }
            case 2 -> {
                a = rub;
                yield eur;
            }
            case 3 -> {
                a = usd;
                yield eur;
            }
            case 4 -> {
                a = usd;
                yield usdt;
            }
            case 5 -> {
                a = usd;
                yield btc;
            }
            default -> b;
        };
        System.out.println("Обменять " + b + " на " + a + ", введите '1'\n" +
                "Обменять " + a + " на " + b + ", введите '2'");
    }

    // сообщение объем обмена
    public static void printExchange(int num_course, int exchange) {
        String a = "";
        a = switch (num_course) {
            case 1 -> {
                if (exchange == 1) yield rub;
                else yield usd;
            }
            case 2 -> {
                if (exchange == 1) yield rub;
                else yield eur;
            }
            case 3 -> {
                if (exchange == 1) yield usd;
                else yield eur;
            }
            case 4 -> {
                if (exchange == 1) yield usd;
                else yield usdt;
            }
            case 5 -> {
                if (exchange == 1) yield usd;
                else yield btc;
            }
            default -> a;
        };
        System.out.println("Сколько " + a + " хотите преобрести?");
    }

    // класс - булевая пара
    public static class Pair {
        private boolean person;
        private boolean terminal;

        public Pair() {
            this.person = true;
            this.terminal = true;
        }

        public void getFirst(boolean first) {
            this.person = first;
        }
        public void getSecond(boolean second) {
            this.terminal = second;
        }
        public boolean first() {
            return this.person;
        }
        public boolean second() {
            return this.terminal;
        }
    }
    // совершение обмена валюты
    public static Pair exchange(int exchange, int num_course, double count) {
        Pair pair = new Pair();
        if (exchange == 1) {
            switch (num_course) {
                case 1:
                    if (capitalUSD >= count)
                        if (startRUB >= count*courseRubUsd) {
                            capitalUSD -= count;
                            startUSD += count;
                            startRUB -= count * courseRubUsd;
                            capitalRUB += count * courseRubUsd;
                            System.out.println("Продано " + count + " " + usd + " за " + String.format("%.2f", count*courseRubUsd) + " " + rub);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 2:
                    if (capitalEUR >= count)
                        if (startRUB >= count*courseRubEur) {
                            capitalEUR -= count;
                            startEUR += count;
                            startRUB -= count*courseRubEur;
                            capitalRUB += count*courseRubEur;
                            System.out.println("Продано " + count + " " + eur + " за " + String.format("%.2f", count*courseRubEur) + " " + rub);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 3:
                    if (capitalEUR >= count)
                        if (startUSD >= count*courseUsdEur) {
                            capitalEUR -= count;
                            startEUR += count;
                            startUSD -= count*courseUsdEur;
                            capitalUSD += count*courseUsdEur;
                            System.out.println("Продано " + count + " " + eur + " за " + String.format("%.2f", count*courseUsdEur) + " " + usd);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 4:
                    if (capitalUSDT >= count)
                        if (startUSD >= count*courseUsdUsdt) {
                            capitalUSDT -= count;
                            startUSDT += count;
                            startUSD -= count*courseUsdUsdt;
                            capitalUSD += count*courseUsdUsdt;
                            System.out.println("Продано " + count + " " + usdt + " за " + String.format("%.2f", count*courseUsdUsdt) + " " + usd);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 5:
                    if (capitalBTC >= count)
                        if (startUSD >= count*courseUsdBtc) {
                            capitalBTC -= count;
                            startBTC += count;
                            startUSD -= count*courseUsdBtc;
                            capitalUSD += count*courseUsdBtc;
                            System.out.println("Продано " + count + " " + btc + " за " + String.format("%.2f", count*courseUsdBtc) + " " + usd);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
            }
        } else {
            switch (num_course) {
                case 1:
                    if (startUSD >= count)
                        if (capitalRUB >= count*courseRubUsd) {
                            startUSD -= count;
                            capitalUSD += count;
                            capitalRUB -= count*courseRubUsd;
                            startRUB += count*courseRubUsd;
                            System.out.println("Куплено " + count + " " + usd + " за " + String.format("%.2f", count*courseRubUsd) + " " + rub);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 2:
                    if (startEUR >= count)
                        if (capitalRUB >= count*courseRubEur) {
                            startEUR -= count;
                            capitalEUR += count;
                            capitalRUB -= count*courseRubEur;
                            startRUB += count*courseRubEur;
                            System.out.println("Куплено " + count + " " + eur + " за " + String.format("%.2f", count*courseRubEur) + " " + rub);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 3:
                    if (startEUR >= count)
                        if (capitalUSD >= count*courseUsdEur) {
                            startEUR -= count;
                            capitalEUR += count;
                            capitalUSD -= count*courseUsdEur;
                            startUSD += count*courseUsdEur;
                            System.out.println("Куплено " + count + " " + eur + " за " + String.format("%.2f", count*courseUsdEur) + " " + usd);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 4:
                    if (startUSDT >= count)
                        if (capitalUSD >= count*courseUsdUsdt) {
                            startUSDT -= count;
                            capitalUSDT += count;
                            capitalUSD -= count*courseUsdUsdt;
                            startUSD += count*courseUsdUsdt;
                            System.out.println("Куплено " + count + " " + usdt + " за " + String.format("%.2f", count*courseUsdUsdt) + " " + usd);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 5:
                    if (startBTC >= count)
                        if (capitalUSD >= count*courseUsdBtc) {
                            startBTC -= count;
                            capitalBTC += count;
                            capitalUSD -= count*courseUsdBtc;
                            startUSD += count*courseUsdBtc;
                            System.out.println("Куплено " + count + " " + btc + " за " + String.format("%.2f", count*courseUsdBtc) + " " + usd);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
            }
        }
        return pair;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Для просмотра МЕНЮ напишите 'меню'");
        menu(0);
        printCourse(0, "");

        String ans = "";
        boolean trade = false;
        while (fl || trade) {
            if (trade) {
                trade = false;
                fl = true;
            }
            if (ans.equals("выйти") || ans.equals("назад")) {
                printCapital(0);
                fl = false;
                continue;
            }
            if (ans.isEmpty() || ans.equals("end")) {
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else if (ans.equals("меню")) {
                menu(1);
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else {
                System.out.println("Некорректный ввод. Повторите попытку.");
            }
            ans = checkInput(in.nextLine().toLowerCase());

            int num_course;
            try {
                num_course = Integer.parseInt(ans);
                if (num_course < 1 || num_course > 5)
                    continue;
            } catch (Exception e) {
                continue;
            }
            ans = "";

            String y_n = "";
            label:
            while (fl) {
                switch (y_n) {
                    case "выйти":
                        printCapital(0);
                        fl = false;
                        break label;
                    case "назад":
                        break label;
                    case "меню":
                        menu(1);
                    case "":
                    case "end":
                        printBuy(num_course);
                        break;
                    default:
                        System.out.println("Некорректный ввод. Повторите попытку.");
                }
                y_n = checkInput(in.nextLine().toLowerCase());

                int exchange;
                if (y_n.equals("1"))
                    exchange = 1;
                else if (y_n.equals("2"))
                    exchange = 2;
                else
                    continue;
                y_n = "";

                String cnt = "";
                label2:
                while (fl) {
                    switch (cnt) {
                        case "выйти":
                            printCapital(0);
                            fl = false;
                            break label2;
                        case "назад":
                            break label2;
                        case "меню":
                            menu(1);
                        case "":
                        case "end":
                            printExchange(num_course, exchange);
                            break;
                        default:
                            switch (cnt) {
                                case "string":
                                    System.out.println("Некорректный ввод. Необходимо ввести число.");
                                    break;
                                case "negative":
                                    System.out.println("Сумма должна быть положительным числом.");
                                    break;
                                case "person":
                                    System.out.println("У вас недостаточно средств для совершения обмена");
                                    break;
                                case "terminal":
                                    System.out.println("Терминалу не хватает средств для совершения обмена");
                                    break;
                            }
                            System.out.println("Введите новую сумму:");
                    }
                    cnt = checkInput(in.nextLine().toLowerCase());

                    double count;
                    try {
                        count = Double.parseDouble(cnt);
                        if (count <= 0) {
                            cnt = "negative";
                            continue;
                        }
                    } catch (Exception e) {
                        if (cnt.equals("end") || cnt.equals("меню") || cnt.equals("назад") || cnt.equals("выйти"))
                            continue;
                        cnt = "string";
                        continue;
                    }
                    cnt = "";

                    Pair pair = exchange(exchange, num_course, count);
                    if (pair.first() && pair.second()) {
                        String randomNum = updateCourse();
                        printCourse(1, randomNum);
                        fl = false;
                        trade = true;
                    } else {
                        if (!pair.first())
                            cnt = "person";
                        else
                            cnt = "terminal";
                    }
                }
            }
            if (!fl) continue;
            ans = "";
        }
    }
}