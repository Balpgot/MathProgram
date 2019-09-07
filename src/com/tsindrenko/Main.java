package com.tsindrenko;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<List<Integer>> mul(List<List<Integer>> f, List<List<Integer>> g, boolean print){
        List<List<Integer>> result = new ArrayList<>();
        result.add(f.get(0));
        result.add(new ArrayList<>());
        for(int i = 0; i<f.get(0).size(); i++){
            result.get(1).add(g.get(1).get(g.get(0).indexOf(f.get(1).get(i))));
        }
        if(print){
            for(int i = 0; i<result.get(0).size(); i++) {
                System.out.print(result.get(0).get(i) + " ");
            }
            System.out.println();
            for(int i = 0; i<result.get(0).size(); i++) {
                System.out.print(result.get(1).get(i) + " ");
            }
            System.out.println('\n');
        }
        return result;
    }

    public static String transposition(String cycles){
        String [] podstanovki = cycles.split(" ");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<podstanovki.length; i++){
            for(int j = 2; j<podstanovki[i].length()-1; j++){
                builder.append("(");
                builder.append(podstanovki[i].charAt(1));
                builder.append(podstanovki[i].charAt(j));
                builder.append(") ");
            }
        }
        return builder.toString();
    }

    public static List<List<List<Integer>>> full_cycles(String cycles){
        List<List<List<Integer>>> result = new ArrayList<>();
        //получаем подстановки из строки
        String [] podstanovki = cycles.split(" ");
        //Добавляем нужное количество подстановок
        for(int i = 0; i<podstanovki.length; i++){
            result.add(new ArrayList<>());
            result.get(i).add(new ArrayList<>());
            result.get(i).add(new ArrayList<>());
        }
        //проходим по каждой скобке
        for(int i = 0; i<podstanovki.length; i++){
            //идем до предпоследнего
            for(int j = 1; j<podstanovki[i].length()-2; j++) {
                //добавляем в первую строку
                result.get(i).get(0).add(Character.getNumericValue(podstanovki[i].charAt(j)));
                //добавляем во вторую строку
                result.get(i).get(1).add(Character.getNumericValue(podstanovki[i].charAt(j+1)));
            }
            //добавляем последний элемент
            result.get(i).get(0).add(Character.getNumericValue(podstanovki[i].charAt(podstanovki[i].length()-2)));
            //во вторую строку добавляем элемент с которого начался цикл
            result.get(i).get(1).add(result.get(i).get(0).get(0));
        }
        //чтобы получить подстановку нужно выполнить result.get()
        return result;
    }

    private static List<List<Integer>> ob(List<List<Integer>> f){
        List<List<Integer>> ob = new ArrayList<>();
        ob.add(f.get(1));
        ob.add(f.get(0));
        return ob;
    }

    private static int inversion(List<List<Integer>> f){
        int result = 0;
        for(int i = 0; i<f.get(0).size()-1; i++){
            for(int j = i+1; j<f.get(0).size(); j++){
                if((f.get(0).get(i)<f.get(0).get(j) && f.get(1).get(i)>f.get(1).get(j))||(f.get(0).get(i)>f.get(0).get(j) && f.get(1).get(i)<f.get(1).get(j))){
                    result++;
                }
            }
        }
        return result;
    }

    private static String cycles(List<List<Integer>> f) {
        int current_number = 0;
        int buff=-1;
        int cycle_start = 0;
        int por_cycle;
        List<Integer> por = new ArrayList<>();
        StringBuilder first_row = new StringBuilder();
        first_row.append("(");
        StringBuilder second_row = new StringBuilder();
        second_row.append("(");
        List<List<Integer>> working = new ArrayList<>();
        working.add(new ArrayList<>());
        working.add(new ArrayList<>());
        for(int i = 0; i<f.get(0).size(); i++){
            working.get(0).add(f.get(0).get(i));
            working.get(1).add(f.get(1).get(i));
        }
        boolean trigger = true;
        while (working.get(0).size()>0){
            por_cycle = 0;
            cycle_start = working.get(0).get(0);
            current_number = working.get(0).get(0);
            trigger = true;
            while (buff!=cycle_start) {
                if(!trigger) {
                    current_number = buff;
                }
                buff = working.get(1).get(working.get(0).indexOf(current_number));
                first_row.append(current_number);
                second_row.append(buff);
                working.get(0).remove(working.get(0).indexOf(current_number));
                working.get(1).remove(working.get(1).indexOf(buff));
                por_cycle++;
                trigger = false;
            }
            first_row.append(") (");
            second_row.append(") (");
            por.add(por_cycle);
        }
        first_row.deleteCharAt(first_row.lastIndexOf("("));
        System.out.println(first_row.toString());
        while(por.size()>1){
            por.set(0,Math.abs(por.get(0)*por.get(1))/gcd(por.get(0),por.get(1)));
            por.remove(1);
        }
        System.out.println("Порядок: " + por.get(0)+'\n');
        return first_row.toString();
    }

    private static int gcd(int a,int b) {
        while (b !=0) {
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static void main(String[] args) {
        int step = 100;
        int startN;
        List<List<Integer>> f = new ArrayList<>();
        f.add(new ArrayList<>());
        f.add(new ArrayList<>());
        List<List<Integer>> g = new ArrayList<>();
        g.add(new ArrayList<>());
        g.add(new ArrayList<>());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество элементов");
        int N = scanner.nextInt();
        System.out.println("С какого числа начинается первая строка? 0 или 1");
        startN = scanner.nextInt();
        //System.out.println("Введите элементы первых строк f и g");
        for(int i = 0; i<N; i++){
            if(startN==0){
                f.get(0).add(i);
                g.get(0).add(i);

            }
            else{
                f.get(0).add(i+1);
                g.get(0).add(i+1);
            }

        }

        System.out.println("Введите элементы второй строки строки f");
        for(int i = 0; i<N; i++){
            f.get(1).add(scanner.nextInt());
        }

        /*System.out.println("Введите элементы первой строки g");
        for(int i = 0; i<N; i++){
            g.get(0).add(scanner.nextInt());
        }*/

        System.out.println("Введите элементы второй строки строки g");
        for(int i = 0; i<N; i++){
            g.get(1).add(scanner.nextInt());
        }

        System.out.println("Номер 1");
        System.out.println("f * g\n");
        mul(f,g,true);
        System.out.println("g * f\n");
        mul(g,f,true);
        System.out.println("Номер 2");
        System.out.println("f^2\n");
        mul(f,f,true);
        System.out.println("f^3\n");
        mul(mul(f,f,true),f,true);
        System.out.println("Номер 3");
        System.out.println("f*u=g\n");
        mul(ob(f),g,true);
        System.out.println("u*g=f\n");
        mul(f,ob(g),true);
        System.out.println("Номер 4");
        int invf = inversion(f);
        if(invf%2 == 0){
            System.out.print("Число инверсий: ");
            System.out.println(invf);
            System.out.println("f - чётная\n");
        }
        else{
            System.out.print("Число инверсий: ");
            System.out.println(invf);
            System.out.println("f - нечётная\n");
        }

        int invg = inversion(g);
        if(invg%2 == 0){
            System.out.print("Число инверсий: ");
            System.out.println(invg);
            System.out.println("g - чётная\n");
        }
        else{
            System.out.print("Число инверсий: ");
            System.out.println(invg);
            System.out.println("g - нечётная\n");
        }

        System.out.println("Номер 5 и 6");
        System.out.println("Разложение f\n");
        cycles(f);
        System.out.println("Разложение g\n");
        cycles(g);
        System.out.println("Номер 7");
        //System.out.println("Введите степень");
        //step = scanner.nextInt();
        List<List<Integer>> buff;
        buff = mul(f,g,false);
        List<List<Integer>> fg;
        fg = mul(f,g,false);
        for(int i = 1; i<step; i++){
            if(i!=step-1)
                buff = mul(buff,fg,false);
            else
                buff = mul(buff,fg,true);
        }
        List<List<List<Integer>>> r = full_cycles(cycles(f));
        List<List<Integer>> buffer;
        for(int i = 0; i<r.size(); i++){
            buffer = r.get(i);

            for(int j = 0; j<buffer.get(0).size(); j++) {
                System.out.print(buffer.get(0).get(j) + " ");
            }
            System.out.println();
            for(int j = 0; j<buffer.get(0).size(); j++) {
                System.out.print(buffer.get(1).get(j) + " ");
            }
            System.out.print('\n');
        }
        System.out.println(transposition(cycles(f)));
    }
}