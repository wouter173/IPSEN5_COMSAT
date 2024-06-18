describe('Batch', () => {
  it('Navigates to Batches', () => {
    cy.login('admin@gmail.com', 'admin');
    cy.wait(300);

    cy.contains('Batches').click();
    cy.get('app-batch-list-item').first().click();

    for (let i = 0; i < 1000; i++) {
      cy.scrollTo(0, i);
      cy.wait(10);
    }
  });
});
