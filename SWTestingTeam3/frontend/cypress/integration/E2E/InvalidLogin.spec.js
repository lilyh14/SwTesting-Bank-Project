describe("Negative/error case", () => {
    it("Attempt an invalid login and expect error", () => {
        cy.visit("/");
        cy.get("form");

        cy.get('input[name="userEmail"]')
            .type("invaliduser@email.com")
            .should("have.value", "invaliduser@email.com");

        cy.get('input[name="userPassword"]')
            .type("invalidPassword")
            .should("have.value", "invalidPassword");

        cy.get('#signin').click();

        cy.get('#errorMsg').contains("Error signing in with password and email!");

    });
});