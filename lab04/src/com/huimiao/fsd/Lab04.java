package com.huimiao.fsd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: Lab04
 * @Description: TODO
 * @author: huimiao
 * @date: 7/25/2019 8:59 PM
 * @version: 1.0
 */
public class Lab04 {
    private static int ONE_YEAR = 12;

    private SalaryInfo buildSalaryInfo() throws Exception {
        String baseSalaryStr,
                incPercentStr,
                dedPercentStr,
                incFrequencyStr,
                dedFrequencyStr,
                yearStr;
        float baseSalary, incPercent, dedPercent;
        int incFrequency, dedFrequency, year;

        baseSalaryStr = Util.readValidData("The starting salary: ", Util::isPositiveNum);
        incPercentStr = Util.readValidData("Increment to be received in percent: ", Util::isPositiveNum);
        incFrequencyStr = Util.readValidData("How frequently is increment received(Every x months): ", Util::isValidFrequence);
        dedPercentStr = Util.readValidData("Deductions on income in percent: ", Util::isPositiveNum);
        dedFrequencyStr = Util.readValidData("How frequently are deductions done(Every x months): ", Util::isValidFrequence);
        yearStr = Util.readValidData("Prediction for (years): ", Util::isPositiveNum);

        baseSalary = Float.valueOf(baseSalaryStr);
        incPercent = Float.valueOf(incPercentStr);
        incFrequency = Integer.valueOf(incFrequencyStr);
        dedPercent = Float.valueOf(dedPercentStr);
        dedFrequency = Integer.valueOf(dedFrequencyStr);
        year = Integer.valueOf(yearStr);

        return new SalaryInfo(baseSalary, incFrequency, dedFrequency, incPercent, dedPercent, year);
    }

    private List<float[]> generatePlan(final SalaryInfo salaryInfo) {
        int totalMonth = ONE_YEAR * salaryInfo.getYear();
        List<float[]> plans = Stream.iterate(0, n -> n + 1).limit(totalMonth).map(n -> {
            float[] plan = {n, 0, 0};
            return plan;
        }).collect(toList());

        for (int i = 0; i < totalMonth; i++) {
            if ((i + 1) % salaryInfo.getIncFrequency() == 0) {
                plans.get(i)[1] = salaryInfo.getIncPercent();
            }
        }

        for (int i = 0; i < totalMonth; i++) {
            if ((i + 1) % salaryInfo.getDedFrequency() == 0) {
                plans.get(i)[2] = salaryInfo.getDedPercent();
            }
        }

        plans = plans.stream().filter(p -> p[1] != 0 || p[2] != 0).collect(toList());

        return plans;
    }


    private List<Report> generateReports(final SalaryInfo salaryInfo) {
        List<float[]> plans = generatePlan(salaryInfo);
        List<Report> reports = new ArrayList<>(salaryInfo.getYear());

        for (int i = 0; i < salaryInfo.getYear(); i++) {
            int j = i;
            Report report = new Report();
            report.setYear(i + 1);

            if (reports.size() == 0) {
                report.setStartingSalary(salaryInfo.getBaseSalary());
                report.setYearEndSalary(salaryInfo.getBaseSalary());
            } else {
                report.setStartingSalary(reports.get(i - 1).getYearEndSalary());
                report.setYearEndSalary(reports.get(i - 1).getYearEndSalary());
            }

            plans.stream()
                    .filter(n -> n[0] > j * ONE_YEAR && n[0] <= (j + 1) * ONE_YEAR)
                    .forEach(
                            n -> {
                                if (n[1] != 0) {
                                    report.setNumOfInc(report.getNumOfInc() + 1);
                                    report.setAmountOfInc(report.getAmountOfInc() + report.getYearEndSalary() * salaryInfo.getIncPercent() / 100);
                                    report.setYearEndSalary(report.getYearEndSalary() + report.getYearEndSalary() * salaryInfo.getIncPercent() / 100);
                                }

                                report.setIncPercent(report.getAmountOfInc() / report.getStartingSalary() * 100);

                                if (n[2] != 0) {
                                    report.setNumOfDed(report.getNumOfDed() + 1);
                                    report.setAmountOfDed(report.getAmountOfDed() + report.getYearEndSalary() * salaryInfo.getDedPercent() / 100);
                                    report.setYearEndSalary(report.getYearEndSalary() - report.getYearEndSalary() * salaryInfo.getDedPercent() / 100);
                                }

                                report.setDedPercent(report.getAmountOfDed() / report.getStartingSalary() * 100);
                            }
                    );

            reports.add(report);
        }

        return reports;
    }


    private void printReports(final List<Report> reports, String title, String head, Consumer<Report> c) {
        System.out.println(title);
        System.out.println(head);
        reports.stream().forEach(i -> c.accept(i));
    }

    public static void main(String[] args) throws Exception {
        Lab04 lab04 = new Lab04();

        SalaryInfo salaryInfo = lab04.buildSalaryInfo();

        List<Report> reports = lab04.generateReports(salaryInfo);

        lab04.printReports(reports,
                "a. Increment Report",
                String.format("%1$-10s%2$-30s%3$-30s%4$-30s%5$-30s\n",
                        "Year",
                        "Starting Salary",
                        "Number of Increments",
                        "Increment % ",
                        "Increment Amount"),
                i -> System.out.printf("%1$-10d%2$-30.2f%3$-30d%4$-30.2f%5$-30.2f\r\n",
                        i.getYear(),
                        i.getStartingSalary(),
                        i.getNumOfInc(),
                        i.getIncPercent(),
                        i.getAmountOfInc()));

        lab04.printReports(reports,
                "\nb. Deduction Report",
                String.format("%1$-10s%2$-30s%3$-30s%4$-30s%5$-30s\n",
                        "Year",
                        "Starting Salary",
                        "Number of deductions",
                        "Deduction % ",
                        "Deduction Amount"),
                i -> System.out.printf("%1$-10d%2$-30.2f%3$-30d%4$-30.2f%5$-30.2f\r\n",
                        i.getYear(),
                        i.getStartingSalary(),
                        i.getNumOfDed(),
                        i.getDedPercent(),
                        i.getAmountOfDed()));


        lab04.printReports(reports,
                "\nc. Prediction",
                String.format("%1$-10s%2$-30s%3$-30s%4$-30s%5$-30s\n",
                        "Year",
                        "Starting Salary",
                        "Increment Amount",
                        "Deduction Amount ",
                        "Salary Growth"),
                i -> System.out.printf("%1$-10d%2$-30.2f%3$-30.2f%4$-30.2f%5$-30.2f\n",
                        i.getYear(),
                        i.getStartingSalary(),
                        i.getAmountOfInc(),
                        i.getAmountOfDed(),
                        i.getSalaryGrowth()));
    }
}
