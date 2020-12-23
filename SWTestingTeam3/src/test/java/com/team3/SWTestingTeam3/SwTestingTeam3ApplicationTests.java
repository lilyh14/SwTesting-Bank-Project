package com.team3.SWTestingTeam3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Objects;
import java.text.DecimalFormat;

@SpringBootTest
class SwTestingTeam3ApplicationTests {

  @Test
  void contextLoads() {}

  // Option 1 Tests   //////////////////////////////////////////////////
  @Test
  void option1_NegativeInput() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(results.get(0) >= 0, "Credit card balance may not be less than 0");
  }

  @Test
  void option1_ValidPercentRange() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(
        results.get(1) >= 0 && results.get(1) <= 100,
        "Credit card interest rate range is between 0-100%");
  }

  @Test
  void option1_ValidMonthInput() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(
        results.get(2) >= 1, "Desired number of months to pay off needs to be at least 1");
  }

  @Test
  void option1_OutputCorrectMonthlyPayment() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(results.get(3) == 33.75, "Output correct monthly payment");
  }

  @Test
  void option1_OutputCorrectPP() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(results.get(4) >= 120, "Output correct principle paid");
  }

  @Test
  void option1_OutputCorrectIP() {
    Functions functions = new Functions();
    ArrayList<Double> results = functions.option1(120, 12.5, 4);
    Assertions.assertTrue(results.get(5) == 15, "Output correct total interest paid");
  }

  // Option2 ArrayList : initDeposit, monthlyContributions, years, interest, totalSavings,
  // totalContributions, interestEarned
  @Test
  void option2_initDepositMustBePositive() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5);
    Assertions.assertTrue(results.get(0) > 0, "Initial deposit should be positive");
  }

  @Test
  void option2_monthlyContributionsMustBePositive() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5);
    Assertions.assertTrue(results.get(1) > 0, "Monthly contributions should be positive");
  }

  @Test
  void option2_APYInterestRateMustBeValidPercentage() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5);
    Assertions.assertTrue(
        results.get(3) >= 0 && results.get(3) <= 100, "APY interest rate must be a valid percent");
  }

  @Test
  void option2_periodMustBePositive() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5);
    Assertions.assertTrue(results.get(2) > 0, "Period(years) must be positive");
  }

  @Test
  void option2_periodMustBeFactor() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5.5f);
    Assertions.assertTrue(results.get(2) % 0.5 == 0, "Period(years) must be a factor of 0.5");
  }

  @Test
  void option2_correctTotalSavings() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 10, 1, 10);
    Assertions.assertTrue(
        results.get(4) == 242.00, "The calculated total savings must be accurate");
  }

  @Test
  void option2_correctTotalContributions() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 50, 5, 5);
    Assertions.assertTrue(results.get(5) == 3000.00, "The total contributions must be accurate");
  }

  @Test
  void option2_correctInterestEarned() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option2(100, 10, 1, 10);
    Assertions.assertTrue(
        results.get(6) == 22.00, "The calculated interest earned must be accurate");
  }

  // Option 3 Tests  //////////////////////////////////////////////////
  @Test
  void option3_creditCardBalanceMustBePositive() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(results.get(0) > 0, "credit card balance should be positive");
  }

  @Test
  void option3_creditCardInterestRateMustBeValidPercentage() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(
        results.get(1) > 0 && results.get(1) < 100,
        "credit card interest rate should be a valid percentage");
  }

  @Test
  void option3_minimumPaymentPercentageMustBeValidPercentage() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(
        results.get(2) > 0 && results.get(2) < 100,
        "minimum payment rate should be a valid percentage");
  }

  @Test
  void option3_correctMonthlyPayment() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(results.get(3) == 5.00, "monthly payment value should be correct");
  }

  @Test
  void option3_correctNumberOfMonthsToPayOffBalance() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(
        results.get(4) == 20, "number of months to pay off balance should be correct");
  }

  @Test
  void option3_correctTotalAmountPaid() {
    Functions functions = new Functions();
    ArrayList<Float> results = functions.option3(100, 5, 5);
    Assertions.assertTrue(results.get(5) == 105.00, "total amount paid should be correct");
  }

  // Option 4 Tests  //////////////////////////////////////////////////
  @Test
  void option4_isDepositPositive() {
    ArrayList<Float> results = Functions.option4(10000, 5, 2.3f);
    Assertions.assertTrue(results.get(0) >= 0, "initial deposit should be positive");
  }

  @Test
  void option4_isPeriodPositive() {
    ArrayList<Float> results = Functions.option4(10000, 5, 2.3f);
    Assertions.assertTrue(results.get(1) >= 0, "period should be positive");
  }

  @Test
  void option4_isAPYValid() {
    ArrayList<Float> results = Functions.option4(10000, 5, 2.3f);
    Assertions.assertTrue(
        results.get(2) >= 0 && results.get(2) <= 100, "APY should be within the range [0-100]");
  }

  @Test
  void option4_isBalanceCorrect() {
    ArrayList<Float> results = Functions.option4(10000, 5, 2.3f);
    Assertions.assertEquals(
        results.get(3).toString(), "11204.13", "total balance should be calculated correctly");
    // Lily changed indexing - skipped 3..
  }

  @Test
  void option4_isInterestCorrect() {
    ArrayList<Float> results = Functions.option4(10000, 5, 2.3f);
    Assertions.assertEquals(
        results.get(4).toString(),
        "1204.13",
        "total interest earned should be calculated correctly");
  }
}
