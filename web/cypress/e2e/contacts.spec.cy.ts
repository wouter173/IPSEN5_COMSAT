describe('Contacts', () => {
  it('Navigates to Contacts', () => {
    cy.login('admin@gmail.com', 'admin');
    cy.wait(300);

    cy.contains('Contacts').click();
  });
});
