describe('Reports', () => {
  it('Navigates to reports', () => {
    cy.login('admin@gmail.com', 'admin');
    cy.wait(1000);
    cy.contains('General').click();
    cy.wait(3000);
    cy.contains('Batch-1').click();
    cy.wait(3000);
  });
});
