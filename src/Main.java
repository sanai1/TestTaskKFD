import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

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
                "1. " + rub + " = " + capitalRUB + "\n" +
                "2. " + usd + " = " + capitalUSD + "\n" +
                "3. " + eur + " = " + capitalEUR + "\n" +
                "4. " + usdt + " = " + capitalUSDT + "\n" +
                "5. " + btc + " = " + capitalBTC);
    }

    // печать актальный обменныъ курсов
    public static void printCourse(int n) {
        if (n == 0) {
            printStart();
            System.out.println("--------------------");
        }
        System.out.println("Актуальные обменные курсы:\n" +
                "1. " + rub_usd + " = " + courseRubUsd + "\n" +
                "2. " + rub_eur + " = " + courseRubEur + "\n" +
                "3. " + usd_eur + " = " + courseUsdEur + "\n" +
                "4. " + usd_usdt + " = " + courseUsdUsdt + "\n" +
                "5. " + usd_btc + " = " + courseUsdBtc);
    }

    // печать средст терминала
    public static void printStart() {
        System.out.println("Средства терминала:\n" +
                "1. " + rub + " = " + startRUB + "\n" +
                "2. " + usd + " = " + startUSD + "\n" +
                "3. " + eur + " = " + startEUR + "\n" +
                "4. " + usdt + " = " + startUSDT + "\n" +
                "5. " + btc + " = " + startBTC);
    }

    // меню
    public static void menu(int n) {
        System.out.println("МЕНЮ\n" +
                "1. Для возвращения к предыдущему шагу напишите 'назад'\n" +
                "2. Для просмотра баланса напишите 'баланс'\n" +
                "3. Для просмотра актального курса напишите 'курс'\n" +
                "4. Для просмотра баланса терминала напишите 'терминал'");
        if (n == 0)
            System.out.println("--------------------");
        else if (n == 1) {
            Scanner in = new Scanner(System.in);
            String ans = in.nextLine().toLowerCase();
            label:
            while (true)
                switch (ans) {
                    case "назад":
                        return;
                    case "баланс":
                        printCapital(1);
                        break label;
                    case "курс":
                        printCourse(1);
                        break label;
                    case "терминал":
                        printStart();
                        break label;
                    default:
                        System.out.println("Некорректное значение. Повторите ввод:");
                        ans = in.nextLine().toLowerCase();
            }
        }
    }

    // изменение курса валютных пар
    public static void updateCourse() {
        final DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Random rn = new Random();

        int minimum = 0, maximum = 50;
        double randNum = (double) (rn.nextInt(maximum - minimum + 1) + minimum)/10;
        int pORm = rn.nextInt(1) + 1;
        if (pORm == 1) {
            courseRubUsd += Double.valueOf(decimalFormat.format(courseRubUsd * randNum / 100).replace(',', '.'));
            courseRubEur += Double.valueOf(decimalFormat.format(courseUsdEur * randNum / 100).replace(',', '.'));
            courseUsdEur += Double.valueOf(decimalFormat.format(courseUsdEur * randNum / 100).replace(',', '.'));
            courseUsdUsdt += Double.valueOf(decimalFormat.format(courseUsdUsdt * randNum / 100).replace(',', '.'));
            courseUsdBtc += Double.valueOf(decimalFormat.format(courseUsdBtc * randNum / 100).replace(',', '.'));
        } else if (pORm == 2) {
            courseRubUsd -= Double.valueOf(decimalFormat.format(courseRubUsd * randNum / 100).replace(',', '.'));
            courseRubEur -= Double.valueOf(decimalFormat.format(courseUsdEur * randNum / 100).replace(',', '.'));
            courseUsdEur -= Double.valueOf(decimalFormat.format(courseUsdEur * randNum / 100).replace(',', '.'));
            courseUsdUsdt -= Double.valueOf(decimalFormat.format(courseUsdUsdt * randNum / 100).replace(',', '.'));
            courseUsdBtc -= Double.valueOf(decimalFormat.format(courseUsdBtc * randNum / 100).replace(',', '.'));
        }
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

        System.out.println("Для вызова МЕНЮ напишите 'меню'");
        menu(0);
        printCourse(0);

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
            if (ans.isEmpty()) {
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else if (ans.equals("меню")) {
                menu(1);
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else {
                System.out.println("Некорректный ввод. Повторите попытку.");
            }
            ans = in.nextLine().toLowerCase();

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
                        printBuy(num_course);
                        break;
                    default:
                        System.out.println("Некорректный ввод. Повторите попытку.");
                }
                y_n = in.nextLine().toLowerCase();

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
                    cnt = in.nextLine().toLowerCase();

                    double count;
                    try {
                        count = Double.parseDouble(cnt);
                        if (count <= 0) {
                            cnt = "negative";
                            continue;
                        }
                    } catch (Exception e) {
                        if (cnt.equals("выйти") || cnt.equals("назад") || cnt.equals("меню"))
                            continue;
                        cnt = "string";
                        continue;
                    }
                    cnt = "";

                    Pair pair = exchange(exchange, num_course, count);
                    if (pair.first() && pair.second()) {
                        updateCourse();
                        System.out.println("Обмен произведен успешно");
                        printCourse(1);
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