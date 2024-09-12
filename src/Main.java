import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    // начальные средства пользователя
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
                "5. " + btc + " = " + String.format("%.7f", capitalBTC));
    }

    // печать актуальных обменных курсов
    public static void printCourse(int n) {
        if (n == 0) {
            printStart();
            System.out.println("--------------------");
        }

        System.out.println("Актуальные обменные курсы:\n" +
                "1. " + rub_usd + " = " + String.format("%.2f", courseRubUsd) + "\n" +
                "2. " + rub_eur + " = " + String.format("%.2f", courseRubEur) + "\n" +
                "3. " + usd_eur + " = " + String.format("%.3f", courseUsdEur) + "\n" +
                "4. " + usd_usdt + " = " + String.format("%.3f", courseUsdUsdt) + "\n" +
                "5. " + usd_btc + " = " + String.format("%.1f", courseUsdBtc));
    }

    // печать средст терминала
    public static void printStart() {
        System.out.println("Средства терминала:\n" +
                "1. " + rub + " = " + String.format("%.2f", startRUB) + "\n" +
                "2. " + usd + " = " + String.format("%.2f", startUSD) + "\n" +
                "3. " + eur + " = " + String.format("%.2f", startEUR) + "\n" +
                "4. " + usdt + " = " + String.format("%.2f", startUSDT) + "\n" +
                "5. " + btc + " = " + String.format("%.7f", startBTC));
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
                printCourse(1);
                yield "end";
            } case "терминал" -> {
                printStart();
                yield "end";
            } default -> str;
        };
    }

    // генерация изменения курса для каждой валютной пары
    public static double getRandomNum() {
        int minimum = 0, maximum = 50;
        Random random = new Random();
        int pORm = ThreadLocalRandom.current().nextInt(1, 3);
        if (pORm == 1)
            return (double) (random.nextInt(maximum - minimum + 1) + minimum)/1000;
        else
            return (double) -(random.nextInt(maximum - minimum + 1) + minimum)/1000;
    }

    // изменение курса валютных пар
    public static void updateCourse() {
            courseRubUsd += Math.floor(100*courseRubUsd*getRandomNum())/100;
            courseRubEur += Math.floor(100*courseRubEur*getRandomNum())/100;
            courseUsdEur += Math.floor(100*courseUsdEur*getRandomNum())/100;
            courseUsdUsdt += Math.floor(100*courseUsdUsdt*getRandomNum())/100;
            courseUsdBtc += Math.floor(100*courseUsdBtc*getRandomNum())/100;
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

    // проверка на максимально возможный обмен валюты
    public static double checkCourse(int num_course, int exchange) {
        return switch (num_course) {
            case 1 -> {
                if (exchange == 1) yield Math.min(Math.floor(100*capitalUSD * courseRubUsd)/100, startRUB);
                else yield Math.min(Math.floor(100 * (capitalRUB / courseRubUsd))/100, startUSD);
            } case 2 -> {
                if (exchange == 1) yield Math.min(Math.floor(100*capitalEUR * courseRubEur)/100, startRUB);
                else yield Math.min(Math.floor(100 * (capitalRUB / courseRubEur))/100, startEUR);
            } case 3 -> {
                if (exchange == 1) yield Math.min(Math.floor(100*capitalEUR * courseUsdEur)/100, startUSD);
                else yield Math.min(Math.floor(100 * (capitalUSD / courseUsdEur))/100, startEUR);
            } case 4 -> {
                if (exchange == 1) yield Math.min(Math.floor(100*capitalUSDT * courseUsdUsdt)/100, startUSD);
                else yield Math.min(Math.floor(100 * (capitalUSD / courseUsdUsdt))/100, startUSDT);
            } case 5 -> {
                if (exchange == 1) yield Math.min(Math.floor(10000000*capitalBTC * courseUsdBtc)/10000000, startUSD);
                else yield Math.min(Math.floor(10000000 * (capitalUSD / courseUsdBtc))/10000000, startBTC);
            } default -> -1;
        };
    }

    // сообщение объем обмена
    public static int printExchange(int num_course, int exchange) {
        String a = switch (num_course) {
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
            default -> "";
        };
        double checkCourse = checkCourse(num_course, exchange);
        if (checkCourse == -1)
            return -1;
        if (num_course == 5 && exchange == 2)
            System.out.println("Введите сумму от 0.001 " + a + " до " + checkCourse(num_course, exchange) + " " + a);
        else
            System.out.println("Введите сумму от 0.01 " + a + " до " + checkCourse(num_course, exchange) + " " + a);
        return 1;
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
        if (num_course == 5) count = Math.floor(10000000*count)/10000000;
        else count = Math.floor(100*count)/100;
        Pair pair = new Pair();
        if (exchange == 1) {
            switch (num_course) {
                case 1:
                    if (capitalUSD >= Math.floor(100 * (count / courseRubUsd))/100)
                        if (startRUB >= count) {
                            capitalUSD -= Math.floor(100 * (count / courseRubUsd))/100;
                            startUSD += Math.floor(100 * (count / courseRubUsd))/100;
                            startRUB -= count;
                            capitalRUB += count;
                            System.out.println("Продано " + Math.floor(100 * (count / courseRubUsd))/100 + " " + usd + " за " + count + " " + rub);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 2:
                    if (capitalEUR >= Math.floor(100 * (count / courseRubEur))/100)
                        if (startRUB >= count) {
                            capitalEUR -= Math.floor(100 * (count / courseRubEur))/100;
                            startEUR += Math.floor(100 * (count / courseRubEur))/100;
                            startRUB -= count;
                            capitalRUB += count;
                            System.out.println("Продано " + Math.floor(100 * (count / courseRubEur))/100 + " " + eur + " за " + count + " " + rub);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 3:
                    if (capitalEUR >= Math.floor(100 * (count / courseUsdEur))/100)
                        if (startUSD >= count) {
                            capitalEUR -= Math.floor(100 * (count / courseUsdEur))/100;
                            startEUR += Math.floor(100 * (count / courseUsdEur))/100;
                            startUSD -= count;
                            capitalUSD += count;
                            System.out.println("Продано " + Math.floor(100 * (count / courseUsdEur))/100 + " " + eur + " за " + count + " " + usd);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 4:
                    if (capitalUSDT >= Math.floor(100 * (count / courseUsdUsdt))/100)
                        if (startUSD >= count) {
                            capitalUSDT -= Math.floor(100 * (count / courseUsdUsdt))/100;
                            startUSDT += Math.floor(100 * (count / courseUsdUsdt))/100;
                            startUSD -= count;
                            capitalUSD += count;
                            System.out.println("Продано " + Math.floor(100 * (count / courseUsdUsdt))/100 + " " + usdt + " за " + count + " " + usd);
                        } else
                            pair.getSecond(false);
                    else
                        pair.getFirst(false);
                    break;
                case 5:
                    if (capitalBTC >= Math.floor(10000000 * (count / courseUsdBtc))/10000000)
                        if (startUSD >= count) {
                            capitalBTC -= Math.floor(10000000 * (count / courseUsdBtc))/10000000;
                            startBTC += Math.floor(10000000 * (count / courseUsdBtc))/10000000;
                            startUSD -= count;
                            capitalUSD += count;
                            System.out.println("Продано " + Math.floor(10000000 * (count / courseUsdBtc))/10000000 + " " + btc + " за " + count + " " + usd);
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
                        if (capitalRUB >= Math.floor(100*count*courseRubUsd)/100) {
                            startUSD -= count;
                            capitalUSD += count;
                            capitalRUB -= Math.floor(100*count*courseRubUsd)/100;
                            startRUB += Math.floor(100*count*courseRubUsd)/100;
                            System.out.println("Куплено " + count + " " + usd + " за " + Math.floor(100*count*courseRubUsd)/100 + " " + rub);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 2:
                    if (startEUR >= count)
                        if (capitalRUB >= Math.floor(100*count*courseRubEur)/100) {
                            startEUR -= count;
                            capitalEUR += count;
                            capitalRUB -= Math.floor(100*count*courseRubEur)/100;
                            startRUB += Math.floor(100*count*courseRubEur)/100;
                            System.out.println("Куплено " + count + " " + eur + " за " + Math.floor(100*count*courseRubEur)/100 + " " + rub);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 3:
                    if (startEUR >= count)
                        if (capitalUSD >= Math.floor(100*count*courseUsdEur)/100) {
                            startEUR -= count;
                            capitalEUR += count;
                            capitalUSD -= Math.floor(100*count*courseUsdEur)/100;
                            startUSD += Math.floor(100*count*courseUsdEur)/100;
                            System.out.println("Куплено " + count + " " + eur + " за " + Math.floor(100*count*courseUsdEur)/100 + " " + usd);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 4:
                    if (startUSDT >= count)
                        if (capitalUSD >= Math.floor(100*count*courseUsdUsdt)/100) {
                            startUSDT -= count;
                            capitalUSDT += count;
                            capitalUSD -= Math.floor(100*count*courseUsdUsdt)/100;
                            startUSD += Math.floor(100*count*courseUsdUsdt)/100;
                            System.out.println("Куплено " + count + " " + usdt + " за " + Math.floor(100*count*courseUsdUsdt)/100 + " " + usd);
                        } else
                            pair.getFirst(false);
                    else
                        pair.getSecond(false);
                    break;
                case 5:
                    if (startBTC >= count)
                        if (capitalUSD >= Math.floor(10000000*count*courseUsdBtc)/10000000) {
                            startBTC -= count;
                            capitalBTC += count;
                            capitalUSD -= Math.floor(100*count*courseUsdBtc)/10000000;
                            startUSD += Math.floor(100*count*courseUsdBtc)/10000000;
                            System.out.println("Куплено " + count + " " + btc + " за " + Math.floor(100*count*courseUsdBtc)/10000000 + " " + usd);
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
            if (ans.isEmpty() || ans.equals("end")) {
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else if (ans.equals("меню")) {
                menu(1);
                System.out.println("Выберите валютную пару, написав ее номер в консоль:");
            } else {
                System.out.println("Некорректный ввод. Повторите попытку.");
            }
            ans = checkInput(in.nextLine().toLowerCase().replace(',', '.'));

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
                y_n = checkInput(in.nextLine().toLowerCase().replace(',', '.'));

                int exchange;
                if (y_n.equals("1"))
                    exchange = 1;
                else if (y_n.equals("2"))
                    exchange = 2;
                else
                    continue;
                y_n = "";

                String cnt = "";
                boolean nowExchange = true;
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
                            if (printExchange(num_course, exchange) == -1)
                                nowExchange = false;

                            break;
                        default:
                            switch (cnt) {
                                case "string":
                                    System.out.println("Некорректный ввод. Необходимо ввести число");
                                    break;
                                case "negative":
                                    System.out.println("Сумма должна быть положительным числом");
                                    break;
                                case "small":
                                    System.out.println("Введена сумма менее допустимой");
                                    break;
                                case "person":
                                    System.out.println("У вас недостаточно средств для совершения обмена");
                                    break;
                                case "terminal":
                                    System.out.println("Терминалу не хватает средств для совершения обмена");
                                    break;
                                case "fail":
                                    System.out.println("Невозможно провести обмен");
                                    break label2;
                            }
                            System.out.println("Введите новую сумму:");
                    }
                    if (nowExchange)
                        cnt = checkInput(in.nextLine().toLowerCase().replace(',', '.'));
                    else
                        cnt = "fail";

                    double count;
                    try {
                        count = Double.parseDouble(cnt);
                        if (count <= 0) {
                            cnt = "negative";
                            continue;
                        } else if (count < 0.01) {
                            if (num_course == 5 && exchange == 2) {
                                if (count < 0.001) {
                                    cnt = "small";
                                    continue;
                                }
                            } else {
                                cnt = "small";
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        if (cnt.equals("end") || cnt.equals("меню") || cnt.equals("назад") || cnt.equals("выйти") || cnt.equals("fail"))
                            continue;
                        cnt = "string";
                        continue;
                    }
                    cnt = "";

                    Pair pair = exchange(exchange, num_course, count);
                    if (pair.first() && pair.second()) {
                        updateCourse();
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