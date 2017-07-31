package com.company;

import java.lang.Math;
import java.util.*;

public class Main {
    public static class city {
        public double x;
        public double y;
        public char name;

        public city(double a, double b, char n) {
            x = a;
            y = b;
            name = n;
        }
    }

    public static double distance(city a, city b) {
        double result = Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
        return result;
    }

    public static class way {
        public String up;
        public String down;
        public double distUp;
        public double distDown;
        public int size;
        public city lastUp;
        public city lastDown;

        public way() {
            up = "";
            down = "";
            distUp = 0;
            distDown = 0;
            size = 0;
            lastDown = null;
            lastUp = null;
        }

        public void addUp(city c) {
            if (lastUp != null)
                distUp += distance(lastUp, c);
            else
                distUp += Math.sqrt(c.x * c.x + c.y * c.y);
            size++;
            up += c.name;
            lastUp = c;
        }

        public void addDown(city c) {
            if (lastDown != null)
                distDown += distance(lastDown, c);
            else
                distDown += Math.sqrt(c.x * c.x + c.y * c.y);
            size++;
            down += c.name;
            lastDown = c;
        }

        public double dist() {
            return distUp + distDown;
        }

        public char lastDownName() {
            if (lastDown != null) {
                return lastDown.name;
            }
            return ' ';
        }

        public char lastUpName() {
            if (lastUp != null) {
                return lastUp.name;
            }
            return ' ';
        }
    }

    public static void setWay(way a, way b) {
        a.up = b.up;
        a.down = b.down;
        a.distUp = b.distUp;
        a.distDown = b.distDown;
        a.size = b.size;
        a.lastDown = b.lastDown;
        a.lastUp = b.lastUp;
    }

    public static String revers(String x) {
        String res = "";
        if (x.length() == 1) return "S";
        for (int i = x.length() - 2; i >= 0; i--) {
            res += x.charAt(i);
        }
        res += 'S';
        return res;
    }

    public static void main(String[] args) {
        int z, n;
        double x, y;
        Scanner scan = new Scanner(System.in);

        z = new Integer(scan.next());

        city S = new city(0, 0, 'S');
        city P;
        while (z-- > 0) {
            n = new Integer(scan.next());
            city[] tab = new city[n];
            for (int i = 0; i < n; i++) {
                x = new Double(scan.next());
                y = new Double(scan.next());
                char c = (char) (97 + i);
                tab[i] = new city(x, y, c);
            }
            x = new Double(scan.next());
            y = new Double(scan.next());
            P = new city(x, y, 'P');

            LinkedList<way> kolejka = new LinkedList<way>();
            way min = new way();
            min.addUp(tab[0]);
            kolejka.addFirst(min);
            min = null;
            int i = 1;
            while (kolejka.size() < n) {
                way first = kolejka.removeFirst();
                way tmp = new way();

                setWay(tmp, first);
                tmp.addUp(tab[i]);
                if ((tmp.lastDownName() == tab[i].name && tmp.lastUpName() == tab[i - 1].name)
                        || (tmp.lastDownName() == tab[i - 1].name && tmp.lastUpName() == tab[i].name)) {
                    if (min == null || min.dist() > tmp.dist())
                        min = tmp;
                } else {
                    kolejka.addLast(tmp);

                }

                way tmp2 = new way();
                setWay(tmp2, first);
                tmp2.addDown(tab[i]);
                if ((tmp2.lastDownName() == tab[i].name && tmp2.lastUpName() == tab[i - 1].name)
                        || (tmp2.lastDownName() == tab[i - 1].name && tmp2.lastUpName() == tab[i].name)) {

                    if (min == null || min.dist() > tmp2.dist())
                        min = tmp2;
                } else
                    kolejka.addLast(tmp2);


                if (i < kolejka.getFirst().size) {
                    kolejka.addLast(min);
                    i++;

                    min = null;
                }
            }

            min = null;
            way tmp = null;
            while (kolejka.isEmpty() == false) {
                tmp = new way();
                way top = kolejka.remove();
                if (top != null) {
                    setWay(tmp, top);

                    tmp.addDown(P);
                    tmp.addUp(P);

                    if (min == null || min.dist() > tmp.dist())
                        min = tmp;
                }
            }
            java.text.DecimalFormat df = new java.text.DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);


            System.out.format(Locale.US, "%.2f", min.dist());
            System.out.print("=");
            System.out.format(Locale.US, "%.2f", min.distUp);
            System.out.print("+");
            System.out.format(Locale.US, "%.2f", min.distDown);
            System.out.println(":S" + min.up + revers(min.down));


        }
        scan.close();
    }

}
