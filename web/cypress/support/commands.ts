Cypress.Commands.add(
  'bypassLogin',
  {
    prevSubject: 'optional',
  },
  () => {
    cy.session([], () => {
      cy.intercept('http://localhost:8080/api/v1/auth/login', {
        fixture: 'login-success.json',
      });
      cy.visit('/login');
      cy.get('input[name="username"]').type('a');
      cy.get('input[name="password"]').type('b');
      cy.get('button[type="submit"]').click();
      cy.wait(500);
      // cy.get('.toast-message').should('be.visible');
      cy.location().should((location) => {
        expect(location.pathname).to.equal('/');
      });
    });
  }
);
