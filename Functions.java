//package com.team3.SWTestingTeam3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Functions {
    static ArrayList<Float> option1(float bal, float interest, float payoff){
        //Credit Card Payoff
		ArrayList<Float> results = new ArrayList<Float>();

        results.add(bal);
		results.add(payoff);
		results.add(interest);
        float payment, ppaid, interestpaid = 0;

        ppaid = bal;
        interestpaid = bal * ((interest/100));
			/*float simplePerMonth = bal/payoff;
			float tempBal = bal;
			for(float i = 0; i < payoff; i++){
				tempBal -= simplePerMonth;
				interestpaid += (tempBal)*((interest/100)/12);
				}*/
        payment = (bal + interestpaid)/payoff;
        DecimalFormat df = new DecimalFormat("##.00");
        System.out.println("Monthly Payment: $" + df.format(payment));
        System.out.println("Total Principle Paid: $" + df.format(ppaid));
        System.out.println("Total Interest Paid: $" + df.format(interestpaid));

		results.add(payment);
		results.add(ppaid);
		results.add(interestpaid);

		return results;
    }

    static ArrayList<Float> option2(float initDeposit, float monthlyContribution, float years, float interest)
    {// Simple Savings Calculator
        // INPUT: initial deposit, monthly contribution, period in years (e.g., 2.5
        // years), simple interest rate calculated yearly (APY)
        // OUTPUT: savings total, total contributions, interest earned
        interest /= 100; //convert interest
        //store inputs and outputs of function
        ArrayList<Float> io = new ArrayList<>();
        io.add(initDeposit);
        io.add(monthlyContribution);
        io.add(years);
        io.add(interest);
        // output
        float totalSavings, totalContributions, interestEarned;
        totalSavings = totalContributions = interestEarned = 0;


        float yearlyContribution = monthlyContribution * 12;

        totalSavings = initDeposit;
        float factor;
        float periodsRemaining = years;
        float eoyBal = 0; // end of year balance: Current Balance + contributions

        // For each compounding period, FORMULA: currBalance = (currBalance + contributions during the period) + (1 + interestRate)
        while (periodsRemaining > 0) {
            if (periodsRemaining > 0 && periodsRemaining < 1) // if years has a .5, need to take half its yearlyContributions and half APY interest
            {
                factor = 0.5f;
            } else {
                factor = 1;
            }
            // save totalSavings in prevBalance to help calculate interestEarned
            eoyBal = totalSavings + (yearlyContribution * factor);
            totalSavings = eoyBal * (1 + (interest * factor));
            // interest earned is what it was previously plus difference in totalSavings from interest
            interestEarned += (totalSavings - eoyBal);
            --periodsRemaining;
        }

        totalContributions = yearlyContribution * years;

        DecimalFormat df = new DecimalFormat("##.00");

        System.out.println("Total Savings: " + df.format(totalSavings));
        System.out.println("Total Contributions: " + df.format(totalContributions));
        System.out.println("Interest Earned: " + df.format(interestEarned));

        io.add(totalSavings);
        io.add(totalContributions);
        io.add(interestEarned);

        return io;
    }

    static ArrayList<Float> option3(float creditCardBalance, float creditCardInterestRate, float minPaymentPercentage) {
        ArrayList<Float> results = new ArrayList<Float>();

        results.add(creditCardBalance);
        results.add(creditCardInterestRate);
        results.add(minPaymentPercentage);

        System.out.println();

        float monthlyPayment = creditCardBalance * (minPaymentPercentage / 100);
        int numMonthsToPayOffBalance = (int) (creditCardBalance / monthlyPayment);

        float totalAmountPaid = creditCardBalance + ((creditCardInterestRate / 100) * creditCardBalance);

        DecimalFormat df = new DecimalFormat("##.00");
        System.out.println("Monthly payment: " + df.format(monthlyPayment));
        System.out.println("Number of months to pay off balance: " + numMonthsToPayOffBalance);
        System.out.println("Total amount paid: " + df.format(totalAmountPaid));

        results.add(monthlyPayment);
        results.add((float) numMonthsToPayOffBalance);
        results.add(totalAmountPaid);

        return results;
    }

    static ArrayList<Float> option4(float deposit, float period, float apy) {
        ArrayList<Float> results = new ArrayList<Float>();

        results.add(deposit);
        results.add(period);
        results.add(apy);

        float total = deposit * (float) Math.pow((1 + (apy / 100)), period);
        float interest = total - deposit;

        DecimalFormat df = new DecimalFormat("##.00");
        System.out.println("Total Balance: $" + df.format(total));
        System.out.println("Interest Earned: $" + df.format(interest));

        results.add(total);
        results.add(interest);

        return results;
    }


    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int option;
        badInput:
        while(true){
            System.out.println("Please enter your menu option: \n");
            System.out.print("1. Credit Card Payoff \n");
            System.out.print("2. Simple Savings Calculator \n");
            System.out.print("3. Credit Card Minimum Payment Calculator \n");
            System.out.print("4. CD Calculator \n");
            System.out.print("5. Exit \n");
            try{
                option = input.nextInt();
            }
            catch(Exception e){
                System.out.println("Not a valid option. Please try again.");
                input.next();
                continue badInput;
            }

            switch(option){
                case 1:
					float bal, interest, payoff;

			        while(true){
                        System.out.println("Please enter CC balance: " );
                        try{
                            bal = input.nextInt();
                            if(bal < 0) throw new Exception();
                            break;
                        }catch(Exception e){
                            System.out.println("Invalid input. Input balance must be numerical and greater than 0.");
                            input.nextLine();
                        }
                    }

			        while(true){
                        System.out.println("Please enter CC interest rate (per year): ");
                        try{
                            interest = input.nextFloat();
                            if(interest < 0 || interest > 100) throw new Exception();
                            break;
                        }catch(Exception e){
                            System.out.println("Invalid input. Input balance must be numerical and between 0-100.");
                            input.nextLine();
				        }
                    }

			        while(true){
                        System.out.println("Please enter desired months to payoff: ");
                        try{
                            payoff = input.nextInt();
                            if(payoff < 1) throw new Exception();
                            break;
                        }catch(Exception e){
                            System.out.println("Invalid input. Input balance must be numerical and greater than or equal to 1.");
                            input.nextLine();
                        }
                    }

                    option1(bal, interest, payoff);
                    break;

                case 2:
                    // input
                    float initDeposit, monthlyContribution, years, interestRate;
                    initDeposit = monthlyContribution = years = interestRate = 0;
                    // Scanner input = new Scanner(System.in);
                    boolean valid = false;
                    System.out.println("Please enter initial deposit: ");
                    do {
                        try {
                            initDeposit = input.nextFloat();
                            if (initDeposit < 0) {
                                System.out.println("Must enter a positive value! Try again.");
                                valid = false;
                            } else {
                                valid = true;
                            }
                        } catch (InputMismatchException e) { // catch non-float input
                            System.out.println("Invalid input. Must enter a float.");
                            input.nextLine(); // have to add this empty nextLine to consume the left over new line
                        }
                    } while (!valid); // The loop ends when a valid float is got from the user
                    valid = false; // reset value for next loop

                    System.out.println("Please enter monthly contribution: ");
                    do {
                        try {
                            monthlyContribution = input.nextFloat();
                            if (monthlyContribution < 0) {
                                System.out.println("Must enter a positive value! Try again.");
                                valid = false;
                            } else {
                                valid = true;
                            }
                        } catch (InputMismatchException e) { // catch non-float input
                            System.out.println("Invalid input. Must enter a float.");
                            input.nextLine(); // have to add this empty nextLine to consume the left over new line
                        }
                    } while (!valid); // The loop ends when a valid float is got from the user
                    valid = false; // reset value for next loop

                    System.out.println("Please enter period in years (can enter .5 years): "); // validation a bit different
                    do {
                        try {
                            years = input.nextFloat();
                            if (years % 0.5 == 0) // check to see if divisible by 0.5
                            {
                                valid = true;
                            } else {
                                System.out.println("Period (years) must be a factor of 0.5! Ex: 3 or 2.5 or 3.0. Try again.");
                                valid = false;
                            }
                            if (years <= 0) {
                                System.out.println("The period cannot be a negative value! Try again.");
                                valid = false;
                            }
                        } catch (InputMismatchException e) { // catch non-float input
                            System.out.println("Invalid input. Must enter a float.");
                            input.nextLine(); // have to add this empty nextLine to consume the left over new line
                        }
                    } while (!valid); // The loop ends when a valid float is got from the user
                    valid = false;

                    System.out.println("Please enter APY interest rate as a percent: ");
                    do {
                        try {
                            interestRate = input.nextFloat();
                            if (interestRate >= 0 && interestRate <= 100) // must be between 0 and 100
                            {
                                valid = true;
                            } else {
                                System.out.println("Interest rate must be between 0 and 100! Try again.");
                                valid = false;
                            }
                        } catch (InputMismatchException e) { // catch non-float input
                            System.out.println("Invalid input. Must enter a float.");
                            input.nextLine(); // have to add this empty nextLine to consume the left over new line
                        }
                    } while (!valid); // The loop ends when a valid float is got from the user
                    option2(initDeposit, monthlyContribution, years, interestRate);
                    break;
                case 3:
                    float creditCardBalance = 0;
                    float creditCardInterestRate = 0;
                    float minPaymentPercentage = 0;

                    Scanner scnr = new Scanner(System.in);

                    while (true){
                        System.out.print("Please enter credit card balance: ");
                        float userInput = scnr.nextFloat();
                        if (userInput < 0.00) {
                            System.out.println("Card can not have negative balance. Please try again.");
                            continue;
                        }
                        else {
                            creditCardBalance = userInput;
                            break;
                        }
                    }

                    while (true){
                        System.out.print("Please enter credit card interest rate: ");
                        float userInput = scnr.nextFloat();
                        if (userInput < 0.00 || userInput > 100.00) {
                            System.out.println("Interest rate must be between 0.0 and 100.0. Please try again.");
                            continue;
                        }
                        else {
                            creditCardInterestRate = userInput;
                            break;
                        }
                    }

                    while (true){
                        System.out.print("Please enter minimum payment percentage: ");
                        float userInput = scnr.nextFloat();
                        if (userInput < 0.00 || userInput > 100.00) {
                            System.out.println("Minimum payment percentage must be between 0.0 and 100.0. Please try again.");
                            continue;
                        }
                        else {
                            minPaymentPercentage = userInput;
                            break;
                        }
                    }

                    option3(creditCardBalance, creditCardInterestRate, minPaymentPercentage);
                    break;
                case 4:
                    float deposit, apy;
                    int period;

                    System.out.print("Please enter an inital deposit in US Dollars ($): ");
                    while (true){
                        try{
                            deposit = input.nextFloat();
                            if (deposit < 0) { throw new Exception(); }
                            break;
                        }catch (InputMismatchException e){
                            System.out.print("Invalid input! Please enter an inital deposit in US Dollars ($) with only numerical characters: ");
                            input.nextLine();
                        }catch (Exception e){
                            System.out.print("Invalid input! Please enter an inital deposit in US Dollars ($) that is non-negative: ");
                            input.nextLine();
                        }
                    }
                    System.out.println("Please enter a period in years: ");
                    while (true){
                        try{
                            period = input.nextInt();
                            if (period < 1 || (Math.floor(period) != period)) { throw new Exception(); }
                            break;
                        }
                        catch (InputMismatchException e){
                            System.out.print("Invalid input! Please enter a period in years with only numerical characters: ");
                            input.nextLine();
                        }catch (Exception e){
                            System.out.print("Invalid input! Please enter a period in years as a positive integer: ");
                            input.nextLine();
                        }
                    }
                    System.out.println("Please enter an Annual Percentage Yield as a percent (%): ");
                    while (true){
                        try{
                            apy = input.nextFloat();
                            if (apy > 100 || apy < 0) { throw new Exception(); }
                            break;
                        }catch (InputMismatchException e){
                            System.out.print("Invalid input! Please enter an Annual Percentage Yield as a percent (%) with only numerical characters: ");
                            input.nextLine();
                        }catch (Exception e){
                            System.out.print("Invalid input! Please enter an Annual Percentage Yield as a percent (%) with values ranging only from 0 to 100: ");
                            input.nextLine();
                        }
                    }

                    option4(deposit, period, apy);
                    break;
                case 5:
                    System.out.println("Exiting");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Not a valid option. Please try again.");
                    break;
            }
        }
    }
}
