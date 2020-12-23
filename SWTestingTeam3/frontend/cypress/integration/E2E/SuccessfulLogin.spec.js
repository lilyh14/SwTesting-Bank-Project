describe("Happy Path", () => {
    it("Successful login", () => {
        cy.visit("/");
        cy.get("form");

        cy.get('input[name="userEmail"]')
            .type("newuser2@gmail.com")
            .should("have.value", "newuser2@gmail.com");

        cy.get('input[name="userPassword"]')
            .type("newuser2")
            .should("have.value", "newuser2");

        cy.get('#signin').click();

        cy.contains("dashboard");

    });
    it("Deposit funds", () => {
        //cy.visit("/");
        //cy.get("form");

        cy.get('input[name="accountID"]')
            .type("GgjAs6W9cuFpx58MTYEG")
            .should("have.value", "GgjAs6W9cuFpx58MTYEG");

        cy.get('input[name="depositAmount"]')
            .type("1000")
            .should("have.value", "1000");

        cy.get('input[name="sourceID"]')
            .type("akLJWinfn24nK")
            .should("have.value", "akLJWinfn24nK");

        cy.get('#depositSubmit').click();

        cy.visit("/");

    });
    it("Transfer funds", () => {
        //cy.visit("/");
        //cy.get("form");

        cy.get('input[name="accountFrom"]')
            .type("GgjAs6W9cuFpx58MTYEG")
            .should("have.value", "GgjAs6W9cuFpx58MTYEG");

        cy.get('input[name="accountTo"]')
            .type("oxe2JysdFUQzMKr0IuV9")
            .should("have.value", "oxe2JysdFUQzMKr0IuV9");

        cy.get('input[name="amount"]')
            .type("1000")
            .should("have.value", "1000");

        cy.get('#transferSubmit').click();

        cy.visit("/");

    });
    it("Error on insufficient funds", () => {
        //cy.visit("/");
        //cy.get("form");

        cy.get('input[name="accountFrom"]')
            .type("oxe2JysdFUQzMKr0IuV9")
            .should("have.value", "oxe2JysdFUQzMKr0IuV9");

        cy.get('input[name="accountTo"]')
            .type("GgjAs6W9cuFpx58MTYEG")
            .should("have.value", "GgjAs6W9cuFpx58MTYEG");

        cy.get('input[name="amount"]')
            .type("100000")
            .should("have.value", "100000");

        cy.get('#transferSubmit').click();

        cy.on('window:alert', (str) => {
            expect(str).to.equal(`Error: Funds have not been transferred.`)
        })

        cy.wait(4000);
        // cy.visit("/");

    });
    it("Do correct Credit Card Payoff", () => {
        //cy.visit("/");
        //cy.get("form");

        cy.get('input[name="creditCardBalance"]')
            .type("10000")
            .should("have.value", "10000");

        cy.get('input[name="creditCardInterestRate"]')
            .type("10")
            .should("have.value", "10");

        cy.get('input[name="numMonthsToPayOff"]')
            .type("10")
            .should("have.value", "10");

        cy.get('#CCPCalculate').click();

        cy.on('window:alert', (str) => {
            expect(str).to.equal(`Monthly Payment: $1100\nTotal Principle Paid: $10000\nTotal Interest Paid: $1000`)
        })

        cy.wait(4000);
        // cy.visit("/");

    });
    it("Do correct Simple Savings", () => {
        //cy.visit("/");
        //cy.get("form");

        cy.get('input[name="initialDeposit"]')
            .type("100000")
            .should("have.value", "100000");

        cy.get('input[name="monthlyContribution"]')
            .type("100")
            .should("have.value", "100");

        cy.get('input[name="years"]')
            .type("10")
            .should("have.value", "10");

        cy.get('input[name="simpleInterest"]')
            .type("10")
            .should("have.value", "10");

        cy.get('#SSCalculate').click();

        cy.on('window:alert', (str) => {
            expect(str).to.equal(`Total Savings: $21037.402\nTotal Contributions: $12000\nInterest Earned: $9037.402`)
        })
        cy.wait(4000);
        // cy.visit("/");

    });
    it("Sign Out Successfully", () => {


        cy.get('#signout').click();

        cy.wait(4000);
        // cy.visit("/");

    });
    it("Successful Admin login", () => {
        cy.visit("/");
        cy.get("form");

        cy.get('input[name="userEmail"]')
            .type("admin@test.com")
            .should("have.value", "admin@test.com");

        cy.get('input[name="userPassword"]')
            .type("adminadmin")
            .should("have.value", "adminadmin");

        cy.get('#signin').click();

        cy.contains("API Log");

    });
});