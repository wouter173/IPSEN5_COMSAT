describe('Login Test', () => {
  it('Logs in with valid credentials', () => {
    // Visit the login page
    cy.visit('/login');

    // Fill in the email field
    cy.get('input[name=username]').type('admin@gmail.com');

    // Fill in the password field
    cy.get('input[name=password]').type('admin');

    // Submit the form
    cy.get('form').submit();

    // Check that we have been redirected to the home page
    cy.url().should('include', '/');
  });
});
